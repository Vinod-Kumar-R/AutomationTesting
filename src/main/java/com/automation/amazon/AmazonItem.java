package com.automation.amazon;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class AmazonItem {
  private static Logger logger = LogManager.getLogger(AmazonItem.class.getName()); 
  
  @Autowired
  GenericMethod genericmethod;
  @Autowired
  WaitMethod waitmethod;
  
  public void addItem() {
    
    WebElement element = genericmethod.getElement("amazonall");
    waitmethod.waitForElementPresent("amazonall");
    element.click();
    
    //first slide bar
    sidebar("amazonsidebar", "Fire TV");
    
    //second slid bar
    sidebar("amazonsidebartwo", "Fire TV Cube");
    
    waitmethod.waitForElementPresent("amazonaddtocart");
    element = genericmethod.getElement("amazonaddtocart");
    element.click();
    
  }
  
  private void sidebar(String element, String text) {
    waitmethod.waitForElementPresent(element);
    List<WebElement> elements = genericmethod.getElements(element);
    for (WebElement sidebar :elements) {
      logger.debug(sidebar.getText());
      if (sidebar.getText().equals(text)) {
        logger.debug("found the element and clicking on it");
        sidebar.click();
        break;
      }
    }
  }

}
