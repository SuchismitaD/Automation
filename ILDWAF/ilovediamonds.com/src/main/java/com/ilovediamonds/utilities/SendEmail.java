package main.java.com.ilovediamonds.utilities;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SendEmail {

	public void sendMail(String html,String subject, String to) {
		
		//  String to1 = "alaka@ilovediamonds.com,suchismita@ilovediamonds.com";
		  String from = "automation.ilovediamonds@gmail.com";
		  Properties properties = System.getProperties();
		  properties.put("mail.smtp.host", "smtp.gmail.com");
		  properties.put("mail.smtp.auth", "true");
		  properties.put("mail.smtp.starttls.enable", "true");
		  properties.put("mail.smtp.port", "587");
		 // properties.setProperty("mail.smtp.username", "suchismita@ilovediamonds.com");
		//  properties.setProperty("mail.smtp.password", "");
		//  properties.setProperty("mail.smtp.username", "suchismita@ilovediamonds.com");
		  //Session session = Session.getDefaultInstance(properties);
		  Session session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("automation.ilovediamonds@gmail.com", "ild@1234");
					}
				  });
		  try {
		   MimeMessage message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(from));
		   /*message.addRecipient(Message.RecipientType.TO, new InternetAddress(
		     to));*/
		   message.addRecipients(Message.RecipientType.TO, to);
		   message.setSubject(subject);
		   //String content = readFile("");
		   message.setContent(html, "text/html; charset=utf-8");
		   Transport.send(message);
		  } catch (MessagingException mex) {
		   mex.printStackTrace();
		  } 
		 }
}
