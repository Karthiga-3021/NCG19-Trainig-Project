# NCG19-Trainig-Project - ACCOUNT PAYABLE

Account Payable project deals with extraction of sensitive details from Invoice statement.

IDE used: Eclipse Neon\
Language used: Java\
Database used: Oracle SQL Developer


AccountPayableMain.java is the main class of this project. It invovke all the actions that are Recieve mail from user,parse the pdf,store the values into database,display the invoice details and user has to approve the invoice.

ReceiveMailAttachment.java is the parent class of this project which receive invoice mail from the user and download it to loacl disk.

ExtractInvoiceData.java is the grand child class of ReceiveMailAttachment.java which extract\
          1) Invoice Number\
          2) Invoice Date\
          3) Order Number\
          4) Customer PO\
          5) Ship to \
          6) Total Invoice\
and status of this invoice statement. Those values are stored into database in table of INVOICE_INFO and display it.

SendAcknowledgementMail.java is the child class of ReceiveMailAttachment.java which send an acknowledgement mail to the user as your invoice has been approved.  
          
