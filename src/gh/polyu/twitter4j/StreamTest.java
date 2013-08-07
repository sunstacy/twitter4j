package gh.polyu.twitter4j;



import gh.polyu.database.TwitterDBHandle;
import gh.polyu.twittercore._Relation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.Twitter;

public class StreamTest {
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	
	public int getFollowers( TwitterDBHandle handle,Twitter twitter,
			long ld) throws TwitterException, InterruptedException
	{
		
		ArrayList<_Relation> relations = new ArrayList<_Relation>();
		long cursor = -1;
		int count = 0;
        IDs ids = null;
        do {
        	
        	try{
            ids = twitter.getFollowersIDs(ld, cursor);
            
            for(long l : ids.getIDs())
            {
            	_Relation re = new _Relation();
            	re.setFollowerId(l);
            	re.setUserId(ld);
            	relations.add(re);
            }
        	}
        	catch(TwitterException e)
        	{
        		System.err.println("ERROR!, SLEEP"+e.getRateLimitStatus().getSecondsUntilReset());
        		try { Thread.sleep ( e.getRateLimitStatus().getSecondsUntilReset()*100 +5000 ) ; 
        		} catch (InterruptedException ie){}
        	}
        	Thread.sleep(3000);
        	handle.insertBatchRelationship("relationTest", relations);
        	count = count+relations.size();
        	//System.out.println("one batch"+count);
        	relations.clear();
        	//System.out.println("cursor:"+cursor);
        	if((cursor!=0)&(count!=0))
        	{
        		cursor = ids.getNextCursor();
        	}
       } 
        while (cursor != 0);
        return count;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		StreamTest ts = new StreamTest();
		int count = 0;
		// TODO Auto-generated method stub
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitterOAuth twtOauth = new twitterOAuth(); 
		  twtOauth.ReadProperties("properties/twitter4j.properties");
		  twtOauth.Authority(twitter);
		StreamTest twtRel = new StreamTest();
		
		final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		Connection conn = handle.conn;
		final Statement s = conn.createStatement();
		boolean start = true;
		int times = 0;
		long time = System.currentTimeMillis();
		int c =0 ;
		while(times<10)
		{
			int sum = 0;
		while(start) {
        if ((System.currentTimeMillis() - time < 3600000) & (sum<15)) {
		try {
			int k = 0;
			ResultSet rs = s.executeQuery("SELECT * FROM myTweet.relation ORDER BY RAND() LIMIT 1");
			String id = "";
			while(rs.next())
				 id = rs.getString(2);
			long iid = Long.valueOf(id);
			k = ts.getFollowers(handle,twitter,iid);
			c++;
			sum = sum+1;
			System.out.println("processed NO."+c+" :" +iid +"Followers:"+k);
			}		
		catch (TwitterException te ) {
            te.printStackTrace();
            System.out.println("Failed to lookup friendships: " + te.getMessage());
            System.exit(-1);
        }
		Thread.sleep(3000);
        }
        else
        {
        	start = false;
        	if(!(sum<14))
        		Thread.sleep(3600000-(System.currentTimeMillis() - time));
        }
		}
		times++;
		}
	}
	
}
