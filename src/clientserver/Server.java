package clientserver;

import gh.polyu.database.TwitterDBHandle;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {  
	  
    private final static Logger logger = Logger.getLogger(Server.class.getName());  
      
    public static void main(String[] args) throws IOException, SQLException {
    	/*
    	 * database connection
    	 */
    	final TwitterDBHandle handle = new TwitterDBHandle();
		handle.intialTwitterDBhandle();
		handle.database_connection();
		Connection conn = handle.conn;
		final Statement s = conn.createStatement();
		
		ArrayList<Long> WaitID = new ArrayList<Long>();
		ArrayList<Long> finishID = new ArrayList<Long>();
		//ResultSet rs = s.executeQuery("select * from myTweet.user");
		//获得全部的user
		//while(rs.next())
		//{
		//	WaitID.add(Long.parseLong(rs.getString(1)));
		//}
		ArrayList<Key> info = new ArrayList<Key>();
		//for(int i = 0; i< 100; i++)
		//{
		//	Key in = new Key();
			/*
			 * 加入key
			 */
		//	info.add(in);
		//}
		
		
		
        ServerSocket server = new ServerSocket(10000);  
  
        while (true) {  
            Socket socket = server.accept();  
            invoke(socket, WaitID, finishID,info);  
        }
    }  
  
    private static void invoke(final Socket socket, final ArrayList<Long> WaitID, ArrayList<Long> finishiID,final ArrayList<Key> info) throws IOException {  
        new Thread(new Runnable() {  
            public void run() {  
                ObjectInputStream is = null;  
                ObjectOutputStream os = null;  
                int count = 1;
                try {  
                    is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
                    os = new ObjectOutputStream(socket.getOutputStream());  


                    Object obj = is.readObject();  
                    Key user = (Key)obj; 
                	
                	Key k = new Key();
                	k.setKey("start" + count);
                	os.writeObject(k);
                	os.flush();
                	count++;
                	Object read = is.readObject();  
                    Key u = new Key();
                    if ( read!= null) {  
                        u = (Key)read;  
                    if(u.getError()==0)//no error
                    {
                       /*
                        * cash to DB	
                        * key放到队尾
                        */
                    }
                    else if(u.getError() == 1)//error of timeout
                    {
                    	/*
                    	 * 信息存入数据库
                    	 * 将为处理的waitId存入waitId中
                    	 * key可以放到队尾
                    	 */
                    }
                    else if(u.getError() ==2)//网络断开
                    {
                    	
                    }
                    }
      
     
 

                    
                } catch (IOException ex) {  
                    logger.log(Level.SEVERE, null, ex);  
                } catch(ClassNotFoundException ex) {  
                    logger.log(Level.SEVERE, null, ex);  
                } finally {  
                    try {  
                        is.close();  
                    } catch(Exception ex) {}  
                    try {  
                        os.close();  
                    } catch(Exception ex) {}  
                    try {  
                        socket.close();  
                    } catch(Exception ex) {}  
                }  
            }  
        }).start();  
    }  
    static class TestServer extends Thread
	{

		Socket client  = null ;
		
		public TestServer(){
			
		}
		
		public TestServer(Socket client)
		{
			this.client = client ;
		}
		
		@Override
		public void run() {
			
			try
			{
				InputStream is = this.client.getInputStream();
				while(true)
				{
					if (this.client.isClosed()
							|| this.client.isConnected() == false)
						break;
	
					try {
						// 方法sendUrgentData，往输出流发送一个字节的数据
						// 只要对方
						// Socket的SO_OOBINLINE属性没有打开，就会自动舍弃这个字节，而SO_OOBINLINE属性默认情况下就是关闭的
						// 如果客户端 开启了 ，就需要处理
						int time = 1000;
						ObjectOutputStream os = new ObjectOutputStream(this.client.getOutputStream());  
						Key user = new Key();
						user.key = "Avaliable";
						os.writeObject(user);
						System.out.println(Thread.currentThread().getName()+"=>"+"心跳测试正常!");
					} catch (Exception ex) {
						System.out.println(Thread.currentThread().getName()+"=>"+"心跳测试异常!");
						break;
					}
					byte []data =  new byte[13];
					is.read(data);
					System.out.println(Thread.currentThread().getName()+"=>"+new String(data));
				}
			}
			catch(Exception e)
			{
				System.out.println(e.toString());
			}
			finally
			{
				System.out.println(Thread.currentThread().getName()+"=>"+"客户端关闭");
			}
		}
		
	}
}  