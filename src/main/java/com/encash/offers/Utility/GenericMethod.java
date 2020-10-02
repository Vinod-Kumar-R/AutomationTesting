package com.encash.offers.Utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.encash.offers.Configuration.ConstantVariable;

/**
 * This class contain the Generic method which is used during Automation execution Script 
 * @author Vinod Kumar R
 *
 */
public class GenericMethod {
	//static Logger logger = Logger.getLogger(GenericMethod.class.getName());
	static Logger logger = LogManager.getLogger(GenericMethod.class.getName());
	public static boolean waitstatus = true;

	/**
	 * This is used to click on the WebElement on current html page
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object i.e xpath 
	 * @return it return the status "pass" if execution success else throw an exception 
	 * @throws Exception
	 */
	public String click(WebDriver driver,String[] StringParam) throws Exception {
		
		String ObjectData = ConstantVariable.GetObject.get(StringParam[0]);
		driver.findElement(By.xpath(ObjectData)).click();
		return "pass";
	}

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
	public  String WaitForElementVisible(WebDriver driver,String[] StringParam) throws Exception {
		String ObjectName = ConstantVariable.GetObject.get(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);	

		logger.debug("Waiting for the Element Visibility "+ ObjectName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjectName)));

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

	public  String WaitForTexttVisible(WebDriver driver, String[] StringParam) throws Exception {
		//String ObjectName = GenericMethod.GetObjectName(string[0]);
		String ObjectName = ConstantVariable.GetObject.get(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.debug("Waiting for the Text to be present "+ ObjectName);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(ObjectName), StringParam[1]));

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

	public  String WaitForAttributedPrent(WebDriver driver,String[] StringParam) throws Exception {
		//String ObjectName = GenericMethod.GetObjectName(StringParam[0]);
		String ObjectName = ConstantVariable.GetObject.get(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.debug("Waiting for the attributed presnt and value "+ ObjectName);
		logger.debug("attribute --------->" +StringParam[1]);
		logger.debug("Value --------->" +StringParam[2]);
		//wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(ObjectName), string[1]));

		wait.until(ExpectedConditions.attributeContains(By.xpath(ObjectName), StringParam[1], StringParam[2]));

		waitstatus = true;
		return "pass";
	}
	
	/**
	 * This method is used to verify the text present for an WebElement
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object which need to compare the text  in html page
	 * StringParam[1] contain the expected text when need to compare with text on html page 
	 * @return it return the status "pass" if execution success else throw an exception 
	 * @throws Exception
	 */

	public String VerifyText(WebDriver driver,String[] StringParam) throws Exception {
		logger.debug("Verifying the text-------> "+StringParam[1]);
		//String ObjectData = GetObjectName(StringParam[0]);
		String ObjectData = ConstantVariable.GetObject.get(StringParam[0]);
		if(driver.findElement(By.xpath(ObjectData)).getText().equalsIgnoreCase(StringParam[1])) {
			return "pass";
		}
		return "pass";
	}

	
	/**
	 * This Method is used to take an WebBrowser Screenshot
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @return the file location in the String format
	 * @throws Exception
	 */
	public  String takeScreenshot(WebDriver driver) throws Exception {
		long Filename = System.currentTimeMillis();
		if (driver instanceof TakesScreenshot) {
			File tempFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(tempFile, new File(ConstantVariable.ScreenShotlocation + File.separator +Filename + ".png"));
		}
		String absolutepath = ConstantVariable.ScreenShotlocation +  File.separator  + Filename + ".png";
		logger.debug("absolutepath "+ absolutepath);
		//String relative[] = absolutepath.split( File.separator.toString() );
		//String relativepath = "../" +relative[relative.length-2]+ File.separator  +relative[relative.length-1];
		String relativepath = new File(ConstantVariable.ResultDatelocaton).toURI().relativize(new File(absolutepath).toURI()).getPath();
		logger.debug(" relative path "+ relativepath);
		//return ConstantVariable.ScreenShotlocation + "/" +Filename + ".png";
		return ".."+File.separator+relativepath;
	}

	/**
	 * This Method is used to verify the Attributed Value contain
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object which need to compare the text  for an attribute value
	 * StringParam[1] contain the expected text when need to compare with text with attribute value 
	 * @return it return the status "pass" if execution success else throw an exception 
	 * @throws Exception
	 *
	 */
	public String verifyAttributedValue (WebDriver driver, String[] StringParam) throws Exception {
		//String ObjectData = GetObjectName(StringParam[0]);
		String ObjectData = ConstantVariable.GetObject.get(StringParam[0]);
		if(driver.findElement(By.xpath(ObjectData)).getAttribute(StringParam[1]).equalsIgnoreCase(StringParam[2])) {
			return "pass";
		}
		else {
			return "false";
		}
	}
	
	/**
	 * This Method is used to enter the text in the text filed
	 * @param driver
	 * @param StringParam
	 * @return
	 */
	public String entertext(WebDriver driver, String[] StringParam) {
		String ObjectData = ConstantVariable.GetObject.get(StringParam[0]);
		driver.findElement(By.xpath(ObjectData)).sendKeys(StringParam[1]);
		
		
		return "pass";
	}
	
}
