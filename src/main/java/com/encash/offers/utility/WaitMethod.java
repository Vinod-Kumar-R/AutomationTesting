package com.encash.offers.utility;

import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.custom.wait.CustomWait;
import com.encash.offers.webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class WaitMethod {
  private static Logger logger = LogManager.getLogger(WaitMethod.class.getName());
  public static boolean waitstatus = true;
  private GenericMethod gm;

  public WaitMethod() {
    gm = new GenericMethod();
  }

  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else throw an exception
   * @throws Exception throw an generic exception
   */
  public  String waitForElementVisible(String stringParam) {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By objectName = gm.byType(stringParam);
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Element Visibility " + objectName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(objectName));

    waitstatus = true;
    return "pass";
  }

  /**
   * This Method is used to Wait for Text Visible in an web page.  
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else throw an exception
   * @throws Exception throw an generic exception
   */

  public  String waitForTexttVisible(String[] stringParam)  {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By objectName = gm.byType(stringParam[0]); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Text to be present " + objectName);
    wait.until(ExpectedConditions.textToBePresentInElementLocated(objectName, stringParam[1]));

    waitstatus = true;
    return "pass";
  }

  /**
   * This method is used wait for an Attributed value present in html page. 
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object which need to present in html page
   *     StringParam[1] contain the attribute in an xpath 
   *     StringParam[2] contain the vaule which need to be present for an attributed  
   * @return it return the status "pass" if execution success else throw an exception
   * @throws Exception throw an generic exception
   */

  public  String waitForAttributedContain(String[] stringParam) {
    By objectName = gm.byType(stringParam[0]);
    waitstatus = false;

    logger.debug("Waiting for the attributed presnt and value " + objectName);
    logger.debug("attribute --------->" + stringParam[1]);
    logger.debug("Value --------->" + stringParam[2]);

    WebDriver driver = BrowserInitialize.getWebDriverInstance();

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    wait.until(ExpectedConditions.attributeContains(objectName, stringParam[1], stringParam[2]));

    waitstatus = true;
    return "pass";
  }

  /**
   * This method is used to wait for angular request completed.
   * @return the status as "pass" if wait for angular request complete
   */
  public String angularWait() {
    NgWebDriver ngwebdriver = BrowserInitialize.getNgWebDriverInstance();
    ngwebdriver.waitForAngularRequestsToFinish();
    return "pass";
  }

  /**
   * This method is used to wait until required element is present in DOM.
   * @param stringParam Object
   * @return
   */
  public String waitForElementPresent(String stringParam) {

    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By objectName = gm.byType(stringParam); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Text to be present " + objectName);
    wait.until(ExpectedConditions.presenceOfElementLocated(objectName));

    waitstatus = true;
    return "pass";

  }

  /**
   * This method is used to wait for Element is not present.
   * @param stringParam contain the objectName
   * @return
   */
  public String waitForElementInvisible(String stringParam) {

    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By objectName = gm.byType(stringParam); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element invisible " + objectName);
    wait.until(ExpectedConditions.invisibilityOfElementLocated(objectName));

    waitstatus = true;
    return "pass";

  }

  /**
   *   
   * @param stringParam
   * @return
   */
  public String waitForElementAttributeNotPresent(String[] stringParam) {

    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By objectName = gm.byType(stringParam[0]); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
                    .pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element attribute not present " + objectName);
    wait.until(CustomWait.attributedNotPresent(objectName, stringParam[1]));

    waitstatus = true;
    return "pass";

  }


}
