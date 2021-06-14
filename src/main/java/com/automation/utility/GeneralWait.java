package com.automation.utility;

import com.automation.configuration.PropertiesValue;
import com.automation.webdriver.BrowserInitialize;
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneralWait {
  
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private PropertiesValue properties;
  
  /**
   * Method used to wait until expected condition meet.
   * @return  FluentWait Object
   */
  public FluentWait<WebDriver> fluentwait() {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    FluentWait<WebDriver> fluentwait = new FluentWait<WebDriver>(driver);
    fluentwait.withTimeout(Duration.ofMinutes(properties.getExplictwait()));
    fluentwait.pollingEvery(Duration.ofSeconds(properties.getPolling()));
    fluentwait.ignoring(NoSuchElementException.class);
    return fluentwait;
  }
  
  /**
   * Method used to wait until expected condition meet.
   * @return FluentWait Object
   * 
   */
  public FluentWait<WebDriver> fluenttimeoutwait() {
    FluentWait<WebDriver> fluentwait = fluentwait();
    fluentwait.withMessage("time out message");
    return fluentwait;
  }

}
