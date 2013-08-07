package gh.polyu.twittercore;

public class _HyperlinkInfo {

	int code = 0; //-1: not found 0: do not start 1: start 2:ready
	String url = "";
	String longUrl = "";
	String content = "";
	String table = "";
	
	public _HyperlinkInfo()
	{
		
	}
	
	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public _HyperlinkInfo(int code, String url, 
			String longUrl, String content)
	{
		this.code = code;
		this.url = url;
		this.longUrl = longUrl;
		this.content = content;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
}
