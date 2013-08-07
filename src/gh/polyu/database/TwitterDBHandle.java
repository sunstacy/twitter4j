package gh.polyu.database;

import gh.polyu.common.CommonFunction;
import gh.polyu.mail.GmailSend;
import gh.polyu.twittercore._HyperlinkInfo;
import gh.polyu.twittercore._Relation;
import gh.polyu.twittercore._TweetInfo;
import gh.polyu.twittercore._TweetLink;
import gh.polyu.twittercore._UserItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

import javax.mail.MessagingException;

import clientserver.Key;


public class TwitterDBHandle extends databasehandle_core {

	/**
	 * @param: 1st File contains N pairs of Key and Secret Twitter 
	 * 		   2nd Updating time of new trending topics
	 * */
	static public void main(String[] args)
	{
		TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		handle.ConnectTest();
	}
	
	public void ConnectTest()
	{
		String sql = "SELECT * FROM myTweet.CHARACTER_SETS LIMIT 0 , 30";
		if (conn == null)
		{
			this.database_connection();
		}
	
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			
			while(rs.next())
			{
				System.out.println(rs.getString("CHARACTER_SET_NAME"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		} finally
		{
			ClearPreparedStatement();
			ClearResultSet();			
		}
	}
	
	public void intialTwitterDBhandle()
	{
		Properties prop = CommonFunction.GetProperties(
				"properties/database.properties");
		initial_databasehandle(prop);
	}
	
	public void intialTwitterDBhandle2()
	{
		Properties prop = CommonFunction.GetProperties(
				"properties/database2.properties");
		initial_databasehandle(prop);
	}
	public String getDatabaseTableNameFromTweetInfo(_TweetInfo newTwt)
	{
		Timestamp timestamp = newTwt.getTweetTimestamp();
		String strMonth = Integer.toString(timestamp.getMonth() + 1);
		if(strMonth.length() == 1)
			strMonth = "0" + strMonth;
		String strYear = Integer.toString(timestamp.getYear() + 1900);
		
		return strYear+strMonth;
	}
	
	public boolean isTweetExistInDB(_TweetInfo newTwt)
	{
		String sql = "SELECT * FROM twitterandhyperlink." + 
						CommonFunction.GetYear() + CommonFunction.GetMonth() +
						" WHERE TwtID='" + newTwt.getlId() + "'";
		
		//System.out.println("#1 Is Tweet Exist in DB #" + sql);
		
		if (conn == null)
		{
			this.database_connection();
		}
	
		boolean bFound = false;
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			
			if(rs.next())
			{
				bFound = true;
			}
			else
			{
				bFound = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			bFound = false;
		} finally
		{
			ClearPreparedStatement();
			ClearResultSet();
			
			return bFound;
		}
		
	}
	
	public void insertBatchRelationship(String table, ArrayList<_Relation> relation) {
		  // TODO Auto-generated method stub
		  String INSERT_RECORD = "insert into " + "myTweet" + 
		       "." + table + 
		       "(UserId,FollowerId)" +
		       "values(?, ?)";
		  
		 //System.out.println("##DB insert " + this.getStrSchm() 
		 //   + ":" + table + " items, number#" + relation.size());
		  
		  try 
		  {
		   pstmt = conn.prepareStatement(INSERT_RECORD);
		   
		   for (_Relation re : relation)
		   {  
		    pstmt.setString(1, String.valueOf(re.getUserId()));
		    pstmt.setString(2, String.valueOf(re.getFollowerId()));
		    pstmt.addBatch();    
		   }    
		   pstmt.executeBatch();
		   
		   ClearPreparedStatement();
		  }catch (SQLException e)
		  {
		   System.err.println("mysql error in insert list of tweets");
		   e.printStackTrace();
		  } 
		  
		 }
	
	public void insertKey(String table, ArrayList<Key> keyList) {
		  // TODO Auto-generated method stub
		  String INSERT_RECORD = "insert into " + "myTweet" + 
		       "." + table + 
		       "(keyID,secret,token,tokenSecret,value)" +
		       "values(?,?,?,?,?)";

		  System.out.println(INSERT_RECORD);
		  try 
		  {
		   pstmt = conn.prepareStatement(INSERT_RECORD);
		   
		   for (Key k : keyList)
		   {  
		    pstmt.setString(1, k.getKey());
		    pstmt.setString(2, k.getValue());
		    pstmt.setString(3, k.getaToken());
		    pstmt.setString(4, k.getcToken());
		    pstmt.setString(5, k.getWaittime());
		    pstmt.addBatch();    
		   }    
		   pstmt.executeBatch();
		   
		   ClearPreparedStatement();
		  }catch (SQLException e)
		  {
		   System.err.println("mysql error in insert list of tweets");
		   e.printStackTrace();
		  } 
		  
		 }
	
	public void insertFilterUser(String table, ArrayList<_UserItem> userlist) {
		  // TODO Auto-generated method stub
		  String INSERT_RECORD = "insert into " + "myTweet" + 
		       "." + table + 
		       "(UserId,Topic,name,screenName,description,createDate,favCount,followCount," +
		       "friendsCount,lang,location,otherInfo,imageUrl)" +
		       "values(?, ?,?,?,?,?,?,?,?,?,?,?,?)";
		  try 
		  {
		   pstmt = conn.prepareStatement(INSERT_RECORD);
		   
		   for (_UserItem u : userlist)
		   {  
		    pstmt.setString(1, u.getUserId());
		    pstmt.setInt(2, 1);
		    pstmt.setString(3, u.getName());
		    pstmt.setString(4,u.getScreenName());
		    pstmt.setString(5, u.getDescription());
		    java.sql.Timestamp st = new java.sql.Timestamp(u.getCreateDate().getTime());
		    pstmt.setTimestamp(6, st);
		    pstmt.setInt(7, u.getFavCount());
		    pstmt.setInt(8, u.getFollowCount());
		    pstmt.setInt(9,u.getFriendsCount());
		    pstmt.setString(10,u.getLang());
		    pstmt.setString(11,u.getLocation());
		    pstmt.setString(12,u.getOtherInfo());
		    pstmt.setString(13,u.getImageUrl());
		    pstmt.addBatch();    
		   }    
		   pstmt.executeBatch();
		   
		   ClearPreparedStatement();
		  }catch (SQLException e)
		  {
		   System.err.println("mysql error in insert list of tweets");
		   e.printStackTrace();
		  } 
		  
		 }
	
	
	public void userTweet(String table, ArrayList<_TweetLink> listLink) throws SQLException {
			  // TODO Auto-generated method stub
		ResultSet rs  = conn.getMetaData().getTables(null, null,  table, null );
		  if (rs.next())  {
		  }
		  else  
		  {
			  System.out.println("create new table "+table);
				String CREATE_TABLE = "create table "+table+"(TweetID varchar(100), UserName varchar(200), TwitterUser varchar(145), OriginID varchar(100), OriginUser varchar(100), place varchar(100), RetweetCount varchar(100), isRetweet int(5), Text varchar(500), Time datetime," +
						"Hashtag varchar(200), URL varchar(200), UerMention varchar(200))";
				Statement st = conn.createStatement();
				st.execute(CREATE_TABLE);
		  }
		 
			  String INSERT_RECORD = "insert into " + "myTweet" + 
			       "." + table + 
			       "(TweetID,UserName,TwitterUser,OriginID,OriginUser,place,RetweetCount," +
			       "isRetweet,Text,Time,Hashtag,URL,UerMention)" +
			       "values(?, ?,?, ?,?,?,?,?,?,?,?,?,?)";
			  
			  System.out.println("##DB insert " + this.getStrSchm() 
			    + ":" + table + " items, number#" + listLink.size());
			  
			  try 
			  {
			   pstmt = conn.prepareStatement(INSERT_RECORD);
			   
			   for (_TweetLink twt : listLink)
			   {  
			    pstmt.setString(1, String.valueOf(twt.getTweetID()));
			    pstmt.setString(2, twt.getUserName());
			    pstmt.setString(3, String.valueOf(twt.getTweetUser()));
			    pstmt.setString(4, String.valueOf(twt.getOriginID()));
			    pstmt.setString(5, String.valueOf(twt.getOriginUser()));
			    pstmt.setString(6, twt.getPlace());
			    pstmt.setString(7, String.valueOf(twt.getRetweetCount()));
			    //pstmt.setString(7,String.valueOf(twt.isFavourate()));
			    pstmt.setString(8,String.valueOf(twt.isRetweet()));
			    pstmt.setString(9, twt.getText());
			    java.sql.Timestamp st = new java.sql.Timestamp(twt.getTime().getTime());
			    pstmt.setTimestamp(10,st );
			    pstmt.setString(11, twt.getHashtag());
			    pstmt.setString(12, twt.getURL());
			    pstmt.setString(13, twt.getUerMention());
			    //pstmt.setString(14, twt.getOther());
			    pstmt.addBatch();    
			   }    
			   pstmt.executeBatch();
			   
			   ClearPreparedStatement();
			  }catch (SQLException e)
			  {
				  
			   System.err.println("mysql error in insert list of tweets");
			   e.printStackTrace();
		       
			  } 
			  
			 }
	
	public void insertBatchTweetHyperlink(String table, ArrayList<_TweetLink> listLink) throws SQLException {
		  // TODO Auto-generated method stub
	ResultSet rs  = conn.getMetaData().getTables(null, null,  table, null );
	  if (rs.next())  {
	  }
	  else  
	  {
		  System.out.println("create new table "+table);
			String CREATE_TABLE = "create table "+table+"(TweetID varchar(100), TwitterUser varchar(145), OriginID varchar(100), OriginUser varchar(100), place varchar(100), RetweetCount varchar(100), isRetweet int(5), Text varchar(500), Time datetime," +
					"Hashtag varchar(200), URL varchar(200), UerMention varchar(200))";
			Statement st = conn.createStatement();
			st.execute(CREATE_TABLE);
	  }
	 
		  String INSERT_RECORD = "insert into " + "myTweet" + 
		       "." + table + 
		       "(TweetID,TwitterUser,OriginID,OriginUser,place,RetweetCount," +
		       "isRetweet,Text,Time,Hashtag,URL,UerMention)" +
		       "values(?,?, ?,?,?,?,?,?,?,?,?,?)";
		  
		  System.out.println("##DB insert " + this.getStrSchm() 
		    + ":" + table + " items, number#" + listLink.size());
		  
		  try 
		  {
		   pstmt = conn.prepareStatement(INSERT_RECORD);
		   
		   for (_TweetLink twt : listLink)
		   {  
		    pstmt.setString(1, String.valueOf(twt.getTweetID()));
		    pstmt.setString(2, String.valueOf(twt.getTweetUser()));
		    pstmt.setString(3, String.valueOf(twt.getOriginID()));
		    pstmt.setString(4, String.valueOf(twt.getOriginUser()));
		    pstmt.setString(5, twt.getPlace());
		    pstmt.setString(6, String.valueOf(twt.getRetweetCount()));
		    //pstmt.setString(7,String.valueOf(twt.isFavourate()));
		    pstmt.setString(7,String.valueOf(twt.isRetweet()));
		    pstmt.setString(8, twt.getText());
		    java.sql.Timestamp st = new java.sql.Timestamp(twt.getTime().getTime());
		    pstmt.setTimestamp(9,st );
		    pstmt.setString(10, twt.getHashtag());
		    pstmt.setString(11, twt.getURL());
		    pstmt.setString(12, twt.getUerMention());
		    //pstmt.setString(14, twt.getOther());
		    pstmt.addBatch();    
		   }    
		   pstmt.executeBatch();
		   
		   ClearPreparedStatement();
		  }catch (SQLException e)
		  {
			  
		   System.err.println("mysql error in insert list of tweets");
		   e.printStackTrace();
	        GmailSend gs = new GmailSend("forevsmileyy@gmail.com","buaaccy0316");
	    	try {
				gs.SendSSLMessage("forevsmileyy@gmail.com", "program error", "loading user stream to Database meets error");
			} catch (MessagingException ee) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  } 
		  
		 }
	public int insertTweet2DB(_TweetInfo newTwt)
	{
		if(isTweetExistInDB(newTwt))
		{
			return 0;
		}
		String table = CommonFunction.GetYear() + CommonFunction.GetMonth();
		String strInsert = "INSERT INTO  myTweet.tweet" + 
				table +
				" (TwtID, TwtTrend, TwtDate, TwtFromUsrID, TwtFromUsrName, " +
				"TwtText, TwtRemark, TwtToUsrID, TwtToUsrName)" +
				" VALUES " +
				"('"+ newTwt.getlId() + "',?,?" +
				",'" + newTwt.getlFromUsr() + "',?,?,'','" +
				newTwt.getlToUsr() + "',?)";
		
		int iCode = -1;
		try {
						
			 
			 PreparedStatement pstmt = conn.prepareStatement(strInsert);
			
			 pstmt.setString(1, newTwt.getStrTrend());
			 		
			 pstmt.setTimestamp(2, newTwt.getTweetTimestamp());
			
			 pstmt.setString(3, newTwt.getStrFromUsrName());
			 
			 pstmt.setString(4, newTwt.getStrText());
			
			 pstmt.setString(5, newTwt.getStrToUsr());
			
			 //pstmt.setString(6, newTwt.getStrJson());
			 
			 pstmt.executeUpdate();
			 
			 if(pstmt != null)
			 {
				 pstmt.close();
				 pstmt = null;
			 }
				
			 iCode = 1;
			 //System.out.println("Insert Tweet to Database #" + newTwt.getlId());
		} catch (SQLException e) {
			
			System.err.println("Error in fun insertTweet2DB of class TwitterDBHandle" );
//			e.printStackTrace();
//			
//			System.err.println("InsertDB error SQL  #" +  newTwt.getStrFromUsrName() + " " 
//					+ newTwt.getStrToUsr() + " " + newTwt.getStrText());	
		}  finally
		{			
			return iCode;
		}
	}
	
	public void ClearPreparedStatement()
	{
		try {
			if (pstmt != null)
			{
				pstmt.close();
				pstmt = null;
			}
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void ClearResultSet()
	{
		try {
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insertFriends2DB(long userId, ArrayList<Long> friendsId)
	{
		if(friendsId == null || IsExistInFollowersTable(userId))
			return;
		
		String INSERT_RECORD = "insert into twitter.friends"  + 
							"(userId, friendId) " +
							"values(?, ?)";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD);
			
			for(Long l : friendsId)
			{
				pstmt.setLong(1, userId);
				pstmt.setLong(2, l);
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			ClearPreparedStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	public void insertFollowers2DB(long userId, ArrayList<Long> followersId)
	{
		if(followersId == null || IsExistInFollowersTable(userId))
			return;
		
		String INSERT_RECORD = "insert into twitter.followers"  + 
							"(userId, followerId) " +
							"values(?, ?)";

		try {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_RECORD);
			
			for(Long l : followersId)
			{
				pstmt.setLong(1, userId);
				pstmt.setLong(2, l);
				pstmt.addBatch();
			}
			
			pstmt.executeBatch();
			ClearPreparedStatement();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	
	public boolean IsExistInFriendsTable(long id)
	{
		String sql = "SELECT * FROM twitter.friends" +
				" WHERE usrld = '" + id + "'";
		
		if (conn == null)
		{
			this.database_connection();
		}
	
		boolean bFound = false;
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			
			if(rs.next())
			{
				bFound = true;
			}
			else
			{
				bFound = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			bFound = false;
		} finally
		{
			ClearPreparedStatement();
			ClearResultSet();
			return bFound;
		}
		
	}	
	
	@SuppressWarnings("finally")
	public boolean IsExistInFollowersTable(long id)
	{
		String sql = "SELECT * FROM twitter.followers" +
		" WHERE usrld = '" + id + "'";

		if (conn == null)
		{
			this.database_connection();
		}
	
		boolean bFound = false;
		try {
			pstmt = conn.prepareStatement(sql);
			rs =  pstmt.executeQuery();
			
			if(rs.next())
			{
				bFound = true;
			}
			else
			{
				bFound = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			bFound = false;
		} finally
		{
			ClearPreparedStatement();
			ClearResultSet();
			return bFound;
		}			
	}

	public int InsertBatch(ArrayList<_HyperlinkInfo> list, String table) {
		// TODO Auto-generated method stub
		int iRet = 0;
		for(int i = 0; i < list.size(); i++)
		{
			if(IsContainHyperlink(list.get(i), table))
			{
				list.remove(i);
			}
		}
		String INSERT_RECORD = "insert into twitterandhyperlink."  + 
						table + 
						"(codedHyperlink, longHyperlink, HyperlinkContent) " +
						"values(?, ?, ?)";
		//System.out.println("#2" + INSERT_RECORD);
		try 
		{
			pstmt = conn.prepareStatement(INSERT_RECORD);
			
			for (_HyperlinkInfo twt : list)
			{		
				pstmt.setString(1, twt.getUrl());
				pstmt.setString(2, twt.getLongUrl());
				pstmt.setString(3, twt.getContent());
				
				pstmt.addBatch();	
				iRet++;
			}
			
			pstmt.executeBatch();
			
			ClearPreparedStatement();
			return iRet;
		}catch (SQLException e)
		{
			System.err.println("mysql error in insert list of tweets");
			e.printStackTrace();
			return iRet;
		}
	}
	
	public boolean IsContainHyperlink(String url,
			String table) 
	{
		// TODO Auto-generated method stub
		String sql = "SELECT codedHyperlink From twitterandhyperlink." +
				table + " WHERE codedHyperlink = ?";
				
		if (conn == null)
		{
			this.database_connection();
		}
	
		boolean bFound = false;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, url);
			rs =  pstmt.executeQuery();
			
			if(rs.next())
			{
				bFound = true;
			}
			else
			{
				bFound = false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			bFound = false;
		} finally
		{
			ClearPreparedStatement();
			ClearResultSet();
			return bFound;
		}			
	}

	public boolean IsContainHyperlink(_HyperlinkInfo hyper,
			String table) 
	{
			return IsContainHyperlink(hyper.getUrl(), table);
		
	}
	
}
