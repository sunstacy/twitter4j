package clientserver;


import java.io.BufferedInputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.InetAddress;
import java.net.Socket;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
  
public class client {  
      
    private final static Logger logger = Logger.getLogger(client.class.getName());  
    
    public static void main(String[] args) throws Exception {  
        while(true) {  
            Socket socket = null;  
            ObjectOutputStream os = null;  
            ObjectInputStream is = null;  
            
            try {  
                socket = new Socket(InetAddress.getLocalHost(), 10000);  
                os = new ObjectOutputStream(socket.getOutputStream());
                is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
                Object obj = is.readObject();  
                Key user = new Key();
                if (obj != null) {  
                    user = (Key)obj;  
                }  
                if(user.getKey().equals("ready?"))
                {
                	System.out.println("receive ready");
                user.setKey("go");
                os.writeObject(user);
                }
                
            } catch(IOException ex) {  
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
    }  
}  
