package com.automation.utility;

import com.automation.custom.wait.CustomWait;
import com.automation.webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;
import java.time.Duration;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * In this class, all related to wait method are written.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class WaitMethod {
    
  @Autowired
  private GenericMethod genericMethod;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private GeneralWait generalwait;
 
  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else fail
   *
   */
  public  String waitForElementVisible(String dataParam) {

    By objectName = genericMethod.getBy(dataParam);
    Wait<WebDriver> wait = generalwait.fluenttimeoutwait();
    log.debug("Waiting for the Element Visibility " + objectName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(objectName));
    return "pass";
  }
  
  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param element is a WeblElement waiting for visible
   * @return it return the status "pass" if execution success else fail
   *
   */
  public  String waitForElementVisible(WebElement element) {
    Wait<WebDriver> wait = generalwait.fluentwait();
    log.debug("Waiting for the WebElement Visibility " + element);
    wait.until(ExpectedConditions.visibilityOf(element));
    return "pass";
  }
  
  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param elements
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else fail
   *
   */
  public  String waitForAllElementVisible(List<WebElement> elements) {

    Wait<WebDriver> wait = generalwait.fluentwait();   
    log.debug("Waiting for All WebElement Visibility " + elements);
    wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    return "pass";
  }

  /**
   * This Method is used to Wait for Text Visible in an web page.  
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else throw an exception
   * 
   */

  public  String waitForTexttVisible(List<String> dataParam)  {
    
    By objectName = genericMethod.getBy(dataParam.get(0)); 
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the Text to be present " + objectName);
    wait.until(ExpectedConditions.textToBePresentInElementLocated(objectName, dataParam.get(1)));
    return "pass";
  }

  /**
   * This method is used wait for an Attributed value present in html page. 
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to present in html page
   *     dataParam[1] contain the attribute in an xpath 
   *     dataParam[2] contain the vaule which need to be present for an attributed  
   * @return it return the status "pass" if execution success else throw an exception
   * 
   */

  public  String waitForAttributedContain(List<String> dataParam) {
    By objectName = genericMethod.getBy(dataParam.get(0));
    log.debug("Waiting for the attributed presnt and value " + objectName);
    log.debug("attribute --------->" + dataParam.get(1));
    log.debug("Value --------->" + dataParam.get(2));
    Wait<WebDriver> wait = generalwait.fluentwait();    
    wait.until(ExpectedConditions.attributeContains(objectName, dataParam.get(1),
                    dataParam.get(2)));
    return "pass";
  }

  /**
   * This method is used to wait for angular request completed.
   * @return the status as "pass" if wait for angular request complete
   */
  public String angularWait() {
    NgWebDriver ngwebdriver = browserinitialize.getNgWebDriverInstance();
    ngwebdriver.waitForAngularRequestsToFinish();
    return "pass";
  }

  /**
   * This method is used to wait until required element is present in DOM.
   * @param dataParam contain Object location in DOM <br> 
   dataParam[0] contain the object Name which need to wait
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementPresent(String dataParam) {

    By objectName = genericMethod.getBy(dataParam); 
    Wait<WebDriver> wait = generalwait.fluentwait();
    log.debug("Waiting for the element to be present " + objectName);
    wait.until(ExpectedConditions.presenceOfElementLocated(objectName));
    return "pass";
  }
 
  /**
   * This method is used to wait for Element is not present.
   * @param dataParam contain the objectName which should not be present in DOM 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementInvisible(String dataParam) {

    By objectName = genericMethod.getBy(dataParam); 
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting element invisible " + objectName);
    wait.until(ExpectedConditions.invisibilityOfElementLocated(objectName));
    return "pass";

  }

  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param locator is of type string
   * @param attributeName is of type string
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributeNotPresent(String locator, String attributeName) {

    By objectName = genericMethod.getBy(locator); 
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting element attribute not present " + objectName);
    wait.until(CustomWait.attributedNotPresent(objectName, attributeName));
    return "pass";
  }
  
  

  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param attributeName is of type string
   * @param element this contain the web element
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributeNotPresent(WebElement element, String attributeName) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting element attribute not present " + element);
    wait.until(CustomWait.attributedNotPresent(element, attributeName));
    return "pass";
  }
  
  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param element this contain WebElement
   * @param attributeName is of type string
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributePresent(WebElement element, String attributeName) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting element attribute not present " + element);
    wait.until(CustomWait.attributedPresent(element, attributeName));
    return "pass";
  }
  
  /**
   * This method is used to wait until element is click able.
   * @param dataParam
   *     dataParam[0] contain element location 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementClickable(String dataParam) {
    
    By objectName = genericMethod.getBy(dataParam); 
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting element clickable " + objectName);
    wait.until(ExpectedConditions.elementToBeClickable(objectName));
    return "pass";
  }
  
  /**
   * This method is used to wait until element is click able.
   * @param element
   *     dataParam[0] contain element location 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementClickable(WebElement element) {
    
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for element to be clickable " + element);
    wait.until(ExpectedConditions.elementToBeClickable(element));
    return "pass";
  }
  
  
  /**
   * This method is used to wait until required some text  present in locator.
   * @param element is WebElement in which text has to be there
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForSomeTextPresent(WebElement element) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the Text to be present " + element);
    wait.until(CustomWait.someTextPresent(element));
    return "pass";
  }
  
  /**
   * This method is used to wait for alert to pop up. 
   * @return after waiting for alert success return pass
   */
  public String waitForAlertPresent() {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the Alert present");
    wait.until(ExpectedConditions.alertIsPresent());
    return "pass";
  }
  
  /**
   * This method is used to wait element not present in the DOM.
   * @param element is WebElement
   * @return pass if executed success
   */
  public String waitForStalenessElement(WebElement element) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the staleness element");
    wait.until(ExpectedConditions.stalenessOf(element));
    return "pass";
  }
  
  
  /**
   * This method is used to wait for notification disappear.
   * @param element is the webElement
   * @return "pass" if executed success
   */
  public String waitForNotificationDisAppear(WebElement element) {
    
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the Notification disappear " + element);
    wait.until(CustomWait.notification(element));
    return "pass";
  }
  

  /**
   * This method is used to wait for notification appear.
   * @param element is the webElement
   * @return "pass" if executed success
   */
  public String waitForNotificationAppear(WebElement element) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the Notification appear " + element);
    wait.until(CustomWait.notificationappear(element));
    return "pass";
  }

  /**
   * This method is used to wait for all the child element are loaded by giving the parent By class.
   * @param parent is By method of parent element
   * @param childLocator is By method of Child element need to load 
   * @return "pass" if execution success
   */
  public String waitForNestedElementsPresence(By parent, By childLocator) {
    
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the child elements " + childLocator);
    wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(parent, childLocator));
    return "pass";
  }
  
  /**
   * This method is used to wait until the child element are loaded.
   * @param element is a WebElement of the parent element
   * @param childLocator is a By element for which it has to wait for presence
   * @return "pass" 
   */
  public String waitForNestedElementPresence(WebElement element, By childLocator) {
    
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the child elements " + childLocator);
    wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(element, childLocator));
    return "pass";
  }
  
  /**
   * This method is used wait for element which is less the specified element.
   * @param byObject is of type String
   * @param number element should be less than
   * @return "pass"
   */
  public String waitForElementLessThan(String byObject, int number) {

    By by = genericMethod.getBy(byObject);
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the locators elements " + by);
    wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, number));
    return "pass";
  }
  
  /**
   * This method is used wait for element which is less the specified element.
   * @param byObject is a By type
   * @param number is number of element should be less than
   * @return "pass"
   */
  public String waitForElementLessThan(By byObject, int number) {

    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the locators elements " + byObject);
    wait.until(ExpectedConditions.numberOfElementsToBeLessThan(byObject, number));
    return "pass";
  }
  
  /**
   * This method is used to wait for element should be equal to.
   * @param byObject is string object of type By
   * @param number is exact number element should present.
   * @return "pass"
   */
  public String waitForElementEqualTo(String byObject, int number) {

    By by = genericMethod.getBy(byObject);
    Wait<WebDriver> wait = generalwait.fluentwait();    
    log.debug("Waiting for the locators elements " + by);
    wait.until(ExpectedConditions.numberOfElementsToBe(by, number));
    return "pass";
  }
  
  /**
   * This is method is used to wait unit the mat row table are less than given number.
   * @param numberofRowLessThan is integer number, which contain the number row should be less than 
   * @return pass after completed execution.
   */
  public String waitForMatTableUpadate(int numberofRowLessThan) {
    
    By child = By.tagName("mat-row");
    waitForElementLessThan(child, numberofRowLessThan);
    return "pass";
  }

  /**
   * This method is used for implicityWait. 
   * @return "pass" on success
   */
  public String waitForimplicitWait() {
    
    WebDriver driver = browserinitialize.getWebDriverInstance();
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    return "pass";
  }
  
  /**
   * This method is used to wait for particular second. 
   * don't use this method more frequently as it will slow the execution speed
   */
  
  public void waitThread() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
  }
  
}
