package com.automation.baseframework;

import com.automation.amazon.AmazonItem;
import com.automation.api.service.PetStoreService;
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
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class contain the keyword which need to executed after reading from the test script file.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class KeywordExecution {
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
  @Autowired
  private PetStoreService petStoreService;


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

      case OPENENCASHURL: 
        log.debug("Opening the URL " + properties.getEncashUrl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getEncashUrl());
        break;

      case OPENADMINURL: 
        log.debug("Opening the URL " + properties.getAdminUrl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getAdminUrl());
        admin.adminurlopen(dataParam);
        break;
        
      case OPENAMAZONURL: 
        log.debug("Opening the URL " + properties.getAmazonurl());
        driver = browserinitialize.getWebDriverInstance();
        driver.get(properties.getAmazonurl());
        break;

      case QUITBROWSER:
        browserinitialize.quitBrowser();
        log.debug("QuiteBrowser");
        break;

      case JISHITEXT:
        encash.jishitext(dataParam);
        log.debug("verified the text");
        break;

      case TAKESCREENSHOT:
        //extentReport.attachScreenshotBase64(genericMethod.takeScreenshot());
        extentReport.attachScreenshotPath(genericMethod.takeScreenshot());
        log.debug("taken the screen shot");
        break;

      case BANNER:
        encash.banner(dataParam);
        log.debug("verifed the Banner");
        break;

      case COMPETATIONFORM:
        admin.competationForm(dataParam);
        log.debug("complete create competation");
        break;

      case REGISTRATIONFORM:
        encash.registrationForm(dataParam);
        log.debug("completed entering all detail for New Registration");
        break;

      case REGISTERUSINGMOBILENUMBER:
        encash.registerUsingMobileNumber(dataParam);
        log.debug("complete entering registration");
        break;

      case CONSENT:
        encash.consent(dataParam);
        log.debug("Expand all consent");
        break;

      case BROWSERTYPE:
        genericMethod.browsertype(dataParam.get(0));
        log.debug("Opening the browser");
        break;
        
      case MOBILEREGISTERUSINGMOBILENUMBER:
        mobileEncash.registerUsingMobileNumber(dataParam);
        log.debug("complete entering registration");
        break;
        
      case SEARCHCOMPETATION:
        encash.searchcompetation(dataParam);
        log.debug("searching for competition and click on it");
        break;
        
      case COMPETITIONQUESETION:
        encash.competitionQuesetion(dataParam);
        log.debug("searching for competition and click on it");
        break;
        
      case MANDATORYQUESTION:
        encash.mandatoryQuesetion(dataParam);
        log.debug("Verifying the question and answer for competitions");
        break;
        
      case STOREWINDOW:
        genericMethod.currentWindow(dataParam.get(0));
        log.debug("Storing the current window with key");
        break;
        
      case SWITCHWINDOW:
        genericMethod.switchWindow(dataParam.get(0));
        log.debug("swtich to the window by key name");
        break;
        
      case SWITCHDRIVER:
        genericMethod.switchDriver(dataParam.get(0));
        log.debug("swtich Driver instance");
        break;
        
      case NEWTAB:
        genericMethod.newTab();
        log.debug("crete new Tab in browser");
        break;
        
      case OPENMAILINATORURL:
        log.debug("Opening Mailinator URL");
        mailinator.openUrl(dataParam);
        break;
        
      case READEMAILOTPMAILINATOR:
        log.debug("Reading the OTP in mail");
        mailinator.readEmailOtp();
        break;
        
      case ENTEREMAILOTP:
        log.debug("entering the OTP");
        encash.enterEmailOtp();
        break;
        
      case GMAIL:
        log.debug("Opening the gmail URL");
        firebase.gmaillogin(dataParam);
        break;
        
      case FIREBASELOGIN:
        log.debug("Opening the firebase URL");
        firebase.firebaselogin();
        break;
        
      case LOGOUTADMIN:
        log.debug("logging out the Admin URL");
        admin.logoutAdmin();
        break;
        
      case OFFERSFORM:
        log.debug("entering the offers form ");
        admin.offersForm(dataParam);
        break;
        
      case SEARCHCOMPETITION:
        log.debug("search for the competation");
        admin.searchCompetition(dataParam);
        break;
        
      case QUESTIONNAIRES:
        log.debug("creating the new questionnaires");
        admin.questionnariesCreate(dataParam);
        break;
        
      case QUESTIONNARIESDELETE:
        log.debug("Deleting the questionnaries");
        admin.questionnariesDelete(dataParam);
        break;
        
      case LEVELS:
        log.debug("creating the levels");
        admin.levels(dataParam);
        break;
        
      case QUESTIONNAIRESTAB:
        log.debug("questnnaires tab");
        admin.questionnairesTab(dataParam);
        break;
        
      case LEVELTAB:
        log.debug("Levels tab");
        admin.leveltab(dataParam);
        break;
        
      case EMAILENCASH:
        log.debug("Email encash");
        encash.emailEncash(dataParam);
        break;
     
      case FIREBASELOGIN1:
        log.debug("firebase login");
        firebase.firebaseLogin1(dataParam);
        break;
      
      case BROWSERSWITCH:
        log.debug("Switching the browser");
        genericMethod.browserSwtich(dataParam);
        break;
        
      case CLOSEBROWSER:
        log.debug("closing the browser");
        genericMethod.browserClose();
        break;
        
      case GOTOLINK:
        log.debug("clicking on the hyperlink");
        genericMethod.goToLink(dataParam.get(0));
        break;
        
      case PARTICIPATE:
        log.debug("clicking on the participate");
        encash.competationparticpate();
        break;
      
      case MOBILEPARTICIPATE:
        log.debug("clicking on the participate");
        mobileEncash.competationparticpate();
        break;
        
      case ENCASHMENU:
        log.debug("click on the navibar and selecting the required option");
        mobileEncash.encashMenu(dataParam);
        break;
        
      case LOGINEMAIL:
        log.debug("click on the login button and login to application");
        encash.loginReisterUser(dataParam);
        break;
        
      case AMAZONITEMADD:
        log.debug("Select the item and add to basket");
        amazon.addItem(dataParam);
        break;
        
      case CREATEPET:
        log.debug("Creating the new Pet");
        petStoreService.createPet(dataParam);
        break;
        
      case GETPET:
        log.debug("get the particular Pet");
        petStoreService.getPet(dataParam);
        break;
        
      default: log.debug("Invalid Keyword");

    }
  }

  /*
   * This method capture the status of each keyword by saying pass or fail.
   * 
   * @param status it contain pass or fail 
   * @param message Message contain information of the keyword executed
   */
  /*public void testResult(String status, String message)   {
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
