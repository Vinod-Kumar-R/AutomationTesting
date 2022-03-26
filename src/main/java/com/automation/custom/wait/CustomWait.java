package com.automation.custom.wait;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This class is used to add all the customer wait method.
 * @author Vinod Kumar R
 *
 */
@Log4j2
public class CustomWait {

  public static ExpectedCondition<Boolean> attributedNotPresent(By byType, 
                  String attributeName) {
    log.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedNotPresent(byType, attributeName);
  }
  
  /**
   * Method is used to wait until attribute not present in DOM.
   * @param element is WebElement
   * @param attributeName is attribute which need to wait untill not present
   * @return "pass"
   */
  public static ExpectedCondition<Boolean> attributedNotPresent(WebElement element, 
                  String attributeName) {
    log.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedNotPresent(element, attributeName);
    
  }
  
  /**
   * Method used to wait for particular attribute present.
   * @param element WebElement
   * @param attributeName name which need to wait to present.
   * @return "Pass"
   */
  public static ExpectedCondition<Boolean> attributedPresent(WebElement element, 
                  String attributeName) {
    log.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedPresent(element, attributeName);
    
  }

  public static ExpectedCondition<Boolean> someTextPresent(WebElement element) {
    log.debug("Calling the Custom wait method Where some text to be present in WebElement");
    return new SomeTextPresent(element);
  }
  
  public static ExpectedCondition<Boolean> notification(WebElement element) {
    log.debug("Calling the Custom wait method Where notification to be present in WebElement");
    return new NotificationDisAppear(element);
  }
  
  public static ExpectedCondition<Boolean> notificationappear(WebElement element) {
    log.debug("Calling the Custom wait method Where notification to be present in WebElement");
    return new NotificationAppear(element);
  }

}
