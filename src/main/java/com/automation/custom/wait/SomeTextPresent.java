package com.automation.custom.wait;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;


@Log4j2
public class SomeTextPresent implements ExpectedCondition<Boolean> {
  private WebElement element;

  public SomeTextPresent(WebElement element) {
    this.element = element;
  }

  @Override
  public Boolean apply(WebDriver driver) {
    // TODO Auto-generated method stub
    log.debug("Waiting for the some text persent -------> " + this.element.getText());
    if (this.element.getText().equals("")) {
      log.debug("Empty text present so return false");
      return false;
    }
    log.debug("Element has some text so return true " + this.element.getText());
    return true;
  }

}
