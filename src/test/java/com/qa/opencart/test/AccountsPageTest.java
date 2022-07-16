package com.qa.opencart.test;

/**
 * @author Nitesh Agrawal
 */

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 200 - Design Account Page for Open Cart Application")
@Story("US 101- Design Login Page Features")
@Story("US-102- Design Account Page Features")
public class AccountsPageTest extends BaseTest {

	// To reach Account Page we have to do Login.
	// We will do login in Before Class annotation.
	// Read Martin Fowler blog for Page Object Model creation.

	@Description("Pre Login for Account Page Test.....")
	@BeforeClass
	public void accPageSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));

	}
	
	// We can compare two Array list also with assert.
 
	@Test
	@Description("Account Page Title Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void accountPageTitleTest() {
		Assert.assertEquals(accountPage.getAccountPageTitle(), Constants.ACCOUNT_PAGE_TITLE);

	}

	@Test
	@Description("Account Page Header Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void accountPageHeaderTest() {
		Assert.assertTrue(accountPage.isAccountsPageHeaderExist());
	}

	@Test
	@Description("Search Exist Test.....")
	@Severity(SeverityLevel.CRITICAL)
	public void searchExistTest() {
		Assert.assertTrue(accountPage.isSearchExist());
	}

	@Test
	@Description("Account Sections Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void accountSectionTest() {
		List<String> accountList = accountPage.getAccountPageSectionList();
		System.out.println("Account Section List:" + accountList);
		Assert.assertEquals(accountList, Constants.Account_Section_List);
	}

	
	// We will write the search page test cases also in Accounts
	// It is not mandatory that every Page class has seperate test class.

	@Test
	@Description("Search Header Test.....")
	@Severity(SeverityLevel.NORMAL)
	public void searchHeaderTest() {
		searchPage = accountPage.doSearch("Macbook");
		String actSearchHeader = searchPage.getResultsPageHeaderValue();
		Assert.assertTrue(actSearchHeader.contains("Macbook"));

	}

	// Test case should be independent from each other with no dependency.
	@Test
	@Description("Check Product count after the Search.....")
	@Severity(SeverityLevel.CRITICAL)
	public void searchProductCountTest() {
		searchPage = accountPage.doSearch("iMAC");
		int actProductCount = searchPage.getProductSerachCount();
		Assert.assertEquals(actProductCount, Constants.IMAC_PRODUCT_COUNT);
	}

	@Test
	@Description("Check Product list after the search.....")
	@Severity(SeverityLevel.CRITICAL)
	public void getSearchProductListTest() {
		searchPage = accountPage.doSearch("iMAC");
		List<String> actProductList = searchPage.getProductResultsList();
		System.out.println(actProductList);
	}

}
