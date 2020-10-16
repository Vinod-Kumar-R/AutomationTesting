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
	static Logger logger = LogManager.getLogger(GenericMethod.class.getName());
	

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
		String relativepath = new File(ConstantVariable.ResultDatelocaton).toURI().relativize(new File(absolutepath).toURI()).getPath();
		logger.debug(" relative path "+ relativepath);
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
