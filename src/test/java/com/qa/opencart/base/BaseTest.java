package com.qa.opencart.base;

/**
 * @author Nitesh Agrawal
 */

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {

	// Maintain references at the base test level and use in child classes.
	public DriverFactory df;
	public WebDriver driver;
	public LoginPage loginPage;
	public AccountsPage accountPage;
	public Properties prop;
	public RegistrationPage regPage;
	public SearchResultsPage searchPage;
	public ProductInfoPage productInfoPage;
	public SoftAssert softAssert;

	@BeforeTest
	public void setup() {
		df = new DriverFactory();
		prop = df.init_prop();
		//This driver will become the thread local copy of the driver.
		driver = df.init_driver(prop);
		loginPage = new LoginPage(driver);
		softAssert=new SoftAssert();
	}

	@AfterTest
	public void tearDown(){
		driver.quit();
	}

}
