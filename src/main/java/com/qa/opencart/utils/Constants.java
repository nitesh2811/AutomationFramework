package com.qa.opencart.utils;

/**
 * @author Nitesh Agrawal
 */

import java.util.Arrays;
import java.util.List;

public class Constants {

	// Static so that we dont have to create the Object of this class.

	public static final String LOGIN_PAGE_TITLE = "Account Login";
	public static final String LOGIN_PAGE_FRACTION_URL = "route=account/login";
	public static final String ACCOUNT_PAGE_TITLE = "My Account";

	public static final List<String> Account_Section_List = Arrays.asList("My Account", "My Orders",
			"My Affiliate Account", "Newsletter");

	public static final String REGISTRATION_PAGE_TITLE = "Register Account";
	public static final int DEFAULT_TIME_OUT = 5;
	public static final String REGISTRATION_PAGE_FRACTION_URL = "route=account/register";
	public static final int MACBOOK_PRODUCT_COUNT = 3;
	public static final int IMAC_PRODUCT_COUNT = 1;
	public static final String REGISTRATION_PAGE_HEADER = "Register Account";
	public static final String ACCOUNT_CREATION_HEADER = "Your Account Has Been Created!";
	public static final Object MACBOOK_IMAGES_COUNT = 4;
	public static final String REGISTGER_SHEET_NAME = "register";
	public static final String PRODUCT_SHEET_NAME ="product";

	public static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/demoCartTestData.xlsx";

}

// For static code analysis it is scanned by the sonal code 
// It tells what are the problem in coding standard.
// It is not mandatory but good companies have this functionalit.




