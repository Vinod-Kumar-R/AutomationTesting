package com.encash.offers.Webdriver;


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
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.ExtentReport;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;

/**
 * This class is used to configuration Browser releated data
 * @author Vinod Kumar R
 *
 */
public class BrowserInitialize {


	static Logger logger = LogManager.getLogger(BrowserInitialize.class);
	private static  WebDriver drivere = null;
	private static EventFiringWebDriver driver;
	private static  NgWebDriver ngwebdriver = null;
	private static JavascriptExecutor jsDriver;
	private static ExtentReport extentreport = null;
	

	/**
	 * Private constructor is made because to maintain the single browser instance
	 */

	private BrowserInitialize() {

	}



/**
 * This method is used to created the Web driver instance 
 */
	private static void createInstance() {

		Desired de = new Desired();
		DriverManagerType driverManagerType = DriverManagerType.valueOf(ConstantVariable.Browser_Binary_Name.toUpperCase());
		WebDriverManager.getInstance(driverManagerType).setup();
		BrowserExecutionType Bt = BrowserExecutionType.valueOf(ConstantVariable.Test_Execution); 

		switch(Bt) {
		case SYSTEM_CHROME:

			//System.setProperty("webdriver.chrome.logfile",ConstantVariable.Configlocation+File.separator+"log"+File.separator+"chromelog.log");
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
		ngwebdriver = new NgWebDriver(jsDriver);
	}


	/**
	 * this method is used to reuses the same instance of Web driver 
	 * @return
	 */
	public static WebDriver GetWebDriverInstance() {
		if(driver == null) {
			createInstance();
		}

		return driver;

	}
/**
 * this method is used to ngwebdriver instance 
 * @return
 */
	public static NgWebDriver GetNgWebDriverInstance() {
		if(driver == null) {
			createInstance();
		}
		return ngwebdriver;
	}

	/**
	 * This method is used to get the ExtenReport instance 
	 * @return
	 */
	public  static ExtentReport getExtentReportInstance() {
		
		if(extentreport == null) {
			extentreport = new ExtentReport();
		}
		return extentreport;
		
	}
	/**
	 * This method is used to quit the browser instance 
	 * @return
	 */
	public static String QuitBrowser() {

		driver.quit();
		driver = null;
		ngwebdriver = null;
		return "pass";
	}

	/**
	 * this method is used the extend report for updating the browser instance used for testing
	 */
	public static void BrowserInfo() {
		extentreport.SetSystemInfo("Browser Name", driver.getCapabilities().getBrowserName());
		extentreport.SetSystemInfo("Browser Version", driver.getCapabilities().getVersion());
		extentreport.SetSystemInfo("Platform", driver.getCapabilities().getPlatform().toString());
	}


}
