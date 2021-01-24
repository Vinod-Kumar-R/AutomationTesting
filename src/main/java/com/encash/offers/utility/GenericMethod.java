package com.encash.offers.utility;

import com.encash.offers.configuration.ApplicationStoreValue;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.webdriver.BrowserInitialize;
import com.encash.offers.webelement.custom.Calendar;
import com.encash.offers.webelement.custom.MatOptions;
import com.encash.offers.webelement.custom.MatTable;
import com.encash.offers.webelement.custom.TabList;
import com.paulhammant.ngwebdriver.ByAngular;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class contain the Generic method which is used during Automation execution Script. 
 * @author Vinod Kumar R
 *
 */
public class GenericMethod {
  static Logger logger = LogManager.getLogger(GenericMethod.class.getName());
  
  @Autowired
  private ApplicationStoreValue storevalue;
  @Autowired
  private Calendar calendar;
  @Autowired
  private BrowserInitialize browserinitialize; 
  @Autowired
  private PropertiesValue properties;
 

  /**
   * This is used to click on the WebElement on current html page.
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object i.e xpath 
   * @return it return the status "pass" if execution success else throw an exception 
   */
  public String click(String dataParam)  {
    WebElement element = getElement(dataParam);
    element.click();
    return "pass";
  }

  /**
   * This method is used to verify the text present for an WebElement.
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to compare the text  in html page
   *     dataParam[1] contain the expected text when need to compare with text on html page 
   * @return it return the status "pass" if execution success else throw an exception 
   */

  public String verifyText(List<String> dataParam)  {
    logger.debug("Verifying the text-------> " + dataParam.get(1));
    WebElement element = getElement(dataParam.get(0));
    if (element.getText().equalsIgnoreCase(dataParam.get(1))) {
      return "pass";
    }
    return "fail";
  }


  /**
   * This Method is used to take an WebBrowser Screenshot.
   * @return the file location in the String format
   */
  public  String takeScreenshot()   {
    WebDriver driver = browserinitialize.getWebDriverInstance();
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
    String relativePath = new File(ConstantVariable.ResultLocation).toURI().relativize(
                    new File(absolutePath).toURI()).getPath();
    logger.debug(" relative path " + relativePath);
    return ".." + File.separator + relativePath;
  }

  /**
   * This Method is used to verify the Attributed Value contain.
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to compare the text  for an attribute value
   *     dataParam[1] contain the expected text when need to compare with text with 
   *     attribute value 
   * @return it return the status "pass" if execution success else throw an exception 
   *
   */
  public String verifyAttributedValue(List<String> dataParam)  {

    WebElement objectData = getElement(dataParam.get(0));
    if (objectData.getAttribute(dataParam.get(1)).equalsIgnoreCase(dataParam.get(2))) {
      return "pass";
    } else {
      return "false";
    }
  }

  /**
   * This Method is used to enter the text in the text filed.
   * @param dataParam contain the text to enter in webpage <br>
   *     dataParam[0] contain the Object Name<br>
   *     dataParam[1] contain the text to enter.<br>
   * @return the status as "Pass" if entertext in web page
   */
  public String entertext(List<String> dataParam) {

    WebElement objectData = getElement(dataParam.get(0));
    objectData.sendKeys(dataParam.get(1));
    return "pass";
  }

  /**
   * This method is used to select the option from drop down list.
   * @param dataParam contain the WebElement of Drop down and text to be form option
   * @return the status as "Pass" if execution success else fail
   */
  public String selectByVisibleText(List<String> dataParam) {
    WebElement objectData = getElement(dataParam.get(0));
    logger.debug("Selecting the text from drop down list " + dataParam.get(1));

    Select select = new Select(objectData);
    select.selectByVisibleText(dataParam.get(1));

    return "pass";
  }
  /**
   * This method is used to override test execution script browser.
   * @param dataParam contain which browser need to switch
   * @return if success then return "pass" else "fail"
   */
  
  public String browsertype(String dataParam) {
    
    if (dataParam.equalsIgnoreCase("config")) {
      browserinitialize.setWebDriverInstance(properties.getTestBrowser());
    } else {
      browserinitialize.setWebDriverInstance(dataParam);
    }
    return "pass";
  }

  /**
   * This method is used to scroll until WebElement is view in web page.
   * @param element is a WebElement of web page
   * @return execution success then return pass
   */
  public String scrolltoelement(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();

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
    WebDriver driver = browserinitialize.getWebDriverInstance();
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
    WebDriver driver = browserinitialize.getWebDriverInstance();
    WebElement element = driver.findElement(byElement);
    return element;
  }

