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
public class CompetitionQuestion {
  
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
    
    waitmethod.waitForElementPresent("competition_question_answer");
    
    WebElement questionanwerElement = genericmethod.getWebElement(baseElement,
                    "competition_question_answer");
    
    WebElement questionElement = genericmethod.getWebElement(questionanwerElement,
                    "competition_question_only");

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
    WebElement questionanwerElement = genericmethod.getWebElement(baseElement,
                    "competition_question_answer");
    
    List<WebElement> answers = genericmethod.getWebElements(questionanwerElement, 
                    "competition_answer_list");
    
    for (WebElement answer : answers) {
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
                    "competition_question_next_cancel");
    
    log.debug("get the button text");
    for (WebElement button : buttons) {
      log.debug("get the button text :- " + button.getText());
      
      if (button.getText().equals("Next")) {
        
        waitmethod.waitForElementClickable(button);
        button.click();
        
        log.debug("waiting for loder class request complete");
        waitmethod.waitForElementPresent("competition_next_question_load");
        waitmethod.waitForElementInvisible("competition_next_question_load");
        break;
      }
    }
    
  }
}
