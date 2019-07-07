package databaseLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Print the invoice details to the user
 */

public class PrintTable {

	public void printDetails(Connection dbConnect) {
		try {
			String query = "select * from INVOICE_INFO";
			PreparedStatement printStmt = dbConnect.prepareStatement(query);
			ResultSet result = printStmt.executeQuery();
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("InvoiceNumber  InoiveDate  OrderNo \t CustomerPO  \t\tSoldTo\t\t\t\t\t\t\t\t\t\t\t\t\t\tTotal Invoice\tStatus");
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			while (result.next()) {
				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getString(3) + "\t"
						+ result.getString(4) + "\t" + result.getString(5) + "\t" + result.getString(6) + "\t"
						+ result.getString(7));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}
	
	public void printApprovedInvoiceDetails(Connection dbconnect) {
		try {
			String query = "select * from INVOICE_INFO where status = ?";
			PreparedStatement printStmt = dbconnect.prepareStatement(query);
			printStmt.setString(1, "Approved");
			ResultSet result = printStmt.executeQuery();
			System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			System.out.println("InvoiceNumber  InoiveDate  OrderNo  \tTotal Invoice\t\tStatus");
			System.out.println("---------------------------------------------------------------------------------------------------------------------------");
			while (result.next()) {

				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getString(3) + "\t"
						+ result.getString(6) + "\t" + result.getString(7));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

}
