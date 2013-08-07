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
		//���ȫ����user
		//while(rs.next())
		//{
		//	WaitID.add(Long.parseLong(rs.getString(1)));
		//}
		ArrayList<Key> info = new ArrayList<Key>();
		//for(int i = 0; i< 100; i++)
		//{
		//	Key in = new Key();
			/*
			 * ����key
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
                        * key�ŵ���β
                        */
                    }
                    else if(u.getError() == 1)//error of timeout
                    {
                    	/*
                    	 * ��Ϣ�������ݿ�
                    	 * ��Ϊ�����waitId����waitId��
                    	 * key���Էŵ���β
                    	 */
                    }
                    else if(u.getError() ==2)//����Ͽ�
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
						// ����sendUrgentData�������������һ���ֽڵ�����
						// ֻҪ�Է�
						// Socket��SO_OOBINLINE����û�д򿪣��ͻ��Զ���������ֽڣ���SO_OOBINLINE����Ĭ������¾��ǹرյ�
						// ����ͻ��� ������ ������Ҫ����
						int time = 1000;
						ObjectOutputStream os = new ObjectOutputStream(this.client.getOutputStream());  
						Key user = new Key();
						user.key = "Avaliable";
						os.writeObject(user);
						System.out.println(Thread.currentThread().getName()+"=>"+"������������!");
					} catch (Exception ex) {
						System.out.println(Thread.currentThread().getName()+"=>"+"���������쳣!");
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
				System.out.println(Thread.currentThread().getName()+"=>"+"�ͻ��˹ر�");
			}
		}
		
	}
}  