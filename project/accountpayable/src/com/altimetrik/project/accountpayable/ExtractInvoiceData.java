package com.altimetrik.project.accountpayable;

import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractInvoiceData extends SendAcknowledgementMail {
	// Method to extract specified field in invoice file and store it into
	// database as INVOICE_INFO
	protected void extractDataStoreDB(Connection dbConnect, String fileName) throws SQLException {

		String invoiceNo = " ", invoiceDate = " ", customerPO = " ", orderNo = " ", totalInvoice = " ",
				soldAddress = " ", status = " UnApproved";

		// Delete old values from the table
		PreparedStatement delValues = dbConnect.prepareStatement("delete from INVOICE_INFO");
		delValues.executeQuery();

		// Load the file
		try (PDDocument document = PDDocument.load(new File("D://" + fileName));) {
			int pages = document.getNumberOfPages();
			PDFTextStripper textStripper = new PDFTextStripper();
			int invoiceAmtCount = 1;

			for (int page = 0; page < pages; page++) {
				textStripper.setStartPage(page);
				textStripper.setEndPage(page);
				String pdfFileInText = textStripper.getText(document);
				if (pdfFileInText.contains("Invoice No") && pdfFileInText.contains("Total Invoice")) {
					String[] lines = pdfFileInText.split("\r\n|\r|\n");
					invoiceAmtCount++;
					for (int i = 0; i < lines.length; i++) {
						
						// Get INVOICE NUMBER
						if (lines[i].trim().equals("Invoice No")) {
							invoiceNo = lines[i + 1];
						}

						// Get INVOICE DATE
						if (lines[i].trim().equals("Invoice Date")) {
							invoiceDate = lines[i + 1];
						}

						// Get TOTAL INVOICE
						if (lines[i].trim().equals("Total Invoice")) {
							if (invoiceAmtCount == 4)
								i = i + 3;
							else
								i = i + 2;
							totalInvoice = lines[++i].replaceAll("\\$", "");

						}

						// Get CUSTOMER P.O
						if (lines[i].trim().equals("Customer P.O.")) {
							customerPO = lines[i + 1];
						}

						// Get ORDER NUMBER
						if (lines[i].trim().equals("Order No")) {
							orderNo = lines[i + 1];
						}

						// Get Sold to Address
						if (lines[i].trim().equals("Sold To")) {
							soldAddress = lines[++i] + " " + lines[++i] + " " + lines[++i];
						}

					}
					storeValuesIntoDatabase(dbConnect, invoiceNo, invoiceDate, orderNo, customerPO, soldAddress,
							totalInvoice, status);
				}
			}
		}

		catch (IOException e) {
			System.out.println("Your attachment not found");
			System.exit(0);
			e.printStackTrace();
		}
		System.out.println("The values are inserted into the table");
		approveInvoice(dbConnect);
	}

	// Store the values into Invoice info table
	protected void storeValuesIntoDatabase(Connection dbconnect, String invoiceNo, String invoiceDate, String orderNo,
			String customerPO, String soldAddress, String invoiceAmt, String status) {

		String query = " insert into INVOICE_INFO (INVOICE_NO, INVOICE_DATE,"
				+ " ORDER_NO, CUSTOMER_P_O, SOLD_TO_ADDRESS, TOTAL_INVOICE_AMOUNT, STATUS)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		try {

			PreparedStatement preparedStmt = dbconnect.prepareStatement(query);
			preparedStmt.setString(1, invoiceNo);
			preparedStmt.setString(2, invoiceDate);
			preparedStmt.setString(3, orderNo);
			preparedStmt.setString(4, customerPO);
			preparedStmt.setString(5, soldAddress);
			preparedStmt.setString(6, invoiceAmt);
			preparedStmt.setString(7, status);
			preparedStmt.execute();

		} catch (SQLException sql) {
			System.out.println(" Your database connection was interrupted");
			sql.printStackTrace();
		}
	}

	public void approveInvoice(Connection dbconnect) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Print the invoice Details");
		printDetails(dbconnect);

		System.out.println("Press 1 to approve the invoice ");
		System.out.println("Press 2 to reject the invoice");

		int choice = sc.nextInt();

		switch (choice) {
		case 1:
			System.out.println("You  selected to approve the invoice");
			System.out.println(" Enter your Invoice Number");
			String userInvoice = sc.next();
			updateStatus(dbconnect, userInvoice, "Approved");
			break;
		case 2:
			System.out.println("Your Invoice has been rejected");
			break;
		}

		System.out.println("Print the approved invoice details");
		printApprovedInvoiceDetails(dbconnect);
	}
	
	/**
	 * 
	 * Print Approved Invoice Details
	 */

	private void printApprovedInvoiceDetails(Connection dbconnect) {
		try {
			String query = "select * from INVOICE_INFO where status = ?";
			PreparedStatement printStmt = dbconnect.prepareStatement(query);
			printStmt.setString(1, "Approved");
			ResultSet result = printStmt.executeQuery();
			System.out.println("InvoiceNumber\tInoiveData\tOrderNo\tTotal Invoice\tStatus");
			while (result.next()) {

				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getString(3) + "\t"
						+ result.getString(6) + "\t" + result.getString(7));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}

	}

	private void printDetails(Connection dbConnect) {
		try {
			String query = "select * from INVOICE_INFO";
			PreparedStatement printStmt = dbConnect.prepareStatement(query);
			ResultSet result = printStmt.executeQuery();
			System.out.println("InvoiceNumber\t InoiveData\tOrderNo\tCustomerPO\tSoldTo\t\tTotal Invoice\tStatus");
			while (result.next()) {
				System.out.println(result.getInt(1) + "\t" + result.getString(2) + "\t" + result.getString(3) + "\t"
						+ result.getString(4) + "\t" + result.getString(5) + "\t" + result.getString(6) + "\t"
						+ result.getString(7));
			}
		} catch (SQLException sq) {
			sq.printStackTrace();
		}
	}

/**
 * Update the status from Unapproved to approved
 */
	private void updateStatus(Connection dbConnect, String invoiceNum, String status) {
		try {
			String query = "update INVOICE_INFO set Status = ? where Invoice_No = ?";
			PreparedStatement preparedStmt = dbConnect.prepareStatement(query);
			preparedStmt.setString(1, status);
			preparedStmt.setString(2, invoiceNum);
			preparedStmt.executeUpdate();
			System.out.println("Your invoice has been approved");
		} catch (SQLException e) {
			System.out.println("Error Occured during approving invoice");
			e.printStackTrace();
			System.exit(0);
		}

	}

}
