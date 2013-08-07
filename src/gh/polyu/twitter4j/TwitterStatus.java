package gh.polyu.twitter4j;

import java.util.ArrayList;
import java.util.List;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class TwitterStatus {

	public ArrayList<Long> getRetweetedByUserIDs(Twitter twitter,
			long statusId)
	{
		ArrayList<Long> listIDs = new ArrayList<Long>();
		try {
			int page = 1;
			IDs ids;
            do {
            	ids = twitter.getRetweetedByIDs(statusId, new Paging(page, 100));

            	for(long id : ids.getIDs())
            	{
            		listIDs.add(id);
            	}

                page++;
            } while (ids.getIDs().length != 0);
			
            return listIDs;
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
		
		//Get a serial of status
		System.out.println("=======User status===========");
		TwitterTimeline timeline = new TwitterTimeline();		
		List<Status>  statuses = timeline.getUserTimeline(twitter, "Lovestruck");
		
		//Get user ids who retweet these status
		System.out.println("=======User who retweet the status===========");
		TwitterStatus   status = new TwitterStatus();
		for(Status sts : statuses)
		{
			System.out.println("who retweets status - " + sts.getId() 
					+ ":" + sts.getText());
			ArrayList<Long> listIDs = status.getRetweetedByUserIDs(twitter, sts.getId());
			for(long id : listIDs)
			{
				System.out.println(id);
			}
		}
		
	    
	}

}
