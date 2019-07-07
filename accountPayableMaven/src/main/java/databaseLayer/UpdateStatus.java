package databaseLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateStatus {
	
	public void approveStatus(Connection dbConnect){
	
		
		Scanner sc = new Scanner(System.in);
	
	while(true){
	System.out.println("\n");
	System.out.println("Press 1 to approve the invoice ");
	System.out.println("Press 2 to reject the invoice");

	int choice = sc.nextInt();
	System.out.println("\n");
	switch (choice) {
	case 1:
		System.out.println("You  selected to approve the invoice");
		System.out.println("Enter your Invoice Number");
		String userInvoice = sc.next();
		try {
			String query = "update INVOICE_INFO set Status = ? where Invoice_No = ?";
			PreparedStatement preparedStmt = dbConnect.prepareStatement(query);
			preparedStmt.setString(1, "Approved");
			preparedStmt.setString(2, userInvoice);
			preparedStmt.executeUpdate();
			System.out.println("Your invoice has been approved");
		} catch (SQLException e) {
			System.out.println("Error Occured during approving invoice");
			e.printStackTrace();
			
		}

		break;
	case 2:
		System.out.println("Your Invoice has been rejected");
		System.exit(0);
		break;
	}
	}
	}
}
