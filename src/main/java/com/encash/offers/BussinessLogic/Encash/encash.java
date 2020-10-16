package com.encash.offers.BussinessLogic.Encash;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Configuration.ConstantVariable;

/**
 * This class contain all the Business logic for Automation 
 * @author Vinod Kumar R
 *
 */
public class encash {
	static Logger logger = LogManager.getLogger(encash.class.getName());
	
	/**
	 * This Method is use to verify the Jishi text when user click on explore 
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is a array of String variable which hold data 
	 * StringParam[0] contain the Object i.e xpath 
	 * rest of the data are text which need to verify in jishi page
	 * @return it return the status "pass" if execution success else  throw an exception 
	 * @throws Exception
	 */
	public String JishiText(WebDriver driver, String[] StringParam) throws Exception {
		
		for(int i=1;i< StringParam.length;i++) {
			String[] data = new String[2];
			data[0] = StringParam[0];
			data[1] = StringParam[i];
			BaseClass.wm.WaitForTexttVisible(driver,data);
			BaseClass.er.WriteLog(Status.PASS, "Taken the screenshot", BaseClass.gm.takeScreenshot(driver));
			BaseClass.gm.VerifyText(driver, data);
			logger.debug("Verified the test "+ data[1]);
		}
		
		return "pass";
	}
	
	/**
	 * This method is used to verify Banner page when user login to encashoffer page 
	 * and take the screen shot of each banner when it is active 
	 * @param driver
	 * the driver parameter contain the instance of WebDriver
	 * @param StringParam
	 * StringParam is array of String which contain 
	 * StringParam[0] contain the Object i.e xpath 
	 * rest of the parameter the Banner name which need to verify 
	 *
	 * This verified the order of banner displayed by comparing the data from excel sheet
	 * and order in the enchashoffer page
	 *
	 * @return the status as "pass" if script executed success else through an exception 
	 * @throws Exception
	 */
	public String Banner(WebDriver driver, String[] StringParam) throws Exception {
		//create an List which contain Banner name so that in the end order can be verified 
		
		List<String> Acutalbanner = new ArrayList<String>();
		List<String> Expectedbanner = new ArrayList<String>();
		List<WebElement> ElementList;
		
		//fetch all the Banner name with title and stored the name in list 
		String object1 = ConstantVariable.GetObject.get(StringParam[0]);
		//Get the list of the banner name 
		ElementList = driver.findElements(By.xpath(object1));
		
		for(WebElement e : ElementList) {
			Acutalbanner.add(e.getAttribute(StringParam[2]).toString());
		}

		for(int i= 3; i<StringParam.length;i++) {
			String[] data = new String[3];
			data[0] = StringParam[1];
			data[1] = StringParam[2];
			data[2] = StringParam[i];
			
			Expectedbanner.add(StringParam[i]);
			
			BaseClass.wm.WaitForAttributedPrent(driver, data);
			BaseClass.er.WriteLog(Status.PASS, "capture the Screen shot "+ StringParam[i], BaseClass.gm.takeScreenshot(driver));
			BaseClass.gm.verifyAttributedValue(driver, data);
			BaseClass.er.WriteLog(Status.PASS, "verified the image "+ StringParam[i]);
		}
		
		
		//compare both Actual banner and Expected list are in same order
		if(!Acutalbanner.equals(Expectedbanner)) {
			logger.debug("Excel Order Banner is not matching with Expected order bannber in UI");
			BaseClass.er.WriteLog(Status.FAIL, "Excel Order Banner is not matching with Expected order bannber in UI");
			return "fail";
		}
		
		return "Pass";
	}

}
