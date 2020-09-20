package com.encash.offers.Webdriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.encash.offers.Configuration.ConstantVariable;
import com.paulhammant.ngwebdriver.NgWebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class is used to configuration Browser releated data
 * @author Vinod Kumar R
 *
 */
public class BrowserInitialize {

	static Logger logger = Logger.getLogger(BrowserInitialize.class);
	public static  WebDriver drivere = null;
	public static EventFiringWebDriver driver;
	public static  NgWebDriver ngwebdriver = null;
	public static JavascriptExecutor jsDriver;
	//public static boolean waitstatus = true;


	private BrowserInitialize() {

	}

	private static void ConfigureWebDriver() {



		if(ConstantVariable.BrowserName.equalsIgnoreCase("Chrome")){
			WebDriverManager.chromedriver().setup();
			drivere = new ChromeDriver();


		}

		if(ConstantVariable.BrowserName.equalsIgnoreCase("firefox")){
			WebDriverManager.firefoxdriver().setup();
			drivere = new FirefoxDriver();
		}
		
		if(ConstantVariable.BrowserName.equalsIgnoreCase("Android")){
			
			DesiredCapabilities ca = new DesiredCapabilities();
			//ca.setCapability("platformName", "Android");
			//ca.setCapability("deviceName", "Android");
			//ca.setCapability("browserName", "Chrome");
			
			
			
			
			
			//ca.setCapability("uiautomator2ServerLaunchTimeout", "100000");
			ca.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			ca.setCapability(MobileCapabilityType.APPIUM_VERSION, "10");
			ca.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus 5");
			ca.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
			ca.setCapability(MobileCapabilityType.UDID, "04895b1943876a3e");
			ca.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 180);
			ca.setCapability("uiautomator2ServerLaunchTimeout", "50000");
			ca.setCapability("chromedriverExecutable", "C:\\Users\\HP\\Downloads\\chromedriver\\chromedriver.exe");
			//ca.setCapability(MobileCapabilityType.BROWSER_VERSION, "Android");
			
			
			try {
				//AndroidDriver<WebElement> drivere = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"),ca);
				drivere = new AppiumDriver(new URL("http://localhost:4723/wd/hub"),ca);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//WebDriverManager.firefoxdriver().setup();
			//drivere = new FirefoxDriver();
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
}
