package com.encash.offers.custom.wait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;



public class CustomWait {

  private static Logger logger = LogManager.getLogger(CustomWait.class.getName());


  public static ExpectedCondition<Boolean> attributedNotPresent(By byType, 
                  String attributeName) {
    logger.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedNotPresent(byType, attributeName);
  }
  
  public static ExpectedCondition<Boolean> attributedNotPresent(WebElement element, 
                  String attributeName) {
    logger.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedNotPresent(element, attributeName);
    
  }
  
  public static ExpectedCondition<Boolean> attributedPresent(WebElement element, 
                  String attributeName) {
    logger.debug("calling the custom wait method AttributedNotPresent");
    return new AttributedPresent(element, attributeName);
    
  }

  public static ExpectedCondition<Boolean> someTextPresent(WebElement element) {
    logger.debug("Calling the Custom wait method Where some text to be present in WebElement");
    return new SomeTextPresent(element);
  }
  
  public static ExpectedCondition<Boolean> notification(WebElement element) {
    logger.debug("Calling the Custom wait method Where notification to be present in WebElement");
    return new NotificationDisAppear(element);
  }
  
  public static ExpectedCondition<Boolean> notificationappear(WebElement element) {
    logger.debug("Calling the Custom wait method Where notification to be present in WebElement");
    return new NotificationAppear(element);
  }

}
