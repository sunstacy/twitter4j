/** 
* Copyright (c) 2013, Danny gao
* All rights reserved.
*
* @author  : Dehong Gao E-mail:gaodehong_polyu@163.com
* @version :
* @Function: Stream based tweet retrieval 
* 			 This is a great API!!
*/
package gh.polyu.twitter4j;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.database.databasehandle_core;
import gh.polyu.twittercore._TweetLink;
import gh.polyu.za.ChangeDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StallWarning;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;


public class TweetStream {
	//WTwitterDBHandle handle = new TwitterDBHandle();
	int re =0;


	public void GetTweetFromStream()
	{
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterOAuth twtOauth = new twitterOAuth(); 
		  twtOauth.ReadProperties("properties/twitter4j.properties");
		  twtOauth.Authority(twitterStream);
		  final TwitterDBHandle handle = new TwitterDBHandle();
			handle.intialTwitterDBhandle();
	        
	       
		StatusListener listener = new StatusListener() {
			int cnt = 0;
			ArrayList<_TweetLink> listlink = new ArrayList<_TweetLink>();
            @Override
            public void onStatus(Status status) {
            	if(status.getLang().equals("en"))
            	{
            	_TweetLink tweet = new _TweetLink();
            	String Test = status.getText();
            	tweet.setText(Test);
            	Date time = status.getCreatedAt();
            	tweet.setTime(time);
            	//System.out.println("Date: "+time);
            	//hashtag
            	HashtagEntity[] hashtagentity = status.getHashtagEntities();
            	StringBuffer hashen = new StringBuffer();
            	for(int i =0; i< hashtagentity.length;i++)
            	{
            		hashen.append(hashtagentity[i].getText());
            		hashen.append(";");
            		
            	}
            	tweet.setHashtag(hashen.toString());

            	URLEntity[] URLEn = status.getURLEntities();
            	StringBuffer URL = new StringBuffer();
            	for(int i =0; i< URLEn.length;i++)
            	{
            		URL.append(URLEn[i].getURL());
            		URL.append(";");
            		
            	}
            	tweet.setURL(URL.toString());

            	//user mention
            	UserMentionEntity[] userEn = status.getUserMentionEntities();
            	StringBuffer mentuser = new StringBuffer();
            	for(int i =0; i< userEn.length;i++)
            	{
            		mentuser.append(userEn[i].getId());
            		mentuser.append(";");
            	}
            	tweet.setUerMention(mentuser.toString());
            	//if(mentuser.length()!=0);
            	//System.out.println("mentuser: "+ mentuser);
            	//tweetID
            	tweet.setTweetID(status.getId());
            	//if(ID!=null)
            	// original twitterID
            	tweet.setOriginID(status.getInReplyToStatusId());
            	//original user ID
            	tweet.setOriginUser(status.getInReplyToUserId());

            	
            	// user ID
            	
            	User users = status.getUser();
            	tweet.setTweetUser(users.getId());

            	
            	//places
            	Place Pl= status.getPlace();
            	String place = "";
            	if(Pl!=null)
            	{
            	place = Pl.getFullName();
            	//System.out.println("place "+place);
            	}
            	tweet.setPlace(place);
            	// Retweetcoun
            	long num = 0;
            	if(status.getRetweetedStatus()!= null)
            	{
            	num = status.getRetweetedStatus().getRetweetCount();
            	//System.out.println("retweetcount"+num);
            	tweet.setRetweetCount(num);
            	tweet.setRetweet(1);
            	}
            	else
            	{
            		tweet.setRetweetCount(0);
                	tweet.setRetweet(0);
            	}
            //	if(Retweet!=null)
            		
            	//System.out.println("Retweetcount: "+ Retweet);
            	
            	//isfavourate
            	boolean favourate = status.isFavorited();
               	/*if(favourate)
               	{
            		fav = 1;
            	tweet.setFavourate(fav);
            	fav =0;
            	System.out.println("isf "+ fav);
               	}*/
            	
            	
            	// is retweet
            	
                //String other = status.toString();
               // tweet.setOther(other);
                listlink.add(tweet);
                //System.out.println("OTHER: "+ other);
            	if((cnt++) % 10000 == 0)
                {
                	System.err.println("totally " + cnt + " tweets downloaded!\t\t" + 
                					new Date(System.currentTimeMillis()));
                	Calendar cal = Calendar.getInstance();
                	int year = cal.get(Calendar.YEAR);
                	int month = cal.get(Calendar.MONTH) + 1;
                	String table = "TweetEn"+String.valueOf(year)+String.valueOf(month);
                	try {
                		handle.database_connection();
						handle.insertBatchTweetHyperlink(table, listlink);
						handle.close_databasehandle();
					} catch (SQLException e) {
						e.printStackTrace();
						handle.close_databasehandle();
						ChangeDB ch = new ChangeDB();
						ch.chang(table, listlink);
					}
                	listlink.clear();
                }
            	
            }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                //System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                //System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                //System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                //System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
	}
	
	
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		TweetStream ts = new TweetStream();
		ts.GetTweetFromStream();
		//ts.handle.close_databasehandle();
	}
}
