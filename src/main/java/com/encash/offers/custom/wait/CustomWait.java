package com.encash.offers.custom.wait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class CustomWait {
  
  private static Logger logger = LogManager.getLogger(CustomWait.class.getName());
  
  public static ExpectedCondition<Boolean> attributedNotPresent(By byType, 
                  String attributeName) {
    logger.debug("calling the customer wait method AttributedNotPresent");
    return new AttributedNotPresent(byType, attributeName);
  }

}
