package com.automation.custom.wait;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

@Log4j2
public class AttributedNotPresent implements ExpectedCondition<Boolean> {

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
    log.debug("xpath " + this.byType);
    log.debug("custom wait implement by " + status);
    if (null == status) {
      log.debug("return true");
      return true;
    }
    log.debug("return false");
    return false;
  }


}


