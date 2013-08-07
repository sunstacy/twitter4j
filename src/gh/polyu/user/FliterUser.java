package gh.polyu.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.twitter4j.twitterOAuth;
import gh.polyu.twittercore._UserItem;

public class FliterUser {
	public static void main(String[] args) throws SQLException, InterruptedException
	{
		final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		ArrayList<String> user = new ArrayList<String>();
		Connection conn = handle.conn;
		Statement st = conn.createStatement();
		String a = args[0];
		String b = args[1];
		ResultSet rs = st.executeQuery("select * from UserItem where id between "+ a + " and "+b);
		while(rs.next())
		{
			String s = rs.getString(2);
			user.add(s);
		}
		handle.close_databasehandle();
		long[] users = new long[100];
		ArrayList<_UserItem> filteruser = new ArrayList<_UserItem>();
		int j =0;
		int count=0;
		
		for(int i = 0; i< user.size();i++)
		{
			System.out.println(user.size() +"  "+i);
			if(i%100 == 0)
			{
				j = 0;
				filteruser.clear();
			}
		
			long l = Long.parseLong(((String)user.get(i)));
			users[j] = l;
			if(j==99 ||(i==(user.size()-1)))
			{
				boolean flag = true;
				while(flag)
				{
				try {
					Twitter twitter = new TwitterFactory().getInstance();
					twitterOAuth twtOauth = new twitterOAuth(); 
					twtOauth.ReadProperties("properties/twitter4"+args[2]+".properties");
					twtOauth.Authority(twitter);
				ResponseList<User> U = twitter.lookupUsers(users);
				
				
				for(User u: U)
				{

						_UserItem item = new _UserItem();
						item.setUserId(String.valueOf(u.getId()));
						item.setCreateDate(u.getCreatedAt());
						item.setDescription(u.getDescription());
						item.setFavCount(u.getFavouritesCount());
						item.setFollowCount(u.getFollowersCount());
						item.setFriendsCount(u.getFriendsCount());
						item.setLang(u.getLang());
						item.setLocation(u.getLocation());
						item.setName(u.getName());
						item.setScreenName(u.getScreenName());
						item.setName(u.getName());
						item.setImageUrl(u.getOriginalProfileImageURL());
						item.setOtherInfo(u.toString());
						filteruser.add(item);
					}
				flag = false;
				} catch (TwitterException e) {
					flag = true;
					System.out.println(e.getMessage());
					System.out.println("ERROR CODE: "+e.getErrorCode());
					try { 
						if(e.getErrorCode()==88)
						{
							System.out.println("Sleeptime" + e.getRateLimitStatus().getSecondsUntilReset()*100); 
							try { Thread.sleep ( e.getRateLimitStatus().getSecondsUntilReset()*100 +5000 ) ; 
			        		} catch (InterruptedException ie){}
						}
						Thread.sleep (60000) ;
					} catch (InterruptedException ie){}
				}
				}
				if(count%100==0)
				{
					handle.database_connection();
					System.out.println("DB open");
				}
				count++;
				handle.insertFilterUser("filterUser", filteruser);
				if(count%100==0)
				{
					handle.close_databasehandle();
					System.out.println("DB close");
				}
				System.out.println("from"+(Integer.valueOf(a)+(count-1)*100)+"to"+(Integer.valueOf(a)+(count-1)*100 + j)+" save size"+filteruser.size());
				Thread.sleep(10000);
			}
				
			
			j++;
		}
		handle.close_databasehandle();
	}
}
