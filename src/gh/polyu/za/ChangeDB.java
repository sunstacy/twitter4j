package gh.polyu.za;

import gh.polyu.database.TwitterDBHandle;
import gh.polyu.mail.GmailSend;
import gh.polyu.twittercore._TweetLink;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;

public class ChangeDB {
	public void chang(String table,ArrayList<_TweetLink> listlink)
	{
	TwitterDBHandle handle2 = new TwitterDBHandle();
	handle2.intialTwitterDBhandle2();
	handle2.database_connection();
	try {
		handle2.userTweet(table, listlink);
		handle2.close_databasehandle();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		GmailSend gs = new GmailSend("cscchenyoyo@gmail.com","910316ccy");
    	try {
			gs.SendSSLMessage("cscchenyoyo@gmail.com", "program error", "both databases are down");
		} catch (MessagingException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
	}
	 GmailSend gs = new GmailSend("cscchenyoyo@gmail.com","910316ccy");
    	try {
			gs.SendSSLMessage("cscchenyoyo@gmail.com", "program error", "change database to another one");
		} catch (MessagingException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
}

}
