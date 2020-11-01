package com.encash.offers.custom.wait;

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

  public AttributedNotPresent(final By byType, final String attributeName) {
    this.byType = byType;
    this.attributeName = attributeName;
  }

  @Override
  public Boolean apply(WebDriver driver) {

    WebElement element = driver.findElement(this.byType);

    String status = element.getAttribute(this.attributeName);
    logger.debug("xpath " + this.byType);
    logger.debug("custom wait implated by " + status);
    if (null == status) {
      return true;
    }
    return false;
  }







}
