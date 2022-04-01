package com.automation.webdriver;

import com.automation.configuration.PropertiesValue;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.appium.java_client.AppiumDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * This class is used to configuration Browser related data.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public final class BrowserInitialize {

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
  @Autowired
  @Lazy
  private GenericMethod genericMethod;


  /**
   * This method is used to created the Web driver instance.
   * @param browserType contain which browser need to open for execution
   */
  private void createInstance(String browserType) {

    BrowserExecutionType browserExecutionType = BrowserExecutionType.valueOf(browserType);
    DriverManagerType driverManagerType = DriverManagerType
                    .valueOf(browserExecutionType.binaryBrower.toUpperCase());
    webDriverManager = WebDriverManager.getInstance(driverManagerType).avoidTmpFolder();
    
    //checking the condition whether browser driver for system or Docker.
    if (properties.isDocker()) {
      webDriverManager.browserInDocker();
      webDriverManager.enableVnc();
      webDriverManager.browserVersion("latest");
      webDriverManager.dockerTimezone("UTC+05:30");
      webDriverManager.config().setUseBetaVersions(false);
      //check docker video recording enable 
      if (properties.isRecording()) {
        webDriverManager.enableRecording();
        webDriverManager.dockerRecordingOutput(properties.getResultfolder() 
                        + File.separator + "Recorder");
      }
    }

    switch (browserExecutionType) {

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

      case ANDROID_CHROME:

        try {
          driver = new AppiumDriver<WebElement>(new URL(properties.getAppiumUrl()), 
                          desired.androidDesired());
        } catch (MalformedURLException e) {
          log.error(e.getMessage());
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
          log.error(e.getMessage());
        }

        break;

      default: 
        log.debug("invalid browser selected");
        break;
    }
    
    Assertions
      .assertThat(driver)
      .as("WebDriver instance is null")
        .isNotNull();

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
    if (driver.getWindowHandles().size() > 1) {
      driver.close();
    }   
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
   */
  public void quitBrowser() {
    driver.quit();
    driver = null;
    ngwebdriver = null;
  }
  
  public WebDriverManager getWebDriverManager() {
    return webDriverManager;
  }

  /**
   * this method is used the extend report for updating the browser instance used for testing.
   */
  public void browserInfo() {
    Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
    extentreport.setSystemInfo("Browser Name", caps.getBrowserName());
    extentreport.setSystemInfo("Browser Version", caps.getBrowserVersion());
    extentreport.setSystemInfo("Platform", caps.getPlatformName().name());    
  }
  
  /**
   * This method is used for recording of browser execution.
   * @param newFilename is the test case id name
   * @param saveFile is boolean value, to decide save the file or no
   * @return file location of recording file.
   * @throws IOException IO Exception.
   */
  public Path browserRecording(String newFilename, boolean saveFile) throws IOException {

    //stop docker recording for successfully executed
    if (properties.isDocker() && properties.isRecording()) {
      webDriverManager.stopDockerRecording();
      Path recordingPath = webDriverManager.getDockerRecordingPath();
      Path destinationPath = new File(properties.getResultfolder() 
                      + File.separator + "Recorder" 
                      + File.separator + newFilename + ".mp4").toPath();
      if (saveFile) {
        FileUtils.moveFile(
                        FileUtils.getFile(recordingPath.toAbsolutePath().toString()),
                        FileUtils.getFile(destinationPath.toAbsolutePath().toString()));
        return destinationPath;
      } else {
        FileUtils.deleteQuietly(recordingPath.toFile());
      }
    }
    return null;
    
  }
  
  /**
   * Method is used to check the status of the browser instance is up, quite or close.
   * @return true if browser is quite or close else false
   */
  public boolean browserStatus() {
    if (driver == null) {
      return true;
    }
    return false;
  }
  
  /**
   * Method is used to print the Docker URL with port number.
   */
  public void dockerUrl() {
    //print the port number used by the docker for VNC
    if (properties.isDocker()) {
      URL vncUrl = webDriverManager.getDockerNoVncUrl(driver);
      if (vncUrl.getHost().equalsIgnoreCase("localhost")) {
        log.info("Docker VNC URL :- " + genericMethod.ipaddress(vncUrl));
      } else {
        log.info("Docker VNC URL :- " + vncUrl);
      }
    }
  }
}
