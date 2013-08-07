package gh.polyu.twitter4j;

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

import gh.polyu.twitter4j.twitterOAuth;
import gh.polyu.twittercore._TweetLink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>This is a code example of Twitter4J Streaming API - filter method support.<br>
 * Usage: java twitter4j.examples.stream.PrintFilterStream [follow(comma separated numerical user ids)] [track(comma separated filter terms)]<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class PrintFilterStream {
    /**
     * Main entry of this application.
     *
     * @param args follow(comma separated user ids) track(comma separated filter terms)
     * @throws twitter4j.TwitterException
     */
    public static void main(String[] args) throws TwitterException {
        if (args.length < 1) {
            System.out.println("Usage: java twitter4j.examples.PrintFilterStream [follow(comma separated numerical user ids)] [track(comma separated filter terms)]");
            System.exit(-1);
        }
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
            	if(status.getLang().equals("en"))
            	{
            	_TweetLink tweet = new _TweetLink();
            	String Test = status.getText();
            	tweet.setText(Test);
            	Date time = status.getCreatedAt();
            	tweet.setTime(time);
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
            	UserMentionEntity[] userEn = status.getUserMentionEntities();
            	StringBuffer mentuser = new StringBuffer();
            	for(int i =0; i< userEn.length;i++)
            	{
            		mentuser.append(userEn[i].getId());
            		mentuser.append(";");
            	}
            	tweet.setUerMention(mentuser.toString());
            	//tweetID
            	tweet.setTweetID(status.getId());
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
            	tweet.setRetweetCount(num);
            	tweet.setRetweet(1);
            	}
            	else
            	{
            		tweet.setRetweetCount(0);
                	tweet.setRetweet(0);
            	}
            	boolean favourate = status.isFavorited();
                listlink.add(tweet);
                //System.out.println("OTHER: "+ other);
            	if((cnt++) % 1000 == 0)
                {
                	System.err.println("totally " + cnt + " tweets downloaded!\t\t" + 
                					new Date(System.currentTimeMillis()));
                	Calendar cal = Calendar.getInstance();
                	int year = cal.get(Calendar.YEAR);
                	int month = cal.get(Calendar.MONTH) + 1;
                	String table = "TweetEn"+String.valueOf(year)+String.valueOf(month);
                	try {
						handle.insertBatchTweetHyperlink(table, listlink);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
                //ex.printStackTrace();
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterOAuth twtOauth = new twitterOAuth(); 
		  twtOauth.ReadProperties("properties/twitter4j.properties");
		  twtOauth.Authority(twitterStream);
        twitterStream.addListener(listener);
        ArrayList<Long> follow = new ArrayList<Long>();
        ArrayList<String> track = new ArrayList<String>();
        for (String arg : args) {
            if (isNumericalArgument(arg)) {
                for (String id : arg.split(",")) {
                	System.err.println("###############"+id);
                    follow.add(Long.parseLong(id));
                    
                }
            } else {
                track.addAll(Arrays.asList(arg.split(",")));
            }
        }
        long[] followArray = new long[follow.size()];
        for (int i = 0; i < follow.size(); i++) {
            followArray[i] = follow.get(i);
        }
        String[] trackArray = track.toArray(new String[track.size()]);

        // filter() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.filter(new FilterQuery(0,followArray,trackArray));
    }

    private static boolean isNumericalArgument(String argument) {
        String args[] = argument.split(",");
        boolean isNumericalArgument = true;
        for (String arg : args) {
            try {
                Integer.parseInt(arg);
            } catch (NumberFormatException nfe) {
                isNumericalArgument = false;
                break;
            }
        }
        return isNumericalArgument;
    }
}
