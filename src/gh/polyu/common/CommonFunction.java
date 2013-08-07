package gh.polyu.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;


public class CommonFunction {
	
	static public String GetYear()
	{
		Date   date = new  Date();
		return String.valueOf(date.getYear() + 1900);
	}
	
	static public String GetMonth()
	{
		Date   date = new  Date();
		String strMonth = String.valueOf(date.getMonth() + 1);
		if(strMonth.length() == 1)
			strMonth = "0" + strMonth;
		
		return strMonth;
	}
	
	static public String Object2Json(Object o)
	{
		return JSONObject.fromObject(o).toString();
	}
	
	static public ArrayList<String> GetLinksFromTweet(String text)
	{
		String strRxRp = "http://.*?\\s";
		Pattern patRp  = Pattern.compile(strRxRp);
		Matcher mthFd  = patRp.matcher(text);
		ArrayList<String> url = new ArrayList<String>();
		
		while(mthFd.find())
		{
			//System.out.println(mthFd.group());
			String link = mthFd.group().trim();
			url.add(link);
		} 
		return url;
	}
	
	static public BigDecimal ConvertPrecision(double d, int cnt)
	{
		BigDecimal   bd   =   new   BigDecimal(d); 
		bd   =   bd.setScale(cnt, BigDecimal.ROUND_HALF_UP);
		return bd;
	}
	
	public static Properties GetProperties(String strFile)
	{
		Properties prop = new Properties();
		File file = new File(strFile);
		InputStream is = null;		
	
        try {
        	if (file.exists()) {
        		is = new FileInputStream(file);
        		prop.load(is);        		
            }
        	return prop;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static int RegularExpressionCount(
			String strText, String strRx)
	{
		int iCnt = 0;
		Pattern pCyberWrd = Pattern.compile(strRx); 
		Matcher mCyberWrd = pCyberWrd.matcher(strText);
		
		while(mCyberWrd.find())
		{
			iCnt++;
		}
		
		return iCnt;
	}

	static long lBeginTime;
	static int  Field_Min = 60;
	static int  Field_Hour = 3600;
	
	
	static public void PreRunBegin()
	{
		CommonFunction.lBeginTime = System.currentTimeMillis();
	}
	
	static public int GetRunTimer(int iField)
	{
		return (int)(System.currentTimeMillis() - CommonFunction.lBeginTime)/1000/iField;
	}
	
	static public int GetRunHour()
	{
		return (int)(System.currentTimeMillis() - CommonFunction.lBeginTime)/1000/CommonFunction.Field_Hour;
	}
	
	static public int GetRunMin()
	{
		return (int)(System.currentTimeMillis() - CommonFunction.lBeginTime)/1000/CommonFunction.Field_Min;
	}
	
	
	
	static public void IsExistFileDel(String strFile)
	{
		File fl = new File(strFile);
		if(fl.exists())
		{
			fl.delete();
		}		
	}
	
	static public boolean IsAllLetter(String str)
	{
			
		for(int i=0;i<str.length();i++)
		{	
			if(!Character.isLetter(str.charAt(i)))
			{	
				return false;
			}
		}
		return true;
	}
	
	static public boolean IsAllNumber(String str)
	{
			
		for(int i=0;i<str.length();i++)
		{	
			if(!Character.isDigit(str.charAt(i)))
			{	
				return false;
			}
		}
		return true;
	}
	
	static public void MakeDir(String strDir)
	{
		File fCateDir = new File(strDir);
		if (!fCateDir.isDirectory())
		{
			fCateDir.mkdirs();
		}
	}
	
	static public String GetCurrentTime()
	{
		return new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss").format(new java.util.Date());

	}
	
	static public boolean Punctuation_RegularExpression(char ch)
	{
		String strCharacter = String.format("%c", ch);
		String regExPuncture = "[\\pP]";
		Pattern pPuncture    = Pattern.compile(regExPuncture);
		Matcher mPuncture    = pPuncture.matcher(strCharacter);
		boolean rstPuncture  = mPuncture.find();
		if (rstPuncture)
		{
			return true;
		} else
		{
			return false;
		}
	}
	
	static public int UnreadableUnicoadeCount_RegularExpression(String str)
	{
		int iCount = 0;
		for(int i = 0; i < str.length(); i++)
		{	
			char ch = str.charAt(i);
			
			if (CommonFunction.Punctuation_RegularExpression(ch))
				continue;			
			if(ch > 127)
			{
				iCount++;
				//System.out.print("#" + ch + " ");
			}			
		}
		
		return iCount;
	}
	
	static public int UnreadableUnicoadeCount_Charactor(String str)
	{
		int iCount = 0;
		for(int i = 0; i < str.length(); i++)
		{				
			if(str.charAt(i) > 127)
			{	
				//System.out.print("#" + (int)str.charAt(i) + " ");
				iCount++;
			}
		}
//		if(iCount > 0)
//			System.out.println(str);
		return iCount;
	}
	
}
