/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gh.polyu.twittercore;

import gh.polyu.common.CommonFunction;
import gh.polyu.common.CommonFunctionDateFormat;
import java.sql.Timestamp;
import java.util.Date;


import twitter4j.Status;

/**
 *
 * @author danny
 */
public class _TweetInfo {

	long    lId = 0;
	Date 	dtd = new Date();
	Timestamp tweetTimestamp = null;
    String 	strDate = "";
	String 	strTrend = "";
    long 	lFromUsr;
    String 	strFromUsrName = "";
    long    lToUsr = -1;
    String  strToUsr = "";
    String 	strText;
    String  strJson = "";
    
    public void _TweetInfo(String id, Timestamp stamp,
    		String strText)
    {
    	this.lId = Long.parseLong(id);
    	this.tweetTimestamp = stamp;
    	this.strText = strText;
    }
    
    public String getStrJson() {
		return strJson;
	}

	public void setStrJson(String strJson) {
		this.strJson = strJson;
	}

	public _TweetInfo(Status t)
    {
    	Tweet2TweetInfo(t);
    }
    
    public _TweetInfo(Status t, String trending)
    {
    	Tweet2TweetInfo(t, trending);
    }
    
    public void Tweet2TweetInfo(Status t, String trending)
	{
    	if(t.getUser() != null)
    	{
    		this.setlFromUsr(t.getUser().getId());
    		this.setStrFromUsrName(t.getUser().getName());
    	}
				
		this.setStrTrend(trending);
		
		this.setTweetTimestamp(
				CommonFunctionDateFormat.String2Timestamp
				(t.getCreatedAt().toString()));
		     
        this.setlId(t.getId());        
		this.setStrText(t.getText());	
		//this.setStrJson(CommonFunction.Object2Json(t));
		
	}
    
    public void Tweet2TweetInfo(Status t)
	{
    	if(t.getUser() != null)
    	{
    		this.setlFromUsr(t.getUser().getId());
    		this.setStrFromUsrName(t.getUser().getName());
    	}
		
		this.setTweetTimestamp(
				CommonFunctionDateFormat.String2Timestamp
				(t.getCreatedAt().toString()));
		
        this.setlId(t.getId());        
		this.setStrText(t.getText());	
		this.setStrJson(CommonFunction.Object2Json(t));
		
	}

    public long getlId() {
		return lId;
	}

	public void setlId(long lId) {
		this.lId = lId;
	}

    public Timestamp getTweetTimestamp() {
		return tweetTimestamp;
	}

	public void setTweetTimestamp(Timestamp tweetTimestamp) {
		this.tweetTimestamp = tweetTimestamp;
	}

    
    public long getlToUsr() {
		return lToUsr;
	}

	public void setlToUsr(long lToUsr) {
		this.lToUsr = lToUsr;
	}

	public String getStrToUsr() {
		return strToUsr;
	}

	public void setStrToUsr(String strToUsr) {
		this.strToUsr = strToUsr;
	}    
    public Date getDtd() {
        return dtd;
    }

    public void setDtd(Date dtd) {
        this.dtd = dtd;
    }
  
    
    public long getlFromUsr() {
        return lFromUsr;
    }

    public void setlFromUsr(long lUsr) {
        this.lFromUsr = lUsr;
    }

    public String getStrText() {
        return strText;
    }

    public void setStrText(String strText) {
        this.strText = strText;
    }

    public String getStrTrend() {
        return strTrend;
    }

    public void setStrTrend(String strTrend) {
        this.strTrend = strTrend;
    }

    public String getStrFromUsrName() {
        return strFromUsrName;
    }

    public void setStrFromUsrName(String strUsrName) {
        this.strFromUsrName = strUsrName;
    }
      
    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public _TweetInfo(){}

    public void setDt(Date parse) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
