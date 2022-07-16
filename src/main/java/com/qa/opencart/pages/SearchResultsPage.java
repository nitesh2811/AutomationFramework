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

import io.qameta.allure.Step;

public class SearchResultsPage {

	// For Search Results Page we can remove driver. It is not needed.
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By searchHeader = By.cssSelector("div#content h1");
	private By products = By.cssSelector("div.caption a");

	// 2. Public Constructor
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3.Public Page actions.

	public String getResultsPageHeaderValue() {
		if (eleUtil.doIsDisplayed(searchHeader))
			return eleUtil.doElementGetText(searchHeader);

		return null;
	}
@Step("getProductSerachCount")
	public int getProductSerachCount() {
		// If serach is taking some time for the element to be visible
		// Once visible we can have the search count for the Product.
		return eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT).size();
	}
@Step("getProductResultsList")
	public List<String> getProductResultsList() {
		List<WebElement> productList = eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
		List<String> productValList = new ArrayList<String>();
		for (WebElement e : productList) {
			String text = e.getText();
			productValList.add(text);
		}
		return productValList;
	}
@Step("selectProduct {0}")
	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("main product name:" + mainProductName);
		List<WebElement> productList = eleUtil.waitForElementsToBeVisible(products, Constants.DEFAULT_TIME_OUT);
		for (WebElement e : productList) {
			String text = e.getText();
			if (text.contains(mainProductName)) {
				e.click();
				break;
			}
		}

		return new ProductInfoPage(driver);

	}
}
