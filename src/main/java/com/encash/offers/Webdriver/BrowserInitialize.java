package com.encash.offers.Webdriver;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;
import com.paulhammant.ngwebdriver.NgWebDriver;

//import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

/**
 * This class is used to configuration Browser releated data
 * @author Vinod Kumar R
 *
 */
public class BrowserInitialize {

	
	static Logger logger = LogManager.getLogger(BrowserInitialize.class);
	public static  WebDriver drivere = null;
	public static EventFiringWebDriver driver;
	
	public static  NgWebDriver ngwebdriver = null;
	public static JavascriptExecutor jsDriver;
	private static Desired desired;
	
/**
 * Private constructor is made because to maintain the single browser instance
 */

	private BrowserInitialize() {
		
	}

	
	

	private static void createInstance() {

		Desired de = new Desired();
		DriverManagerType driverManagerType = DriverManagerType.valueOf(ConstantVariable.Browser_Binary_Name.toUpperCase());
		WebDriverManager.getInstance(driverManagerType).setup();

		BrowserExecutionType Bt = BrowserExecutionType.valueOf(ConstantVariable.Test_Execution); 
		
		switch(Bt) {
			case SYSTEM_CHROME:
				
				drivere = new ChromeDriver(de.ChromeDesired());
				break;
		
			case SYSTEM_FIREFOX:
				
				drivere = new FirefoxDriver(de.FirefoxDesired());
				break;

			case SYSTEM_OPERA:
				
				drivere = new OperaDriver(de.OperaDesired());
				break;
			
			case SYSTEM_EDGE:
				
				drivere = new EdgeDriver(de.EdgeDesired());
				break;
			
			case SYSTEM_IEXPLORER:
				
				drivere = new InternetExplorerDriver(de.InternetExploreDesired());
				break;
			
			case SYSTEM_SAFARI:
				
				drivere = new SafariDriver(de.SafariDesired());
				break;
				
			case ANDROID_CHROME :
				break;
			case IOS_SAFARI:
				break;
			case SYSTEM_MOBILE_EMULATION:
				drivere = new ChromeDriver();
				break;
				
			default : 
				
				logger.debug("invalid browser selected");
			}
			
			driver = new EventFiringWebDriver(drivere);
			EventListener ei = new EventListener();
			driver.register(ei);
			
			jsDriver = (JavascriptExecutor) driver ;
			ngwebdriver = new NgWebDriver(jsDriver).withRootSelector("9.1.9");
	}


	public static WebDriver GetWebDriverInstance() {
		if(driver == null) {
			createInstance();
		}
	
		return driver;

	}

	public static NgWebDriver GetNgWebDriverInstance() {
		if(driver == null) {
			createInstance();
		}
		return ngwebdriver;
	}

	public static String QuitBrowser() {
		driver.quit();
		driver = null;
		ngwebdriver = null;
		return "pass";
	}

	public static void BrowserInfo() {
		BaseClass.er.SetSystemInfo("Browser Name", driver.getCapabilities().getBrowserName());
		BaseClass.er.SetSystemInfo("Browser Version", driver.getCapabilities().getVersion());
		BaseClass.er.SetSystemInfo("Platform", driver.getCapabilities().getPlatform().toString());
	}
	
	
}
