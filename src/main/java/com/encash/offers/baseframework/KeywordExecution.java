package com.encash.offers.baseframework;

import com.aventstack.extentreports.Status;
import com.encash.offers.bussiness.admin.Admin;
import com.encash.offers.bussiness.encash.Encash;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.firebase.Firebase;
import com.encash.offers.mailinator.Mailinator;
import com.encash.offers.mobile.encash.MobileEncash;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class contain the keyword which need to executed after reading from the test script file.
 * @author Vinod Kumar R
 *
 */
public class KeywordExecution {
  private static Logger logger = LogManager.getLogger(KeywordExecution.class.getName());
  private WebDriver driver;
  @Autowired
  private GenericMethod genericMethod;
  @Autowired
  private Admin admin;
  @Autowired
  private WaitMethod waitMethod;
  @Autowired
  private Encash encash;
  @Autowired
  private ExtentReport extentReport;
  @Autowired
  private MobileEncash mobileEncash;
  private String status;
  private KeywordType keyword;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private Mailinator mailinator;
  @Autowired
  private Firebase firebase;


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
   * @param dataParam contain the array of string data which is required executed particular
   *     keyword (nothing but Method)
   * 
   */
  public void executed(List<String> dataParam)  {

    switch (keyword) {

      case openencashurl : 
        logger.debug("Opening the URL " + ConstantVariable.EncashURL);
        driver = browserinitialize.getWebDriverInstance();
        driver.get(ConstantVariable.EncashURL);
        status = "pass";
        testResult(status, "Browser Open");
        break;

      case openadminurl : 
        logger.debug("Opening the URL " + ConstantVariable.AdminURL);
        driver = browserinitialize.getWebDriverInstance();
        driver.get(ConstantVariable.AdminURL);
        status = admin.adminurlopen(dataParam);
        testResult(status, "Browser Open");
        break;

      case waitforelementvisible :
        logger.debug("Waiting for the element visible");
        status = waitMethod.waitForElementVisible(dataParam.get(0));
        testResult(status, keyword.toString());
        break;

      case waitfortexttvisible :
        logger.debug("Waiting for the Text visible");
        status = waitMethod.waitForTexttVisible(dataParam);
        testResult(status, keyword.toString());
        break;

      case click :
        logger.debug("clicking  on Element");
        status = genericMethod.click(dataParam.get(0));
        logger.debug("clicked  on Element");
        testResult(status, keyword.toString());
        break;

      case quitbrowser :
        status = BrowserInitialize.quitBrowser();
        logger.debug("QuiteBrowser");
        testResult(status, keyword.toString());
        break;

      case verifytext :
        status = genericMethod.verifyText(dataParam);
        logger.debug("verified the text");
        testResult(status, keyword.toString());
        break;

      case jishitext :
        status = encash.jishitext(dataParam);
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
        status = waitMethod.waitForAttributedContain(dataParam);
        logger.debug("Waited for An Attibuted ");
        testResult(status, keyword.toString());
        break;

      case verifyattributedvalue :
        status = genericMethod.verifyAttributedValue(dataParam);
        logger.debug("verified the attributed Value");
        testResult(status, keyword.toString());
        break;

      case banner :
        status = encash.banner(dataParam);
        logger.debug("verifed the Banner");
        testResult(status, keyword.toString());
        break;

      case entertext:
        status = genericMethod.entertext(dataParam);
        logger.info("Enter the text");
        testResult(status, keyword.toString());
        break;

      case competationform:
        status = admin.competationForm(dataParam);
        logger.info("complete create competation");
        testResult(status, keyword.toString());
        break;

      case comment :
        break;

      case registrationform :
        status = encash.registrationForm(dataParam);
        logger.info("completed entering all detail for New Registration");
        testResult(status, keyword.toString());
        break;

      case registerUsingMobileNumber:
        status = encash.registerUsingMobileNumber(dataParam);
        logger.info("complete entering registration");
        testResult(status, keyword.toString());
        break;

      case consent:
        status = encash.consent(dataParam);
        logger.info("Expand all consent");
        testResult(status, keyword.toString());
        break;
        
      case waitForElementInvisible:
        status = waitMethod.waitForElementInvisible(dataParam.get(0));
        logger.info("waiting for Element invisible");
        testResult(status, keyword.toString());
        break;

      case browsertype:
        status = genericMethod.browsertype(dataParam.get(0));
        logger.info("Opening the browser");
        testResult(status, keyword.toString());
        break;
        
      case mobileregisterUsingMobileNumber:
        status = mobileEncash.registerUsingMobileNumber(dataParam);
        logger.info("complete entering registration");
        testResult(status, keyword.toString());
        break;
        
      case waitForElementPresent:
        status = waitMethod.waitForElementPresent(dataParam.get(0));
        logger.info("waiting for Element Present");
        testResult(status, keyword.toString());
        break;
        
      case angularWait:
        status = waitMethod.angularWait();
        logger.info("angularWait");
        testResult(status, keyword.toString());
        break;
        
      case searchcompetation:
        status = encash.searchcompetation(dataParam);
        logger.info("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case mandatoryquestion:
        status = encash.mandatoryquestion(dataParam);
        logger.info("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case competationquestion:
        status = encash.competationquestion(dataParam);
        logger.info("Verifying the question and answer for competitions");
        testResult(status, keyword.toString());
        break;
        
      case storewindow :
        genericMethod.currentWindow(dataParam.get(0));
        logger.info("Storing the current window with key");
        testResult("Pass", keyword.toString());
        break;
        
      case switchwindow:
        genericMethod.switchWindow(dataParam.get(0));
        logger.info("swtich to the window by key name");
        testResult("Pass", keyword.toString());
        break;
        
      case newtab:
        genericMethod.newTab();
        logger.info("crete new Tab in browser");
        testResult("Pass", keyword.toString());
        break;
        
      case openmailinatorurl:
        status = mailinator.openUrl(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case reademailotpmailinator:
        status = mailinator.readEmailOtp();
        testResult(status, keyword.toString());
        break;
        
      case enteremailotp:
        status = encash.enterEmailOtp();
        testResult(status, keyword.toString());
        break;
        
      case gmail:
        status = firebase.gmaillogin(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case firebaselogin:
        status = firebase.firebaselogin();
        testResult(status, keyword.toString());
        break;
        
      case logoutadmin:
        status = admin.logoutAdmin();
        testResult(status, keyword.toString());
        break;
        
      case offersform:
        status = admin.offersForm(dataParam);
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
