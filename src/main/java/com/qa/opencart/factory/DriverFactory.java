package com.qa.opencart.factory;

/**
 * @author Nitesh Agrawal
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.DriverException;
import com.qa.opencart.utils.Browser;
import com.qa.opencart.utils.Errors;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public static String highlight;
	public OptionsManager optionManager;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static final Logger log = Logger.getLogger(DriverFactory.class);

	/**
	 * This method is used to initialize WebDriver on the basis of given browser
	 * name. This method will take care of remote and local execution
	 * 
	 * @param properties files.
	 * @return
	 */
	public WebDriver init_driver(Properties prop) {
		String browserName = prop.getProperty("browser").trim();
		// System.out.println("browser name is :" + browserName);
		log.info("brower name is :" + browserName);
		highlight = prop.getProperty("highlight").trim();
		optionManager = new OptionsManager(prop);

		if (browserName.equalsIgnoreCase(Browser.CHROME_BROWSER_VALUE)) {
			log.info("Running Test Case on chrome browser");

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(Browser.CHROME_BROWSER_VALUE);
			} else {

				WebDriverManager.chromedriver().setup();

				// driver = new ChromeDriver(optionManager.getChromeOptions());
				tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
			}
		}

		else if (browserName.equalsIgnoreCase(Browser.FIREFOX_BROWSER_VALUE)) {
			log.info("Running Test case on the firefox browser");
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteWebDriver(Browser.FIREFOX_BROWSER_VALUE);
			} else {

				WebDriverManager.firefoxdriver().setup();
				// driver = new FirefoxDriver(optionManager.getFireFoxOptions());
				tlDriver.set(new FirefoxDriver(optionManager.getFireFoxOptions()));
			}
		} else if (browserName.equalsIgnoreCase(Browser.SAFARI_BROWSER_VALUE)) {
			log.info("Running Test case on the safari browser");
			// We dont need Web Driver Manager for safari browser.
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
		} else {
			log.info("Please pass the right brower name:" + browserName);
			// System.out.println("Please pass the right browser name :" + browserName);
			throw new DriverException("Browser is not correct");
		
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
		log.info("Running Test on Environment:" + envName);
		// System.out.println("Running Test on :" + envName);
		// This will give me the environment name.

		if (envName == null) {
			// We will execute the test cases on QA Environment.
			// System.out.println("No Environment is given.Hence running it on QA
			// Environment");
			log.info("No Environment is provided by the user. Hence running it on QA");
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
					log.info(Errors.BROWSER_NOT_FOUND);
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
		// try {
		// ip = new FileInputStream("./src/test/resources/config/config.properties");
		//
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
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

	public void init_remoteWebDriver(String browserName) {

		log.info("Running test cases on remote grid server :" + browserName);

		if (browserName.equalsIgnoreCase("chrome")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManager.getChromeOptions()));
			} catch (MalformedURLException e) {

				e.printStackTrace();
			}

		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			try {
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionManager.getFireFoxOptions()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

	}

}

// We can pass multiple environment properties also from command prompt like browser name etc.
// We have to use System.getProperty to get the values from the command prompt.
// If we are not allowed to use WebDriverManager then we have to use System.getProperty
