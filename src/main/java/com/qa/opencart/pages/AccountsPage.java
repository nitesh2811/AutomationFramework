package com.qa.opencart.pages;

/**
 * @author Nitesh Agrawal
 */

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	// No Seperate class for By locator.
	// It will be a bad approach and create unnecessary maintainence.

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By search = By.name("search");
	private By header = By.cssSelector("div#logo a");
	private By accountsList = By.cssSelector("div#content h2");
	private By searchBtn = By.cssSelector("div#search button");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getAccountPageTitle() {
		return eleUtil.waitForTitleIs(Constants.DEFAULT_TIME_OUT, Constants.ACCOUNT_PAGE_TITLE);
	}

	public boolean isAccountsPageHeaderExist() {
		return eleUtil.doIsDisplayed(header);
	}

	public boolean isSearchExist() {
		return eleUtil.doIsDisplayed(search);
	}

	public List<String> getAccountPageSectionList() {
		List<WebElement> sectionList = eleUtil.waitForElementsToBeVisible(accountsList, Constants.DEFAULT_TIME_OUT);
		List<String> accSecValList = new ArrayList<String>();
		for (WebElement e : sectionList) {
			String text = e.getText();
			accSecValList.add(text);
		}
		return accSecValList;

	}

	public SearchResultsPage doSearch(String productName) {
		if (isSearchExist()) {
			eleUtil.doSendKeys(search, productName);
			eleUtil.doClick(searchBtn);
			return new SearchResultsPage(driver);
		}

		return null;
	}

}
