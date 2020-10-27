package com.encash.offers.baseframework;

import com.aventstack.extentreports.Status;
import com.encash.offers.bussiness.admin.Admin;
import com.encash.offers.bussiness.encash.Encash;
import com.encash.offers.configuration.ConstantVariable;
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
  private GenericMethod gm;
  private Admin ad;
  private WaitMethod wm;
  private Encash en;
  private ExtentReport er;
  private String status;
  private KeywordType keyword;

  /**
   * This the default contractor which will initialize the required class variable.
   */

  public KeywordExecution() {
    ad = new Admin();
    wm = new WaitMethod();
    en = new Encash();
    gm = new GenericMethod();
    er = BrowserInitialize.getExtentReportInstance();

  }

  /**
   * This method is used to set the value for keyword.
   * @param keyword
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
   * @throws Exception generic exception are throw
   */
  public void executed(String[] stringParam) throws Exception {

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
        status = ad.adminurlopen(stringParam);
        testResult(status, "Browser Open");
        break;

      case waitforelementvisible :
        logger.debug("Waiting for the element visible");
        status = wm.waitForElementVisible(stringParam[0]);
        testResult(status, keyword.toString());
        break;

      case waitfortexttvisible :
        logger.debug("Waiting for the Text visible");
        status = wm.waitForTexttVisible(stringParam);
        testResult(status, keyword.toString());
        break;

      case click :
        logger.debug("clicking  on Element");
        status = gm.click(stringParam[0]);
        logger.debug("clicked  on Element");
        testResult(status, keyword.toString());
        break;

      case quitbrowser :
        status = BrowserInitialize.quitBrowser();
        logger.debug("QuiteBrowser");
        testResult(status, keyword.toString());
        break;

      case verifytext :
        status = gm.verifyText(stringParam);
        logger.debug("verified the text");
        testResult(status, keyword.toString());
        break;

      case implictwait :
        logger.debug("Manually waiting ");
        Thread.sleep(Integer.parseInt(stringParam[0]));
        break;

      case jishitext :
        status = en.jishitext(stringParam);
        logger.debug("verified the text");
        testResult(status, keyword.toString());
        break;

      case takescreenshot :
        er.attachScreenshot(gm.takeScreenshot());
        status = "pass";
        logger.debug("taken the screen shot");
        testResult(status, keyword.toString());
        break;

      case waitforattributedcontain :
        status = wm.waitForAttributedContain(stringParam);
        logger.debug("Waited for An Attibuted ");
        testResult(status, keyword.toString());
        break;

      case verifyattributedvalue :
        status = gm.verifyAttributedValue(stringParam);
        logger.debug("verified the attributed Value");
        testResult(status, keyword.toString());
        break;

      case banner :
        status = en.banner(stringParam);
        logger.debug("verifed the Banner");
        testResult(status, keyword.toString());
        break;

      case entertext:
        status = gm.entertext(stringParam);
        logger.info("Enter the text");
        testResult(status, keyword.toString());
        break;

      case createcompetation:
        status = ad.createCompetation(stringParam);
        logger.info("complete create competation");
        testResult(status, keyword.toString());
        break;

      case comment :
        break;
       
      case enterotp :
        status = en.enterOtp(stringParam);
        logger.info("completed entering OTP");
        testResult(status, keyword.toString());
        break;
        
      case newregistration :
        status = en.newRegistration(stringParam);
        logger.info("completed entering all detail for New Registration");
        testResult(status, keyword.toString());
        break;

      default: logger.info("Invalid Keyword");

    }
  }

  /**
   * This method capture the status of each keyword by saying pass or fail.
   * 
   * @param status it contain pass or fail 
   * @param message Message contain information of the keyword 
   * @throws Exception throw an generic exception
   */
  public void testResult(String status, String message) throws  Exception {
    if (status.equalsIgnoreCase("Pass")) {
      er.writeLog(Status.PASS, message);
    }
    if (status.equalsIgnoreCase("fail")) {
      er.attachScreenshot(gm.takeScreenshot());
      er.writeLog(Status.FAIL, message);
    }

  }

}
