package com.encash.offers.firebase;

import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import com.google.common.collect.Lists;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class Firebase {
  private static Logger logger = LogManager.getLogger(Firebase.class);

  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private PropertiesValue properties;

  private WebDriver driver;


  /**
   * This method is used to login to Gmail.
   * @param dataParam contain the login id and passsword
   * @return "pass" if executed success
   */
  public String gmaillogin(List<String> dataParam) {
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getGmailUrl());

    logger.debug("enter the gmail id");
    waitmethod.waitForElementPresent("gmail_id");
    WebElement element = genericmethod.getElement("gmail_id");
    element.sendKeys(dataParam.get(0));

    logger.debug("click on the next button");
    element = genericmethod.getElement("gmail_next");
    element.click();

    logger.debug("enter the gmail Password");
    waitmethod.waitForElementAttributeNotPresent("gmail_wait", "aria-busy");
    element = genericmethod.getElement("gmail_password");
    element.sendKeys(dataParam.get(1));

    logger.debug("click on the next button");
    element = genericmethod.getElement("gmail_next");
    element.click();

    return "pass";

  }

  /**
   * This method is used to open the firebase console.
   * @return pass if execution success
   */
  public String firebaselogin() {
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getFirebaseUrl());

    logger.debug("waiting for firebase console");
    waitmethod.waitForElementVisible("firebaseconsole");
    WebElement element = genericmethod.getElement("firebaseconsole");
    element.click();

    logger.debug("waiting for project");
    waitmethod.waitForElementVisible("firebase_project");
    element = genericmethod.getElement("firebase_project");
    element.click();

    logger.debug("clicking on the authenication page");
    waitmethod.waitForElementPresent("firebase_authentication_all");
    List<WebElement> groups = genericmethod.getElements("firebase_authentication_all");
    for (WebElement group : groups) {
      logger.debug(group.getText());
      group.click();
      break;
    }

    logger.debug("clicking on the sign in method");
    waitmethod.waitForElementPresent("firebase_signmethod");
    element = genericmethod.getElement("firebase_signmethod");
    element.click();

    logger.debug("click in the authentication");
    List<WebElement> authenticationtypes = genericmethod.getElements("firebase_authenicationtype");
    for (WebElement authenticationtype : authenticationtypes) {
      List<WebElement> cells = genericmethod.getWebElements(
                      authenticationtype, "firebase_authenicationtype_cell");
      for (WebElement cell : cells) {
        logger.debug(cell.getText());
        if (cell.getText().equalsIgnoreCase("Phone")) {
          cell.click();
          break;
        }
      }

    }
    
    logger.debug("expanding the phone number");
    waitmethod.waitForElementPresent("firebase_exandphonenumber");
    element = genericmethod.getElement("firebase_exandphonenumber");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    //check if the table row is equal to 1 or greater 
    logger.debug("deleted the phone number");
    List<WebElement> phonelists = genericmethod.getElements("firebase_phonenumberlist");
    for (WebElement phonelist : Lists.reverse(phonelists)) {
      //genericmethod.scrolltoelement(phonelist);
      // genericmethod.mouseover(phonelist);
      WebElement phonecell = genericmethod.getWebElement(phonelist, "firebase_phonenumbercell");
      //genericmethod.scrolltoelement(phonecell);
      waitmethod.waitForElementVisible(phonecell);
      genericmethod.mouseover(phonecell);
      WebElement deletebutton = genericmethod.getWebElement(phonecell, 
                      "firebase_phone_deletebutton");
      deletebutton.click();
      //phonecell.click();
    }
    
    logger.debug("Enter the phone number and verification code");
    element = genericmethod.getElement("firebase_phonenumber");
    waitmethod.waitForElementVisible(element);
    element.sendKeys("+41 123456791");
    
    logger.debug("Enter the phone verification ");
    element = genericmethod.getElement("firebase_phoneotp");
    waitmethod.waitForElementVisible(element);
    element.sendKeys("123456");
    
       
    logger.debug("clicking on the Add button");
    waitmethod.waitForElementAttributeNotPresent("firebase_phoneadd", "disabled");
    element = genericmethod.getElement("firebase_phoneadd");
    waitmethod.waitForElementClickable(element);
    element.click();
    
    

    return "pass";
  }

}
