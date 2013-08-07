package gh.polyu.za;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.twitter4j.twitterOAuth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import clientserver.Key;

/*
 * load twitter developer key to DB
 * 
 * 
 */
public class SaveKey {
	public void loadKey(ArrayList<Key> keyList) throws SQLException {
		for(int i = 0 ; i< keyList.size();i++)
		{
			Key key = keyList.get(i);
		   System.out.println(key.getKey()+key.getValue()+key.getaToken()+key.getcToken());
		   Twitter twitter = new TwitterFactory().getInstance();
		   twitterOAuth twtOauth = new twitterOAuth();
		   twtOauth.AuthoritywithS(twitter, key);
		   int flag = 0;
		   String[] usrlist = new String[]{"iphoneappstorm"};   
           try {
			ResponseList<User> users = twitter.lookupUsers(usrlist);
		} catch (TwitterException e) {
			   System.err.println("error"+key.getKey());
        	   keyList.remove(i);
        	   i--;
		}

		}
		final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		Connection conn = handle.conn;

		handle.insertKey("KeySet", keyList);
	}
	public ArrayList<Key> readfile(String file) throws IOException
	{
		ArrayList<Key> key = new ArrayList<Key>();
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String reader;
		int count = 0;
		
		int num = 0;
		String s = "";
		while((reader = bf.readLine())!=null)
		{
			if(reader.length()>0)
			{
				if(reader.startsWith("SOURCE"))
				{
					s = reader.substring(6,reader.length());
				}
				else
				{
			   if(count == 0)
			   {
				   Key k = new Key();
				   k.setWaittime(s);
				   k.setKey(reader.trim());
				   key.add(k);
				   System.out.println(reader.trim());
				   
			   }
			   else if(count == 1)
			   {
				   //int a = reader.lastIndexOf("\t");
				   ///String s = reader.substring(a+1,reader.length());
				   System.out.println(reader.trim());
				   key.get(num).setValue(reader.trim());
			   }
			   else if (count==2)
			   {
				   //int a = reader.lastIndexOf("\t");
				  // String s = reader.substring(a+1,reader.length());
				   System.out.println(reader.trim());
				   key.get(num).setaToken(reader.trim());
			   }
			   else if (count==3)
			   {
				   //int a = reader.lastIndexOf("\t");
				   //String s = reader.substring(a+1,reader.length());
				   System.out.println(reader.trim());
				   key.get(num).setcToken(reader.trim());
				   count = -1;
				   num++;
					System.out.println(num);
			   }
			   count++;
			}
			}
			
		}
		return key;
	}
	public static void main(String[] args) throws IOException, TwitterException, SQLException
	{
		SaveKey sk= new SaveKey();
		ArrayList<Key> key = sk.readfile("D:/MyWork/twittertest/twitter4j/data/key/new.txt");
		System.out.println(key.toString());
		sk.loadKey(key);
	}

}
