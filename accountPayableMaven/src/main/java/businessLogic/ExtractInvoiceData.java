package businessLogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import databaseLayer.StoreInvoiceData;


/*
 * Extract the required fields from the invoice pdf
 */
public class ExtractInvoiceData  {
	
	Scanner sc = new Scanner(System.in);
	SpecifiedInvoiceData specifiedData = new SpecifiedInvoiceData();

	
	public void  extractDataStoreDB(Connection dbConnect, String fileName) throws SQLException {

		
		StoreInvoiceData storeValues = new StoreInvoiceData(); 
		// Delete old values from the table
		PreparedStatement delValues = dbConnect.prepareStatement("delete from INVOICE_INFO");
		delValues.executeQuery();

		// Load the file and get the invoice details
		try(PDDocument document = PDDocument.load(new File("D://" + fileName));) 
		{
			
			int pages = document.getNumberOfPages();
			PDFTextStripper textStripper = new PDFTextStripper();
			int invoiceAmtCount = 1;

			for (int page = 0; page < pages; page++)
			{
				textStripper.setStartPage(page);
				textStripper.setEndPage(page);
				String pdfFileInText = textStripper.getText(document);
				if (pdfFileInText.contains("Invoice No") && pdfFileInText.contains("Total Invoice"))
				{
					String[] lines = pdfFileInText.split("\r\n|\r|\n");
					invoiceAmtCount++;
					for (int i = 0; i < lines.length; i++) {
						
						
						if (lines[i].trim().equals("Invoice No")) {
							 specifiedData.setInvoiceNo(lines[i + 1]);
						}

							if (lines[i].trim().equals("Invoice Date")) {
								specifiedData.setInvoiceDate( lines[i + 1]);
						}

						if (lines[i].trim().equals("Total Invoice")) {
							if (invoiceAmtCount == 4)
								i = i + 3;
							else
								i = i + 2;
							specifiedData.setTotalInvoice( lines[++i].replaceAll("\\$", ""));
						}

						if (lines[i].trim().equals("Customer P.O.")) {
							specifiedData.setCustomerPO(lines[i + 1]);
						}

						if (lines[i].trim().equals("Order No")) {
							specifiedData.setOrderNo(lines[i + 1]);
						}

						if (lines[i].trim().equals("Sold To")) {
							String soldAddress = lines[++i] + " " + lines[++i] + " " + lines[++i];
							specifiedData.setSoldAddress(soldAddress);
						}

					}
					storeValues.storeValuesIntoDatabase(dbConnect);
				}
			}
			
			
		}
		catch(FileNotFoundException fe){
			System.out.println("Your file was not in given location");
			fe.printStackTrace();
		}
		
		catch (IOException e) {
			System.out.println("Your attachment not found");
			System.exit(0);
			e.printStackTrace();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		
	}

	
	
}
