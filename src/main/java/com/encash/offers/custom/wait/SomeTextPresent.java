package com.encash.offers.custom.wait;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;


public class SomeTextPresent implements ExpectedCondition<Boolean> {
  private static Logger logger = LogManager.getLogger(SomeTextPresent.class.getName());
  private WebElement element;

  public SomeTextPresent(WebElement element) {
    this.element = element;
  }

  @Override
  public Boolean apply(WebDriver driver) {
    // TODO Auto-generated method stub
    logger.debug("Waiting for the some text persent -------> " + this.element.getText());
    if (this.element.getText().equals("")) {
      logger.debug("Empty text present so return false");
      return false;
    }
    logger.debug("Element has some text so return true " + this.element.getText());
    return true;
  }

}
