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
import org.openqa.selenium.remote.BrowserType;
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

	//static Logger logger = Logger.getLogger(BrowserInitialize.class);
	static Logger logger = LogManager.getLogger(BrowserInitialize.class);
	public static  WebDriver drivere = null;
	public static EventFiringWebDriver driver;
	
	public static  NgWebDriver ngwebdriver = null;
	public static JavascriptExecutor jsDriver;
	private static Desired desired;
	//public static boolean waitstatus = true;


	private BrowserInitialize() {
		
	}

	
	

	public static void createInstance() {

		Desired de = new Desired();
		DriverManagerType driverManagerType = DriverManagerType.valueOf(ConstantVariable.BrowserName.toUpperCase());
		WebDriverManager.getInstance(driverManagerType).setup();

		BrowserExecutionType Bt = BrowserExecutionType.valueOf(ConstantVariable.BrowserName); 
		
		switch(Bt) {
			case CHROME:
				
				drivere = new ChromeDriver(de.ChromeDesired());
				break;
		
			case FIREFOX:
				
				drivere = new FirefoxDriver(de.FirefoxDesired());
				break;

			case OPERA:
				
				drivere = new OperaDriver(de.OperaDesired());
				break;
			
			case EDGE:
				
				drivere = new EdgeDriver(de.EdgeDesired());
				break;
			
			case IEXPLORER:
				
				drivere = new InternetExplorerDriver(de.InternetExploreDesired());
				break;
			
			case SAFARI:
				
				drivere = new SafariDriver(de.SafariDesired());
				break;
				
			default : 
				
				logger.debug("invalid browser selected");
			}
			
			driver = new EventFiringWebDriver(drivere);
			EventListener ei = new EventListener();
			driver.register(ei);



			//driver.manage().window().fullscreen();
			jsDriver = (JavascriptExecutor) driver ;
			ngwebdriver = new NgWebDriver(jsDriver).withRootSelector("9.1.9");
	}


	public static WebDriver GetWebDriverInstance() {
		if(driver == null) {
			//ConfigureWebDriver();
			createInstance();
		}
	
		return driver;

	}

	public static NgWebDriver GetNgWebDriverInstance() {
		if(driver == null) {
			//ConfigureWebDriver();
			createInstance();
		}
		return ngwebdriver;
	}

	public static String QuitBrowser() {
		driver.quit();
		driver = null;
		return "pass";
	}

	public static void BrowserInfo() {
		BaseClass.er.SetSystemInfo("Browser Name", driver.getCapabilities().getBrowserName());
		BaseClass.er.SetSystemInfo("Browser Version", driver.getCapabilities().getVersion());
		BaseClass.er.SetSystemInfo("Platform", driver.getCapabilities().getPlatform().toString());
	}
	
	
}
