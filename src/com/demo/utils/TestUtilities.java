package com.demo.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// This utility is used with Eribank tests
public class TestUtilities {
	
	// This is a higher level method that is to be used to verify that the 
	// amount before and after deduction is done correctly and is expecting
	// the amount text captured from the Eribank application - which is in the format 
	// of 99.99$.
	
	static public void verifyPostPaymentAmount(String beforeAmount, String afterAmount, String deductAmount)
	{
	
        double amt1 = TestUtilities.getBalanceAmountFromString(beforeAmount);
        System.out.println("amount before deduction: " + amt1);

        double amt2 = TestUtilities.getBalanceAmountFromString(afterAmount);
        System.out.println("amount after deduction: " + amt2);
        
        double amt3 = Double.valueOf(deductAmount);
        
        try {
            assertTrue(amt2==amt1-amt3); 
            System.out.println("Test Passed: " + 
            		"\nAmount Before Deduction: " + beforeAmount + 
            		"\nAmount Deducted: " + deductAmount + 
            		"\nAmount After Deduction Expected: " + (amt1-amt3) + 
            		"\nAmount After Deduction Reported: " + afterAmount); 
        }
        catch (AssertionError ae)
        {
        	fail("Test Failed: " + 
            		"\nAmount Before Deduction: " + beforeAmount + 
            		"\nAmount Deducted: " + deductAmount + 
            		"\nAmount After Deduction Expected: " + (amt1-amt3) + 
            		"\nAmount After Deduction Reported: " + afterAmount); 
        }


	}
	
	// This is a method with a logic error in the verification to be used to fail
	// the verification on purpose
	static public void verifyPostPaymentAmountWithError(String beforeAmount, String afterAmount, String deductAmount)
	{
	
        double amt1 = TestUtilities.getBalanceAmountFromString(beforeAmount);
        System.out.println("amount before deduction: " + amt1);

        double amt2 = TestUtilities.getBalanceAmountFromString(afterAmount);
        System.out.println("amount after deduction: " + amt2);
        
        double amt3 = Double.valueOf(deductAmount);
        
        try {
        	
        	// logic error below - it should be amt1- amt3, not amt1 + amt3
            assertTrue(amt2==amt1 + amt3); 
            System.out.println("Test Passed: " + 
            		"\nAmount Before Deduction: " + beforeAmount + 
            		"\nAmount Deducted: " + deductAmount + 
            		"\nAmount After Deduction Expected: " + (amt1-amt3) + 
            		"\nAmount After Deduction Reported: " + afterAmount); 
        }
        catch (AssertionError ae)
        {
        	fail("Test Failed: " + 
            		"\nAmount Before Deduction: " + beforeAmount + 
            		"\nAmount Deducted: " + deductAmount + 
            		"\nAmount After Deduction Expected: " + (amt1 + amt3) + 
            		"\nAmount After Deduction Reported: " + afterAmount); 
        }

	}
	
	
	
