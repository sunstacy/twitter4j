package gh.polyu.twitter4j;


import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import clientserver.Key;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.ning.http.client.oauth.ConsumerKey;
import com.ning.http.client.oauth.OAuthSignatureCalculator;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.RequestToken;
import twitter4j.internal.http.HttpClientWrapper;
import twitter4j.internal.http.HttpResponse;

public class twitterOAuth {

	/**
     * Usage: java  twitter4j.examples.oauth.GetAccessToken [consumer key] [consumer secret]
     *
     *
     * @param args message
     */
	
	public Properties prop = new Properties();
	private File file = null;
	
	
	public void ReadProperties()
	{
		InputStream is = null;
        try {
        	file = new File("properties/twitter4j.properties");
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }            
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
        }
	}
	
	public void ReadProperties(String strfile)
	{
		file = new File(strfile);
		
		InputStream is = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
            }            
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.exit(-1);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ignore) {
                }
            }
        }
	}
	
	static boolean bAuthorized = false;
	public int Authority(Twitter twitter, String consumerKey, String consumerSecret)
	{
		twitterOAuth.bAuthorized = true;
			    
		try { 
			System.out.println(consumerKey + consumerSecret);
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
           
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            AccessToken accessToken = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                try {
                    System.out.println(requestToken.getAuthorizationURL());
                }  catch (Exception e) {
                }
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
            System.out.println("Got access token.");
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());
            
            return 1;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get accessToken: " + te.getMessage());
            //System.exit(-1);
            return 0;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            //System.exit(-1);
            return 0;
        }          
	}
	public void Authority(TwitterStream twitter)
	  {     
	      twitter.setOAuthConsumer(this.prop.getProperty("consumerKey"),
	        this.prop.getProperty("consumerSecret"));
	      AccessToken at = new AccessToken(this.prop.getProperty("accessToken"),
	        this.prop.getProperty("accessSecret"));    
	      twitter.setOAuthAccessToken(at);
	      
	  }
	    
	    public void Authority(Twitter twitter)
	  {     

	      twitter.setOAuthConsumer("eCllMp1Pxk9F3mXpelGadQ",
	        "QrdOhApd9noQF02dvavbAEhVMpZFmh995jLceh15Q");
	      AccessToken at = new AccessToken("1637984935-HITtm1DxNvourVRRupnBj3ydSXxNjEGrjRZrJaP",
	        "W2wjgRcauQEZDK6VlglBfAiLJCcTVR68H3qAdLAtE");    
	      twitter.setOAuthAccessToken(at);
	      
	  }
	    
	    public void AuthoritywithS(TwitterStream twitter,Key key)
		  {     
		      twitter.setOAuthConsumer(key.getKey(),key.getValue());
		      AccessToken at = new AccessToken(key.getaToken(),key.getcToken());    
		      twitter.setOAuthAccessToken(at);
		      
		  }
		    
		    public void AuthoritywithS(Twitter twitter,Key key)
		  {     
		      twitter.setOAuthConsumer(key.getKey(),key.getValue());
		      AccessToken at = new AccessToken(key.getaToken(),key.getcToken());    
		      twitter.setOAuthAccessToken(at);
		      
		  }
	    

	public int Authority(TwitterStream twitter, String consumerKey, String consumerSecret)
	{
		twitterOAuth.bAuthorized = true;
			    
		try { 
			System.out.println(consumerKey + consumerSecret);
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
           
            RequestToken requestToken = twitter.getOAuthRequestToken();
            System.out.println("Got request token.");
            System.out.println("Request token: " + requestToken.getToken());
            System.out.println("Request token secret: " + requestToken.getTokenSecret());
            AccessToken accessToken = null;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (null == accessToken) {
                System.out.println("Open the following URL and grant access to your account:");
                System.out.println(requestToken.getAuthorizationURL());
                try {
                    System.out.println(requestToken.getAuthorizationURL());
                } catch (Exception ignore) {
                } 
                System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                String pin = br.readLine();
                try {
                    if (pin.length() > 0) {
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    } else {
                        accessToken = twitter.getOAuthAccessToken(requestToken);
                    }
                } catch (TwitterException te) {
                    if (401 == te.getStatusCode()) {
                        System.out.println("Unable to get the access token.");
                    } else {
                        te.printStackTrace();
                    }
                }
            }
            System.out.println("Got access token.");
            System.out.println("Access token: " + accessToken.getToken());
            System.out.println("Access token secret: " + accessToken.getTokenSecret());
            
            return 1;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get accessToken: " + te.getMessage());
            //System.exit(-1);
            return 0;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            //System.exit(-1);
            return 0;
        }          
	}
	
	public static boolean isbAuthorized() {
		return bAuthorized;
	}
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) 
	{     
		
		Twitter twitter = new TwitterFactory().getInstance();
		
		twitterOAuth twtOauth = new twitterOAuth();	
		twtOauth.ReadProperties("properties/twitter4j.properties");
		twtOauth.Authority(twitter, twtOauth.prop.getProperty("consumerKey"), 
				twtOauth.prop.getProperty("consumerSecret"));
		
		System.out.println(System.currentTimeMillis());
		
    }

	

}
