package com.encash.offers.baseframework;

import com.aventstack.extentreports.Status;
import com.encash.offers.bussiness.admin.Admin;
import com.encash.offers.bussiness.encash.Encash;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.mobile.encash.MobileEncash;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * This class contain the keyword which need to executed after reading from the test script file.
 * @author Vinod Kumar R
 *
 */
public class KeywordExecution {
  private static Logger logger = LogManager.getLogger(KeywordExecution.class.getName());
  private WebDriver driver;
  private GenericMethod genericMethod;
  private Admin admin;
  private WaitMethod waitMethod;
  private Encash encash;
  private ExtentReport extentReport;
  private MobileEncash mobileEncash;
  private String status;
  private KeywordType keyword;

  /**
   * This the default contractor which will initialize the required class variable.
   */

  public KeywordExecution() {
    admin = new Admin();
    waitMethod = new WaitMethod();
    encash = new Encash();
    genericMethod = new GenericMethod();
    mobileEncash = new MobileEncash();
    extentReport = BrowserInitialize.getExtentReportInstance();

  }

  /**
   * This method is used to set the value for keyword.
   * @param keyword which will be read from Test Data file and executed method
   */
  public void setvalue(KeywordType keyword) {
    this.keyword = keyword;

  }

  /**
   * This method is used to executed the keyword which is read from Test Script file.
   * 
   * <p>the 2nd column in the Test script file is called Keyword 
   * @param stringParam contain the array of string data which is required executed particular
   *     keyword (nothing but Method)
   * 
   */
  public void executed(String[] stringParam)  {

    switch (keyword) {

      case openencashurl : 
        logger.debug("Opening the URL " + ConstantVariable.EncashURL);
        driver = BrowserInitialize.getWebDriverInstance();
        driver.get(ConstantVariable.EncashURL);
        status = "pass";
        testResult(status, "Browser Open");
        break;

      case openadminurl : 
        logger.debug("Opening the URL " + ConstantVariable.AdminURL);
        driver = BrowserInitialize.getWebDriverInstance();
        driver.get(ConstantVariable.AdminURL);
        status = admin.adminurlopen(stringParam);
        testResult(status, "Browser Open");
        break;

      case waitforelementvisible :
        logger.debug("Waiting for the element visible");
        status = waitMethod.waitForElementVisible(stringParam[0]);
        testResult(status, keyword.toString());
        break;

      case waitfortexttvisible :
        logger.debug("Waiting for the Text visible");
        status = waitMethod.waitForTexttVisible(stringParam);
        testResult(status, keyword.toString());
        break;

      case click :
        logger.debug("clicking  on Element");
        status = genericMethod.click(stringParam[0]);
        logger.debug("clicked  on Element");
        testResult(status, keyword.toString());
        break;

      case quitbrowser :
        status = BrowserInitialize.quitBrowser();
        logger.debug("QuiteBrowser");
        testResult(status, keyword.toString());
        break;

      case verifytext :
        status = genericMethod.verifyText(stringParam);
        logger.debug("verified the text");
        testResult(status, keyword.toString());
        break;

      case jishitext :
        status = encash.jishitext(stringParam);
        logger.debug("verified the text");
        testResult(status, keyword.toString());
        break;

      case takescreenshot :
        extentReport.attachScreenshot(genericMethod.takeScreenshot());
        status = "pass";
        logger.debug("taken the screen shot");
        testResult(status, keyword.toString());
        break;

      case waitforattributedcontain :
        status = waitMethod.waitForAttributedContain(stringParam);
        logger.debug("Waited for An Attibuted ");
        testResult(status, keyword.toString());
        break;

      case verifyattributedvalue :
        status = genericMethod.verifyAttributedValue(stringParam);
        logger.debug("verified the attributed Value");
        testResult(status, keyword.toString());
        break;

      case banner :
        status = encash.banner(stringParam);
        logger.debug("verifed the Banner");
        testResult(status, keyword.toString());
        break;

      case entertext:
        status = genericMethod.entertext(stringParam);
        logger.info("Enter the text");
        testResult(status, keyword.toString());
        break;

      case createcompetation:
        status = admin.createCompetation(stringParam);
        logger.info("complete create competation");
        testResult(status, keyword.toString());
        break;

      case comment :
        break;

      case registrationform :
        status = encash.registrationForm(stringParam);
        logger.info("completed entering all detail for New Registration");
        testResult(status, keyword.toString());
        break;

      case registerUsingMobileNumber:
        status = encash.registerUsingMobileNumber(stringParam);
        logger.info("complete entering registration");
        testResult(status, keyword.toString());
        break;

      case consent:
        status = encash.consent(stringParam);
        logger.info("Expand all consent");
        testResult(status, keyword.toString());
        break;
        
      case waitForElementInvisible:
        status = waitMethod.waitForElementInvisible(stringParam[0]);
        logger.info("waiting for Element invisible");
        testResult(status, keyword.toString());
        break;

      case browsertype:
        status = genericMethod.browsertype(stringParam[0]);
        logger.info("Opening the browser");
        testResult(status, keyword.toString());
        break;
        
      case mobileregisterUsingMobileNumber:
        status = mobileEncash.registerUsingMobileNumber(stringParam);
        logger.info("complete entering registration");
        testResult(status, keyword.toString());
        break;
        
      case waitForElementPresent:
        status = waitMethod.waitForElementPresent(stringParam[0]);
        logger.info("waiting for Element Present");
        testResult(status, keyword.toString());
        break;
        
      case angularWait:
        status = waitMethod.angularWait();
        logger.info("angularWait");
        testResult(status, keyword.toString());
        break;
        
      case searchcompetation:
        status = encash.searchcompetation(stringParam);
        logger.info("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case mandatoryquestion:
        status = encash.mandatoryquestion(stringParam);
        logger.info("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case competationquestion:
        status = encash.competationquestion(stringParam);
        logger.info("Verifying the question and answer for competitions");
        testResult(status, keyword.toString());
        break;

      default: logger.info("Invalid Keyword");

    }
  }

  /**
   * This method capture the status of each keyword by saying pass or fail.
   * 
   * @param status it contain pass or fail 
   * @param message Message contain information of the keyword executed
   */
  public void testResult(String status, String message)   {
    if (status.equalsIgnoreCase("Pass")) {
      extentReport.writeLog(Status.PASS, message);
    }
    if (status.equalsIgnoreCase("fail")) {
      extentReport.attachScreenshot(genericMethod.takeScreenshot());
      extentReport.writeLog(Status.FAIL, message);
    }

  }

}
