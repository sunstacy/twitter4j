package gh.polyu.twitter4j;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterTrending {

	public Trends GetTrendingTopic(Twitter twitter)
	{
		try {
			// 1 - world wide            
            return twitter.getPlaceTrends(1);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get trends: " + te.getMessage());
            return null;
        }
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Twitter twitter = new TwitterFactory().getInstance();
		
		twitterOAuth twtOauth = new twitterOAuth();	
		twtOauth.ReadProperties("properties/twitter4j.properties");
		twtOauth.Authority(twitter, twtOauth.prop.getProperty("consumerKey"), 
				twtOauth.prop.getProperty("consumerSecret"));
		
		TwitterTrending trend = new TwitterTrending();
		trend.GetTrendingTopic(twitter);
		
	}

}
