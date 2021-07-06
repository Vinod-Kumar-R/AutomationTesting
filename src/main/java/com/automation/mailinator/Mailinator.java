package com.automation.mailinator;

import com.automation.configuration.ApplicationStoreValue;
import com.automation.configuration.PropertiesValue;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webdriver.BrowserInitialize;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class Mailinator {
  
  private static Logger logger = LogManager.getLogger(Mailinator.class.getName());
  private WebDriver driver;

  @Autowired
  private WaitMethod waitmethod;
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private ApplicationStoreValue storevalue;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private PropertiesValue properties;
  
  /**
   * This method is used to open the mailinator URL and open the given email id.
   * @param dataParam contain the email id to which need to open
   * @return if success execution then return "pass"
   */
  public String openUrl(List<String> dataParam) {
    
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getMailinatorUrl());
    
    //wait for element present
    logger.debug("Waiting for the element present email_id ");
    waitmethod.waitForElementPresent("email_id");
    
    logger.debug("enter the email id " + dataParam.get(0));
    WebElement element = genericmethod.getElement("email_id");
    element.sendKeys(dataParam.get(0));
    
    logger.debug("click on the Go button");
    element = genericmethod.getElement("email_id_go");
    element.click();
    
    return "pass";
  }
  
  /**
   * This method is used to read the OTP from mail and delete it.
   * @return after read the OTP it will return pass status
   */
  public String readEmailOtp() {
    
    logger.debug("wait for email to receive");
    //waitmethod.waitForElementPresent("open_first_email");
    waitmethod.waitForElementVisible("open_first_email");
    
    logger.debug("click on the first email received");
    WebElement element = genericmethod.getElement("open_first_email");
    element.click();
    
    logger.debug("switch to frame for message body");
    element = genericmethod.getElement("email_content_frame");
    genericmethod.switchframe(element);
    
    logger.debug("wait for completed email to open");
    waitmethod.waitForElementPresent("mailinator_email_otp");
    
    logger.debug("Read the OTP from mail");
    element = genericmethod.getElement("mailinator_email_otp");
    storevalue.storedOtp = element.getText();
    
    logger.debug("switch to parent frame");
    genericmethod.switchframedefault();
    
    return "pass";
  }

}
