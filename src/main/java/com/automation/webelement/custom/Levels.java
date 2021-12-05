package com.automation.webelement.custom;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Levels {

  private static Logger logger = LogManager.getLogger(Levels.class);
  WebElement element;
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;

  public void setElement(WebElement element) {
    this.element = element;
  }

  /**
   * This method is used to get the number levels present current time.
   * @return the int value.
   */
  private int numbetOfLevels() {

    List<WebElement> numberofLevels = genericmethod.getWebElements(this.element, 
                    "competition_levels_questionnaries_base");
    logger.debug("number of levels are " + numberofLevels.size());
    return numberofLevels.size();

  }

  /**
   * This method is used to create a new level.
   */
  public void createLevels() {

    List<WebElement> levels = genericmethod.getWebElements(this.element, 
                    "competition_levels_questionnaries_base");

    //get the last levels row 
    WebElement level = levels.get(numbetOfLevels() - 1);

    List<WebElement> buttons = genericmethod.getWebElements(level, "competition_new_levels");

    logger.debug("Number of button are " + buttons.size());

    butto:
      for (WebElement button : buttons) {
        logger.debug("button text " + button.getText());
        if (button.getText().equalsIgnoreCase("add")) {
          button.click();
          logger.debug("click on the add button");
          break butto;
        }
      }
  }
  
  /**
   * This method is used to click on the last level questionnaires.
   */
  public void selectQuestionnairesLevel() {

    List<WebElement> levels = genericmethod.getWebElements(this.element, 
                    "competition_levels_questionnaries_base");

    WebElement level = levels.get(numbetOfLevels() - 1);

    logger.debug("getting the questionnairesbutton");
    WebElement questionnairesbutton = genericmethod.getWebElement(level,
                    "competition_levels_select_questionnaires");
    logger.debug("click on the questionnairesbutton");
    questionnairesbutton.click();
    
  }
  
  /**
   * This method is used save created levels.
   */
  public void saveLevels() {
    
    WebElement savebutton = genericmethod.getWebElement(this.element, "competition_level_save");
    waitmethod.waitForElementClickable(savebutton);
    savebutton.click();
  }

}
