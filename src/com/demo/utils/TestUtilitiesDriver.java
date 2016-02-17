package com.demo.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class TestUtilitiesDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testReadDataFromExcel();
		
	}

	
	// this method shows how to use the GetDataRecordsFromExcel(String filename)
	// method on TestUtilities.
	
	private static void testReadDataFromExcel() {
		
		ArrayList<LinkedHashMap<String,String>> data = new ArrayList<LinkedHashMap<String,String>>();
		LinkedHashMap<String,String> currRow;

		// When you use the GetDataRecordsFromExcel method, you already know the structure of
		// the file. So what you want to do here, is to declare all the column names
		
		String col1Name = "FIRST_NAME";
		String col2Name = "MOBILE_NO";
		String col3Name = "DOB";
		
		// Call the method by passing in the location of the excel file. 
		// The method only works with the Sheet1 worksheet in the Excel file
		// Row 1 of the file indicates the column name. So make sure that row 1 in \
		// the data file corresponds to the column names you have above.
		
		data = TestUtilities.GetDataRecordsFromExcel("D:/testdata.xlsx");
		
		// This section of code below basically iterates through the ArrayList returned by the method.
		Iterator<LinkedHashMap<String, String>> i = data.iterator();
		int index = 0;
		while (i.hasNext())
		{
			index++;
			
			// Each entry in the ArrayList is a LinkedHashMap containing 2 strings, one being the key and the 
			// other being the data. 
			// The next method here gets the LinkedHashMap into the variable called currRow.
			currRow = i.next();
			System.out.print("Row " + index + ": ");
			
			// You get the data by calling the get method on the LinkedHashMap object by passing
			// in the name of the column.
			System.out.print(col1Name + ": " + currRow.get(col1Name));
			System.out.print(" | ");
			System.out.print(col2Name + ":" + currRow.get(col2Name));
			System.out.print(" | ");
			System.out.println(col3Name + ":" + currRow.get(col3Name));
			
		}
	}

}
