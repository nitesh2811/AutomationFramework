package com.qa.opencart.pages;

/**
 * @author Nitesh Agrawal
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

@SuppressWarnings("unused")
public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.name("firstname");
	private By lastName = By.name("lastname");
	private By email = By.name("email");
	private By telephone = By.name("telephone");
	private By password = By.name("password");
	private By confPassword = By.name("confirm");
	private By subsNewsLetterNo = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='0']");
	private By subsNewsLetterYes = By.xpath("//label[@class='radio-inline']//input[@type='radio' and @value='1']");
	private By privacyCheckbox = By.name("agree");
	private By logOutLink=By.name("link");
	private By submitBtn = By.cssSelector("input.btn");

	private By registrationHeader = By.cssSelector("div#content h1");
	private By successMessg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");

	// For one or two locators there is not point to create a new page
	// Small components we can consider the part of same page.

	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getRegistrationPageTitleValue() {
		return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT, Constants.REGISTRATION_PAGE_TITLE);

	}

	public String getRegistrationPageHeaderValue() {
		if (eleUtil.doIsDisplayed(registrationHeader))
			return eleUtil.doElementGetText(registrationHeader);

		return null;

	}

	public boolean doRegistration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForElementToBeVisible(this.firstName, Constants.DEFAULT_TIME_OUT).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(confPassword, password);
		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(this.subsNewsLetterYes);
		} else {
			eleUtil.doClick(this.subsNewsLetterNo);
		}
		eleUtil.doClick(this.privacyCheckbox);
		eleUtil.doClick(this.submitBtn);

		if (getAccountRegisterSuccessMessg().contains(Constants.ACCOUNT_CREATION_HEADER)) {
			goToRegisterPage();
			return true;
		}

		return false;

	}

	private String getAccountRegisterSuccessMessg() {
		return eleUtil.waitForElementToBeVisible(successMessg, Constants.DEFAULT_TIME_OUT).getText();
	}

	private void goToRegisterPage() {
		eleUtil.doClick(logoutLink);
		eleUtil.doClick(registerLink);
	}

}
