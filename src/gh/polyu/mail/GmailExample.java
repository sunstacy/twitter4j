package gh.polyu.mail;



import java.util.ArrayList;

import javax.mail.MessagingException;

public class GmailExample {

	public static void main(String args[]) throws Exception 
	{         
		new GmailExample().COMP323Assignment_II("Score of Assignment-II in COMP323P"); 
		//new GmailExample().COMP323FinalScore("Middle-Term Quiz of COMP323P");
		//new GmailExample(). COMP323Notification("Notification for all students of COMP323 Project");
    }  
	
	
	private void COMP323Notification(String strTitle) {
		
		// TODO Auto-generated method stub
		ArrayList<String> listID = new ArrayList<String>();
		ArrayList<String> listScore = new ArrayList<String>();
		String strFileID = ".\\ExtSrc\\JMail-IR\\Comp323_ID.txt";
		JMailCOMP.ReadFileLine(listID, strFileID);
		String strFileScr= ".\\ExtSrc\\JMail-IR\\Comp323_Score.txt";
		JMailCOMP.ReadFileLine(listScore, strFileScr);
		
		GmailSend gmail = new GmailSend("gaodehong.polyu@gmail.com", "comp09903149r");
		
		for (int i = 0 ; i < listID.size(); i++)
		{
			String notification = 
					"Hi, \n\n" +
					"Notification for students of COMP323 Project:\n" +
					"1. Due to the difficulties of assignment 2, we extend the deadline of assignment 2 to 2012-04-15 11:55 PM (Sunday).\n" +
					"2. For assignment 2, the report is required as well. As said in my lab, students can refer the requirements of assignment 1 report.\n" +
					"\t(a) a short written report(no more than 5 pages);\n" +
					"\t(b) including methodology and algorithm used in your program;\n" +
					"\t(c) other functions to improve your input method.\n" +
					"3. The lab content will be included in the final examination, and it will take part in about 5%(5 marks).\n" +
					"\nPlease DO NOT directly reply to this email account.\n" +
					"For any questions, please mail to csdgao@comp.polyu.edu.hk.\n" +
					"\nBest regards,\n\n" +
					"Gao Dehong\n";
			
			String strReceiverAddress = listID.get(i) + "@connect.polyu.hk";			
			strReceiverAddress = strReceiverAddress.toLowerCase();
			try {
				gmail.SendSSLMessage(strReceiverAddress, strTitle, notification);
				System.out.println("Mail Deliver successfully #" + strReceiverAddress + "\n\n");
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.err.println("Mail Delivering failed #" + strReceiverAddress  + "\n\n");
			}
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void COMP323FinalScore(String strTitle)
	{
			ArrayList<String> listID = new ArrayList<String>();
			ArrayList<String> listScore = new ArrayList<String>();
			String strFileID = ".\\ExtSrc\\JMail-IR\\Comp323_ID.txt";
			JMailCOMP.ReadFileLine(listID, strFileID);
			String strFileScr= ".\\ExtSrc\\JMail-IR\\Comp323_Score.txt";
			JMailCOMP.ReadFileLine(listScore, strFileScr);
			
			GmailSend gmail = new GmailSend("gaodehong.polyu@gmail.com", "comp09903149r");
			
			for (int i = 0 ; i < listID.size(); i++)
			{
				String strContentTemplate = "Dear " + listID.get(i) + ":\n\n"									
									+ "Your score of middle-term quiz is " + listScore.get(i)  + "\n"
//									+ "Thanks for taking COMP5324 this sememster.\n" 
									+ "Please DO NOT directly reply to this email account.\n"
									+ "For any questions, please mail to csdgao@comp.polyu.edu.hk.\n\n"
									+ "Best regards,\n\n" 
									+ "Gao Dehong";
				
				String strReceiverAddress = listID.get(i) + "@connect.polyu.hk";			
				strReceiverAddress = strReceiverAddress.toLowerCase();
				try {
					gmail.SendSSLMessage(strReceiverAddress, strTitle, strContentTemplate);
					System.out.println("Mail Deliver successfully #" + strReceiverAddress + "\n\n");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.err.println("Mail Delivering failed #" + strReceiverAddress  + "\n\n");
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	public void COMP323Assignment_II(String strTitle)
	{
			ArrayList<String> listID = new ArrayList<String>();
			ArrayList<String> listScore = new ArrayList<String>();
			String strFileID = ".\\ExtSrc\\JMail-IR\\323_Ass1_ID.txt";
			JMailCOMP.ReadFileLine(listID, strFileID);
			String strFileScr= ".\\ExtSrc\\JMail-IR\\323_Ass1_Score.txt";
			JMailCOMP.ReadFileLine(listScore, strFileScr);
			
			GmailSend gmail = new GmailSend("gaodehong.polyu@gmail.com", "comp09903149r");
			
			for (int i = 0 ; i < listID.size(); i++)
			{
				String strContentTemplate = "Dear " + listID.get(i) + ":\n\n" 
									+ "The score of your Assignment-II in COMP323P is " + listScore.get(i) + ".\n"
									+ "The total score of Assignment-II is 10.\n" 
									+ "\nPlease DO NOT directly reply to this email account.\n"
									+ "For any question, please mail to me csdgao@comp.polyu.edu.hk\n\n"
									+ "Best regards,\n\n" 
									+ "GAO Dehong";
				
				String strReceiverAddress = listID.get(i) + "@connect.polyu.hk";			
				strReceiverAddress = strReceiverAddress.toLowerCase();
				try {
					gmail.SendSSLMessage(strReceiverAddress, strTitle, strContentTemplate);
					System.out.println("Mail Deliver successfully #" + strReceiverAddress + "\n\n");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.err.println("Mail Delivering failed #" + strReceiverAddress  + "\n\n");
				}
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	public void COMP323Assignment_I(String strTitle)
	{
			ArrayList<String> listID = new ArrayList<String>();
			ArrayList<String> listScore = new ArrayList<String>();
			String strFileID = ".\\ExtSrc\\JMail-IR\\COMP323P-Assignment-II-ID.txt";
			JMailCOMP.ReadFileLine(listID, strFileID);
			String strFileScr= ".\\ExtSrc\\JMail-IR\\COMP323P-Assignment-II-Sorce.txt";
			JMailCOMP.ReadFileLine(listScore, strFileScr);
			
			GmailSend gmail = new GmailSend("gaodehong.polyu@gmail.com", "comp09903149r");
			
			for (int i = 0 ; i < listID.size(); i++)
			{
				String strContentTemplate = "Dear " + listID.get(i) + ":\n\n" 
									+ "The score of your Assignment-I in COMP323P is " + listScore.get(i) + ".\n"
									+ "For any question, please mail to me csdgao@comp.polyu.edu.hk\n\n"
									+ "Best regards,\n\n" 
									+ "GAO Dehong";
				
				String strReceiverAddress = listID.get(i) + "@polyu.edu.hk";			
				strReceiverAddress = strReceiverAddress.toLowerCase();
				try {
					gmail.SendSSLMessage(strReceiverAddress, strTitle, strContentTemplate);
					System.out.println("Mail Deliver successfully #" + strReceiverAddress + "\n\n");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.err.println("Mail Delivering failed #" + strReceiverAddress  + "\n\n");
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	public void COMP323Quiz_I(String strTitle)
	{
			ArrayList<String> listID = new ArrayList<String>();
			ArrayList<String> listScore = new ArrayList<String>();
			String strFileID = ".\\ExtSrc\\JMail-IR\\COMP323P-ID.txt";
			JMailCOMP.ReadFileLine(listID, strFileID);
			String strFileScr= ".\\ExtSrc\\JMail-IR\\COMP323P-Score-Quiz1.txt";
			JMailCOMP.ReadFileLine(listScore, strFileScr);
			
			GmailSend gmail = new GmailSend("gaodehong.polyu@gmail.com", "comp09903149r");
			
			for (int i = 0 ; i < listID.size(); i++)
			{
				String strContentTemplate = "Dear " + listID.get(i) + ":\n\n" 
									+ "The score of your quiz - I in COMP323P is " + listScore.get(i) + ".\n"
									+ "For any question, please mail to me csdgao@comp.polyu.edu.hk\n\n"
									+ "Best regards,\n\n" 
									+ "GAO Dehong";
				
				String strReceiverAddress = listID.get(i) + "@polyu.edu.hk";			
				strReceiverAddress = strReceiverAddress.toLowerCase();
				try {
					gmail.SendSSLMessage(strReceiverAddress, strTitle, strContentTemplate);
					System.out.println("Mail Deliver successfully #" + strReceiverAddress + "\n\n");
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					System.err.println("Mail Delivering failed #" + strReceiverAddress  + "\n\n");
				}
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
}
