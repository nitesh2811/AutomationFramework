package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Errors;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageInvaildTest extends BaseTest {

	
	
	@DataProvider
	public Object[][] getInvalidLoginData() {
		
		return new Object[][] {
			{" "," "},
			{"nitesh",""},
			{"","password"},
			{"na123"," "}
		};	
	}
	
	@Test(dataProvider="getInvalidLoginData")
	@Description("Login Test with invalid credentials.....")
	@Severity(SeverityLevel.NORMAL)
	public void loginInvalidDataTest(String invalidUserName,String invalidPassword) {
		// Check for the negative test case.
		// Negative test case is when unwanted data is passed into the system.
		Assert.assertTrue(loginPage.doInvalidLogin(invalidUserName, invalidPassword),Errors.LOGIN_PAGE_ERROR_MESSAGE_NOT_DISLPAYED);
	}
	
	// We can pass our own message also with the Assertion also.
	// 
	//	@Test
	//	public void sum() {
	//		
	//		int a=10;
	//		int b=20;
	//		int sum=a+b;
	//		Assert.assertEquals(sum,40,"Sum is not correct.....");
	//  }
	
}
