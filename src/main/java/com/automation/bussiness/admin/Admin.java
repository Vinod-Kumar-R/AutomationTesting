package com.automation.bussiness.admin;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Admin {

  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;


  /**
   * This method is used to open the Admin URL page.
   * @param dataParam contain the parameter value
   *
   */

  public void adminurlopen(List<String> dataParam)  {
    //enter username and password 
    log.debug("Enter the User name ");
    waitmethod.waitForElementPresent("admin_username");
    WebElement username = genericmethod.getElement("admin_username");
    username.sendKeys(dataParam.get(0));
    
    log.debug("Enter the User Password ");
    WebElement password = genericmethod.getElement("admin_password");
    password.sendKeys(dataParam.get(1));
    
    log.debug("click on login button");
    WebElement login = genericmethod.getElement("admin_login");
    login.click();
  }

  /**
   * This method is used to create a new competition in admin page. 
   * @param dataParam contain the parameter value
   * 
   */
  public void competationForm(List<String> dataParam)  {
    
    gotoCompetation();
    log.debug("create new Competitions");
    createNewCompetitions();
    
    log.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    log.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    log.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    log.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    log.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    log.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    log.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    log.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    log.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    log.debug("clicking on the calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    log.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    log.debug("clicking on the competition add button");
    element = genericmethod.getElement("competition_add");
    element.click();
    
    log.debug("enable the isActive button");
    waitmethod.waitForElementPresent("competition_active");
    element = genericmethod.getElement("competition_active");
    element.click();
    
    log.debug("Wait for notification disappear");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);
    
    log.debug("click on the save button");
    element = genericmethod.getElement("competition_save");
    waitmethod.waitForElementClickable(element);
    element.click();
    
    log.debug("Wait for notification appear");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    log.debug("Wait for notification disappear");
    waitmethod.waitForNotificationDisAppear(element);

  }
  
  /**
   * This method is used to fill the offers forms.
   * @param dataParam data required for form
   */
  public void offersForm(List<String> dataParam) {
    gotoOffers();
    createNewCompetitions();


    log.debug("Enter the Competition name");
    WebElement element = genericmethod.getElement("competition_name");
    waitmethod.waitForElementVisible(element);
    element.sendKeys(dataParam.get(0));
    
    log.debug("Enter the competition description ");
    element = genericmethod.getElement("competition_description");
    element.sendKeys(dataParam.get(1));
    
    log.debug("click on the Client dropdown");
    element = genericmethod.getElement("competition_client");
    element.click();
    
    log.debug("selecting the option from client list");
    element = genericmethod.getElement("competition_client_options");
    //waitmethod.waitForElementVisible(element);
    genericmethod.matOption(element, dataParam.get(2));
    
    log.debug("click on the locatons");
    element = genericmethod.getElement("competition_location");
    waitmethod.waitForElementVisible(element);
    element.click();
    
    log.debug("select the location options");
    element = genericmethod.getElement("competition_location_options");
    genericmethod.matOption(element, dataParam.get(3));
    
    log.debug("clicking on the categeory");
    element = genericmethod.getElement("competition_categories");
    element.click();
    
    log.debug("Selecting the Multiple categories");
    element = genericmethod.getElement("competition_categories_options");
    List<String> categorieslist =  Arrays.asList(dataParam.get(4).split("~"));
    genericmethod.matOptions(element, categorieslist);
    
    log.debug("clicking on the overlay");
    element = genericmethod.getElement("competition_overlay");
    element.click();
    
    log.debug("clicking on the start calendar button");
    element = genericmethod.getElement("competition_opencalendar");
    element.click();
    
    log.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender");
    genericmethod.dateSelection(element, dataParam.get(5), dataParam.get(6), dataParam.get(7));
    
    log.debug("clicking on the end calendar button");
    element = genericmethod.getElement("competition_opencalendar2");
    element.click();
    
    log.debug("selecting the date from calendar");
    element = genericmethod.getElement("competition_calender2");
    genericmethod.dateSelection(element, "10", "FEB", "2026");
  }

  /**
   * This the common method used when ever used to click on Competition.
   */
  public void gotoCompetation() {

    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    log.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    log.debug("click on the Encash button");
    element = genericmethod.getElement("competitions");
    waitmethod.waitForElementClickable(element);
    element.click();

    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
  }
  
  
  /**
   * This method is used to create the new competitions.
   */
  public void createNewCompetitions() {
    
    WebElement element;
    log.debug("click on the three dot  new competations");
    waitmethod.waitForElementPresent("threedot");
    element = genericmethod.getElement("threedot");
    element.click();

    log.debug("waiting for the Angular request completed");
    waitmethod.angularWait();

    log.debug("clicking on the Add to queue button");
    element = genericmethod.getElement("competitions_queue");
    element.click();
    waitmethod.angularWait();
  }
  
  /**
   * This method is used to go to Offers.
   */
  public void gotoOffers() {
    
    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    WebElement element = null;  
    log.debug("click on the Encash button");
    element = genericmethod.getElement("expand_encash");
    element.click();

    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    log.debug("click on the Encash button");
    element = genericmethod.getElement("offers");
    element.click();

    log.debug("Waiting for the Angular request completed");
    waitmethod.angularWait();
    
    log.debug("create new offers");
    //createNewCompetitions();
  }
  
  /**
   * This method is used to logout from admin application.
   */
  public void logoutAdmin() {
    
    log.debug("clicking the on the user login name menu");
    WebElement element = genericmethod.getElement("logout_admin_menu");
    element.click();
    
    log.debug("clickin on the logout button");
    waitmethod.waitForElementPresent("logout_admin");
    element = genericmethod.getElement("logout_admin");
    element.click();
    
    genericmethod.refreshPage();
  }
 
  /**
  * This method is used to Search for particular competition.
  * @param dataParam contain competition name which need to search
  */
  public void searchCompetition(List<String> dataParam) {
    log.debug("Go to competatition ");
    gotoCompetation();

    log.debug("typing on competitition search box");
    waitmethod.waitForElementPresent("competition_search_page");
    WebElement element = genericmethod.getElement("competition_search_page");
    element.sendKeys(dataParam.get(0));

    log.debug("wait for result data to load");
    //waitmethod.waitForElementLessThan("competititon_search_result", 2);
    waitmethod.waitForMatTableUpadate(5);
    
    log.debug("click on the competition search result");
    element = genericmethod.getElement("competititon_search_result");
    //waitmethod.waitThread();
    genericmethod.matTable(element, dataParam.get(0));
  }
  
  /**
   * This method is used to click on the question tab and click on add button.  
   * @param dataParam , contain tab name in which it has to click
   */
  public void questionnairesTab(List<String> dataParam) {
    
    log.debug("click on the question tab");
    waitmethod.waitForElementPresent("competition_tab");
    WebElement element = genericmethod.getElement("competition_tab");
    genericmethod.tabselect(element, dataParam.get(0));
    
    //click on the questionnaries button for first time
    if (dataParam.get(1).equalsIgnoreCase("yes")) {
      log.debug("click on the Add questionnaires click");
      element = genericmethod.getElement("competition_question_button");
      waitmethod.waitForElementClickable(element);
      element.click();
    }
  }
  
  
  /**
   * This method is used to add the questionnaires in competition.
   * @param dataParam contain the data
   */
  public void questionnariesCreate(List<String> dataParam) {

    WebElement element = genericmethod.getElement("competition_questionaries_list");

    if (dataParam.get(0).equalsIgnoreCase("New")) {
      log.debug("Create a new row for questionnnaries");
      genericmethod.createNewQuesetionnariesRow(element);
      log.debug("select the questinnaries from dropdown list");
      genericmethod.newQuestionnaire(element, dataParam.get(1));
    } else {
      genericmethod.newQuestionnaire(element, dataParam.get(1));
    }

    log.debug("Save the questionnaires");
    genericmethod.saveQuestionnaries(element);
    
    log.debug("waiting for the notification");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);
  }
  
  /**
   * This method is used to Delete the Questionnaires in Admin.
   * @param dataParam contain which questionnaires need to deleted
   */
  public void questionnariesDelete(List<String> dataParam) {
    
    WebElement element = genericmethod.getElement("competition_questionaries_list");
    
    log.debug("delete the questionnaries row");
    genericmethod.deleteQuestionnariesRow(element, dataParam.get(0));

  }
  
  /**
   * This method is used to select the Level tab.
   * @param dataParam a list of data which is required for clicking on level tab
   */
  public void leveltab(List<String> dataParam) {
    
    log.debug("click on the level tab");
    waitmethod.waitForElementPresent("competition_tab");
    WebElement element = genericmethod.getElement("competition_tab");
    genericmethod.tabselect(element, dataParam.get(0));
    
    //click on the levels button for first time
    if (dataParam.get(1).equalsIgnoreCase("yes")) {
      log.debug("clicking on the add level button");
      element = genericmethod.getElement("add_levels");
      waitmethod.waitForElementVisible(element);
      element.click();
    }
  }
  
  /**
   * This method is used to select the levels while creating the questionnaires.
   * @param dataParam contain the list of data required for creating levels
   */
  public void levels(List<String> dataParam) {
    
    log.debug("getting the base element for levels");
    WebElement element = genericmethod.getElement("competition_levels_base");

    if (!dataParam.get(0).equalsIgnoreCase("New")) {
      log.debug("selecting the questionnaires for levels");
      genericmethod.selectLevelsQuestionnaries(element, dataParam.get(1));
    } else {
      log.debug("click creating the new level to add");
      genericmethod.createNewLevelsRow(element);
      log.debug("selecting the questionnaires for levels");
      genericmethod.selectLevelsQuestionnaries(element, dataParam.get(1));
    }

    genericmethod.levelSave(element);
    
    log.debug("waiting for the notification");
    waitmethod.waitForElementPresent("notification");
    element = genericmethod.getElement("notification");
    waitmethod.waitForNotificationAppear(element);
    closeNotification();
    waitmethod.waitForNotificationDisAppear(element);
  }
  
  
  /**
   * This method is used to close the notification shown to user.
   */
  public void closeNotification() {
    log.debug("wait for close notification button present");
    waitmethod.waitForElementPresent("closenotification");
    
    log.debug("click on the close notification button");
    WebElement element = genericmethod.getElement("closenotification");
    element.click();
  }

}
