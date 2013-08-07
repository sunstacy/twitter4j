package gh.polyu.twitter4j;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SearchTweets {

	public List<Status> searchTrendingTweets(Twitter twitter, String strQuery)
	{
		
		try {
			Query qr = new Query(strQuery);
			qr.setLang("en");
			QueryResult result = twitter.search(qr);
			return result.getTweets();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return null;
		}
	} 
	
	public static void main(String[] args) 
	{       
		Twitter twitter = new TwitterFactory().getInstance();
		
		twitterOAuth twtOauth = new twitterOAuth();	
		twtOauth.ReadProperties("properties/twitter4j.properties");
		twtOauth.Authority(twitter, twtOauth.prop.getProperty("consumerKey"), 
				twtOauth.prop.getProperty("consumerSecret"));
		
		List<Status> listTwts = new SearchTweets().searchTrendingTweets(
				twitter, "Obama");
		
		for(Status twt : listTwts)
		{
			System.out.println(twt.getId() + " " + twt.getText());
		}
    }
}
