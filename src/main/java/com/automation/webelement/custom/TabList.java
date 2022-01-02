package com.automation.webelement.custom;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Log4j2
public class TabList {
  
  WebElement element;
  
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
      log.debug("tab name is " + tab.getText());
      if (tab.getText().equals(tabname)) {
        tab.click();
        log.debug("clicked on the tab name");
        break;
      }
    }
  }

}
