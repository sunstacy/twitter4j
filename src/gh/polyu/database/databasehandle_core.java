package gh.polyu.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class databasehandle_core 
			implements databasehandle_core_support{

	public ResultSet rs = null;
	public Statement stmt = null;
	public Connection conn = null;
	public PreparedStatement pstmt = null;
	
	//---IP and Schame name
	private String strip = null;
	private String strPort = null;	
	private String strSchm = null;
	private String strUsr  = null;
	private String strPwd  = null;

	@Override
	public int initial_databasehandle(Properties prop) {
		// TODO Auto-generated method stub
		if (prop == null)
			return -1;
		//System.err.println("ip is :" + prop.getProperty("ip"));
		this.setStrSchm(prop.getProperty("schema"));
		this.setStrUsr(prop.getProperty("root"));
		this.setStrPwd(prop.getProperty("key"));
		this.setStrip(prop.getProperty("ip"));
		this.setStrPort(prop.getProperty("port"));
		return 0;
	}

	@Override
	public void close_databasehandle() {
		// TODO Auto-generated method stub
		try {
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			if (stmt != null)
			{
				stmt.close();
				stmt = null;
			}
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error occurs in close connection!");
		}  
	}

	@Override
	public int database_connection() {
		// TODO Auto-generated method stub
		String strName = "jdbc:mysql://" + strip + ":"+ strPort + "/" + strSchm;		
		System.out.println(strName);
		try 
		{
			//load the jdbc
			Class.forName("com.mysql.jdbc.Driver");
			//connect the db
			conn = DriverManager.getConnection(strName, strUsr, strPwd);			
			
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("##Error in \"Class.forName\"");
			return 0;
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("##Error in \"Class.getConnection\"");
			return 0;
		}
		return 1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties prop = new Properties();
		File file = new File("properties/database.properties");
		InputStream is = null;		
	
        try {
        	if (file.exists()) {
        		is = new FileInputStream(file);
        		prop.load(is);        		
            }   
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		databasehandle_core dbhandle = new databasehandle_core();
		dbhandle.initial_databasehandle(prop);
		dbhandle.database_connection();	
          
	}

	
	public void setStrip(String strip) {
		this.strip = strip;
	}

	public String getStrip() {
		return strip;
	}

	public void setStrSchm(String strSchm) {
		this.strSchm = strSchm;
	}

	public String getStrSchm() {
		return strSchm;
	}

	public void setStrPort(String strPort) {
		this.strPort = strPort;
	}

	public String getStrPort() {
		return strPort;
	}

	public void setStrUsr(String strUsr) {
		this.strUsr = strUsr;
	}

	public String getStrUsr() {
		return strUsr;
	}

	public void setStrPwd(String strPwd) {
		this.strPwd = strPwd;
	}

	public String getStrPwd() {
		return strPwd;
	}

}
