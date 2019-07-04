package com.altimetrik.project.accountpayable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * This is the main method to invoke this Saas product of AccountPayable 
 *
 */
public class AccountPayableMain extends ExtractInvoiceData {

	public static void main(String[] args) {

		String userName = "hr";
		String password = "hr";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);

		// Establish a database connection
		try (Connection conn = DriverManager.getConnection(url, connectionProps);) {
			AccountPayableMain startProcess = new AccountPayableMain();
			String fileName = startProcess.receiveAttachement();
			startProcess.extractDataStoreDB(conn, fileName);
			startProcess.sendAcknowldgement();
		} catch (SQLException e) {
			System.out.println("Database connection was interrupted...");
			System.out.println("Give correct credential  to establish an connection");
			e.printStackTrace();
		}

	}

}
