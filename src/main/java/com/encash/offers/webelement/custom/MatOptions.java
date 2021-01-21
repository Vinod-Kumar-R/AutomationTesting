package com.encash.offers.webelement.custom;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;


public class MatOptions {

  WebElement options;
  private static Logger logger = LogManager.getLogger(MatOptions.class);

  public MatOptions(WebElement options) {
    super();
    this.options = options;
  }

  /**
   * This method is used to select mat-options.
   * @param optionText text need to be select
   */
  public void selectVisibleText(String optionText) {
    WebElement option = this.options.findElement(By.xpath(
                    ".//mat-option[normalize-space(.) = " + Quotes.escape(optionText) + "]"));
    option.click();
  }

  /**
   * This method is used to select Multiple option. 
   * @param optionText list of text need to select
   */
  public void multipleSelectText(List<String> optionText) {

    List<WebElement> optiones = this.options.findElements(By.tagName("mat-option"));

    for (String selecttext : optionText) { 
      logger.debug("Text need to select " + selecttext);
      for (WebElement option : optiones) {
        logger.debug("list message are " + option.getText());
        if (selecttext.equalsIgnoreCase(option.getText())) {
          option.click(); 
          logger.debug("options has been selected");
          break;
        }
        
      }
    }
  }


}
