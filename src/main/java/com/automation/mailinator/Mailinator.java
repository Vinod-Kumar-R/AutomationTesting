package com.automation.mailinator;

import com.automation.configuration.ApplicationStoreValue;
import com.automation.configuration.PropertiesValue;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webdriver.BrowserInitialize;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to Mailinator related method.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class Mailinator {
  
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
   */
  public void openUrl(List<String> dataParam) {
    
    driver = browserinitialize.getWebDriverInstance();
    driver.get(properties.getMailinatorUrl());
    
    //wait for element present
    log.debug("Waiting for the element present email_id ");
    waitmethod.waitForElementPresent("email_id");
    
    log.debug("enter the email id " + dataParam.get(0));
    WebElement element = genericmethod.getElement("email_id");
    element.sendKeys(dataParam.get(0));
    
    log.debug("click on the Go button");
    element = genericmethod.getElement("email_id_go");
    element.click();
  }
  
  /**
   * This method is used to read the OTP from mail and delete it.
   */
  public void readEmailOtp() {
    
    log.debug("wait for email to receive");
    //waitmethod.waitForElementPresent("open_first_email");
    waitmethod.waitForElementVisible("open_first_email");
    
    log.debug("click on the first email received");
    WebElement element = genericmethod.getElement("open_first_email");
    element.click();
    
    log.debug("switch to frame for message body");
    element = genericmethod.getElement("email_content_frame");
    genericmethod.switchframe(element);
    
    log.debug("wait for completed email to open");
    waitmethod.waitForElementPresent("mailinator_email_otp");
    
    log.debug("Read the OTP from mail");
    element = genericmethod.getElement("mailinator_email_otp");
    storevalue.storedOtp = element.getText();
    
    log.debug("switch to parent frame");
    genericmethod.switchframedefault();

  }

}
