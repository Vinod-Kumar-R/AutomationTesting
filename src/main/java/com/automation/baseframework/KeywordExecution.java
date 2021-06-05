package com.automation.baseframework;

import com.automation.bussiness.admin.Admin;
import com.automation.bussiness.encash.Encash;
import com.automation.configuration.PropertiesValue;
import com.automation.firebase.Firebase;
import com.automation.mailinator.Mailinator;
import com.automation.mobile.encash.MobileEncash;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webdriver.BrowserInitialize;
import com.aventstack.extentreports.Status;

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
  private PropertiesValue properties;
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
        logger.debug("Opening the URL " + properties.getEncashUrl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getEncashUrl());
        status = "pass";
        testResult(status, "Browser Open");
        break;

      case openadminurl : 
        logger.debug("Opening the URL " + properties.getAdminUrl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getAdminUrl());
        status = admin.adminurlopen(dataParam);
        testResult(status, "Browser Open");
        break;

      case quitbrowser :
        status = browserinitialize.quitBrowser();
        logger.debug("QuiteBrowser");
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

      case banner :
        status = encash.banner(dataParam);
        logger.debug("verifed the Banner");
        testResult(status, keyword.toString());
        break;

      case competationform:
        status = admin.competationForm(dataParam);
        logger.debug("complete create competation");
        testResult(status, keyword.toString());
        break;

      case registrationform :
        status = encash.registrationForm(dataParam);
        logger.debug("completed entering all detail for New Registration");
        testResult(status, keyword.toString());
        break;

      case registerUsingMobileNumber:
        status = encash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        testResult(status, keyword.toString());
        break;

      case consent:
        status = encash.consent(dataParam);
        logger.debug("Expand all consent");
        testResult(status, keyword.toString());
        break;

      case browsertype:
        status = genericMethod.browsertype(dataParam.get(0));
        logger.debug("Opening the browser");
        testResult(status, keyword.toString());
        break;
        
      case mobileregisterUsingMobileNumber:
        status = mobileEncash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        testResult(status, keyword.toString());
        break;
        
      case searchcompetation:
        status = encash.searchcompetation(dataParam);
        logger.debug("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case competitionquesetion:
        status = encash.competitionQuesetion(dataParam);
        logger.debug("searching for competition and click on it");
        testResult(status, keyword.toString());
        break;
        
      case mandatoryquestion:
        status = encash.mandatoryQuesetion(dataParam);
        logger.debug("Verifying the question and answer for competitions");
        testResult(status, keyword.toString());
        break;
        
      case storewindow :
        genericMethod.currentWindow(dataParam.get(0));
        logger.debug("Storing the current window with key");
        testResult("Pass", keyword.toString());
        break;
        
      case switchwindow:
        genericMethod.switchWindow(dataParam.get(0));
        logger.debug("swtich to the window by key name");
        testResult("Pass", keyword.toString());
        break;
        
      case switchdriver:
        genericMethod.switchDriver(dataParam.get(0));
        logger.debug("swtich Driver instance");
        testResult("Pass", keyword.toString());
        break;
        
      case newtab:
        genericMethod.newTab();
        logger.debug("crete new Tab in browser");
        
        testResult("Pass", keyword.toString());
        break;
        
      case openmailinatorurl:
        logger.debug("Opening Mailinator URL");
        status = mailinator.openUrl(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case reademailotpmailinator:
        logger.debug("Reading the OTP in mail");
        status = mailinator.readEmailOtp();
        testResult(status, keyword.toString());
        break;
        
      case enteremailotp:
        logger.debug("entering the OTP");
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
        status = firebase.firebaselogin();
        testResult(status, keyword.toString());
        break;
        
      case logoutadmin:
        logger.debug("logging out the Admin URL");
        status = admin.logoutAdmin();
        testResult(status, keyword.toString());
        break;
        
      case offersform:
        logger.debug("entering the offers form ");
        status = admin.offersForm(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case searchcompetition:
        logger.debug("search for the competation");
        status = admin.searchCompetition(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case questionnaires:
        logger.debug("creating the new questionnaires");
        status = admin.questionnariesCreate(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case questionnariesdelete:
        logger.debug("Deleting the questionnaries");
        status = admin.questionnariesDelete(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case levels:
        logger.debug("creating the levels");
        status = admin.levels(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case questionnairestab:
        logger.debug("questnnaires tab");
        status = admin.questionnairesTab(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case leveltab:
        logger.debug("Levels tab");
        status = admin.leveltab(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case emailencash:
        logger.debug("Email encash");
        status = encash.emailEncash(dataParam);
        testResult(status, keyword.toString());
        break;
     
      case firebaselogin1:
        logger.debug("firebase login");
        status = firebase.firebaseLogin1(dataParam);
        testResult(status, keyword.toString());
        break;
      
      case browserswitch:
        logger.debug("Switching the browser");
        status = genericMethod.browserSwtich(dataParam);
        testResult(status, keyword.toString());
        break;
        
      case closebrowser:
        logger.debug("closing the browser");
        genericMethod.browserClose();
        testResult("pass", keyword.toString());
        break;
        
      case gotolink:
        logger.debug("clicking on the hyperlink");
        genericMethod.goToLink(dataParam.get(0));
        testResult("pass", keyword.toString());
        break;
        
      case participate:
        logger.debug("clicking on the participate");
        encash.competationparticpate();
        testResult("pass", keyword.toString());
        break;
      
      case mobileparticipate:
        logger.debug("clicking on the participate");
        mobileEncash.competationparticpate();
        testResult("pass", keyword.toString());
        break;
        
      case encashmenu:
        logger.debug("click on the navibar and selecting the required option");
        mobileEncash.encashMenu(dataParam);
        testResult("pass", keyword.toString());
        break;
        
      case loginemail:
        logger.debug("click on the login button and login to application");
        encash.loginReisterUser(dataParam);
        testResult("pass", keyword.toString());
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
