package gh.polyu.twittercore;

public class _TrendInfo {
	
	private String trendID;
	private String trendName;
	private String trendCate;
	private String trendCreatedDate;
	private long startTime = System.currentTimeMillis();
	
	public int getExistingHour()
	{
		return (int) ((System.currentTimeMillis() - startTime)/1000/3600);
	}
	
	public void setTrendID(String trendID) {
		this.trendID = trendID;
	}
	public String getTrendID() {
		return trendID;
	}
	public void setTrendCate(String trendCate) {
		this.trendCate = trendCate;
	}
	public String getTrendCate() {
		return trendCate;
	}
	
	public void setTrendCreatedDate(String trendCreatedDate) {
		this.trendCreatedDate = trendCreatedDate;
	}
	public String getTrendCreatedDate() {
		return trendCreatedDate;
	}
	public void setTrendName(String trendName) {
		this.trendName = trendName;
	}
	public String getTrendName() {
		return trendName;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getStartTime() {
		return startTime;
	}

}
