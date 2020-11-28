package com.encash.offers.webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import com.encash.offers.utility.WaitMethod;


/**
 * This Class is used for WebDriver Event Listener. 
 * @author Vinod Kumar R
 *
 */
public class EventListener extends AbstractWebDriverEventListener {

  private static Logger logger = LogManager.getLogger(EventListener.class);
  
  /**
   * Constructor.
   */
  public EventListener() {

  }
  
  @Override
  public void onException(Throwable throwable, WebDriver driver) {
    // TODO Auto-generated method stub
   // super.onException(throwable, driver);
  
  }
  
  
  @Override
  public void beforeClickOn(WebElement element, WebDriver driver) {
    // TODO Auto-generated method stub
    super.beforeClickOn(element, driver);
  }



 

  /**
   * wait scenario throw an exception on polling time until time out exception.
   * so we are getting still timeout exception if element are not found
   * @param arg0 
   * @param arg1
   */
  /**public void onException(Throwable arg0, WebDriver arg1) {

    logger.debug("waiting for the element----> " + arg0.getMessage()); 
    logger.debug("waiting for specific element1 --------> " + arg0.getLocalizedMessage());
    logger.debug("waiting for specific element2 --------> " + arg0.getCause());
    logger.debug("waiting for specific element3 --------> " + arg1.getClass().getTypeName());
    
    if (!WaitMethod.waitstatus 
                    && arg0.getClass().equals("org.openqa.selenium.NoSuchElementException")) {

      logger.debug("Waiting for the element " + arg0.getMessage());
      //do nothing 
    }

    if (WaitMethod.waitstatus) {
      logger.debug("got an exception--> " + arg0.getMessage());
      arg0.getMessage();
    }
    
    
  } **/

}
