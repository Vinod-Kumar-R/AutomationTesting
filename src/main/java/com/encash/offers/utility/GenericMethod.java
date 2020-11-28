package com.encash.offers.utility;

import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.ByAngular;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


/**
 * This class contain the Generic method which is used during Automation execution Script. 
 * @author Vinod Kumar R
 *
 */
public class GenericMethod {
  static Logger logger = LogManager.getLogger(GenericMethod.class.getName());


  /**
   * This is used to click on the WebElement on current html page.
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object i.e xpath 
   * @return it return the status "pass" if execution success else throw an exception 
   */
  public String click(String stringParam)  {
    WebElement element = getElement(stringParam);
    element.click();
    return "pass";
  }

  /**
   * This method is used to verify the text present for an WebElement.
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object which need to compare the text  in html page
   *     StringParam[1] contain the expected text when need to compare with text on html page 
   * @return it return the status "pass" if execution success else throw an exception 
   */

  public String verifyText(String[] stringParam)  {
    logger.debug("Verifying the text-------> " + stringParam[1]);
    WebElement element = getElement(stringParam[0]);
    if (element.getText().equalsIgnoreCase(stringParam[1])) {
      return "pass";
    }
    return "fail";
  }


  /**
   * This Method is used to take an WebBrowser Screenshot.
   * @return the file location in the String format
   */
  public  String takeScreenshot()   {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    long filename = System.currentTimeMillis();
    if (driver instanceof TakesScreenshot) {
      File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      try {
        FileUtils.copyFile(tempFile, new File(ConstantVariable.ScreenShotlocation 
                        + File.separator + filename + ".png"));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        logger.error(e.getStackTrace());
      }
    }
    String absolutePath = ConstantVariable.ScreenShotlocation +  File.separator
                    + filename + ".png";
    logger.debug("absolutepath " + absolutePath);
    String relativePath = new File(ConstantVariable.ResultDatelocaton).toURI().relativize(
                    new File(absolutePath).toURI()).getPath();
    logger.debug(" relative path " + relativePath);
    return ".." + File.separator + relativePath;
  }

  /**
   * This Method is used to verify the Attributed Value contain.
   * @param stringParam
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object which need to compare the text  for an attribute value
   *     StringParam[1] contain the expected text when need to compare with text with 
   *     attribute value 
   * @return it return the status "pass" if execution success else throw an exception 
   *
   */
  public String verifyAttributedValue(String[] stringParam)  {

    WebElement objectData = getElement(stringParam[0]);
    if (objectData.getAttribute(stringParam[1]).equalsIgnoreCase(stringParam[2])) {
      return "pass";
    } else {
      return "false";
    }
  }

  /**
   * This Method is used to enter the text in the text filed.
   * @param stringParam contain the text to enter in webpage <br>
   *     stringParam[0] contain the Object Name<br>
   *     stringparam[1] contain the text to enter.<br>
   * @return the status as "Pass" if entertext in web page
   */
  public String entertext(String[] stringParam) {

    WebElement objectData = getElement(stringParam[0]);
    objectData.sendKeys(stringParam[1]);
    return "pass";
  }

  /**
   * This method is used to select the option from drop down list.
   * @param stringParam contain the WebElement of Drop down and text to be form option
   * @return the status as "Pass" if execution success else fail
   */
  public String selectByVisibleText(String[] stringParam) {
    WebElement objectData = getElement(stringParam[0]);
    logger.debug("Selecting the text from drop down list " + stringParam[1]);

    Select select = new Select(objectData);
    select.selectByVisibleText(stringParam[1]);

    return "pass";
  }
  /**
   * This method is used to override test execution script browser.
   * @param stringParam contain which browser need to switch
   * @return if success then return "pass" else "fail"
   */
  
  public String browsertype(String stringParam) {
    BrowserInitialize.setWebDriverInstance(stringParam);
    return "pass";
  }

  /**
   * This method is used to scroll until WebElement is view in web page.
   * @param element is a WebElement of web page
   * @return
   */
  public String scrolltoelement(WebElement element) {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();

    JavascriptExecutor je = (JavascriptExecutor) driver;
    je.executeScript("arguments[0].scrollIntoView(true);", element);

    return "pass";
  }


  /**
   * This Element is used to get the WebElement for and element.
   * @param objectName this contain the object value of web page
   * @return the webelement
   */
  public WebElement getElement(String objectName) {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By byElement = byType(objectName);
    WebElement element = driver.findElement(byElement);
    return element;
  }

  /**
   * This method accept the By element type and return webElement.
   * @param byElement contain the DOM location 
   * @return WebElement
   */
  public WebElement getElement(By byElement) {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    WebElement element = driver.findElement(byElement);
    return element;
  }

  /**
   * This Method is used to get the WebElements from an element.
   * @param object this contain the object value of web page
   * @return WebElement
   */
  public List<WebElement> getElements(String object) {
    WebDriver driver = BrowserInitialize.getWebDriverInstance();
    By byElement = byType(object);
    List<WebElement> element = driver.findElements(byElement);
    return element;
  }

  /**
   * This method is used to child WebElement.
   * @param element contain parent WebElement
   * @param object contain child WebElement location
   * @return WebElement
   */
  public WebElement getWebElement(WebElement element, String object) {
    By byElement = byType(object);
    return element.findElement(byElement);
  }

  /**
   * This method is used to get all the child WebElement list of parent Element.
   * @param element contain the parent WebElement
   * @param object contain the child WebElement location 
   * @return List&lt;WebElement&gt;
   */
  public List<WebElement> getWebElements(WebElement element, String object) {

    By byElement = byType(object);
    List<WebElement> elements = element.findElements(byElement);
    return elements;
  }


  /**
   * This method is used for generic way of getting By class type
   * i.e. id, xpath,css,name etc
   * @param object contain the Object key
   * @return the By type
   */
  public By byType(String object) {

    List<String> data = ConstantVariable.GetObject.get(object);

    //get the locator value type
    ByMethod locator = ByMethod.valueOf(data.get(0));
    String expression = data.get(1);

    By byElement = null;
    switch (locator) {
      case xpath: {
        byElement = By.xpath(expression);
        break;
      }
      case id: {
        byElement = By.id(expression);
        break;
      }
      case name: {
        byElement = By.name(expression);
        break;
      }
      case classname: {
        byElement = By.className(expression);
        break;
      }
      case linktext: {
        byElement = By.linkText(expression);
        break;
      }
      case paritallinktext: {
        byElement = By.partialLinkText(expression);
        break;
      }
      case tagname: {
        byElement = By.tagName(expression);
        break;
      }
      case angularbuttontext: {
        byElement = ByAngular.buttonText(expression);
        break;
      }
      case repeater: {
        byElement = ByAngular.repeater(expression);
        break;
      }
      case exactrepeater : {
        byElement = ByAngular.exactRepeater(expression);
        break;
      }
      case binding: {
        byElement = ByAngular.binding(expression);
        break;
      }
      case exactbinding: {
        byElement = ByAngular.exactBinding(expression);
        break;
      }
      case model: {
        byElement = ByAngular.model(expression);
        break;
      }
      case options: {
        byElement = ByAngular.options(expression);
        break;
      }
      case partialbuttontext: {
        byElement = ByAngular.partialButtonText(expression);
        break;
      }
      case csscontainingtext: {
        byElement = ByAngular.cssContainingText(expression, "searchText");
        break;
      }
      default :
        logger.info("Invalid By Class");
    }
    return byElement;

  }

}
