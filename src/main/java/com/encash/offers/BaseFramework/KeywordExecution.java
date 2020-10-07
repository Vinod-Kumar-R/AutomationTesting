package com.encash.offers.BaseFramework;


import org.openqa.selenium.WebDriver;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.Status;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;

/**
 * This class contain the keyword which need to executed after reading from the test script file
 * @author Vinod Kumar R
 *
 */
public class KeywordExecution {
	static Logger logger = LogManager.getLogger(KeywordExecution.class.getName());
	private WebDriver driver;
	private NgWebDriver ngdriver;
	String status;
	KeywordType keyword;
	
	/**
	 * This the default contractor which will initialize the WebDriver variable
	 */

	public KeywordExecution () {
		
		driver = BrowserInitialize.GetWebDriverInstance();
		ngdriver = BrowserInitialize.GetNgWebDriverInstance();
	}
	
	public void setvalue (KeywordType keyword) {
		this.keyword = keyword;
		
	}

	/**
	 * This method is used to executed the keyword which is read from Test Script file
	 * <p> the 2nd column in the Test script file is called Keyword 
	 * @param StringParam 
	 * contain the array of string data which is required executed particular keyword (nothing but Method)
	 * @throws Exception
	 */
	public void Executed(String[] StringParam) throws Exception {

		switch(keyword) {

		case openencashurl : 
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Opening the URL "+ ConstantVariable.EncashURL);
			driver = BrowserInitialize.GetWebDriverInstance();
			ngdriver = BrowserInitialize.GetNgWebDriverInstance();
			driver.get(ConstantVariable.EncashURL);
			status = "pass";
			Testresult(status, "Browser Open");
			break;
			
		case openadminurl : 
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Opening the URL "+ ConstantVariable.AdminURL);
			driver = BrowserInitialize.GetWebDriverInstance();
			ngdriver = BrowserInitialize.GetNgWebDriverInstance();
			driver.get(ConstantVariable.AdminURL);
			status = "pass";
			Testresult(status, "Browser Open");
			break;

		case waitforelementvisible :

			logger.debug("Waiting for the element visible");
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			status = BaseClass.gm.WaitForElementVisible(driver,StringParam);
			Testresult(status, keyword.toString());
			break;
			
		case waitfortexttvisible :
			
			logger.debug("Waiting for the Text visible");
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			status = BaseClass.gm.WaitForTexttVisible(driver,StringParam);
			Testresult(status, keyword.toString());
			
			break;
		
		case click :
			
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("clicking  on Element");
			status = BaseClass.gm.click(driver,StringParam);
			logger.debug("clicked  on Element");
			Testresult(status, keyword.toString());
			break;
		
		case quitbrowser :
			
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			status = BrowserInitialize.QuitBrowser();
			logger.debug("QuiteBrowser");
			Testresult(status, keyword.toString());
			break;
			
		case verifytext :
			
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			status = BaseClass.gm.VerifyText(driver, StringParam);
			logger.debug("verified the text");
			Testresult(status,keyword.toString());
			break;
			
		case implictwait :
			
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Manually waiting ");
			Thread.sleep(Integer.parseInt(StringParam[0]));
			break;
			
		case jishitext :
			
			BaseClass.er.WriteInfo("Executing key word --->"+ keyword);
			status = BaseClass.lg.JishiText(driver, StringParam);
			logger.debug("verified the text");
			Testresult(status,keyword.toString());
			break;
			
		case takescreenshot :
			
			BaseClass.er.WriteInfo("Executing key word ---> "+ keyword);
			BaseClass.er.AttachScreenshot(BaseClass.gm.takeScreenshot(driver));
			status = "pass";
			logger.debug("taken the screen shot");
			Testresult(status,keyword.toString());
			break;
		
		case waitforattributedprent :
			
			BaseClass.er.WriteInfo("Executing key word ---> "+ keyword);
			status = BaseClass.gm.WaitForAttributedPrent(driver,StringParam);
			logger.debug("Waited for An Attibuted ");
			Testresult(status,keyword.toString());
			break;
			
		case verifyattributedvalue :
			
			BaseClass.er.WriteInfo("Executing key word ---> "+ keyword);
			status = BaseClass.gm.verifyAttributedValue(driver,StringParam);
			logger.debug("verified the attributed Value");
			Testresult(status,keyword.toString());
			break;
		
		case banner :
			
			BaseClass.er.WriteInfo("Executing key word ---> "+ keyword);
			status = BaseClass.lg.Banner(driver,StringParam);
			logger.debug("verifed the Banner");
			Testresult(status,keyword.toString());
			break;
			
		case entertext:
			
			BaseClass.er.WriteInfo("Executing key word ---> "+ keyword);
			status = BaseClass.gm.entertext(driver,StringParam);
			logger.info("Enter the text");
			Testresult(status,keyword.toString());
			
		case comment :
			 break;

		}
	}

	/**
	 * This method capture the status of each keyword by saying pass or fail
	 * 
	 * @param status 
	 * it contain pass or fail 
	 * @param message
	 * Message contain information of the keyword 
	 * @throws Exception
	 */
	public void Testresult(String status,String message) throws  Exception {
		if(status.equalsIgnoreCase("Pass")) {
			BaseClass.er.WriteLog(Status.PASS, message );
		}
		if(status.equalsIgnoreCase("fail")) {
			BaseClass.er.AttachScreenshot(BaseClass.gm.takeScreenshot(driver));
			BaseClass.er.WriteLog(Status.FAIL, message );
		}

	}

}
