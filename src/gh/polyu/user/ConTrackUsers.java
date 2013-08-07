package gh.polyu.user;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.mail.GmailSend;
import gh.polyu.twitter4j.twitterOAuth;
import gh.polyu.za.CmdOption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import clientserver.Key;

public class ConTrackUsers {
	int begin_id;
	int end_id;
	int program;
	public ConTrackUsers(int begin_id, int end_id, int program)
	{
		this.begin_id = begin_id;
		this.end_id = end_id;
		this.program = program;
		}
	public int getMyInt(int a,int b) {
		return(((double)a/(double)b)>(a/b)?a/b+1:a/b);
		}
	public void con() 
	{
		int p = program;
		int key_begin_id = (begin_id/5000)+1;
		System.out.print(key_begin_id);
		int key_end_id = this.getMyInt((end_id-begin_id),5000)+key_begin_id-1;
		TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		Connection conn = handle.conn;
		
		ArrayList<Key> keyset = new ArrayList<Key>();
		try{
		Statement st = conn.createStatement();
		String sql = "select * from KeySet where id between "+key_begin_id+" and " +key_end_id;
		//System.out.println(sql);
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next())
		{
			Key k = new Key();
			k.setKey(rs.getString(2));
			k.setValue(rs.getString(3));
			k.setaToken(rs.getString(4));
			k.setcToken(rs.getString(5));
			//System.out.println(k.getKey()+" "+k.getValue()+" "+k.getaToken()+" "+k.getcToken());
			keyset.add(k);
			
		}
		}
		
		catch(SQLException e)
		{
			System.out.println("table keyset error");
		}
		System.out.println("keyset size :"+ keyset.size());
		handle.close_databasehandle();
		int count = this.getMyInt((end_id-begin_id+1),keyset.size());//每一个账号监控的user数目
		//System.out.println(count);
			
		for(int i = 0; i< keyset.size();i++)
		{
			
			handle.database_connection();
			long[] follow = new long[count];
			try{
			//TwitterStream twitter = new TwitterStreamFactory().getInstance();
			//twitterOAuth twtOauth = new twitterOAuth();
			//twtOauth.AuthoritywithS(twitter, keyset.get(i));
			Statement st2 = handle.conn.createStatement();
			String sql2 = "";
			if(i<(keyset.size()-1))
			    sql2 = "select * from User where id between "+( begin_id + i*count) +" and "+(begin_id+count*(i+1));
			else
				sql2 = "select * from User where id between "+( begin_id + i*count) +" and "+(end_id);
	        System.out.println(sql2);
			ResultSet rs1 = st2.executeQuery(sql2);//跟踪的userid
	        
	        int j = 0;
	        while(rs1.next())
	        {
		        String user = rs1.getString(2);
		        follow[j] = (Long.valueOf(user));
		        //System.out.println("No"+j+user);
		        j++;
	        }
			}
			catch(SQLException e)
			{
				System.out.println("UserItem" + e.getMessage());
			}
			handle.close_databasehandle();
			TrackUsers track = new TrackUsers(follow,keyset.get(i));
				track.track(i,p);
	}
}

	public static void main(String[] args)
	{
		CmdOption option = new CmdOption();
		CmdLineParser parser = new CmdLineParser(option);
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ConTrackUsers con = new ConTrackUsers(option.fromID,option.toID,option.program);
		con.con();
	}
}
