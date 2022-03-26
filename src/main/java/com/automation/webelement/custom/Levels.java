package com.automation.webelement.custom;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to create a custom level.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class Levels {

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
    log.debug("number of levels are " + numberofLevels.size());
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

    log.debug("Number of button are " + buttons.size());

    butto:
      for (WebElement button : buttons) {
        log.debug("button text " + button.getText());
        if (button.getText().equalsIgnoreCase("add")) {
          button.click();
          log.debug("click on the add button");
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

    log.debug("getting the questionnairesbutton");
    WebElement questionnairesbutton = genericmethod.getWebElement(level,
                    "competition_levels_select_questionnaires");
    log.debug("click on the questionnairesbutton");
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
