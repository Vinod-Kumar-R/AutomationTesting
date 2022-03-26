package com.automation.webelement.custom;

import com.automation.utility.WaitMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to create custom for Mat Options.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class MatOptions {

  @Autowired
  private WaitMethod waitmethod;
  WebElement options;
 
  public void setOptions(WebElement options) {
    this.options = options;
  }

  /**
   * This method is used to select mat-options.
   * @param optionText text need to be select
   */
  public void selectVisibleText(String optionText) {
    
    log.debug("waiting for the options to be present");
    waitmethod.waitForNestedElementPresence(this.options, By.xpath(
                    "//mat-option[normalize-space(.) = " + Quotes.escape(optionText) + "]"));
    
    WebElement option = this.options.findElement(By.xpath(
                    "//mat-option[normalize-space(.) = " + Quotes.escape(optionText) + "]"));
    log.debug("scroll to element");
    //genericmethod.scrolltoelement(option);
    log.debug("click on the options");
    waitmethod.waitForElementVisible(option);
    option.click();
  }

  /**
   * This method is used to select Multiple option. 
   * @param optionText list of text need to select
   */
  public void multipleSelectText(List<String> optionText) {

    List<WebElement> optiones = this.options.findElements(By.tagName("mat-option"));

    for (String selecttext : optionText) { 
      log.debug("Text need to select " + selecttext);
      for (WebElement option : optiones) {
        log.debug("list message are " + option.getText());
        if (selecttext.equalsIgnoreCase(option.getText())) {
          option.click(); 
          log.debug("options has been selected");
          break;
        }
        
      }
    }
  }

}
