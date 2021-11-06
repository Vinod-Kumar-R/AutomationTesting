package com.automation.webdriver;

import com.automation.configuration.PropertiesValue;
import com.automation.utility.ExtentReport;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class is used to configuration Browser related data.
 * @author Vinod Kumar R
 *
 */
public final class BrowserInitialize {

  private static Logger logger = LogManager.getLogger(BrowserInitialize.class);
  private WebDriver driver;
  private NgWebDriver ngwebdriver;
  private JavascriptExecutor jsDriver;
  private WebDriverManager webDriverManager;
  @Autowired
  private  ExtentReport extentreport;
  @Autowired
  private PropertiesValue properties;
  @Autowired
  private Desired desired;


  /**
   * This method is used to created the Web driver instance.
   * @param browserType contain which browser need to open for execution
   */
  private void createInstance(String browserType) {

    BrowserExecutionType bt = BrowserExecutionType.valueOf(browserType);
    DriverManagerType driverManagerType = DriverManagerType.valueOf(bt.binaryBrower.toUpperCase());
    
    webDriverManager = WebDriverManager.getInstance(driverManagerType);
    
    //checking the condition whether browser driver for system or Docker.
    if (properties.isDocker()) {
      webDriverManager.browserInDocker();
      webDriverManager.enableVnc();
      webDriverManager.browserVersion("latest");
      webDriverManager.dockerTimezone("UTC+05:30");
      webDriverManager.config().setUseBetaVersions(false);      
    }

    switch (bt) {

      case CHROME:
        driver = webDriverManager.capabilities(desired.chromeDesired()).create();
        break;

      case FIREFOX:
        driver = webDriverManager.capabilities(desired.firefoxDesired()).create();
        break;

      case OPERA:
        driver = webDriverManager.capabilities(desired.operaDesired()).create();
        break;

      case EDGE:
        driver = webDriverManager.capabilities(desired.edgeDesired()).create();
        break;

      case IEXPLORER:
        driver = webDriverManager.capabilities(desired.internetExploreDesired()).create();
        break;

      case SAFARI:
        driver = webDriverManager.capabilities(desired.safariDesired()).create();
        break;

      case ANDROID_CHROME :

        try {
          driver = new AppiumDriver<WebElement>(new URL(properties.getAppiumUrl()), 
                          desired.androidDesired());
        } catch (MalformedURLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        break;

      case IOS_SAFARI:
        break;

      case MOBILE_EMULATION:
        driver = webDriverManager.capabilities(desired.mobileSystembrowser()).create();
        break;

      case BROWSER_STACK:
        try {
          driver = new RemoteWebDriver(new URL(properties.getBrowserStackUrl()), 
                          desired.browserStack());
          driver.manage().window().maximize();
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          logger.error(e.getMessage());
        }

        break;

      default : 
        logger.debug("invalid browser selected");
        break;
    }
    
    //print the port number used by the docker for VNC
    if (properties.isDocker()) {
      logger.info("Docker URL :- " + webDriverManager.getDockerNoVncUrl(driver));
    }

    jsDriver = (JavascriptExecutor) driver;
    ngwebdriver = new NgWebDriver(jsDriver);
  }

  /**
   * this method is used to reuses the same instance of Web driver.
   * @return WebDriver instance
   */
  
  public WebDriver getWebDriverInstance() {
    if (driver == null) {
      createInstance(properties.getTestBrowser());
    }

    return driver;
  }
  
  /**
   * This method is used to override the Browser type during execution time.
   * @param browserType contain which browser has to executed
   */
  public void setWebDriverInstance(String browserType) {
    if (driver != null) {
      quitBrowser();
    }
    if (driver == null) {
      createInstance(browserType);
    }
  }
  
  /**
   * This method is used to set the DriverInstance.
   * @param browserType which browser need to set for current execution
   */
  public void setSwitchDriverInstance(String browserType) {
    if (driver != null) {
      driver = null;
      createInstance(browserType);
     
    }
  }
  
  /**
   * This method is used to set the DriverInstance.
   * @param driver is of Type EventFiringWebDriver
   */
  public void setDriverInstance(WebDriver driver) {
    this.driver = driver;
    
  }
  
  /**
   * This method is used to close the current Browser.
   */
  public void closeBrowser() {
    driver.close();
  }
  
  /**
   * this method is used to ngwebdriver instance. 
   * @return ngWebDriver instance
   */
  public NgWebDriver getNgWebDriverInstance() {
    if (driver == null) {
      createInstance(properties.getTestBrowser());
    }
    jsDriver = (JavascriptExecutor) driver;
    ngwebdriver = new NgWebDriver(jsDriver);
    return ngwebdriver;
  }

  /**
   * This method is used to quit the browser instance. 
   * @return status as "Pass" if execution completed 
   */
  public String quitBrowser() {

    driver.quit();
    driver = null;
    ngwebdriver = null;
    return "pass";
  }

  /**
   * this method is used the extend report for updating the browser instance used for testing.
   */
  public void browserInfo() {
    Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
    extentreport.setSystemInfo("Browser Name", caps.getBrowserName());
    extentreport.setSystemInfo("Browser Version", caps.getBrowserVersion());
    extentreport.setSystemInfo("Platform", caps.getPlatformName().name());
    extentreport.setSystemInfo("OS Version number", 
                    caps.getPlatformName().family().name());    
  }

}
