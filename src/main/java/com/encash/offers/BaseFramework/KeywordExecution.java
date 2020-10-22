package com.encash.offers.BaseFramework;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import com.aventstack.extentreports.Status;
import com.encash.offers.BussinessLogic.Admin.admin;
import com.encash.offers.BussinessLogic.Encash.encash;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.ExtentReport;
import com.encash.offers.Utility.GenericMethod;
import com.encash.offers.Utility.WaitMethod;
import com.encash.offers.Webdriver.BrowserInitialize;

/**
 * This class contain the keyword which need to executed after reading from the test script file
 * @author Vinod Kumar R
 *
 */
public class KeywordExecution {
	static Logger logger = LogManager.getLogger(KeywordExecution.class.getName());
	private WebDriver driver;
	private GenericMethod gm;
	private admin ad;
	private WaitMethod wm;
	private encash en;
	private ExtentReport er;
	String status;
	KeywordType keyword;
	
	/**
	 * This the default contractor which will initialize the required class variable
	 */

	public KeywordExecution () {
		ad = new admin();
		wm= new WaitMethod();
		en = new encash();
		gm = new GenericMethod();
		er = BrowserInitialize.getExtentReportInstance();
		
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
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Opening the URL "+ ConstantVariable.EncashURL);
			driver = BrowserInitialize.GetWebDriverInstance();
			driver.get(ConstantVariable.EncashURL);
			status = "pass";
			Testresult(status, "Browser Open");
			break;
			
		case openadminurl : 
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Opening the URL "+ ConstantVariable.AdminURL);
			driver = BrowserInitialize.GetWebDriverInstance();
			driver.get(ConstantVariable.AdminURL);
			status = ad.adminurlopen(StringParam);
			Testresult(status, "Browser Open");
			break;

		case waitforelementvisible :

			logger.debug("Waiting for the element visible");
			er.WriteInfo("Executing key word --->"+ keyword);
			status = wm.WaitForElementVisible(StringParam);
			Testresult(status, keyword.toString());
			break;
			
		case waitfortexttvisible :
			
			logger.debug("Waiting for the Text visible");
			er.WriteInfo("Executing key word --->"+ keyword);
			status = wm.WaitForTexttVisible(StringParam);
			Testresult(status, keyword.toString());
			
			break;
		
		case click :
			
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("clicking  on Element");
			status = gm.click(StringParam[0]);
			logger.debug("clicked  on Element");
			Testresult(status, keyword.toString());
			break;
		
		case quitbrowser :
			
			er.WriteInfo("Executing key word --->"+ keyword);
			status = BrowserInitialize.QuitBrowser();
			logger.debug("QuiteBrowser");
			Testresult(status, keyword.toString());
			break;
			
		case verifytext :
			
			er.WriteInfo("Executing key word --->"+ keyword);
			status = gm.VerifyText(StringParam);
			logger.debug("verified the text");
			Testresult(status,keyword.toString());
			break;
			
		case implictwait :
			
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.debug("Manually waiting ");
			Thread.sleep(Integer.parseInt(StringParam[0]));
			break;
			
		case jishitext :
			
			er.WriteInfo("Executing key word --->"+ keyword);
			status = en.JishiText(StringParam);
			logger.debug("verified the text");
			Testresult(status,keyword.toString());
			break;
			
		case takescreenshot :
			
			er.WriteInfo("Executing key word ---> "+ keyword);
			er.AttachScreenshot(gm.takeScreenshot());
			status = "pass";
			logger.debug("taken the screen shot");
			Testresult(status,keyword.toString());
			break;
		
		case waitforattributedprent :
			
			er.WriteInfo("Executing key word ---> "+ keyword);
			status = wm.WaitForAttributedPrent(StringParam);
			logger.debug("Waited for An Attibuted ");
			Testresult(status,keyword.toString());
			break;
			
		case verifyattributedvalue :
			
			er.WriteInfo("Executing key word ---> "+ keyword);
			status = gm.verifyAttributedValue(StringParam);
			logger.debug("verified the attributed Value");
			Testresult(status,keyword.toString());
			break;
		
		case banner :
			
			er.WriteInfo("Executing key word ---> "+ keyword);
			status = en.Banner(StringParam);
			logger.debug("verifed the Banner");
			Testresult(status,keyword.toString());
			break;
			
		case entertext:
			
			er.WriteInfo("Executing key word ---> "+ keyword);
			status = gm.entertext(StringParam);
			logger.info("Enter the text");
			Testresult(status,keyword.toString());
			break;
		
		case createcompetation:
			er.WriteInfo("Executing key word ---> "+ keyword);
			status = ad.CreateCompetation(StringParam);
			logger.info("Enter the text");
			Testresult(status,keyword.toString());
			break;
			
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
			er.WriteLog(Status.PASS, message );
		}
		if(status.equalsIgnoreCase("fail")) {
			er.AttachScreenshot(gm.takeScreenshot());
			er.WriteLog(Status.FAIL, message );
		}

	}

}
