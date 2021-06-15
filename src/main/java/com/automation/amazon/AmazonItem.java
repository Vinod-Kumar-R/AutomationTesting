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
  
  /**
   * method is used to add the selected item to cart.
   * @return 
   */
  public String addItem(List<String> dataParam) {
    
    logger.debug("clicking on the 'All' hyberlink in amazon");
    WebElement element = genericmethod.getElement("amazonall");
    waitmethod.waitForElementPresent("amazonall");
    element.click();
    
    logger.debug("enalbe the side bar and select the Main categeory iteam");
    sidebar("amazonsidebar", dataParam.get(0));
    
    logger.debug("select the sub categeroy iteam");
    sidebar("amazonsidebartwo", dataParam.get(1));
    
    waitmethod.waitForElementPresent("amazonaddtocart");
    logger.debug("add select iteam to cart");
    element = genericmethod.getElement("amazonaddtocart");
    element.click();
    
    return "pass";
    
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
