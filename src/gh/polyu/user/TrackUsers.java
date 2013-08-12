package gh.polyu.user;


/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import twitter4j.*;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.mail.GmailSend;
import gh.polyu.twitter4j.twitterOAuth;
import gh.polyu.twittercore._TweetLink;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;

import clientserver.Key;

/**
 * <p>This is a code example of Twitter4J Streaming API - filter method support.<br>
 * Usage: java twitter4j.examples.stream.PrintFilterStream [follow(comma separated numerical user ids)] [track(comma separated filter terms)]<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TrackUsers {
    /**
     * Main entry of this application.
     *
     * @param args follow(comma separated user ids) track(comma separated filter terms)
     * @throws twitter4j.TwitterException
     * @throws SQLException 
     */
	Key key = new Key();
	long[]follow;
    boolean alive = true;

	public TrackUsers(long[] follow, Key key) {
		this.follow = follow;
		this.key = key;
		System.out.println(key.getKey()+" "+key.getValue()+" "+key.getaToken()+" "+key.getcToken());
		System.out.println(follow.length);
	}
    public void track(final int no, final int p) {
    	final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
 
        while(alive)
        {
        	alive = false;
        StatusListener listener = new StatusListener() {
            ArrayList<_TweetLink> listlink = new ArrayList<_TweetLink>();
            int cnt = 0;
            String oldmonth = "20138";
            String table = "UserTweet20138";
            String oldday = "";
            String currentday = "";
            String currentmonth = "";
            long lastinsert = 0l;
            long nowinsert = 0l;
            int newday = 0;
            String newtime = "";

            @Override

            public void onStatus(Status status) {
            	if(status.getId()==123 &&  status.getText().equals("YOU are WORNG!."))
            	{
            		System.out.println("Connection Need to be rebuilt!!");
            		alive = true;
            	}
            	else if(status.getLang().equals("en"))
            	{
            	_TweetLink tweet = new _TweetLink();
            	String Test = status.getText();
            	tweet.setText(Test);
            	Date time = status.getCreatedAt();
            	tweet.setTime(time);
            	tweet.setUserName(status.getUser().getName());
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
                Calendar cal = Calendar.getInstance();
            	int year = cal.get(Calendar.YEAR);
            	int month = cal.get(Calendar.MONTH) + 1;
            	int day = cal.get(Calendar.DAY_OF_MONTH);
            	currentmonth = String.valueOf(year)+String.valueOf(month);
            	currentday = String.valueOf(day);
            	if(currentmonth.equals(oldmonth))
            	{
            	    if(currentday.equals(oldday))
            		   ;
            	    else
            	    {
            		newday = 1;
            		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("MM月dd日HH:mm:ss "); 
            		Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间     
            		newtime   =   formatter.format(curDate);     
            	    }
            	}
            	else
            	{
            		try{
            		handle.database_connection();
            		table = "UserTweet"+String.valueOf(year)+String.valueOf(month);
            		System.out.println("create new table "+table);
    				String CREATE_TABLE = "create table "+table+"(TweetID varchar(100), UserName varchar(200), TwitterUser varchar(145), OriginID varchar(100), OriginUser varchar(100), place varchar(100), RetweetCount varchar(100), isRetweet int(5), Text varchar(500), Time datetime," +
    						"Hashtag varchar(200), URL varchar(200), UerMention varchar(200))";
    				Statement st = handle.conn.createStatement();
    				st.execute(CREATE_TABLE);
    				String Create_Index = "alter table " + table+" add index time (Time)";
            		st.execute(Create_Index);
            		String Create_Index2 = "alter table " + table+" add index userID (TwitterUser)";
            		st.execute(Create_Index2);
            		String key = "ALTER TABLE "+table+" ADD PRIMARY KEY (TweetID)";
            		st.execute(key);
            		}
            		catch(SQLException e)
            		{
            			e.printStackTrace();
            		}
    				handle.close_databasehandle();
    				try{
    					TwitterDBHandle handle2 = new TwitterDBHandle();
						handle2.intialTwitterDBhandle2();
                		handle2.database_connection();
                		table = "UserTweet"+String.valueOf(year)+String.valueOf(month);
                		System.out.println("create new table "+table);
        				String CREATE_TABLE = "create table "+table+"(TweetID varchar(100), UserName varchar(200), TwitterUser varchar(145), OriginID varchar(100), OriginUser varchar(100), place varchar(100), RetweetCount varchar(100), isRetweet int(5), Text varchar(500), Time datetime," +
        						"Hashtag varchar(200), URL varchar(200), UerMention varchar(200))";
        				Statement st = handle2.conn.createStatement();
        				st.execute(CREATE_TABLE);
        				String Create_Index = "alter table " + table+" add index time (Time)";
                		st.execute(Create_Index);
                		String Create_Index2 = "alter table " + table+" add index userID (TwitterUser)";
                		st.execute(Create_Index2);
                		String key = "ALTER TABLE "+table+" ADD PRIMARY KEY (TweetID)";
                		st.execute(key);
                		handle2.close_databasehandle();
                		}
                		catch(SQLException e)
                		{
                			e.printStackTrace();
                		}
            	}
                //System.out.println("OTHER: "+ other);
     
            	if((cnt++) % 1000 == 0)
                {
 
            		if(newday ==1)
                	{
            			newday = 0;
                		oldday = currentday;
                		GmailSend gs = new GmailSend("cscchenyoyo@gmail.com","910316ccy");
                		gs.send("THREAD"+p+" :"+"program no"+no+ "message"+ newtime, "I am still alive");
                		newtime = "";
                	}
                	
                	try {
                		handle.database_connection();
						handle.userTweet(table, listlink);
						nowinsert = System.currentTimeMillis();
						System.err.println("No: "+no+"program "+"totally " + cnt + " tweets downloaded!\n" + 
            					new Date(nowinsert)+ "  "+ new Date(lastinsert));
						lastinsert = nowinsert;
						nowinsert = 0l;
						handle.close_databasehandle();
					} catch (SQLException e) {
						handle.close_databasehandle();
						e.printStackTrace();
						// TODO Auto-generated catch block
						TwitterDBHandle handle2 = new TwitterDBHandle();
						handle2.intialTwitterDBhandle2();
						handle2.database_connection();
						
						try {
							handle2.userTweet(table, listlink);
							nowinsert = System.currentTimeMillis();
							System.err.println("New Database No: "+no+"program "+"totally " + cnt + " tweets downloaded!\n" + 
	            					new Date(nowinsert)+ new Date(lastinsert));
							lastinsert = nowinsert;
							nowinsert = 0l;
							handle2.close_databasehandle();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							GmailSend gs = new GmailSend("cscchenyoyo@gmail.com","910316ccy");
					    	try {
								gs.SendSSLMessage("cscchenyoyo@gmail.com", "program error", "both databases are down");
							} catch (MessagingException ee) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						 GmailSend gs = new GmailSend("cscchenyoyo@gmail.com","910316ccy");
					    	try {
								gs.SendSSLMessage("cscchenyoyo@gmail.com", "program error", "change database to another one");
							} catch (MessagingException ee) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
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
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterOAuth twtOauth = new twitterOAuth(); 
		twtOauth.AuthoritywithS(twitterStream,key );
        twitterStream.addListener(listener);
        twitterStream.filter(new FilterQuery(0,follow));

      /*  try {
			Thread.sleep(3000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        }
    }

}
