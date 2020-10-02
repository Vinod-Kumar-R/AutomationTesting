package com.encash.offers.Webdriver;

import java.net.MalformedURLException;
import java.net.URL;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;
import com.paulhammant.ngwebdriver.NgWebDriver;

//import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

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

	private static void ConfigureWebDriver() {

		desired = new Desired();

		if(ConstantVariable.BrowserName.equalsIgnoreCase("Chrome")){
			WebDriverManager.chromedriver().setup();
             logger.info("Chrome initialized method is called");
			ChromeOptions op =  desired.ChromeDesired();
			// ChromeDriver driver = new ChromeDriver(de);
			drivere = new ChromeDriver(op);
		/**	driver = new EventFiringWebDriver(drivere);
			EventListener ei = new EventListener();
			driver.register(ei);
			*/
		}

		if(ConstantVariable.BrowserName.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions fo = desired.FirefoxDesired();
			drivere = new FirefoxDriver(fo);
	/**		driver = new EventFiringWebDriver(drivere);
			EventListener ei = new EventListener();
			driver.register(ei);
	*/		
		}

		if(ConstantVariable.BrowserName.equalsIgnoreCase("Android")){
			DesiredCapabilities ca = desired.AndroidDesired();
/**
			try {
				//AppiumDriv
				drivere = new AppiumDriver<WebElement>(new URL(ConstantVariable.AppiumURL),ca);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
**/
		}
		
		if(ConstantVariable.BrowserName.equalsIgnoreCase("MobileChrome")){
			// WebDriverManager.globalConfig().getChromeDriverVersion();
			//WebDriverManager.chromedriver().setup();
			//WebDriverManager.chromedriver().browserVersion("83.0.4103.106").setup();
			
			//System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\Downloads\\chromedriver\\chromedriver.exe");
			//emulator
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\Downloads\\chromedriver83\\chromedriver.exe");
			drivere = new ChromeDriver(desired.MobileChromedriver());
			
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
			ConfigureWebDriver();
		}
	
		return driver;

	}

	public static NgWebDriver GetNgWebDriverInstance() {
		if(driver == null) {
			ConfigureWebDriver();
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
