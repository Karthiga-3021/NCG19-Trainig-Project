package databaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Establish the database connection

public class DatabaseConnection {

	public Connection  madeConnection(){
		
	
	final String dbUserName = "hr";
	final String dbPassword = "hr";
	final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	Connection dbconnection = null;
	
	try{
		dbconnection = DriverManager.getConnection(url,dbUserName,dbPassword);
	}
	catch( SQLException se){
		System.out.println("Your Database Conenction was interrupted");
		se.printStackTrace();
		}
		
	return dbconnection;
	
	}
}