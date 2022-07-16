package com.qa.opencart.test;
/**
 * @author Nitesh Agrawal
 */

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

// We have various Annotations provided by Allure Report
// Severity
// Description: Use for @Test Annotations.
// Epic: This is for the Login Page Test and not for the Login Page Class
// Story: This is also for the Test Class not Page Class.
// Step: Use for Page actions
// These Annotations are useful to print the information on the Report.
// We can write annotations below @Test as well as above @Test.
// Annotations are not mandatory to write.
// Blocker is when selenium is not able to find Element or the Synchro Problem
// Failure is when the assertions are failed.
// Allure report increases the visibility of the Test Case


// Epic consist of multiple User Stories.


@Epic("Epic 100 - Design Login Page for Open Cart Application")
@Story("US 101 - Design Login Page features")
public class LoginPageTest extends BaseTest {

	// In test class we are not calling any selenium driver APIs or features
	// No WebDriver APIs in Test Class. TestNG is kind of Test client used for
	// executing the Test Cases.
	// Same thing with Python, Cypress, WebDriverIO ,Mocha ,Jasmine
	// We will only have assertions in Test Class.
	// We can inherit the references from the Base Test.

	@Test
	@Description("Login Page Title Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageTitleTest() {
		String loginPageTitle = loginPage.getLoginPageTitle();
		System.out.println("Page Title:" + loginPageTitle);
		Assert.assertEquals(loginPageTitle, Constants.LOGIN_PAGE_TITLE);
	}

	@Test
	@Description("Login Page URL Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void loginPageUrlTest() {
		String url = loginPage.getLoginPageUrl();
		System.out.println("Login Page :" + url);
		Assert.assertTrue(url.contains(Constants.LOGIN_PAGE_FRACTION_URL));
	}

	@Test
	@Description("Forgot Password Link Test.....")
	@Severity(SeverityLevel.CRITICAL)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Test
	@Description("Login Test with correct Username and Password")
	@Severity(SeverityLevel.BLOCKER)
	public void loginTest() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		System.out.println("Logged in Success");
		// Checked Successfully logged in or not.
		Assert.assertTrue(accountPage.isAccountsPageHeaderExist());
	}

	@Test
	@Description("Register Link Test.....")
	@Severity(SeverityLevel.CRITICAL)
	public void isRegisterLinkExistTest() {
		Assert.assertTrue(loginPage.isRegisterLinkExist());
	}
}
