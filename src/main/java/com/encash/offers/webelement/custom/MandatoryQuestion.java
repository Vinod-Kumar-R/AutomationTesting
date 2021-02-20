package com.encash.offers.webelement.custom;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class MandatoryQuestion {
  
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  WebElement baseElement;
  private static Logger logger = LogManager.getLogger(MandatoryQuestion.class);

  public void setElement(WebElement baseElement) {
    this.baseElement = baseElement;
  }
  
  /**
   * This method is used to get the question. 
   * @param question to which need to compare it
   * @return True, if question matches else false 
   */
  public boolean verifyQuestion(String question) {
    
    WebElement questionanwerElement = genericmethod.getWebElement(baseElement,
                    "mandatory_question_answer");
    WebElement questionElement = genericmethod.getWebElement(questionanwerElement,
                    "mandatory_question_only");

    logger.debug("fetched question :- " + questionElement.getText());
    logger.debug("question got from excel :- " + question);
    if (questionElement.getText().equals(question)) {
      logger.debug("both question are equal");
      return true;
    }
    logger.debug("both question are not equal ");
    return false;
  }
  
  /**
   * This method is used to select the answer.
   * @param answerSelect contain data to which answer need to select
   * @return the boolean status 
   */
  public Boolean selectAnswer(String answerSelect) {
    WebElement questionanwerElement = genericmethod.getWebElement(baseElement,
                    "mandatory_question_answer");
    
    List<WebElement> answers = genericmethod.getWebElements(questionanwerElement, 
                    "mandatory_answer_list");
    
    logger.debug("re-run 2");
    
    for (WebElement answer : answers) {
      logger.debug("feachted answer :- " + answer.getText());
      if (answer.getText().equals(answerSelect)) {
        answer.click();
        logger.debug("answer found");
        return true;
      }
    }
    
    logger.debug("Answer not found");
    return false;
    
  }
  
  /**
   * This method is used to click on the next button on mandatory question.
   */
  public void nextQuestion() {
    
    List<WebElement> buttons = genericmethod.getWebElements(baseElement,
                    "mandatory_question_next_cancel");
    
    logger.debug("get the button text");
    for (WebElement button : buttons) {
      logger.debug("get the button text :- " + button.getText());
      
      if (button.getText().equals("Next")) {
        
        waitmethod.waitForElementClickable(button);
        button.click();
        
        logger.debug("waiting for loder class request complete");
        waitmethod.waitForElementPresent("mandatory_next_question_load");
        waitmethod.waitForElementInvisible("mandatory_next_question_load");
        break;
      }
    }
    
  }
}
