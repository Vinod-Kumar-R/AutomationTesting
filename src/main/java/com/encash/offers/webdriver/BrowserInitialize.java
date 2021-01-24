package com.encash.offers.webdriver;


import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.JsWaiter;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class is used to configuration Browser related data.
 * @author Vinod Kumar R
 *
 */
public final class BrowserInitialize {


  private static Logger logger = LogManager.getLogger(BrowserInitialize.class);
  private static  WebDriver drivere;
  private static EventFiringWebDriver driver;
  private static  NgWebDriver ngwebdriver;
  private static JavascriptExecutor jsDriver;
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
    WebDriverManager.getInstance(driverManagerType).setup();
     

    switch (bt) {
      case CHROME:

        //System.setProperty("webdriver.chrome.logfile",ConstantVariable
        //.Configlocation+File.separator+"log"+File.separator+"chromelog.log");
        drivere = new ChromeDriver(desired.chromeDesired());
        break;

      case FIREFOX:

        drivere = new FirefoxDriver(desired.firefoxDesired());
        break;

      case OPERA:

        drivere = new OperaDriver(desired.operaDesired());
        break;

      case EDGE:

        drivere = new EdgeDriver(desired.edgeDesired());
        break;

      case IEXPLORER:

        drivere = new InternetExplorerDriver(desired.internetExploreDesired());
        break;

      case SAFARI:

        drivere = new SafariDriver(desired.safariDesired());
        break;

      case ANDROID_CHROME :
        break;
      case IOS_SAFARI:
        break;
      case MOBILE_EMULATION:
        drivere = new ChromeDriver(desired.mobileSystembrowser());
        break;

      default : 

        logger.debug("invalid browser selected");
        break;
    }


    driver = new EventFiringWebDriver(drivere);
    EventListener ei = new EventListener();
    driver.register(ei);

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
      JsWaiter.setDriver(driver);
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
      JsWaiter.setDriver(driver);
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
    extentreport.setSystemInfo("Browser Name", driver.getCapabilities().getBrowserName());
    extentreport.setSystemInfo("Browser Version", driver.getCapabilities().getVersion());
    extentreport.setSystemInfo("Platform", driver.getCapabilities().getPlatform().toString());
  }


}
