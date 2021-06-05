package com.automation.custom.wait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class AttributedNotPresent implements ExpectedCondition<Boolean> {


  private static Logger logger = LogManager.getLogger(AttributedNotPresent.class.getName());

  private By byType;
  private String attributeName;
  private WebElement element;

  public AttributedNotPresent(final By byType, final String attributeName) {
    this.byType = byType;
    this.attributeName = attributeName;
  }

  public AttributedNotPresent(final WebElement element, final String attributeName) {
    this.element = element;
    this.attributeName = attributeName;
  }

  @Override
  public Boolean apply(WebDriver driver) {

    WebElement element = null;
    if (this.byType != null) { 
      element = driver.findElement(this.byType);
    }
    if (this.element != null) {
      element = this.element;
    }
    String status = element.getAttribute(this.attributeName);
    logger.debug("xpath " + this.byType);
    logger.debug("custom wait implement by " + status);
    if (null == status) {
      logger.debug("return true");
      return true;
    }
    logger.debug("return false");
    return false;
  }


}


