package gh.polyu.twitter4j;

import java.util.List;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class TwitterTweet {

	public List<User> getWhoRetweeted(Twitter twitter,
			long statusId) throws TwitterException
	{
		List<User> users = twitter.getRetweetedBy(statusId);
		return users;
	}
	
	public ResponseList<Status> getRetweetsOfStatus(Twitter twitter,
			long statusId)
    {	
		try {
			ResponseList<Status> list = twitter.getRetweets(statusId);
			for(Status sts : list)
			{
				System.out.println("@" + sts.getUser().getName() +  + sts.getId() 
						+ ":" + sts.getText());
			}	
			return list;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Twitter twitter = new TwitterFactory().getInstance();
		//Authorize the Twitter
		twitterOAuth oauth = new twitterOAuth();		
		oauth.ReadProperties("properties/twitter4j.properties");
		oauth.Authority(twitter, oauth.prop.getProperty("consumerKey"), 
				oauth.prop.getProperty("consumerSecret"));
				
		TwitterTweet twitTwt =  new TwitterTweet();
		long statusID = 157030929871798272l;
		
		System.out.println("Which tweet(s) is retweeted from status" + statusID);
		twitTwt.getRetweetsOfStatus(twitter, statusID);
		
//		statusID = 157010493805047808l;		
//		System.out.println("Which tweet(s) is retweeted from status" + statusID);
//		twitTwt.getRetweetsOfStatus(twitter, statusID);
//		
//		statusID = 155948809623576577l;		
//		System.out.println("Which tweet(s) is retweeted from status" + statusID);
//		twitTwt.getRetweetsOfStatus(twitter, statusID);
//		
//		statusID = 155947918006820865l;		
//		System.out.println("Which tweet(s) is retweeted from status" + statusID);
//		twitTwt.getRetweetsOfStatus(twitter, statusID);
//		
//		statusID = 153370566718533632l;		
//		System.out.println("Which tweet(s) is retweeted from status" + statusID);
//		twitTwt.getRetweetsOfStatus(twitter, statusID);
//		
//		statusID = 157010493805047808l;		
//		System.out.println("Which tweet(s) is retweeted from status" + statusID);
//		twitTwt.getRetweetsOfStatus(twitter, statusID);
			 
	}

}
