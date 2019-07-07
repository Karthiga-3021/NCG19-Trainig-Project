package presentationLayer;

import java.sql.Connection;
import java.sql.SQLException;
import businessLogic.ExtractInvoiceData;
import databaseLayer.DatabaseConnection;
import databaseLayer.PrintTable;
import databaseLayer.UpdateStatus;


/*
 * Main method to invoke all task in business,database and presentation layer
 */
public class AccountPayableMain {

	public static void main(String[] args)  {
		
			DatabaseConnection connect = new DatabaseConnection();
			ReceiveMailAttachment receiveMail = new ReceiveMailAttachment();
			ExtractInvoiceData extractData = new ExtractInvoiceData();
			PrintTable printDetails = new PrintTable();
			UpdateStatus approval  = new UpdateStatus();
			SendAcknowledgementMail sendMail = new SendAcknowledgementMail();
		
		try{	
			
			Connection dbconnect = connect.madeConnection();
			
			String fileName = receiveMail.receiveEmailAttachment();
		
			extractData.extractDataStoreDB(dbconnect,fileName);
			
			printDetails.printDetails(dbconnect);
			
			approval.approveStatus(dbconnect);
			
			printDetails.printApprovedInvoiceDetails(dbconnect);
			
			sendMail.sendAcknowldgement();
			
		}catch(SQLException s){
			System.out.println("Your system was not working properly");
			s.printStackTrace();
		}
	}
}
