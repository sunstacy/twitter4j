package gh.polyu.user;

import gh.polyu.database.TwitterDBHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SaveUser {
	public static void main(String[] args) throws IOException, SQLException
	{
		final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		HashSet<String> user = new HashSet<String>();
		Connection conn = handle.conn;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from User");
		while(rs.next())
		{
			String s = rs.getString(1);
			user.add(s);
		}
		ArrayList<String> u = new ArrayList<String>();
		Iterator it = user.iterator();
		int count = 0;
		while(it.hasNext())
		{
			String s= (String) it.next();
			u.add(s);
			if(count%10000==0)
			{
				handle.insertUser("UserItem", u);
				u.clear();
				System.out.println("saved users" + count);
			}
			count++;
			
		}
		handle.insertUser("UserItem", u);
		System.out.println("saved users" + count);
		
	}

}
