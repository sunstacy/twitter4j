package gh.polyu.twitter4j;



import gh.polyu.database.TwitterDBHandle;
import gh.polyu.twittercore._Relation;

import java.sql.Connection;
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

public class getFollowers {
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	
	public void getFollowers(HashSet<Long> fid,LinkedHashSet<Long> cid, TwitterDBHandle handle,Twitter twitter,
			long ld) throws TwitterException, InterruptedException
	{
		
		ArrayList<_Relation> relations = new ArrayList<_Relation>();
		long cursor = -1;
        IDs ids = null;
        System.out.println("Listing following ids.");
        do {
        	int count = 0;
        	try{
            ids = twitter.getFollowersIDs(ld, cursor);
            
            for(long l : ids.getIDs())
            {
            	_Relation re = new _Relation();
            	re.setFollowerId(l);
            	re.setUserId(ld);
            	relations.add(re);
            	if(fid.contains(l))
            		;
            	else
            		cid.add(l);
            }
        	}
        	catch(TwitterException e)
        	{
        		System.out.println(e);
        		try { Thread.sleep ( e.getRateLimitStatus().getSecondsUntilReset()*100 +5000 ) ; 
        		} catch (InterruptedException ie){}
        	}
        	Thread.sleep(3000);
        	handle.insertBatchRelationship("relation", relations);
        	count = count+relations.size();
        	System.out.println("one batch"+count);
        	relations.clear();
        	System.out.println("cursor:"+cursor);
        	if((cursor!=0)&(count!=0))
        	{
        		cursor = ids.getNextCursor();
        	}
       } 
        while (cursor != 0);
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		HashSet<Long> fid = new HashSet<Long>();//已经查询好的id
		LinkedHashSet<Long> cid = new LinkedHashSet<Long>();//待查询的id
		getFollowers ts = new getFollowers();
		int count = 0;
		// TODO Auto-generated method stub
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitterOAuth twtOauth = new twitterOAuth(); 
		  twtOauth.ReadProperties("properties/twitter4j.properties");
		  twtOauth.Authority(twitter);
		getFollowers twtRel = new getFollowers();
		
		final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		Connection conn = handle.conn;
		final Statement s = conn.createStatement();
		
		try {
			long ld = 731223740; 
			cid.add(ld);
			ld = 15830701;
			cid.add(ld);
			ld = 249527815;
			cid.add(ld);
			ld = 16540939;
			cid.add(ld);
			while(fid.size()<100000)
			{
	            Iterator it = cid.iterator();
	            String c= it.next().toString();
	            long cc = 0l;
	            try{
	            	 cc = Long.parseLong(c);
	            	 System.out.println("processing:"+c);
	            }
	            catch(NumberFormatException e)
	            {
	            	System.out.println("change error");
	            }
	    
				ts.getFollowers(fid,cid,handle,twitter,cc);
				cid.remove(cc);
				fid.add(cc);
				System.out.println("processed:"+ c);
			}		
        } catch (TwitterException te ) {
            te.printStackTrace();
            System.out.println("Failed to lookup friendships: " + te.getMessage());
            System.exit(-1);
        }
    }
	
}
