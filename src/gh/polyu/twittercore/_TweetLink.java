package gh.polyu.twittercore;

import java.util.Date;

import javax.xml.crypto.Data;

public class _TweetLink {
	long TweetID;
	long TweetUser;
	String UserName;
	long OriginID;
	long OriginUser;
	String place;
	long RetweetCount;
	int isFavourate;
	int isRetweet;
	String Text;
	Date Time;
	String Hashtag;
	String URL;
	String UerMention;
	String other;
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public long getTweetID() {
		return TweetID;
	}
	public void setTweetID(long tweetID) {
		TweetID = tweetID;
	}
	public long getTweetUser() {
		return TweetUser;
	}
	public void setTweetUser(long tweetUser) {
		TweetUser = tweetUser;
	}
	public long getOriginID() {
		return OriginID;
	}
	public void setOriginID(long originID) {
		OriginID = originID;
	}
	public long getOriginUser() {
		return OriginUser;
	}
	public void setOriginUser(long originUser) {
		OriginUser = originUser;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public long getRetweetCount() {
		return RetweetCount;
	}
	public void setRetweetCount(long l) {
		RetweetCount = l;
	}
	public int isFavourate() {
		return isFavourate;
	}
	public void setFavourate(int isFavourate) {
		this.isFavourate = isFavourate;
	}
	public int isRetweet() {
		return isRetweet;
	}
	public void setRetweet(int isRetweet) {
		this.isRetweet = isRetweet;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	public String getHashtag() {
		return Hashtag;
	}
	public void setHashtag(String hashtag) {
		Hashtag = hashtag;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getUerMention() {
		return UerMention;
	}
	public void setUerMention(String uerMention) {
		UerMention = uerMention;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}

}
