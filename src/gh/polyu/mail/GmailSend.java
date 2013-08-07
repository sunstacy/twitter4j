package gh.polyu.mail;

import java.security.Security;  
import java.util.ArrayList;
import java.util.Properties;  
  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;
import com.sun.net.ssl.internal.ssl.*;

public class GmailSend {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";  
    private static final String SMTP_PORT = "465";     
    private static String emailFromAddress = "";  
    private static String emailFromKey = "";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";  
    private static Session session = null;
	
    
    
    public GmailSend(String strFromMailAddress, String strKey)
    {
    	GmailSend.emailFromAddress = strFromMailAddress;
    	GmailSend.emailFromKey     = strKey;
    	Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider()); 
    	InitialGmailInfo();
    }
     
    public void InitialGmailInfo()
    {
    	boolean AuthorizationDebug = true;  
  	    boolean SessionDebug = true;
        Properties props = new Properties();  
        props.put("mail.smtp.host", SMTP_HOST_NAME);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.debug", AuthorizationDebug);  
        props.put("mail.smtp.port", SMTP_PORT);  
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);  
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);  
        props.put("mail.smtp.socketFactory.fallback", "false");  
        
        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {          	  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(GmailSend.emailFromAddress, GmailSend.emailFromKey);  
            }  
        });
        session.setDebug(SessionDebug); 
        
        
    }
    
    public void SendSSLMessage(String recipinet,
    		String subject, 
    		String message
    		)throws MessagingException
    {
    	Message msg = new MimeMessage(session);  
        InternetAddress addressFrom = new InternetAddress(GmailSend.emailFromAddress);  
        msg.setFrom(addressFrom);         
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipinet));        
        msg.setSubject(subject);  
        msg.setContent(message, "text/plain");  
        Transport.send(msg); 
    }
    public void send(String title,String content)
    {
    	
    	try {
			this.SendSSLMessage("cscchenyoyo@gmail.com", title, content);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
  

	
}
