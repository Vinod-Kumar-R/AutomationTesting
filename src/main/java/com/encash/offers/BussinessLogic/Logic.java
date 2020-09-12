package com.encash.offers.BussinessLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Utility.GenericMethod;

public class Logic {
	static Logger logger = Logger.getLogger(GenericMethod.class);
	
	
	public String JishiText(WebDriver driver, String[] StringParam) throws Exception {
		//String ObjectData = GenericMethod.GetObjectName(StringParam[0]); // GetObjectName(StringParam[0]);
		for(int i=1;i<= StringParam.length-2;i++) {
			String[] data = new String[2];
			data[0] = StringParam[0];
			data[1] = StringParam[i];
			logger.info("Waiting for test visible "+ data[1]);
			BaseClass.gm.WaitForTexttVisible(driver,data);
			BaseClass.gm.VerifyText(driver, data);
			logger.info("Verified the test "+ data[1]);
		}
		
		return "pass";
	}
	
	
	public String Banner(WebDriver driver, String[] StringParam) throws Exception {
		//create an array of Banner so that in the end order can be verified 
		
		List<String> Acutalbanner = new ArrayList<String>();
		List<String> Expectedbanner = new ArrayList<String>();
		List<WebElement> ElementList;
		
		//fetch all the Banner name with title and stored the name in list 
		String object1 = GenericMethod.GetObjectName(StringParam[0]);
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
			
			BaseClass.gm.WaitForAttributedPrent(driver, data);
			BaseClass.er.WriteLog(Status.PASS, "capture the Screen shot "+ StringParam[i], BaseClass.gm.takeScreenshot(driver));
			BaseClass.gm.verifyAttributedValue(driver, data);
			BaseClass.er.WriteLog(Status.PASS, "verified the image "+ StringParam[i]);
		}
		
		
		//compare both Actual banner and Expected list are in same order
		if(!Acutalbanner.equals(Expectedbanner)) {
			logger.info("Excel Order Banner is not matching with Expected order bannber in UI");
			BaseClass.er.WriteLog(Status.FAIL, "Excel Order Banner is not matching with Expected order bannber in UI");
			return "fail";
		}
		
		return "Pass";
	}

}
