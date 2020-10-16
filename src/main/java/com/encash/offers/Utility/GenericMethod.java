package com.encash.offers.Utility;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Webdriver.BrowserInitialize;

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
	public String click(String[] StringParam) throws Exception {
		
		WebElement clicktype = getElement(StringParam[0]);
		clicktype.click();
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

	public String VerifyText(String[] StringParam) throws Exception {
		logger.debug("Verifying the text-------> "+StringParam[1]);
		WebElement ObjectData = BaseClass.gm.getElement(StringParam[0]);
		if(ObjectData.getText().equalsIgnoreCase(StringParam[1])) {
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
	public  String takeScreenshot() throws Exception {
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
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
	public String verifyAttributedValue (String[] StringParam) throws Exception {
		
		WebElement ObjectData = BaseClass.gm.getElement(StringParam[0]);
		if(ObjectData.getAttribute(StringParam[1]).equalsIgnoreCase(StringParam[2])) {
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
	public String entertext(String[] StringParam) {

		WebElement ObjectData = BaseClass.gm.getElement(StringParam[0]);
		ObjectData.sendKeys(StringParam[1]);
		return "pass";
	}
	

	/**
	 * This Element is used to get the WebElement for and element 
	 * @param driver
	 * @param Object
	 * @return
	 */
	public WebElement getElement(String Object) {
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
		By byElement = ByType(Object);
		WebElement element = driver.findElement(byElement);
		return element;
	}
	
	/**
	 * This Method is used to get the WebElements from an element
	 * @param driver
	 * @param Object
	 * @return
	 */
	public List<WebElement> getElements(String Object){
		WebDriver driver = BrowserInitialize.GetWebDriverInstance();
		By byElement = ByType(Object);
		List<WebElement> element = driver.findElements(byElement);
		return element;
	}


	/**
	 * This method is used for generic way of getting By class type
	 * i.e. id, xpath,css,name etc
	 * @param Object
	 * @return
	 */
	public By ByType(String Object) {

		List<String> data = ConstantVariable.GetObject.get(Object);

		//get the locator value type
	ByMethod locator = ByMethod.valueOf(data.get(0));
		String expression = data.get(1);

		By byElement = null;
		switch (locator) {
		case xpath: {
			byElement = By.xpath(expression);
			break;
		}
		case id: {
			byElement = By.id(expression);
			break;
		}
		case name: {
			byElement = By.name(expression);
			break;
		}
		case classname: {
			byElement = By.className(expression);
			break;
		}
		case linktext: {
			byElement = By.linkText(expression);
			break;
		}
		case paritallinktext: {
			byElement = By.partialLinkText(expression);
			break;
		}
		case tagname: {
			byElement = By.tagName(expression);
			break;
		}
	}
		return byElement;

	}
	
}
