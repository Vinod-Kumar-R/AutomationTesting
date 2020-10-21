package com.encash.offers.Utility;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class WaitMethod {
	static Logger logger = LogManager.getLogger(WaitMethod.class.getName());
	public static boolean waitstatus = true;
	
	/**
	 * This Method is used to Wait for an Element Visible in an web page  
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object which need to wait in html page 
	 * @return it return the status "pass" if execution success else throw an exception
	 * @throws Exception
	 */
	public  String WaitForElementVisible(String[] StringParam) throws Exception {
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
		By ObjectName = BaseClass.gm.ByType(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);	

		logger.debug("Waiting for the Element Visibility "+ ObjectName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(ObjectName));

		waitstatus = true;
		return "pass";
	}
	
	/**
	 * This Method is used to Wait for Text Visible in an web page  
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object which need to wait in html page 
	 * @return it return the status "pass" if execution success else throw an exception
	 * @throws Exception
	 */

	public  String WaitForTexttVisible(String[] StringParam) throws Exception {
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
		By ObjectName = BaseClass.gm.ByType(StringParam[0]); 
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.debug("Waiting for the Text to be present "+ ObjectName);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(ObjectName, StringParam[1]));

		waitstatus = true;
		return "pass";
	}
	
	/**
	 * This method is used wait for an Attributed value present in html page 
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object which need to present in html page
	 * StringParam[1] contain the attribute in an xpath 
	 * StringParam[2] contain the vaule which need to be present for an attributed  
	 * @return it return the status "pass" if execution success else throw an exception
	 * @throws Exception
	 */

	public  String WaitForAttributedPrent(String[] StringParam) throws Exception {
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
		By ObjectName = BaseClass.gm.ByType(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.debug("Waiting for the attributed presnt and value "+ ObjectName);
		logger.debug("attribute --------->" +StringParam[1]);
		logger.debug("Value --------->" +StringParam[2]);
		
		wait.until(ExpectedConditions.attributeContains(ObjectName, StringParam[1], StringParam[2]));

		waitstatus = true;
		return "pass";
	}
	
	public String AngularWait() {
		NgWebDriver ngwebdriver = BrowserInitialize.GetNgWebDriverInstance();
		ngwebdriver.waitForAngularRequestsToFinish();
		return "pass";
	}

}
