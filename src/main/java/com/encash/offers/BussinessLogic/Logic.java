package com.encash.offers.BussinessLogic;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

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
		String[] Actualbanner = new String[StringParam.length-2];
		
		for(int i= 2; i<StringParam.length;i++) {
			String[] data = new String[3];
			data[0] = StringParam[0];
			data[1] = StringParam[1];
			data[2] = StringParam[i];
			Actualbanner[i-2] = StringParam[i];
			BaseClass.gm.WaitForAttributedPrent(driver, data);
			//BaseClass.er.AttachScreenshot(BaseClass.gm.takeScreenshot(driver));
			BaseClass.er.WriteLog(Status.PASS, "capture the Screen shot "+ StringParam[i], BaseClass.gm.takeScreenshot(driver));
			BaseClass.gm.verifyAttributedValue(driver, data);
			BaseClass.er.WriteLog(Status.PASS, "verified the image "+ StringParam[i]);
		}
		
		//verify Acutualbanner and expected Banner
		for(int i=0;i<StringParam.length-2;i++) {
			if(StringParam[i+2].equals(Actualbanner[i])) {
				logger.info("Excel Oder Banner -> "+ StringParam[1+2] + 
						" is matching with Recored -> "+ Actualbanner[i]);
			}
			else {
				logger.info("Excel Oder Banner -> "+ StringParam[1+2] + 
						" is matching with Recored -> "+ Actualbanner[i]);
				BaseClass.er.WriteLog(Status.FAIL, "Excel Oder Banner -> "+ StringParam[1+2] + 
				" is mismatch with Recored -> "+ Actualbanner[i]);
				return "fail";
			}
		}
		
		return "Pass";
	}

}
