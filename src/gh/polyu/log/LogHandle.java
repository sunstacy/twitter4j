package gh.polyu.log;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogHandle {
	
	static public void LongFileInitial()
	{
		String strFile = "./log.html";
		File fl = new File(strFile);
		if (fl.exists())
		{
			fl.delete();
		}
	}
	
	static public void LogWriteline(String strLine)
	{
		String strFile = "./log.html";
		
		try {
			PrintWriter pw = new PrintWriter (new FileWriter(strFile, true), true);
			
			pw.append(strLine + "\n");
			pw.flush();
			
			if(pw != null) pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
