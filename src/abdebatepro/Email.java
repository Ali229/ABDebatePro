/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abdebatepro;
/**
 *
 * @author Programmer
 */
// File Name SendEmail.java


import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public Email(String e, String u, String p) {

		final String username = "abdebatepro@gmail.com";
		final String password = "shiva123";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(e));
			message.setSubject("Password Request - ABBebatePro");
			message.setText("Dear " + u + ","
				+ System.getProperty("line.separator") 
                                + System.getProperty("line.separator") 
                                + "You have requested your password for AB Debate Pro." 
                                + System.getProperty("line.separator") 
                                + "Your password is: " + p);
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException ex) {
			throw new RuntimeException(ex);
		}catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}