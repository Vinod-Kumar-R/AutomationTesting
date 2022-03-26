package com.automation.custom.wait;

import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * This class is used to wait for particular element not present. 
 * @author Vinod Kumar R
 *
 */
@Log4j2
public class NotificationDisAppear implements ExpectedCondition<Boolean> {
  private WebElement element;
 
  
  public NotificationDisAppear(WebElement element) {
    super();
    this.element = element;
  }
  
  

  @Override
  public @Nullable Boolean apply(WebDriver driver) {
    // TODO Auto-generated method stub
    
    log.debug("waiting for the notification");
    
    int notificationsize = this.element.findElements(By.xpath("//nz-notification")).size();
    
    if (notificationsize == 0) {
      log.debug("notification size is equal to zero");
      return true;
    }
    log.debug("notification size is greater then zero");

    return false;
  }

}
