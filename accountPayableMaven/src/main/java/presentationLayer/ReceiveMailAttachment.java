package presentationLayer;

import java.io.File;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;


/**
 * 
 * This class is used to received the invoice attachment from gmail account
 *
 */
 public class ReceiveMailAttachment  {
	MailCredentials credential = new MailCredentials();
	String pop3Host = "pop.gmail.com";// change accordingly
	String mailStoreType = "pop3";
	
	public   String receiveEmailAttachment( ) {
		
		// Set properties
	
		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		// Get the Session object.
		Session session = Session.getInstance(props);
		String downloadFile = null;
		try {
			// Create the POP3 store object and connect to the pop store.
			Store store = session.getStore("pop3s");
			store.connect(pop3Host, credential.getMailID(), credential.getMailPassword());

			// Create the folder object and open it in your mailbox.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from the folder object.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message" + messages.length);

			// Iterate the messages

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				credential.setFromAddress(message.getFrom()[0].toString());
				System.out.println("From: " + credential.getFromAddress());

				// Iterate recipients
				System.out.println("To: ");
				String toAdrs = " ";
				for (int j = 0; j < toAddress.length; j++) {
					toAdrs = toAddress[j].toString();
				}
				System.out.println(toAdrs);

				// Iterate multiparts
				if(message.getContent() instanceof Multipart)
				{
						Multipart multipart = (Multipart) message.getContent();
						for (int k = 0; k < multipart.getCount(); k++) 
						{
							BodyPart bodyPart = multipart.getBodyPart(k);
							if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) 
							{
								System.out.println("file name " + bodyPart.getFileName());
								System.out.println("size " + bodyPart.getSize());
								System.out.println("content type " + bodyPart.getContentType());
								InputStream stream = (InputStream) bodyPart.getInputStream();
								File targetFile = new File("D:\\" + bodyPart.getFileName());
								downloadFile = bodyPart.getFileName();
								java.nio.file.Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
								// IOUtils.closeQuietly(stream);
							}
						}
				}
			}

			// close the folder and store objects
			emailFolder.close(false);
			store.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return downloadFile;
	}

	
}
