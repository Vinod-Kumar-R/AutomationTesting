package com.automation.webelement.custom;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to custom the create question.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class CreateQuestionnaire {

  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  WebElement element;

  public void setElement(WebElement element) {
    this.element = element;
  }

  /**
   * This will return how many questionnaires are there.
   * @return number of row
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

    log.debug("Number of button are " + buttons.size());
    
    butto:
      for (WebElement button : buttons) {
        log.debug("button text " + button.getText());
        if (button.getText().equalsIgnoreCase("add")) {
          waitmethod.waitForElementClickable(button);
          button.click();
          log.debug("click on the add button");
          break butto;
        }
      }
  }
  
  /**
   * This method is used to created new row for creating questionnaries.
   */
  public void selectquestionnaries() {
    
    List<WebElement> creates = genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
    waitmethod.waitForAllElementVisible(creates);
    
    // -1 is added becase list start from 0
    WebElement create = creates.get(getNumberofQuestionnaires() - 1);
    WebElement selectquestion = genericmethod.getWebElement(create, 
                    "competition_questionnaries_dropdown");
    waitmethod.waitForElementVisible(selectquestion);
    log.debug("clicking on the questionnaries dropdown to show");
    selectquestion.click();
    
  }
  
  /**
   * this method is used to save created questionnaires.
   */
  public void saveQuestionnaries() {
    
    List<WebElement> creates =  genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
   
    
    //buttons has save and save all to product
    WebElement buttons = creates.get(getNumberofQuestionnaires());
    
    //getting list of buttons
    List<WebElement> buttonlist = genericmethod.getWebElements(buttons, 
                    "competition_questionnaries_save_lists");

    for (WebElement save : buttonlist) {
      log.debug("Competition button text name : " + save.getText());
      if (save.getText().equals("SAVE")) {
        waitmethod.waitForElementClickable(save);
        log.debug("click on the Save button");
        save.click();
      }
    }
    
  }
  
  /**
   * This method is used to delete particular questionnaires based on questionnaire text.
   * @param deleteText contain the text which need to delete
   */
  public void deleteQuestionnaries(String deleteText) {

    List<WebElement> creates =  genericmethod.getWebElements(element, 
                    "competition_questionnaries_lists");
    outerloop:
      for (WebElement list : creates) {

        WebElement selectquestionnaries = genericmethod.getWebElement(list, 
                        "competition_questionnaires_select");

        List<WebElement> buttons = list.findElements(By.xpath("button"));

        log.debug("questionnaries select " + selectquestionnaries.getText());
        if (selectquestionnaries.getText().equals(deleteText)) {


          for (WebElement button : buttons) {
            log.debug("button text " + button.getText());
            if (button.getText().equalsIgnoreCase("delete")) {
              button.click();
              log.debug("click on the delete button");
              break outerloop;
            }
          }
        }
      }
  }

}


