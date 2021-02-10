package com.encash.offers.webelement.custom;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateQuestionnaire {

  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  WebElement element;
  private static Logger logger = LogManager.getLogger(CreateQuestionnaire.class);

  public void setElement(WebElement element) {
    this.element = element;
  }

  /**
   * This will return how many questionnaires are there.
   * @return 
   */
  public int getNumberofQuestionnaires() {
    int row = element.findElements(By.xpath("div")).size();
    // total row minus 2 is because to get only question not with save and save all product
    return row - 1;
  }

  /**
   * This method will created a new questionnaire row.
   */
  public void createNewQuestionnaries() {

    List<WebElement> creates = element.findElements(By.xpath("div"));

    // -1 is added becase list start from 0
    WebElement create = creates.get(getNumberofQuestionnaires() - 1);

    List<WebElement> buttons =  create.findElements(By.xpath("button"));

    logger.debug("Number of button are " + buttons.size());
    
    butto:
      for (WebElement button : buttons) {
        logger.debug("button text " + button.getText());
        if (button.getText().equalsIgnoreCase("add")) {
          waitmethod.waitForElementClickable(button);
          button.click();
          logger.debug("click on the add button");
          break butto;
        }
      }
  }
  
  
  public void selectquestionnaries() {
    
    List<WebElement> creates = genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
    waitmethod.waitForAllElementVisible(creates);
    
    // -1 is added becase list start from 0
    WebElement create = creates.get(getNumberofQuestionnaires() - 1);
    WebElement selectquestion = genericmethod.getWebElement(create, 
                    "competition_questionnaries_dropdown");
    waitmethod.waitForElementVisible(selectquestion);
    logger.debug("clicking on the questionnaries dropdown to show");
    selectquestion.click();
    
  }
  
  public void saveQuestionnaries() {
    
    List<WebElement> creates =  genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
    //  element.findElements(By.xpath("div"));
    
    WebElement savebutton = creates.get(getNumberofQuestionnaires());
    waitmethod.waitForElementClickable(savebutton);
    savebutton.click();
    
  }
  
  public void deleteQuestionnaries(String deleteText) {

    List<WebElement> creates =  genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
    outerloop:
      for (WebElement list : creates) {

        WebElement selectquestionnaries = genericmethod.getWebElement(list, 
                        "competition_questionnaires_select");

        List<WebElement> buttons = list.findElements(By.xpath("button"));

        logger.debug("questionnaries select " + selectquestionnaries.getText());
        if (selectquestionnaries.getText().equals(deleteText)) {


          for (WebElement button : buttons) {
            logger.debug("button text " + button.getText());
            if (button.getText().equalsIgnoreCase("delete")) {
              button.click();
              logger.debug("click on the delete button");
              break outerloop;
            }
          }
        }
      }
  }




}


