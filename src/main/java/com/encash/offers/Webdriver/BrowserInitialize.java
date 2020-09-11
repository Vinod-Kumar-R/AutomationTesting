package com.encash.offers.Webdriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.GenericMethod;
import com.paulhammant.ngwebdriver.NgWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

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


		driver = new EventFiringWebDriver(drivere);
		EventListener ei = new EventListener();
		driver.register(ei);
		driver.manage().window().fullscreen();
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
