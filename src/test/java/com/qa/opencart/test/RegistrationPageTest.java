package com.qa.opencart.test;

/**
 * @author Nitesh Agrawal
 */

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {

	
	@BeforeClass
	public void regPageSetup() {
		regPage = loginPage.navigateToRegisterLink();
	}

	@Test
	public void registrationPageTitleTest() {
		Assert.assertEquals(regPage.getRegistrationPageTitleValue(), Constants.REGISTRATION_PAGE_TITLE);
	}

	@Test
	public void registrationPageHeaderTest() {
		Assert.assertEquals(regPage.getRegistrationPageHeaderValue(), Constants.REGISTRATION_PAGE_HEADER);
	}

	public String getRandomEmail() {
		Random random = new Random();
		String email = "JanAutomation" + random.nextInt(1000) + "@gmail.com";
		return email;
	}

	// Data provider is from TestNG. It is TestNG feature.
	@DataProvider
	public Object[][] getRegistrationData() {
		// It will help to provide data to my test annotation.
		// We have to maintin object 2D array.
		return new Object[][] { { "Nitesh", "Agrawal", "7042885528", "Nitesh@123", "yes" },
				{ "Anu", "Kamath", "7042885588", "anu@123", "no" },
				{ "Gangan", "Tyagi", "7012885588", "gagan@123", "yes" } };
	}

	@DataProvider
	public Object[][] getRegisterData() {
		Object[][] regData = ExcelUtil.getTestData(Constants.REGISTGER_SHEET_NAME);
		System.out.println(regData);
		return regData;
	}

	@Test(dataProvider = "getRegistrationData")
	public void userRegistrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		// This will be iterated for all the values provided by data provider.
		Assert.assertTrue(
				regPage.doRegistration(firstName, lastName, getRandomEmail(), telephone, password, subscribe));

	}

	@Test(dataProvider = "getRegisterData")
	public void userRegistrationTestExcel(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		// This will be iterated for all the values provided by data provider.
		// Data Provider is getting data from the Excel Sheet.
		Assert.assertTrue(
				regPage.doRegistration(firstName, lastName, getRandomEmail(), telephone, password, subscribe));

	}
	
	
}

//Data provider can be in same class. It is easy for the maintainence point of view.
//We can provide data from the excel sheet also
//If second user cannot be created then it will create the third user.
//Direct data provider is more conveinent that giving data from excel sheet.
//Same data provider can be used for multiple @Test annotations 
//We can also have multiple data provider.
//We can dump the data inside the database in the QA Environment.
//From data entry point of view the Excel sheet is Ok otherwise its better to maintain data directly.
