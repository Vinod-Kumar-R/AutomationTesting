package com.automation.baseframework;

import com.automation.amazon.AmazonItem;
import com.automation.bussiness.admin.Admin;
import com.automation.bussiness.encash.Encash;
import com.automation.configuration.PropertiesValue;
import com.automation.firebase.Firebase;
import com.automation.mailinator.Mailinator;
import com.automation.mobile.encash.MobileEncash;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.webdriver.BrowserInitialize;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class contain the keyword which need to executed after reading from the test script file.
 * @author Vinod Kumar R
 *
 */
@Component
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
  private Encash encash;
  @Autowired
  private ExtentReport extentReport;
  @Autowired
  private MobileEncash mobileEncash;
  //private String status;
  private KeywordType keyword;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private Mailinator mailinator;
  @Autowired
  private Firebase firebase;
  @Autowired
  private AmazonItem amazon;


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
        break;

      case openadminurl : 
        logger.debug("Opening the URL " + properties.getAdminUrl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getAdminUrl());
        admin.adminurlopen(dataParam);
        break;
        
      case openamazonurl : 
        logger.debug("Opening the URL " + properties.getAmazonurl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getAmazonurl());
        break;

      case quitbrowser :
        browserinitialize.quitBrowser();
        logger.debug("QuiteBrowser");
        break;

      case jishitext :
        encash.jishitext(dataParam);
        logger.debug("verified the text");
        break;

      case takescreenshot :
        extentReport.attachScreenshotBase64(genericMethod.takeScreenshot());
        //extentReport.attachScreenshotPath(genericMethod.takeScreenshot());
        logger.debug("taken the screen shot");
        break;

      case banner :
        encash.banner(dataParam);
        logger.debug("verifed the Banner");
        break;

      case competationform:
        admin.competationForm(dataParam);
        logger.debug("complete create competation");
        break;

      case registrationform :
        encash.registrationForm(dataParam);
        logger.debug("completed entering all detail for New Registration");
        break;

      case registerUsingMobileNumber:
        encash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        break;

      case consent:
        encash.consent(dataParam);
        logger.debug("Expand all consent");
        break;

      case browsertype:
        genericMethod.browsertype(dataParam.get(0));
        logger.debug("Opening the browser");
        break;
        
      case mobileregisterUsingMobileNumber:
        mobileEncash.registerUsingMobileNumber(dataParam);
        logger.debug("complete entering registration");
        break;
        
      case searchcompetation:
        encash.searchcompetation(dataParam);
        logger.debug("searching for competition and click on it");
        break;
        
      case competitionquesetion:
        encash.competitionQuesetion(dataParam);
        logger.debug("searching for competition and click on it");
        break;
        
      case mandatoryquestion:
        encash.mandatoryQuesetion(dataParam);
        logger.debug("Verifying the question and answer for competitions");
        break;
        
      case storewindow :
        genericMethod.currentWindow(dataParam.get(0));
        logger.debug("Storing the current window with key");
        break;
        
      case switchwindow:
        genericMethod.switchWindow(dataParam.get(0));
        logger.debug("swtich to the window by key name");
        break;
        
      case switchdriver:
        genericMethod.switchDriver(dataParam.get(0));
        logger.debug("swtich Driver instance");
        break;
        
      case newtab:
        genericMethod.newTab();
        logger.debug("crete new Tab in browser");
        break;
        
      case openmailinatorurl:
        logger.debug("Opening Mailinator URL");
        mailinator.openUrl(dataParam);
        break;
        
      case reademailotpmailinator:
        logger.debug("Reading the OTP in mail");
        mailinator.readEmailOtp();
        break;
        
      case enteremailotp:
        logger.debug("entering the OTP");
        encash.enterEmailOtp();
        break;
        
      case gmail:
        logger.debug("Opening the gmail URL");
        firebase.gmaillogin(dataParam);
        break;
        
      case firebaselogin:
        logger.debug("Opening the firebase URL");
        firebase.firebaselogin();
        break;
        
      case logoutadmin:
        logger.debug("logging out the Admin URL");
        admin.logoutAdmin();
        break;
        
      case offersform:
        logger.debug("entering the offers form ");
        admin.offersForm(dataParam);
        break;
        
      case searchcompetition:
        logger.debug("search for the competation");
        admin.searchCompetition(dataParam);
        break;
        
      case questionnaires:
        logger.debug("creating the new questionnaires");
        admin.questionnariesCreate(dataParam);
        break;
        
      case questionnariesdelete:
        logger.debug("Deleting the questionnaries");
        admin.questionnariesDelete(dataParam);
        break;
        
      case levels:
        logger.debug("creating the levels");
        admin.levels(dataParam);
        break;
        
      case questionnairestab:
        logger.debug("questnnaires tab");
        admin.questionnairesTab(dataParam);
        break;
        
      case leveltab:
        logger.debug("Levels tab");
        admin.leveltab(dataParam);
        break;
        
      case emailencash:
        logger.debug("Email encash");
        encash.emailEncash(dataParam);
        break;
     
      case firebaselogin1:
        logger.debug("firebase login");
        firebase.firebaseLogin1(dataParam);
        break;
      
      case browserswitch:
        logger.debug("Switching the browser");
        genericMethod.browserSwtich(dataParam);
        break;
        
      case closebrowser:
        logger.debug("closing the browser");
        genericMethod.browserClose();
        break;
        
      case gotolink:
        logger.debug("clicking on the hyperlink");
        genericMethod.goToLink(dataParam.get(0));
        break;
        
      case participate:
        logger.debug("clicking on the participate");
        encash.competationparticpate();
        break;
      
      case mobileparticipate:
        logger.debug("clicking on the participate");
        mobileEncash.competationparticpate();
        break;
        
      case encashmenu:
        logger.debug("click on the navibar and selecting the required option");
        mobileEncash.encashMenu(dataParam);
        break;
        
      case loginemail:
        logger.debug("click on the login button and login to application");
        encash.loginReisterUser(dataParam);
        break;
        
      case amazonitemadd:
        logger.debug("Select the item and add to basket");
        amazon.addItem(dataParam);
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
 /* public void testResult(String status, String message)   {
    if (status.equalsIgnoreCase("Pass")) {
      extentReport.writeLog(Status.PASS, message);
    }
    if (status.equalsIgnoreCase("fail")) {
      extentReport.attachScreenshotBase64(genericMethod.takeScreenshot());
      //extentReport.attachScreenshotPath(genericMethod.takeScreenshot());
      extentReport.writeLog(Status.FAIL, message);
    }

  }
*/
}