	// This is a helper method that encapsulates the logic to read a record from an Excel 
	// file and return the result in an ArrayList.
	// Each item in the ArrayList is Hashtable that represents a row of data from 
	// the Excel file. 
	//
	// Excel file format needs to keep the first row as Column Name.
	//
	// You can access each column by referencing it using the column name
	// Eg. to get to the data in Column 4, you look for it using the key "Col4".
	static public ArrayList<LinkedHashMap<String,String>> GetDataRecordsFromExcel(String pathToFile)
	{
		Connection conn = null; 
		Statement stmnt = null;
		
		ArrayList<LinkedHashMap<String,String>>  dataRecords = new ArrayList<LinkedHashMap<String,String>> ();
		
		/* JDBC ODBC bridge has been removed from Java 8. Temp solution to hard code the data here.
		try {
			 
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
	
			//using DSN connection. Here qa is the name of DSN
			//c = DriverManager.getConnection( "jdbc:odbc:qa", "", "" );
	
			//using DSN-less connection
			conn = DriverManager.getConnection(
					"jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ=" + pathToFile);
			
			 stmnt = conn.createStatement();
//			 DatabaseMetaData dbmd = conn.getMetaData();
//			 ResultSet mdrs = dbmd.getTables(null, null, null, null);
//			 
//			 while(mdrs.next())
//			 {
//				 System.out.println("Table: " + mdrs.getString("TABLE_NAME"));
//			 }
			 
			 String query = "select * from [Sheet1$]";
			 ResultSet rs = stmnt.executeQuery( query );
			 
			 ResultSetMetaData rsmd = rs.getMetaData();
			 int totalCols = rsmd.getColumnCount();
			 
	
			 while( rs.next() )
			 {
				 LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
				 
				 for (int i=1; i<=totalCols; i++)
				 {
					 String colName = rsmd.getColumnName(i);
					 String colValue = rs.getString(i);
					 row.put(colName, colValue);
					 // System.out.print(" || " + colName + ":" + colValue);
				 }
				 dataRecords.add(row);
			 }
		}
		catch( Exception e ) {
			 System.err.println( e );
		}
		finally
		{
			try {
				if (stmnt != null)
					stmnt.close();
				if (conn != null)
					conn.close();
			}
			catch( Exception e ) {
				System.err.println( e );
			}
		}
		*/

		// This section added to hardcode the data returned
		/*
		 for (int i=0;i<5;i++)
		 {
			 LinkedHashMap<String,String> row = new LinkedHashMap<String,String>();
			 
			 String colName = "DEDUCT_AMOUNT";
			 String colValue = String.valueOf(i);
			 row.put(colName, colValue);
			 // System.out.print(" || " + colName + ":" + colValue);
			 dataRecords.add(row);
		 }
		 ///////// End of Section
 	   */

		 // This section uses Apache POI to get the data from Excel file
		try {
		      
	        FileInputStream file = new FileInputStream(pathToFile);	//Create the input stream from the xlsx/xls file
	        XSSFWorkbook workbook = new XSSFWorkbook(file);								//Create Workbook instance for xlsx file input stream
	        XSSFSheet sheet = workbook.getSheetAt(0);									//Get first sheet from the workbook
	        
	        ArrayList<String> columnNames = new ArrayList<String>();
	        
	        
	        int numOfCols = 0;
	        // Iterate through the rows
	        for( Row row : sheet ) {
	        	
	        	// The first row is header row
	        	if( row.getRowNum() == 0 ) 
	        	{
	        		// Iterate through cells to get the column names and place them in a columnNames array.
	        		Iterator<Cell> it = row.iterator();
	        		numOfCols=0;
	        		while (it.hasNext())
	        		{
	        			numOfCols++;
	        			// numOfCols - 1 below to make sure index starts with 0
	        			columnNames.add(numOfCols-1, it.next().getStringCellValue());
	        		}
	        		
	        		continue; // move to the next iteration
	        	}
        		
	        	// Iterate through the cells in a row and put each cell value and its associated column name
	        	// into a LinkedHashMap
	        	LinkedHashMap<String,String> dataRow = new LinkedHashMap<String,String>();
	        	
        		for (int currCol=0; currCol<numOfCols; currCol++)
        		{
        			dataRow.put(columnNames.get(currCol), row.getCell(currCol).getStringCellValue());
        		}
        		
        		dataRecords.add(dataRow);
	        }
	        
	        workbook.close();
	        file.close();
	      
			}
	        //return carList;
			catch (Exception e) {
				System.out.println("Exception: " + e);
			}		 
		 
		 
		return dataRecords;
	}
	
	
	
	// This method takes in a string containing the balance in the format
	// 99.00$
	// and proceeds to strip the $ and return the actual amount 
	// as a Java Double.
	
	static private double getBalanceAmountFromString(String inAmount)
	{
		
		int idx = inAmount.indexOf("$");
		
		String cleanAmount = inAmount.substring(0,idx);
		Double amount = new Double(cleanAmount);
				
		return amount;
	}
	

}
