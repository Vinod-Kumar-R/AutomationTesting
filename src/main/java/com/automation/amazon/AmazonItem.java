package com.automation.amazon;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AmazonItem {
  
  @Autowired
  GenericMethod genericmethod;
  @Autowired
  WaitMethod waitmethod;
  
  /**
   * method is used to add the selected item to cart.
   * @param dataParam
   *        dataParam[0] item to be select from first side bar
   *        dataParam[1] item to be select from second side bar
   */
  public void addItem(List<String> dataParam) {

    log.debug("clicking on the 'All' hyberlink in amazon");
    WebElement element = genericmethod.getElement("amazonall");
    waitmethod.waitForElementPresent("amazonall");
    element.click();

    log.debug("enable the side bar and select the Main categeory iteam");
    sidebar("amazonsidebar", dataParam.get(0));

    log.debug("select the sub categeroy iteam");
    sidebar("amazonsidebartwo", dataParam.get(1));

    waitmethod.waitForElementPresent("amazonaddtocart");
    log.debug("add select iteam to cart");
    element = genericmethod.getElement("amazonaddtocart");
    element.click();

  }

  private void sidebar(String element, String text) {
    waitmethod.waitForElementPresent(element);
    List<WebElement> elements = genericmethod.getElements(element);
    for (WebElement sidebar :elements) {
      log.debug(sidebar.getText());
      if (sidebar.getText().equals(text)) {
        log.debug("found the element and clicking on it");
        sidebar.click();
        //waitmethod.waitThread();
        break;
      }
    }
  }

}
