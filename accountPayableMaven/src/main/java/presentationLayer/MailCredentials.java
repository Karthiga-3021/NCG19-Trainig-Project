package presentationLayer;

/**
 * 
 *This class store mailCredentials of sender and receiver
 */
public class MailCredentials {
	final String mailID = "karthiga.d31@gmail.com";
	final String mailPassword = "mithostel1003";
	String fromAddress="";
	
	
	public String getMailPassword() {
		return mailPassword;
	}
	
	public void setFromAddress(String fromAddress){
		this.fromAddress = fromAddress;
	}
	
	public String getFromAddress() {
		return fromAddress;
	}
	
	public String getMailID(){
		return mailID;
	}
	
}
