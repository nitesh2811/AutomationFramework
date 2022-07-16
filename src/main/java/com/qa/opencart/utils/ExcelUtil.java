package com.qa.opencart.utils;

/**
 * @author Nitesh Agrawal
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	// Selenium does not provide any utility to read Excel.
	// Apache POI API will be used for the reading of the Excel data.

	// No of rows in the Excel file is the number of time iteration will be
	// performed.

	private static String TEST_DATA_SHEET_PATH =Constants.TEST_DATA_SHEET_PATH;
	private static Workbook book;
	private static org.apache.poi.ss.usermodel.Sheet sheet;

	public static Object[][] getTestData(String sheetName) {
		Object data[][] = null;
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(ip);
			sheet = book.getSheet(sheetName);
			// This will give the matrix dimenson of the sheet.
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		

			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).toString();
				}
			}
		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return data;
	}

}
