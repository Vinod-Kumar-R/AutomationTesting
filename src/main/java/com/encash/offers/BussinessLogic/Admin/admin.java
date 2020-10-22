package com.encash.offers.BussinessLogic.Admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import com.encash.offers.Utility.ExtentReport;
import com.encash.offers.Utility.GenericMethod;
import com.encash.offers.Utility.WaitMethod;
import com.encash.offers.Webdriver.BrowserInitialize;

public class admin {
	static Logger logger = LogManager.getLogger(admin.class);
	
	private GenericMethod gm;
	private WaitMethod wm;
	private ExtentReport er;
	
	public admin() {
		gm = new GenericMethod();
		wm = new WaitMethod();
		er = BrowserInitialize.getExtentReportInstance();
		
	}
	
	public String adminurlopen(String[] StringParam) throws Exception {
		//enter username and password 
		logger.debug("Enter the User name ");
		WebElement username = gm.getElement("admin_username");
		username.sendKeys(StringParam[0]);
		logger.debug("Enter the User Password ");
		WebElement password = gm.getElement("admin_password");
		password.sendKeys(StringParam[1]);
		logger.debug("click on login button");
		WebElement login = gm.getElement("admin_login");
		login.click();
		return "pass";
	}
	
	public String CreateCompetation(String[] StringParam) throws Exception {
		WebElement element;		
		
		GotoCompetation();
		wm.AngularWait();
		//element = gm.getElement(StringParam[4]);
		//element.click();
		
		return "pass";
	}
	
	public String GotoCompetation() {
		WebElement element;	
		logger.debug("Waiting for the Angular request completed");
		wm.AngularWait();
		
		logger.debug("click on the Encash button");
		element = gm.getElement("expand_encash");
		element.click();
		
		logger.debug("Waiting for the Angular request completed");
		wm.AngularWait();
		
		logger.debug("click on the Encash button");
		element = gm.getElement("competitions");
		element.click();
		
		logger.debug("Waiting for the Angular request completed");
		wm.AngularWait();
		
		
		logger.debug("click on the three dot  new competations");
		element = gm.getElement("threedot");
		element.click();
		
		logger.debug("waiting for the Angular request completed");
		wm.AngularWait();
		
	    logger.debug("clicking on the Add to queue button");
	    element = gm.getElement("competitions_queue");
	    element.click();
	    
	    return "Pass";
	}

}
