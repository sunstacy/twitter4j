package gh.polyu.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Locale;

public class CommonFunctionDateFormat {

	static public String Date2String(java.util.Date dt, String format)
	{
		//format = "yyyy-MMM-dd-HH-mm-ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(dt);
	}
	
	
	static public Timestamp String2Timestamp(String strText)
	{
		if (strText.isEmpty() 
				|| strText.equals("null")
				|| strText.length() == 0)
		{
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		java.util.Date date = new java.util.Date();
		try {
			date  =  sdf.parse(strText);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(date.getTime());
	}
}
