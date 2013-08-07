package gh.polyu.twittercore;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import com.google.common.base.Preconditions;

public class FollowersOfUser  implements Writable {

	private static final byte VERSION = 1;

	private byte version;
	private int httpStatusCode;
	private long timestamp;
	private String html;

	
	
	public FollowersOfUser()
	{
		this.version = VERSION;
	}
	
	public FollowersOfUser(int httpStatusCode, long timestamp, String html) {
	    this.version = VERSION;
	    this.httpStatusCode = httpStatusCode;
	    this.timestamp = timestamp;
	    this.html = Preconditions.checkNotNull(html);
	}
	
	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