  /**
   * This Method is used to get the WebElements from an element.
   * @param object this contain the object value of web page
   * @return WebElement
   */
  public List<WebElement> getElements(String object) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
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
   * This method is used to stored the current window with key name. 
   * @param key is used to uniquely identified 
   */
  public void currentWindow(String key) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    String uniquename = driver.getWindowHandle();
    storevalue.windowHandle.put(key, uniquename);
 
  }
  
  /**
   * This method is used to switch between the window by providing the key name.
   * @param key is used to get the unique Window
   */
  public void switchWindow(String key) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    String uniquename = storevalue.windowHandle.get(key);
    driver.switchTo().window(uniquename);
  }
  
  /**
   * This method is used to created new blank tab.
   */
  public void newTab() {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    String a = "window.open('about:blank','_blank');";
    ((JavascriptExecutor ) driver). executeScript(a);
    
    Set<String> handles = driver.getWindowHandles();
    String currentWindowHandle = driver.getWindowHandle();
    for (String handle : handles) {
      if (!currentWindowHandle.equals(handle)) {
        driver.switchTo().window(handle);
      }
    }
    
  }
  
  /**
   * This method is used to switch to particular frame.
   * @param element contain the frame WebElement
   */
  public void switchframe(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    logger.debug("Switch to frame " + element);
    driver.switchTo().frame(element);
  }
  
  /**
   * This method is used to switch to parent frame.
   */
  public void switchframedefault() {
    WebDriver driver = browserinitialize.getWebDriverInstance();

    logger.debug("Switch to parent frame ");
    driver.switchTo().defaultContent();
  }
  
  /**
   * This method is used to mouse over  WebElement.
   * @param element is a WebElement
   */
  public void mouseover(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    
    Actions action = new Actions(driver);
    action.moveToElement(element).build().perform();
    
  }
  
  /**
   * This method is used to select the single option from dropdown.
   * @param element WebElement
   * @param textSelect text to select
   */
  public void matOption(WebElement element, String textSelect) {
    MatOptions options = new MatOptions(element);
    options.selectVisibleText(textSelect);
  }
  
  /**
   * This method is used to select the multiple option from dropdown.
   * @param element WebElement
   * @param dataParam contain the list of data which need to select
   */
  public void matOptions(WebElement element, List<String> dataParam) {
    MatOptions options = new MatOptions(element);
    options.multipleSelectText(dataParam);
  }
  
  /**
   * This Method is used to set the calendar. 
   * @param element WebElement
   * @param date Date in D
   * @param month in MMM i.e. JAN
   * @param year in YYYY i.e. 1986
   */
  public void dateSelection(WebElement element, String date, String month, String year) {
    calendar.setCalendar(element);
    calendar.selectDate(date, month, year);
  }
  
  /**
   * This method is used to select the data in the mat table.
   * @param element is WebElement
   * @param data is used to click on the cell
   */
  public void matTable(WebElement element, String data) {
    MatTable mattable = new MatTable(element);
    mattable.selectdata(data);
  }
  
  /**
   * This method is used to switch between tab.
   * @param element is WebElement
   * @param tabname is the name to which it has to swtich
   */
  public void tabselect(WebElement element, String tabname) {
    TabList tab = new TabList(element);
    tab.selectTab(tabname);
  }
  
  /**
   * This method is used to zip a folder.
   * @param sourcNoteseDirPath  is the source path folde to which it has to zip
   * @param zipFilePath  is the destination folder in which zip folder has to present
   * @return Path where zip file has been created.
   * @throws IOException  exception are throw when file not found exception
   */
  public Path zip(final String sourcNoteseDirPath, final String zipFilePath)
                  throws IOException {
    //delete the file if exist
    Files.deleteIfExists(Paths.get(zipFilePath));
    
    Path zipFile = Files.createFile(Paths.get(zipFilePath));

    Path sourceDirPath = Paths.get(sourcNoteseDirPath);
    try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
                    Stream<Path> paths = Files.walk(sourceDirPath)) {
      paths
      .filter(path -> !Files.isDirectory(path))
      .forEach(path -> {
        
        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
        try {
          zipOutputStream.putNextEntry(zipEntry);
          Files.copy(path, zipOutputStream);
          zipOutputStream.closeEntry();
        } catch (IOException e) {
          System.err.println(e);
        }
      });
    }

    logger.debug("Zip is created at : " + zipFile);
    
    return zipFile;
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
