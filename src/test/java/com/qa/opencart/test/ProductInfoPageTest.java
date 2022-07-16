package com.qa.opencart.test;

/**
 * @author Nitesh Agrawal
 */

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class ProductInfoPageTest extends BaseTest {

	@BeforeClass
	public void productInfoSetup() {
		accountPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductData() {
		return new Object[][] { { "MacBook", "MacBook Pro" }, { "MacBook", "MacBook Air" },
				{ "Apple", "Apple Cinema 30\"" }

		};
	}

	@DataProvider
	public Object[][] getProductDataExcel() {
		Object productData[][] = ExcelUtil.getTestData(Constants.PRODUCT_SHEET_NAME);
		return productData;
	}

	@Test(dataProvider = "getProductData")
	public void productInfoHeaderTest(String productName, String mainProductName) {
		// This should not be written in @BeforeClass because data provider works for
		// @Test only
		// It will supply data to the Test case.
		searchPage = accountPage.doSearch(productName);
		productInfoPage = searchPage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductHeaderText(), mainProductName);

	}

	@Test(dataProvider = "getProductDataExcel")
	public void productInfoHeaderTestExcel(String productName, String mainProductName) {
		// This should not be written in @BeforeClass because data provider works for
		// @Test only
		// It will supply data to the Test case.
		searchPage = accountPage.doSearch(productName);
		productInfoPage = searchPage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductHeaderText(), mainProductName);

	}

	@Test
	public void productImagesTest() {
		// This should not be written in @BeforeClass because data provider works for
		// @Test only
		// It will supply data to the test case.
		searchPage = accountPage.doSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Air");
		Assert.assertEquals(productInfoPage.getProductImagesCount(), Constants.MACBOOK_IMAGES_COUNT);
		// Image count is static data not a dynamic data.

	}

	@Test
	public void productInfoTest() {

		// Is it a good practise for writing multiple assertions ? No, its not
		// AAA rule for unit level test case
		// Arrange Act Assert
		// Not more than one assert as per AAA pattern.

		// Below five assertions are pointing to single test so no harm but generally
		// its not done this way.
		// Two seperate features should not be combined genrally.

		// Below is hard assertions- If assertion is failed , test is terminated and
		// marked as fail.
		// In below case we have to use Soft Assertions.

		// searchPage = accountPage.doSearch("MacBook");
		// productInfoPage = searchPage.selectProduct("MacBook Pro");
		// Map<String,String> actProductInfoMap=productInfoPage.getProductInfo();
		// actProductInfoMap.forEach((k,v)->System.out.println(k+":"+v));
		// Assert.assertEquals(actProductInfoMap.get("productname"),"MacBook Pro");
		// Assert.assertEquals(actProductInfoMap.get("Brand"),"Apple");
		// Assert.assertEquals(actProductInfoMap.get("Reward Points"),"800");
		// Assert.assertEquals(actProductInfoMap.get("price"),"$2,000.00");
		// Assert.assertEquals(actProductInfoMap.get("Product Code"),"Product 18");

		// In Some exceptional cases it is allowed to write multiple assertions in
		// single test.
		// Normally we dont have to use more than one assertion in single test
		// annotation.
		// All assertion are for the single Product meta data.
		// Soft assertion - Execute all the assertions and then tell out of given set of
		// assertions how many are passed or failed.
		searchPage = accountPage.doSearch("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k, v) -> System.out.println(k + ":" + v));
		softAssert.assertEquals(actProductInfoMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(actProductInfoMap.get("price"), "$2,000.00");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertAll();// This is mandatory to write.
		// It will give all the failure assertions on the console not the passed
		// assertions.
		// For single assertions we have to go with hard assertions.

	}

}

//Its good practise to put data provider in the test class above the @Test annotation
//When data is less we really dont need to maintain the Excel sheet 
//Using Excel sheet is Old School Approach.
//Maintaining the excel sheet also means data is hardcoded in excel sheet
//Constant static data can be maintained any where

//Runing same test with diff test data is data driven approach.
//Delta aaproach- If the search functionlaity is working  for 5  products 
//we will not search for 50000 products 

//Excel sheet maintainence is old school concept.
//When manual team is also involved we can maintain data in excel sheet.
//Training can be given to manual team also.
//Database approach is the worst approach. It is not recommended as its creates dependency in test framework

//For UI automation we will not have 100 columns.

//same data provider can ve used for multiple Test annotations.

//Page Chaining is also known as zig zag pattern.
