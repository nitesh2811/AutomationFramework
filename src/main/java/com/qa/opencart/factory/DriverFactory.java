package com.qa.opencart.factory;

/**
 * @author Nitesh Agrawal
 */

import java.io.File;

/**
 * @author Nitesh Agrawal
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.utils.Browser;
import com.qa.opencart.utils.Errors;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	public static final Logger log=Logger.getLogger(DriverFactory.class);

	/**
	 * This method is used to initialize WebDriver on the basis of given browser
	 * name. This method will take care of remote and local execution
	 * 
	 * @param properties files.
	 * @return
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is :" + browserName);
		log.info("brower name is :"+browserName);
		highlight = prop.getProperty("highlight").trim();
		optionManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase(Browser.CHROME_BROWSER_VALUE)) {
			WebDriverManager.chromedriver().setup();
			log.info("Running Test Case on chrome browser");
			// driver = new ChromeDriver(optionManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
		}

		else if (browserName.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VALUE)) {
			WebDriverManager.firefoxdriver().setup();
			// driver = new FirefoxDriver(optionManager.getFireFoxOptions());
			tlDriver.set(new FirefoxDriver(optionManager.getFireFoxOptions()));

		} else if (browserName.equalsIgnoreCase(Browser.SAFARI_BROWSER_VALUE)) {
			// We dont need Web Driver Manager for safari browser.
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		} else {
			System.out.println("Please pass the right browser name :" + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().fullscreen();
		getDriver().get(prop.getProperty("url"));
		log.info(prop.getProperty("url"));

		return getDriver();
	}

	/**
	 * This will return the thread local copy of the WebDriver(driver).Every thread
	 * will gets its own copy of the Driver.
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initialize the properties based on given environment
	 * QA/Dev/Stage/Prod.
	 * 
	 * @return
	 */

	public Properties init_prop() {

		FileInputStream ip = null;

		// We have to pass the envrionment variable from the maven command line.
		// maven clean install -Denv="qa"
		// maven clean install means perform the entire life-cycle.
		// env is the variable name
		// Make sure JAVA_HOME is setup in the Environment Variables.
		// We have to avoid the use of hard coded values.
		// Framework should be generic in nature.

		String envName = System.getProperty("env");
		System.out.println("Running Test on :" + envName);
		// This will give me the environment name.

		if (envName == null) {
			// We will execute the test cases on QA Environment.
			System.out.println("No Environment is given.Hence running it on QA Environment");
			log.info("No Environment is provided by the user");
			try {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				default:
					System.out.println(Errors.BROWSER_NOT_FOUND);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		prop = new Properties();
		try {
			prop.load(ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// . (dot) means from the current project directory
		// We dont have to use throws declaration in the framework
		// It is a checked Exception.(Exception checked by compiler)
		//		try {
		//			ip = new FileInputStream("./src/test/resources/config/config.properties");
		//			
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		return prop;
	}

	/**
	 * Take ScreenShot Method
	 */

	public static String getScreenShot() {

		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}


// We can pass multiple environment properties also from command prompt
// We have to use System.getProperty to get the values from the command prompt.


// If we are not allowed to use WebDriverManager then we have to use System.getProperty
