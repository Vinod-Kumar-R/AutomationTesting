package com.encash.offers.bussiness.admin;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

public class Admin {

  static Logger logger = LogManager.getLogger(Admin.class);
  private GenericMethod gm;
  private WaitMethod wm;
  

  /**
   * In constructor variable are initialized.
   */
  public Admin() {
    gm = new GenericMethod();
    wm = new WaitMethod();
    

  }
  /**
   * This method is used to open the Admin URL page.
   * @param stringParam contain the parameter value
   * @return the status of execution i.e. pass or fail
   * @throws Exception throw an generic exception
   */

  public String adminurlopen(String[] stringParam) throws Exception {
    //enter username and password 
    logger.debug("Enter the User name ");
    WebElement username = gm.getElement("admin_username");
    username.sendKeys(stringParam[0]);
    logger.debug("Enter the User Password ");
    WebElement password = gm.getElement("admin_password");
    password.sendKeys(stringParam[1]);
    logger.debug("click on login button");
    WebElement login = gm.getElement("admin_login");
    login.click();
    return "pass";
  }

  /**
   * This method is used to create a new competition in admin page. 
   * @param stringParam contain the parameter value
   * @return  the status of execution i.e. pass or fail
   * @throws Exception throw an generic exception
   */
  public String createCompetation(String[] stringParam) throws Exception {
    //WebElement element;

    gotoCompetation();
    wm.angularWait();
    //element = gm.getElement(StringParam[4]);
    //element.click();

    return "pass";
  }

  /**
   * This the common method used when ever used to click on Competition.
   * @return the status of execution i.e. pass or fail
   */
  public String gotoCompetation() {

    logger.debug("Waiting for the Angular request completed");
    wm.angularWait();

    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = gm.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    wm.angularWait();

    logger.debug("click on the Encash button");
    element = gm.getElement("competitions");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    wm.angularWait();


    logger.debug("click on the three dot  new competations");
    element = gm.getElement("threedot");
    element.click();

    logger.debug("waiting for the Angular request completed");
    wm.angularWait();

    logger.debug("clicking on the Add to queue button");
    element = gm.getElement("competitions_queue");
    element.click();

    return "Pass";
  }

}
