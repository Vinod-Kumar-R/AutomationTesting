package com.encash.offers.utility;

import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.custom.wait.CustomWait;
import com.encash.offers.webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;


public class WaitMethod {
  private static Logger logger = LogManager.getLogger(WaitMethod.class.getName());
  public static boolean waitstatus = true;
  @Autowired
  private GenericMethod genericMethod;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private PropertiesValue properties;
 
  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to wait in html page 
   * @return it return the status "pass" if execution success else fail
   *
   */
  public  String waitForElementVisible(String dataParam) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(dataParam);
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class)
                    .withMessage("time out message");

    logger.debug("Waiting for the Element Visibility " + objectName);
    wait.until(ExpectedConditions.visibilityOfElementLocated(objectName));

    waitstatus = true;
    return "pass";
  }
  
  /**
   * This Method is used to Wait for an Element Visible in an web page.  
   * @param element is a WeblElement waiting for visible
   * @return it return the status "pass" if execution success else fail
   *
   */
  public  String waitForElementVisible(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the WebElement Visibility " + element);
    wait.until(ExpectedConditions.visibilityOf(element));

    waitstatus = true;
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
    WebDriver driver = browserinitialize.getWebDriverInstance();
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for All WebElement Visibility " + elements);
    wait.until(ExpectedConditions.visibilityOfAllElements(elements));

    waitstatus = true;
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
    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(dataParam.get(0)); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Text to be present " + objectName);
    wait.until(ExpectedConditions.textToBePresentInElementLocated(objectName, dataParam.get(1)));

    waitstatus = true;
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
    By objectName = genericMethod.byType(dataParam.get(0));
    waitstatus = false;

    logger.debug("Waiting for the attributed presnt and value " + objectName);
    logger.debug("attribute --------->" + dataParam.get(1));
    logger.debug("Value --------->" + dataParam.get(2));

    WebDriver driver = browserinitialize.getWebDriverInstance();

    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    wait.until(ExpectedConditions.attributeContains(objectName, dataParam.get(1),
                    dataParam.get(2)));

    waitstatus = true;
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
   * @param dataParam contain Object location in DOM
   * <br> dataParam[0] contain the object Name which need to wait
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementPresent(String dataParam) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(dataParam); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the element to be present " + objectName);
    wait.until(ExpectedConditions.presenceOfElementLocated(objectName));

    waitstatus = true;
    return "pass";

  }
  


  /**
   * This method is used to wait for Element is not present.
   * @param dataParam contain the objectName which should not be present in DOM 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementInvisible(String dataParam) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(dataParam); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element invisible " + objectName);
    wait.until(ExpectedConditions.invisibilityOfElementLocated(objectName));

    waitstatus = true;
    return "pass";

  }

  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param locator is of type string
   * @param attributeName is of type string
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributeNotPresent(String locator, String attributeName) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(locator); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element attribute not present " + objectName);
    wait.until(CustomWait.attributedNotPresent(objectName, attributeName));

    waitstatus = true;
    return "pass";

  }
  
  

  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param attributeName is of type string
   * @param element this contain the web element
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributeNotPresent(WebElement element, String attributeName) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
     
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element attribute not present " + element);
    wait.until(CustomWait.attributedNotPresent(element, attributeName));

    waitstatus = true;
    return "pass";

  }
  
  /**
   * This method is used to wait for attributed not present in particular DOM.
   * @param element this contain WebElement
   * @param attributeName is of type string
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementAttributePresent(WebElement element, String attributeName) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
     
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element attribute not present " + element);
    wait.until(CustomWait.attributedPresent(element, attributeName));

    waitstatus = true;
    return "pass";

  }
  
  /**
   * This method is used to wait until element is click able.
   * @param dataParam
   *     dataParam[0] contain element location 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementClickable(String dataParam) {
    
    WebDriver driver = browserinitialize.getWebDriverInstance();
    By objectName = genericMethod.byType(dataParam); 
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting element clickable " + objectName);
    wait.until(ExpectedConditions.elementToBeClickable(objectName));

    waitstatus = true;
    
    return "pass";
  }
  
  /**
   * This method is used to wait until element is click able.
   * @param element
   *     dataParam[0] contain element location 
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForElementClickable(WebElement element) {
    
    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for element to be clickable " + element);
    wait.until(ExpectedConditions.elementToBeClickable(element));

    waitstatus = true;
    
    return "pass";
  }
  
  
  /**
   * This method is used to wait until required some text  present in locator.
   * @param element is WebElement in which text has to be there
   * @return it return the status "pass" if execution success else fail
   */
  public String waitForSomeTextPresent(WebElement element) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
     
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Text to be present " + element);
    wait.until(CustomWait.someTextPresent(element));
    

    waitstatus = true;
    return "pass";

  }
  
  /**
   * This method is used to wait for alert to pop up. 
   * @return after waiting for alert success return pass
   */
  public String waitForAlertPresent() {

    WebDriver driver = browserinitialize.getWebDriverInstance();
     
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Alert present");
    wait.until(ExpectedConditions.alertIsPresent());
   
    waitstatus = true;
    return "pass";

  }
  
  /**
   * This method is used to wait element not present in the DOM.
   * @param element is WebElement
   * @return pass if executed success
   */
  public String waitForStalenessElement(WebElement element) {

    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the staleness element");
    wait.until(ExpectedConditions.stalenessOf(element));
   
    waitstatus = true;
    return "pass";
    
  }
  
  
  /**
   * This method is used to wait for notification disappear.
   * @param element is the webElement
   * @return "pass" if executed success
   */
  public String waitForNotificationDisAppear(WebElement element) {
    
    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Notification disappear " + element);
    wait.until(CustomWait.notification(element));
    
    waitstatus = true;
    return "pass";
  }
  

  /**
   * This method is used to wait for notification appear.
   * @param element is the webElement
   * @return "pass" if executed success
   */
  public String waitForNotificationAppear(WebElement element) {

    WebDriver driver = browserinitialize.getWebDriverInstance();

    waitstatus = false;
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
                    .withTimeout(Duration.ofMinutes(properties.getExplictwait()))   
                    .pollingEvery(Duration.ofSeconds(properties.getPolling()))   
                    .ignoring(NoSuchElementException.class);

    logger.debug("Waiting for the Notification appear " + element);
    wait.until(CustomWait.notificationappear(element));

    waitstatus = true;
    return "pass";
  }
  
  
  public String waitForimplicitWait() {
    
    WebDriver driver = browserinitialize.getWebDriverInstance();
    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    return "pass";
  }
  
  public void waitThread() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}
