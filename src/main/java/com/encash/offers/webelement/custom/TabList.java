package com.encash.offers.webelement.custom;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TabList {
  
  WebElement element;
  private static Logger logger = LogManager.getLogger(TabList.class);
  
  
  public TabList(WebElement element) {
    super();
    this.element = element;
  }
  
  /**
   * This method is used to select on tab name in which it has tag name as div.
   * @param tabname need to click on the tabname
   * 
   */
  public void selectTab(String tabname) {
    
    List<WebElement> tabs = this.element.findElements(By.xpath("div"));
    
    for (WebElement tab : tabs) {
      logger.debug("tab name is " + tab.getText());
      if (tab.getText().equals(tabname)) {
        tab.click();
        logger.debug("clicked on the tab name");
        break;
      }
    }
    
   
  }

}
