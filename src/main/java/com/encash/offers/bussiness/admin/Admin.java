package com.encash.offers.bussiness.admin;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class Admin {

  private static Logger logger = LogManager.getLogger(Admin.class);
  @Autowired
  private GenericMethod gemericMethod;
  @Autowired
  private WaitMethod waitMethod;


  /**
   * This method is used to open the Admin URL page.
   * @param dataParam contain the parameter value
   * @return the status of execution i.e. pass or fail
   *
   */

  public String adminurlopen(List<String> dataParam)  {
    //enter username and password 
    logger.debug("Enter the User name ");
    WebElement username = gemericMethod.getElement("admin_username");
    username.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the User Password ");
    WebElement password = gemericMethod.getElement("admin_password");
    password.sendKeys(dataParam.get(1));
    
    logger.debug("click on login button");
    WebElement login = gemericMethod.getElement("admin_login");
    login.click();
    
    return "pass";
  }

  /**
   * This method is used to create a new competition in admin page. 
   * @param dataParam contain the parameter value
   * @return  the status of execution i.e. pass or fail
   * 
   */
  public String createCompetation(List<String> dataParam)  {
    //WebElement element;

    gotoCompetation();
    waitMethod.angularWait();
    //element = gm.getElement(dataParam[4]);
    //element.click();

    return "pass";
  }

  /**
   * This the common method used when ever used to click on Competition.
   * @return the status of execution i.e. pass or fail
   */
  public String gotoCompetation() {

    logger.debug("Waiting for the Angular request completed");
    waitMethod.angularWait();
    //JsWaiter.waitUntilAngularReady();
    //JsWaiter.waitJQueryAngular();

    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = gemericMethod.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitMethod.angularWait();
    // JsWaiter.waitForAngularLoad();

    logger.debug("click on the Encash button");
    element = gemericMethod.getElement("competitions");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitMethod.angularWait();
    //JsWaiter.waitForAngularLoad();


    logger.debug("click on the three dot  new competations");
    element = gemericMethod.getElement("threedot");
    element.click();

    logger.debug("waiting for the Angular request completed");
    waitMethod.angularWait();
    //JsWaiter.waitForAngularLoad();

    logger.debug("clicking on the Add to queue button");
    element = gemericMethod.getElement("competitions_queue");
    element.click();

    return "Pass";
  }

}
