package com.qa.opencart.pages;

/**
 * @author Nitesh Agrawal
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

@SuppressWarnings("unused")
public class ProductInfoPage {

	// TDD - Test driven development approach.
	// Test Cases for the page and page is not available
	// To write test cases we are refactoring our approach
	// To write test cases we are refactoring our framework.

	private WebDriver driver;
	private ElementUtil eleUtil;

	// Locators for the ProductInfoPage

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("div#content img");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");

	private By qty = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By successMessg = By.cssSelector("div#product-product div");
	private Map<String, String> productInfoMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderText() {
		return eleUtil.doElementGetText(productHeader);
	}

	public int getProductImagesCount() {
		return eleUtil.waitForElementsToBeVisible(productImages, Constants.DEFAULT_TIME_OUT).size();

	}

	public Map<java.lang.String, java.lang.String> getProductInfo() {

		// If we use TreeMap it will sort the Order based on the keys(Capitals letter
		// first).

		productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap.put("productname", getProductHeaderText());
		productInfoMap.put("imagescount", String.valueOf(getProductImagesCount()));
		getProductMetaData();
		getProductPriceData();

		return productInfoMap;

	}

		// Method is private as it belong to this class only 
	private void getProductMetaData() {
		// Brand: Apple
		// Product Code: Product 18
		// Reward Points: 800
		// Availability: Out of Stock
		List<WebElement> metaDataList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaDataList) {
			String text = e.getText().trim();
			String[] meta = text.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}
	}

	private void getProductPriceData() {
		List<WebElement> metaPriceList = eleUtil.getElements(productPriceData);
		String price = metaPriceList.get(0).getText().trim();
		String exPrice = metaPriceList.get(1).getText().trim();
		productInfoMap.put("price", price);
		productInfoMap.put("ExTaxPrice", exPrice);
	}

}
