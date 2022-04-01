package com.automation.utility;

import com.automation.configuration.ApplicationStoreValue;
import com.automation.configuration.ConstantVariable;
import com.automation.configuration.PropertiesValue;
import com.automation.webdriver.BrowserInitialize;
import com.automation.webelement.custom.CreateQuestionnaire;
import com.automation.webelement.custom.Levels;
import com.automation.webelement.custom.MatOptions;
import com.automation.webelement.custom.MatTable;
import com.automation.webelement.custom.TabList;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.paulhammant.ngwebdriver.ByAngular;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.locators.RelativeLocator.RelativeBy;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * This class contain the Generic method which is used during Automation execution Script. 
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class GenericMethod {

  @Autowired
  private ApplicationStoreValue storevalue;
  @Autowired
  private BrowserInitialize browserinitialize; 
  @Autowired
  private PropertiesValue properties;
  @Autowired
  @Lazy
  private WaitMethod waitmethod;
  @Autowired
  @Lazy
  private CreateQuestionnaire questinnaire;
  @Autowired
  @Lazy
  private MatOptions options;
  @Autowired
  @Lazy
  private MatTable mattable;
  @Autowired
  @Lazy
  private Levels levels;
  @Autowired
  private ConstantVariable constantVariable;
 
  /**
   * This method is used to verify the text present for an WebElement.
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to compare the text  in html page
   *     dataParam[1] contain the expected text when need to compare with text on html page 
   * @return it return the status "pass" if execution success else throw an exception 
   */
  public String verifyText(List<String> dataParam)  {
    log.debug("Verifying the text-------> " + dataParam.get(1));
    WebElement element = getElement(dataParam.get(0));
    if (element.getText().equalsIgnoreCase(dataParam.get(1))) {
      return "pass";
    }
    return "fail";
  }

  
  /*
   * changing the image from path location to base 64 for that reason comment below code
   */
  /**
   * This Method is used to take an WebBrowser Screenshot.
   * @return the file location in the String format
   */
   
  public  String takeScreenshot() {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    long filename = System.currentTimeMillis();
    if (driver instanceof TakesScreenshot) {
      File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      try {
        FileUtils.copyFile(tempFile, new File(ConstantVariable.screenShotlocation 
                        + File.separator + filename + ".png"));
      } catch (IOException e) {
        log.error(e.getStackTrace());
      }
    }
    String absolutePath = ConstantVariable.screenShotlocation +  File.separator
                    + filename + ".png";
    log.debug("absolutepath " + absolutePath);
    
    //Block of code is comment because in KLOV community report required the absolute path rather 
    // then relative path 
    // relative path is used only for attach the extent repor to email 
    // as of now report are not attach to email so comment out the below code
    // 
    
    String relativePath = new File(ConstantVariable.resultLocation).toURI().relativize(
                    new File(absolutePath).toURI()).getPath();
    log.debug(" relative path " + relativePath);
    return ".." + File.separator + relativePath;
    
    //uncomment the below comment when relativePath is commented
    //return absolutePath;
  }


  /**
   * Method is used to take screenshot in Base64 format.
   * this can be used when KLOV community support Base64.  
   * @return string in base 64 format
   */
  
  public  String takeScreenshot64() {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    String tempFile = null;
    if (driver instanceof TakesScreenshot) {
      tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }
    return tempFile;
  }

  /**
   * This Method is used to verify the Attributed Value contain.
   * @param dataParam
   *     dataParam is a array of String variable which hold data 
   *     dataParam[0] contain the Object which need to compare the text  for an attribute value
   *     dataParam[1] contain the expected text when need to compare with text with 
   *     attribute value  
   */
  public void verifyAttributedValue(List<String> dataParam)  {

    WebElement objectData = getElement(dataParam.get(0));
    
    Assertions
      .assertThat(objectData.getAttribute(dataParam.get(1)))
      .as("Both the attribute value are not equal")
        .isEqualToIgnoringCase(dataParam.get(2));
  }

  /**
   * This method is used to select the option from drop down list.
   * @param dataParam contain the WebElement of Drop down and text to be form option
   */
  public void selectByVisibleText(List<String> dataParam) {
    WebElement objectData = getElement(dataParam.get(0));
    log.debug("Selecting the text from drop down list " + dataParam.get(1));

    Select select = new Select(objectData);
    select.selectByVisibleText(dataParam.get(1));

  }
  
  /**
   * This method is used to override test execution script browser.
   * @param dataParam contain which browser need to switch
   */
  public void browsertype(String dataParam) {
    
    if (dataParam.equalsIgnoreCase("config")) {
      browserinitialize.setWebDriverInstance(properties.getTestBrowser());
    } else {
      browserinitialize.setWebDriverInstance(dataParam);
    }
  }
  
  /**
   * This method is used to stored the current window information
   * and switch to newly created window of type browser.
   * @param dataParam contain the browser info
   */
  public void browserSwtich(List<String> dataParam) {
    
    currentDriver(dataParam.get(0));
    browserinitialize.setSwitchDriverInstance(dataParam.get(1));
  }
  
  /**
   * This method is used to store the current Driver instance.
   * @param key contain the name in which it has to store.
   */
  public void currentDriver(String key) {
    WebDriver driver =  browserinitialize.getWebDriverInstance();
    storevalue.driverinstance.put(key, driver);
 
  }
  
  /**
   * This method is used to switch between Driver instance.
   * @param key contain the name of instance key to which it has to set
   */
  public void switchDriver(String key) {
    WebDriver driver = storevalue.driverinstance.get(key);
    log.debug("dirver instance restore " + driver);
    browserinitialize.setDriverInstance(driver);
  }
  
  /**
   * This method is used to close the current browser.
   */
  public void browserClose() {
    browserinitialize.closeBrowser();
  }

  /**
   * This method is used to scroll until WebElement is view in web page.
   * @param element is a WebElement of web page
   */
  public void scrolltoelementTop(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();

    JavascriptExecutor je = (JavascriptExecutor) driver;
    je.executeScript("arguments[0].scrollIntoView(true);", element);
   
  }
  
  /**
   * This method is used to scroll until WebElement is view in web page.
   * @param element is a WebElement of web page
   */
  public void scrolltoelementBottom(WebElement element) {
    WebDriver driver = browserinitialize.getWebDriverInstance();

    JavascriptExecutor je = (JavascriptExecutor) driver;
    je.executeScript("arguments[0].scrollIntoView(false);", element);
  }
  
  /**
   * this method is used to scroll browser in y axis with specific.
   * negative value is used to scroll up and positive value is used scroll down
   * @param element is WebElement of element in which it has to scroll
   * @param offset is a int value in which it has to scroll.
   */
  public void scrollToElementOffsetYaxix(WebElement element, int offset) {

    WebDriver driver = browserinitialize.getWebDriverInstance();

    JavascriptExecutor je = (JavascriptExecutor) driver;

    je.executeScript("window.scrollTo(" + element.getLocation().getX() + "," 
                    + (element.getLocation().getY() + offset) + ");");
  }
  
  /**
   * This method is used to check browser is mobile view or Desktop view.
   * @return true if it mobile view else false
   */
  public boolean isMobileview() {

    WebDriver driver = browserinitialize.getWebDriverInstance();

    Dimension dimension = driver.manage().window().getSize();

    if (dimension.getHeight() <= 747 && dimension.getWidth() <= 1080) {
      return true;
    }

    return false;

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
   * This method accept the RelativeBy Class type and return webElement.
   * @param relativeBy contain the DOM location 
   * @return WebElement
   */
  public WebElement getElement(RelativeBy relativeBy) {
    WebDriver driver = browserinitialize.getWebDriverInstance();
    WebElement element = driver.findElement(relativeBy);
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
   * This method is used to get the RelativeLocatos, so that it can be use other 
   * function such as below, above, leftof, Rightof.
   * @param objectName is the nearest object 
   * @return RelativeBy
   */
  public RelativeBy getRelativeLocators(String objectName) {
    
    By by = getBy(objectName);
    return RelativeLocator.with(by);
    
  }
  
  /**
   * This method is used to get the object in the form of By.
   * @param objectName is String
   * @return ByElement
   */
  public By getBy(String objectName) {
    
    By byElement = byType(objectName);
    return byElement;
   
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
    
    //selenum 4 provde the way to create a tab in browser
    driver.switchTo().newWindow(WindowType.TAB);
    
    /*
    String a = "window.open('about:blank','_blank');";
    ((JavascriptExecutor ) driver). executeScript(a);
    */
    
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
    
    log.debug("Switch to frame " + element);
    driver.switchTo().frame(element);
  }
  
  /**
   * This method is used to switch to parent frame.
   */
  public void switchframedefault() {
    WebDriver driver = browserinitialize.getWebDriverInstance();

    log.debug("Switch to parent frame ");
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
   * This method is used to click on the hyperlink.
   * @param dataParam contain the hyperlink text with a tag
   */
  public void goToLink(String dataParam) {
    
    log.debug("waite for overlay backdrop invisiable");
    waitmethod.waitForElementInvisible("overlaybackdrop");
    
    WebElement element = getElement(dataParam);
    element.click();
  }
  
  /**
   * This method is used to select the single option from dropdown.
   * @param element WebElement
   * @param textSelect text to select
   */
  public void matOption(WebElement element, String textSelect) {
    
    options.setOptions(element);
    log.debug("Selecting the options from dropdown " + textSelect);
    options.selectVisibleText(textSelect);
  }
  
  /**
   * This method is used to select the multiple option from dropdown.
   * @param element WebElement
   * @param dataParam contain the list of data which need to select
   */
  public void matOptions(WebElement element, List<String> dataParam) {
    
    options.setOptions(element);
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
   // calendar.setCalendar(element);
   // calendar.selectDate(date, month, year);
  }
  
  /**
   * This method is used to select the data in the mat table.
   * @param element is WebElement
   * @param data is used to click on the cell
   */
  public void matTable(WebElement element, String data) {
    mattable.setTable(element);
    mattable.selectdata(data);
  }
  
  /*
  public void matTableRefresh(WebElement element) {
    mattable.setTable(element);
    WebElement firstrow =  mattable.waitMatTable();
    waitmethod.waitForElementAttributeNotPresent(firstrow, "style");
    //waitmethod.waitForStalenessElement(element);
  }
 */ 
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
   * This method is used to create a New Questionnaire in Admin. 
   * @param element contain the questionnaire Webelement
   * @param selectText contain which quuestinnaire need to select from dropdown
   */
  public void newQuestionnaire(WebElement element, String selectText) {
    questinnaire.setElement(element);
    questinnaire.selectquestionnaries();
    WebElement listdata = getElement("competition_questionnaries_list_data");
    waitmethod.waitForElementVisible(listdata);
    matOption(listdata, selectText);
  }
  
  /**
   * This method used to create new Questionnaries row.
   * @param element is a webElemnt of base element.
   */
  public void createNewQuesetionnariesRow(WebElement element) {
    questinnaire.setElement(element);
    questinnaire.createNewQuestionnaries();
  }
  
  /**
   * this method is used to save the questionnaries.
   * @param element is a webelement of base element
   */
  public void saveQuestionnaries(WebElement element) {
    questinnaire.setElement(element);
    questinnaire.saveQuestionnaries();
  }
  
  /**
   * this method used to delete the questionnaires. 
   * @param element contian the base element
   * @param deleteText questionnaires text which need to deleted
   */
  public void deleteQuestionnariesRow(WebElement element, String deleteText) {
    questinnaire.setElement(element);
    questinnaire.deleteQuestionnaries(deleteText);
  }
  
  /**
   * This method used to create new row in levels.
   * @param element is a webelement.
   */
  public void createNewLevelsRow(WebElement element) {
    levels.setElement(element);
    levels.createLevels();
  }
  
  /**
   * This method used to select the questionnaires in levels.
   * @param element is the base Webelement
   * @param selectText questionnaires text need to select 
   */
  public void selectLevelsQuestionnaries(WebElement element, String selectText) {
    levels.setElement(element);
    levels.selectQuestionnairesLevel();
    WebElement listdata = getElement("competition_questionnaries_list_data");
    waitmethod.waitForElementVisible(listdata);
    matOption(listdata, selectText);
    
  }

  /**
   * This method used to refresh current page.
   */
  public void refreshPage() {

    WebDriver driver = browserinitialize.getWebDriverInstance();
    driver.navigate().refresh();
  }
  
  /**
   * This method used to save level in admin.
   * @param element is the base element.
   */
  public void levelSave(WebElement element) {
    
    levels.setElement(element);
    levels.saveLevels();
  }
  
  /**
   * Method is used to convert the Json to particular JAVA object.
   * @param json : Json Data
   * @param clazz is the generic Type to which it need to convert to JAVA object.
   * @param <T> is used for generic
   * @return generic Type of Class T
   */
  public <T> T fromJson(String json, Class<T> clazz) {
    return new Gson().fromJson(json, clazz);
  }
  
  /**
   * Method is used to convert the Json Array to particular JAVA object.
   * @param json , Json Data 
   * @param clazz is the generic Type to which it need to convert to JAVA object.
   * @param <T> is used for generic
   * @return generic Type of Class T
   */
  public <T> List<T> fromJsonAsList(String json, Class<T[]> clazz) {
    return Arrays.asList(new Gson().fromJson(json, clazz));
  }
  
  /**
   * Method is used to convert JAVA object to Json file.
   * @param object is a java object  to which it has to convert.
   * @param filePath complete file location in which it has to write.
   */
  public void toJsonFile(Object object, String filePath) {
    Gson gson = new Gson();
    
    try {
      Writer writer = new FileWriter(filePath);
      gson.toJson(object, writer);
      writer.flush();
      writer.close();
    } catch (JsonIOException | IOException e) {
      log.error(e.getMessage());
    }
  }
    
  /**
   * This method is used to zip a folder.
   * @param sourcNoteseDirPath  is the source path folder to which it has to zip
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
              log.error(e);
            }
          });
    }

    log.debug("Zip is created at : " + zipFile);
    
    return zipFile;
  }
  
  /**
   * Method used to zip a single file.
   * @param zippath is location where the zip file to store
   * @param filetozip  is file which need to zip
   * @return the zip file location.
   * @throws IOException  IO Operation
   */
  public File zipFile(String zippath, Path filetozip) throws IOException {
    Path zipFile = Files.createFile(Paths.get(zippath));
    
    ZipEntry zipEntry = new ZipEntry(filetozip.getFileName().toString());
    ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
    zipOutputStream.putNextEntry(zipEntry);
    Files.copy(filetozip, zipOutputStream);
    zipOutputStream.close();

    return zipFile.toFile();
  }
  
  /**
   * Method used to copy the file form InputStream to file.
   * @param dowloadedFile file in InputStream.
   * @param filename name of the file 
   */
  public void copyfile(InputStream dowloadedFile, String filename) {
    try {
      BufferedInputStream inputStream = new BufferedInputStream(dowloadedFile);
      FileOutputStream fileOs = new FileOutputStream(filename); 
      byte[] data = new byte[1024];
      int byteContent;
      while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
        fileOs.write(data, 0, byteContent);
      }
      fileOs.close();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }
  
  /**
   * Method is used to convert the local host to IP Address.
   * @param url address
   * @return IP address. 
   */
  public String ipaddress(URL url) {
    
    InetAddress ipaddress;
    String address = null;
    try {
      ipaddress = InetAddress.getLocalHost();
      address = url.toExternalForm();
      address = address.replaceAll("localhost", ipaddress.getHostAddress());
      
    } catch (UnknownHostException e) {
      log.error(e.getMessage());
    }
    
    return address;
  }
  
  /**
   * This method is used for generic way of getting By class type
   * i.e. id, xpath,css,name etc
   * @param object contain the Object key
   * @return the By type
   */
  private By byType(String object) {

    List<String> data = constantVariable.getObject(object);

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
      case exactrepeater: {
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
      default:
        log.info("Invalid By Class");
    }
    return byElement;
  }
}
