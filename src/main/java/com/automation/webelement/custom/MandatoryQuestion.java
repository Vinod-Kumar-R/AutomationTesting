package com.automation.webelement.custom;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MandatoryQuestion {
  
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  WebElement baseElement;
  
  public void setElement(WebElement baseElement) {
    this.baseElement = baseElement;
  }
  
  /**
   * This method is used to get the question. 
   * @param question to which need to compare it
   * @return True, if question matches else false 
   */
  public boolean verifyQuestion(String question) {
    
    WebElement questionElement = genericmethod.getWebElement(baseElement,
                    "mandatory_question");
    
    log.debug("fetched question :- " + questionElement.getText());
    log.debug("question got from excel :- " + question);
    if (questionElement.getText().equals(question)) {
      log.debug("both question are equal");
      return true;
    }
    log.debug("both question are not equal ");
    return false;
  }
  
  /**
   * This method is used to select the answer.
   * @param answerSelect contain data to which answer need to select
   * @return the boolean status 
   */
  public Boolean selectAnswer(String answerSelect) {
    
    List<WebElement> answers = genericmethod.getWebElements(baseElement, 
                    "mandatory_answer_list");
   
    for (WebElement answer : answers) {
      
      if (genericmethod.isMobileview()) {
        genericmethod.scrolltoelementBottom(answer);
      }
      
      log.debug("feachted answer :- " + answer.getText());
      if (answer.getText().equals(answerSelect)) {
        answer.click();
        log.debug("answer found");
        return true;
      }
    }
    
    log.debug("Answer not found");
    return false;
  }

  /**
   * This method is used to click on the next button on mandatory question.
   */
  public void nextQuestion() {
    
    List<WebElement> buttons = genericmethod.getWebElements(baseElement,
                    "mandatory_next_question");
    
    log.debug("get the button text");
    for (WebElement button : buttons) {
      log.debug("get the button text :- " + button.getText());
      
      if (button.getText().equals("Next")) {
        
        waitmethod.waitForElementClickable(button);
        button.click();
        
        log.debug("waiting for loder class request complete");
        waitmethod.waitForElementPresent("mandatory_next_question_wait");
        waitmethod.waitForElementInvisible("mandatory_next_question_wait");
        break;
      }
    }
    
  }
}
