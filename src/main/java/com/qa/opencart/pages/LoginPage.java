package com.qa.opencart.pages;

/**
 * @author Nitesh Agrawal
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.Errors;

import io.netty.util.Constant;
import io.qameta.allure.Step;

public class LoginPage {

	// Private so that only this particular class can use it
	// No one can access this driver from outside.
	// ElementUtil Object will be created as private variable.
	private WebDriver driver;
	private ElementUtil eleUtil;

	// Private By locators(Encapsulation Concept)
	// Locators should be design in form of Encapsulation.
	// This should not be accessd by other class.
	// Every page has a private web driver intialize by constructor.
	// Element Util class is used to peform the Page Actions.

	// 1.Private By locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwd = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By invalidLogin=By.xpath("//ul[@class='breadcrumb']/following-sibling::div");

	// 2.Public page constructor
	// Public because we can create the Objects from outside the class.
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// We can access the private By locators using public page actions.
	// 3.Public page actions:
	// We can write annotations for the Login Page also.
	// Step annotation is used.
	// It is good practise to write Step annotation.
	// We can write multiple Step annotations also.
	@Step("Getting Login Page Title.....")
	public String getLoginPageTitle() {
		return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_TITLE);
	}

	@Step("Getting Login Page URL.....")
	public String getLoginPageUrl() {
		return eleUtil.waitForUrl(Constants.DEFAULT_TIME_OUT, Constants.LOGIN_PAGE_FRACTION_URL);
	}

	// Access the private data members inside public layer-Encapsulation.
	// Page factory is deprecated and is now not used. New design pattern using
	// private By locators.
	// @FindBy - means creating WebElement in advance. This is not recommended now.
	// In Page Factory we are creating all the unnecessary WebElement the moment we
	// load the class.
	// This is not required and should be avoided. We need only specific element
	// when it is required.
	// We are not hitting any Server by creating the Private By locators.
	// We can use private data member inside the public layer.

	@Step("Checking Forgot Password Link is available or not.....")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwd);
	}

	// It will return landing page class Object which is the Account Page.
	// It is method responsibility to return next landing page object.

	@Step("Login to the application with username {0} and password {1}.....")
	public AccountsPage doLogin(String un, String pwd) {
		eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT).sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.clickWhenReady(loginBtn, Constants.DEFAULT_TIME_OUT);
		return new AccountsPage(driver);
	}
	
	@Step("Login to the application with invalid credentials.....")
	public boolean doInvalidLogin(String un, String pwd) {
		WebElement element=eleUtil.waitForElementToBeVisible(emailId, Constants.DEFAULT_TIME_OUT);
		element.clear();
		element.sendKeys(un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.clickWhenReady(loginBtn, Constants.DEFAULT_TIME_OUT);
		String actualErrorMessage=eleUtil.getElement(invalidLogin).getText();
		if (actualErrorMessage.equals(Errors.LOGIN_PAGE_ERROR_MESSAGE)) {
			return true;
		}
	return false;
	}

	@Step("Checking Register Link exist or not.....")
	public boolean isRegisterLinkExist() {
		return eleUtil.waitForElementToBeVisible(registerLink, Constants.DEFAULT_TIME_OUT).isDisplayed();
	}

	@Step("Navigating to Register Page.....")
	public RegistrationPage navigateToRegisterLink() {
		if (isRegisterLinkExist()) {
			eleUtil.doClick(registerLink);
			return new RegistrationPage(driver);
		}

		return null;
	}

}
