package databaseLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import businessLogic.SpecifiedInvoiceData;

/*
 * Store the invoice values into INVOICE_INFO table
 */
public class StoreInvoiceData {

	
	SpecifiedInvoiceData specificData= new SpecifiedInvoiceData();
	
	
	public void storeValuesIntoDatabase(Connection conn)  {

	
		
		String query = " insert into INVOICE_INFO (INVOICE_NO, INVOICE_DATE,"
				+ " ORDER_NO, CUSTOMER_P_O, SOLD_TO_ADDRESS, TOTAL_INVOICE_AMOUNT, STATUS)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		try {

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1,specificData.getInvoiceNo());
			preparedStmt.setString(2, specificData.getInvoiceDate());
			preparedStmt.setString(3, specificData.getOrderNo());
			preparedStmt.setString(4, specificData.getCustomerPO());
			preparedStmt.setString(5, specificData.getSoldAddress());
			preparedStmt.setString(6, specificData.getTotalInvoice());
			preparedStmt.setString(7, specificData.getStatus());
			preparedStmt.execute();

		} catch (SQLException sql) {
			System.out.println(" Your database connection was interrupted");
			sql.printStackTrace();
		}
	}
	
}
