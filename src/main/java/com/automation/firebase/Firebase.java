package com.automation.firebase;

import com.automation.configuration.PropertiesValue;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webdriver.BrowserInitialize;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Firebase {

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
   */
  public void gmaillogin(List<String> dataParam) {
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getGmailUrl());

    log.debug("enter the gmail id");
    waitmethod.waitForElementPresent("gmail_id");
    WebElement element = genericmethod.getElement("gmail_id");
    element.sendKeys(dataParam.get(0));

    log.debug("click on the next button");
    element = genericmethod.getElement("gmail_next");
    element.click();

    log.debug("enter the gmail Password");
    waitmethod.waitForElementAttributeNotPresent("gmail_wait", "aria-busy");
    element = genericmethod.getElement("gmail_password");
    element.sendKeys(dataParam.get(1));

    log.debug("click on the next button");
    element = genericmethod.getElement("gmail_next");
    element.click();

  }

  /**
   * This method is used to open the firebase console.
   */
  public void firebaselogin() {
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getFirebaseUrl());

    log.debug("waiting for firebase console");
    waitmethod.waitForElementVisible("firebaseconsole");
    WebElement element = genericmethod.getElement("firebaseconsole");
    element.click();

    log.debug("waiting for project");
    waitmethod.waitForElementVisible("firebase_project");
    element = genericmethod.getElement("firebase_project");
    element.click();

    log.debug("clicking on the authenication page");
    waitmethod.waitForElementPresent("firebase_authentication_all");
    List<WebElement> groups = genericmethod.getElements("firebase_authentication_all");
    for (WebElement group : groups) {
      log.debug(group.getText());
      group.click();
      break;
    }

    log.debug("clicking on the sign in method");
    waitmethod.waitForElementPresent("firebase_signmethod");
    element = genericmethod.getElement("firebase_signmethod");
    element.click();

    log.debug("click in the authentication");
    List<WebElement> authenticationtypes = genericmethod.getElements("firebase_authenicationtype");
    for (WebElement authenticationtype : authenticationtypes) {
      List<WebElement> cells = genericmethod.getWebElements(
                      authenticationtype, "firebase_authenicationtype_cell");
      for (WebElement cell : cells) {
        log.debug(cell.getText());
        if (cell.getText().equalsIgnoreCase("Phone")) {
          cell.click();
          break;
        }
      }

    }
    
    log.debug("expanding the phone number");
    waitmethod.waitForElementPresent("firebase_exandphonenumber");
    element = genericmethod.getElement("firebase_exandphonenumber");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    //check if the table row is equal to 1 or greater 
    log.debug("deleted the phone number");
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
    
    log.debug("Enter the phone number and verification code");
    element = genericmethod.getElement("firebase_phonenumber");
    waitmethod.waitForElementVisible(element);
    element.sendKeys("+41 123456791");
    
    log.debug("Enter the phone verification ");
    element = genericmethod.getElement("firebase_phoneotp");
    waitmethod.waitForElementVisible(element);
    element.sendKeys("123456");
    
       
    log.debug("clicking on the Add button");
    waitmethod.waitForElementAttributeNotPresent("firebase_phoneadd", "disabled");
    element = genericmethod.getElement("firebase_phoneadd");
    waitmethod.waitForElementClickable(element);
    element.click();
  }
  
  /**
   * this method is used to login to firebase.
   * @param dataParam contain the login detail
   */
  public void firebaseLogin1(List<String> dataParam) {
    
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getFirebaseUrl());
    
    log.debug("waiting for firebase sign in button");
    waitmethod.waitForElementVisible("firebaselogin");
    WebElement element = genericmethod.getElement("firebaselogin");
    element.click();
  }

}
