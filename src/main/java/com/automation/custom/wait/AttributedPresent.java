package com.automation.custom.wait;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This class is used to create a custom wait for particular attribute are present.
 * @author Vinod Kumar R
 *
 */
@Log4j2
public class AttributedPresent implements ExpectedCondition<Boolean> {

  private By byType;
  private String attributeName;
  private WebElement element;

  public AttributedPresent(final By byType, final String attributeName) {
    this.byType = byType;
    this.attributeName = attributeName;
  }

  public AttributedPresent(final WebElement element, final String attributeName) {
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
    log.debug("custom wait implated by " + status);
    if (null != status) {
      return true;
    }
    return false;
  }



}
