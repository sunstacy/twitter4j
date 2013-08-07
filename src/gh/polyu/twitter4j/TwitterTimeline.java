package gh.polyu.twitter4j;

import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterTimeline {
	
	public List<Status> getPublicTimeline(Twitter twitter)
	{		 
        try {
            List<Status> statuses = twitter.getPublicTimeline();            
            return statuses;
            
        } catch (TwitterException te) {
//            te.printStackTrace();
            System.err.println("Failed to get timeline: " + te.getMessage());
            return null;
        }        
	}

	public List<Status> getUserTimeline(Twitter twitter,
			String userName)
	{
		try {
			List<Status> statuses = twitter.getUserTimeline(userName);
			
			for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " +
                		status.getId() + "-" + status.getText());
                System.out.println(
                		"status.getInReplyToStatusId():" + status.getInReplyToStatusId()+ " -- " + 
                        "status.getRetweetCount():" + status.getRetweetCount() + " -- " +
                		"status.getRetweetedStatus():" + status.getRetweetedStatus());

            }
			
			return statuses;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<Status> getRetweetedByUser(Twitter twitter,
			String userName)
	{
		try {
			List<Status> statuses = twitter.getRetweetedByUser(userName, new Paging(1));
			
			for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " +
                		status.getId() + "-" +  status.getText());
                System.out.println(
                		"status.getInReplyToStatusId():" + status.getInReplyToStatusId()+ " -- " + 
                        "status.getRetweetCount():" + status.getRetweetCount() + " -- " +
                		"status.getRetweetedStatus().getId():" + status.getRetweetedStatus().getId());
          
			}
			
			return statuses;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Status> getRetweetedToUser(Twitter twitter,
			String userName)
	{
		try {
			List<Status> statuses = twitter.getRetweetedToUser(userName, new Paging(1));
			
			for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " +
                		status.getId() + "-" +  status.getText());
            }
			
			return statuses;
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
		//one randomly selected user: Lovestruck 
		Twitter twitter = new TwitterFactory().getInstance();
		
		TwitterTimeline timeline = new TwitterTimeline();
		List<Status> statuses = timeline.getPublicTimeline(twitter);
		System.out.println("Showing public timeline.");
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
        }
		
//		 TwitterTimeline timeline = new TwitterTimeline();
//		 System.out.println("=======User Recent status===========");
//		 timeline.getUserTimeline(twitter, "dannygao");
		 
//		 System.out.println("=======User Recent status===========");
//		 timeline.getUserTimeline(twitter, "dannygao");
//		 System.out.println("=======Tweets Retweet by user===========");
//		 timeline.getRetweetedByUser(twitter, "dannygao1984");
//		 System.out.println("=======Tweets Retweet to user===========");
//		 timeline.getRetweetedToUser(twitter, "dannygao1984");
		 
		
		
	}

}
