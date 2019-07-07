package com.altimetrik.AccountPayableMaven;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendAcknowledgementMail extends ReceiveMailAttachment {

	protected void sendAcknowldgement() {

		final String fromAddress = "karthiga.d31@gmail.com";
		final String password = "mithostel1003";
		
		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromAddress, password);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(fromAdrs.toString()));
			message.setSubject("Accounts Payable");
			message.setText("Your Invoice has been approved successfully");
			// send message
			Transport.send(message);
			System.out.println("Approved Successfully");
			System.out.println("Check your inbox for acknowledgement");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
