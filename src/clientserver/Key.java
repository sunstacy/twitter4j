package clientserver;

import gh.polyu.twittercore._Relation;

import java.util.ArrayList;

public class Key  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	String key; 
	String value;
	String aToken;
	String cToken;
	int status;
	String waittime;
	ArrayList<_Relation> relation;
	ArrayList<Long> waitId;
	int error;
	public void key(String key, String value, String aToken,String cToken, int status)
	{
		this.key = key;
		this.value = value;
		this.aToken = aToken;
		this.cToken = cToken;
		this.status = status;
		this.waitId = new ArrayList<Long>();
		this.relation = new ArrayList<_Relation>();
	}
	public Key clear(Key key)
	{
		key.relation.clear();
		key.waitId.clear();
		return key;
		
	}
	
	public void empty()
	{
		this.setaToken("");
		this.setcToken("");
		this.setKey("");
		this.setValue("");
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getaToken() {
		return aToken;
	}
	public void setaToken(String aToken) {
		this.aToken = aToken;
	}
	public String getcToken() {
		return cToken;
	}
	public void setcToken(String cToken) {
		this.cToken = cToken;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<_Relation> getRelation() {
		return relation;
	}
	public void setRelation(ArrayList<_Relation> relation) {
		this.relation = relation;
	}
	public ArrayList<Long> getWaitId() {
		return waitId;
	}
	public void setWaitId(ArrayList<Long> waitId) {
		this.waitId = waitId;
	}
	public int getError() {
		return error;
	}
	public void setError(int error) {
		this.error = error;
	}
	public String getWaittime() {
		return waittime;
	}
	public void setWaittime(String waittime) {
		this.waittime = waittime;
	}

	

}
