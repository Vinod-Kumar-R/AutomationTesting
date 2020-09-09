package com.encash.offers.BussinessLogic;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.encash.offers.BaseFramework.BaseClass;
import com.encash.offers.Utility.GenericMethod;
import com.encash.offers.Webdriver.BrowserInitialize;

public class Logic {
	static Logger logger = Logger.getLogger(GenericMethod.class);
	public String JishiText(WebDriver driver, String[] StringParam) throws Exception {
		//String ObjectData = GenericMethod.GetObjectName(StringParam[0]); // GetObjectName(StringParam[0]);
		for(int i=1;i<= StringParam.length-2;i++) {
			String[] data = new String[2];
			data[0] = StringParam[0];
			data[1] = StringParam[i];
			logger.info("Waiting for test visible "+ data[1]);
			BrowserInitialize.WaitForTexttVisible(data);
			BaseClass.gm.VerifyText(driver, data);
			logger.info("Verified the test "+ data[1]);
		}
		
		return "pass";
	}

}
