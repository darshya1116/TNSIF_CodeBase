package com.tnsif.day15.JDBCoperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCtable {

	public static void main(String[] args) {
		
		try {
			//step 1: Load or register the driver
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver is load successfully");
			
			//step 2: create connection 
			
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/TNSIF_CodeBase", "root", "Work@16##06//");
			System.out.println("Connection created successfully");
			
			//step 3: create a statement 
			@SuppressWarnings("unused")
			Statement stmt=con.createStatement();
			
			System.out.println("Statement created succesfully");
						
		
		}
		catch(Exception e)
		{
			System.err.println(e);
		}

	}

}