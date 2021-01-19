package com.encash.offers.bussiness.admin;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class Admin {

  private static Logger logger = LogManager.getLogger(Admin.class);
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;


  /**
   * This method is used to open the Admin URL page.
   * @param dataParam contain the parameter value
   * @return the status of execution i.e. pass or fail
   *
   */

  public String adminurlopen(List<String> dataParam)  {
    //enter username and password 
    logger.debug("Enter the User name ");
    WebElement username = genericmethod.getElement("admin_username");
    username.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the User Password ");
    WebElement password = genericmethod.getElement("admin_password");
    password.sendKeys(dataParam.get(1));
    
    logger.debug("click on login button");
    WebElement login = genericmethod.getElement("admin_login");
    login.click();
    
    return "pass";
  }

  /**
   * This method is used to create a new competition in admin page. 
   * @param dataParam contain the parameter value
   * @return  the status of execution i.e. pass or fail
   * 
   */
  public String competationForm(List<String> dataParam)  {
    
    gotoCompetation();
    
    logger.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    logger.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    logger.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    logger.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    logger.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    logger.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    logger.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    logger.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    logger.debug("clicking on the calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    return "pass";
  }
  
  /**
   * This method is used to fill the offers forms.
   * @param dataParam data required for form
   * @return
   */
  public String offersForm(List<String> dataParam) {
    gotoOffers();
    createNewCompetitions();


    logger.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    logger.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    logger.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    logger.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    logger.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    logger.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    logger.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    logger.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    logger.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    logger.debug("clicking on the start calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    logger.debug("clicking on the end calendar button");
    element = genericmethod.getElement("competition_opencalendar2");
    element.click();
    
    logger.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender2");
    genericmethod.dateSelection(element, "10", "FEB", "2026");
    
    
    return "pass";
    
  }

  /**
   * This the common method used when ever used to click on Competition.
   * @return the status of execution i.e. pass or fail
   */
  public String gotoCompetation() {

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("competitions");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("create new Competitions");
    createNewCompetitions();

    return "Pass";
  }
  
  
  /**
   * This method is used to create the new competitions.
   * @return
   */
  public String createNewCompetitions() {
    
    WebElement element;
    logger.debug("click on the three dot  new competations");
    element = genericmethod.getElement("threedot");
    element.click();

    logger.debug("waiting for the Angular request completed");
    waitmethod.angularWait();

    logger.debug("clicking on the Add to queue button");
    element = genericmethod.getElement("competitions_queue");
    element.click();
    waitmethod.angularWait();
    
    return "Pass";
  }
  
  /**
   * This method is used to go to Offers.
   * @return
   */
  public String gotoOffers() {
    
    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("click on the Encash button");
    element = genericmethod.getElement("offers");
    element.click();

    logger.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    logger.debug("create new offers");
    //createNewCompetitions();
    
    return "pass";
  }
  
  /**
   * This method is used to logout from admin application.
   * @return
   */
  public String logoutAdmin() {
    
    logger.debug("clicking the on the user login name menu");
    WebElement element = genericmethod.getElement("logout_admin_menu");
    element.click();
    
    logger.debug("clickin on the logout button");
    waitmethod.waitForElementPresent("logout_admin");
    element = genericmethod.getElement("logout_admin");
    element.click();
    
    return "pass";
    
  }

}
