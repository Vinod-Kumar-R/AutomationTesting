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
        logger.info("Executing the keyWord " + keyword.toString());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(ConstantVariable.EncashURL);
        status = "pass";
        testResult(status, "Browser Open");
        break;

      case openadminurl : 
        logger.debug("Opening the URL " + ConstantVariable.AdminURL);
        logger.info("Executing the keyWord " + keyword.toString());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(ConstantVariable.AdminURL);
        status = admin.adminurlopen(dataParam);
        testResult(status, "Browser Open");
        break;

      case waitforelementvisible :
        logger.debug("Waiting for the element visible");
        logger.info("Executing the keyWord " + keyword.toString());
        status = waitMethod.waitForElementVisible(dataParam.get(0));
        testResult(status, keyword.toString());
        break;

      case waitfortexttvisible :
        logger.debug("Waiting for the Text visible");
        logger.info("Executing the keyWord " + keyword.toString());
        status = waitMethod.waitForTexttVisible(dataParam);
        testResult(status, keyword.toString());
        break;

      case click :
        status = genericMethod.click(dataParam.get(0));
        logger.debug("clicked  on Element");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case quitbrowser :
        status = browserinitialize.quitBrowser();
        logger.debug("QuiteBrowser");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case verifytext :
        status = genericMethod.verifyText(dataParam);
        logger.debug("verified the text");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case jishitext :
        status = encash.jishitext(dataParam);
        logger.debug("verified the text");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case takescreenshot :
        extentReport.attachScreenshot(genericMethod.takeScreenshot());
        status = "pass";
        logger.debug("taken the screen shot");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case waitforattributedcontain :
        status = waitMethod.waitForAttributedContain(dataParam);
        logger.debug("Waited for An Attibuted ");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case verifyattributedvalue :
        status = genericMethod.verifyAttributedValue(dataParam);
        logger.debug("verified the attributed Value");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case banner :
        status = encash.banner(dataParam);
        logger.debug("verifed the Banner");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case entertext:
        status = genericMethod.entertext(dataParam);
        logger.debug("Enter the text");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case competationform:
        status = admin.competationForm(dataParam);
        logger.debug("complete create competation");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case comment :
        break;

      case registrationform :
        status = encash.registrationForm(dataParam);
        logger.debug("completed entering all detail for New Registration");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case registerUsingMobileNumber:
        status = encash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case consent:
        status = encash.consent(dataParam);
        logger.debug("Expand all consent");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case waitForElementInvisible:
        status = waitMethod.waitForElementInvisible(dataParam.get(0));
        logger.debug("waiting for Element invisible");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;

      case browsertype:
        status = genericMethod.browsertype(dataParam.get(0));
        logger.debug("Opening the browser");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case mobileregisterUsingMobileNumber:
        status = mobileEncash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case waitForElementPresent:
        status = waitMethod.waitForElementPresent(dataParam.get(0));
        logger.debug("waiting for Element Present");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case angularWait:
        status = waitMethod.angularWait();
        logger.debug("angularWait");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case searchcompetation:
        status = encash.searchcompetation(dataParam);
        logger.debug("searching for competition and click on it");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case mandatoryquestion:
        status = encash.mandatoryquestion(dataParam);
        logger.debug("searching for competition and click on it");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case competationquestion:
        status = encash.competationquestion(dataParam);
        logger.debug("Verifying the question and answer for competitions");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult(status, keyword.toString());
        break;
        
      case storewindow :
        genericMethod.currentWindow(dataParam.get(0));
        logger.debug("Storing the current window with key");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult("Pass", keyword.toString());
        break;
        
      case switchwindow:
        genericMethod.switchWindow(dataParam.get(0));
        logger.debug("swtich to the window by key name");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult("Pass", keyword.toString());
        break;
        
      case newtab:
        genericMethod.newTab();
        logger.debug("crete new Tab in browser");
        logger.info("Executing the keyWord " + keyword.toString());
        testResult("Pass", keyword.toString());
        break;
        
      case openmailinatorurl:
        logger.debug("Opening Mailinator URL");
        logger.info("Executing the keyWord " + keyword.toString());
        status = mailinator.openUrl(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case reademailotpmailinator:
        logger.debug("Reading the OTP in mail");
        logger.info("Executing the keyWord " + keyword.toString());
        status = mailinator.readEmailOtp();
        testResult(status, keyword.toString());
        break;
        
      case enteremailotp:
        logger.debug("entering the OTP");
        logger.info("Executing the keyWord " + keyword.toString());
        status = encash.enterEmailOtp();
        testResult(status, keyword.toString());
        break;
        
      case gmail:
        logger.debug("Opening the gmail URL");
        status = firebase.gmaillogin(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case firebaselogin:
        logger.debug("Opening the firebase URL");
        logger.info("Executing the keyWord " + keyword.toString());
        status = firebase.firebaselogin();
        testResult(status, keyword.toString());
        break;
        
      case logoutadmin:
        logger.debug("logging out the Admin URL");
        logger.info("Executing the keyWord " + keyword.toString());
        status = admin.logoutAdmin();
        testResult(status, keyword.toString());
        break;
        
      case offersform:
        logger.debug("entering the offers form ");
        logger.info("Executing the keyWord " + keyword.toString());
        status = admin.offersForm(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case searchcompetition:
        logger.debug("search for the competation");
        logger.info("Executing the keyWord " + keyword.toString());
        status = admin.searchCompetition(dataParam);
        testResult(status, keyword.toString());
        break;

      default: logger.debug("Invalid Keyword");

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
