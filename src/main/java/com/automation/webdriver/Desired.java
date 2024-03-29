package com.automation.webdriver;

import com.automation.configuration.ConfigurationReader;
import com.automation.configuration.PropertiesValue;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
//import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is related to Desired capability of each browser.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class Desired {

  @Autowired
  private PropertiesValue properties;
  private String headless = "headless";

  /**
   * This Method is used to configured the Chrome Options before execution.
   * @return the choreOptions setting for Chrome borwser
   */
  public ChromeOptions chromeDesired() {

    ChromeOptions chrome = new ChromeOptions();

    chrome.setHeadless(properties.getHeadlessBrowser());
    chrome.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    chrome.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
    //chrome.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
    chrome.addExtensions(new File(properties.getChromeextension()));
    
    //chrome.add_argument("--enable-javascript");
    if (properties.getHeadlessBrowser()) {
      chrome.addArguments("--window-size=1280,800");
    }
    chrome.addArguments("--start-maximized");
    chrome.addArguments("enable-javascript");
    //chrome.addArguments("--disable-notifications");

    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("profile.default_content_setting_values.notifications", 2);

    chrome.setExperimentalOption("prefs", prefs);
    return chrome;
  }

  /**
   * This method is used configured the Firefox options before executing firefox.
   * @return the firefoxOptions for Firefox browser
   */

  public FirefoxOptions firefoxDesired() {

    FirefoxOptions options = new FirefoxOptions();
    options.setHeadless(properties.getHeadlessBrowser());
    return options;
  }

  /**
   * This method is used to set the Desired Capabilities for Android Devices 
   * before execution of test.
   * @return the Desired Capabilities for Android devices
   */
  public  DesiredCapabilities androidDesired() {
    DesiredCapabilities dc = new DesiredCapabilities();

    ConfigurationReader cr = new ConfigurationReader();
    cr.readConfig(properties.getMobiledevices());

    Iterator<String> androidkeys = cr.getAllKeys();

    while (androidkeys.hasNext()) {
      String key = androidkeys.next();
      String value = cr.getConfigurationStringValue(key);
      dc.setCapability(key, value);
    }


    return dc;
  }

  /**
   * This method is used to set the desired capability for Mobile Chrome driver.
   * @return chromeOptions for Mobile Chrome driver
   */
  public  ChromeOptions mobileChromedriver() {
    ChromeOptions chromeOptions = new ChromeOptions();
    //chromeOptions.setExperimentalOption("androidActivity", "com.google.android.apps.chrome.Main");
    chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
    //chromeOptions.setExperimentalOption("androidDeviceSerial", "04895b1943876a3e");
    chromeOptions.setExperimentalOption("androidDeviceSerial", "emulator-5554");

    return chromeOptions;
  }

  /**
   * This method is used to set Opera capability.
   * @return the Operation Options for Opera browser
   */
  @Deprecated
  /**
  public OperaOptions operaDesired() {
    OperaOptions oo = new OperaOptions();
    oo.setCapability(headless, properties.getHeadlessBrowser());
    return oo;
  }
**/
  /**
   * This method is used to set Edge capability.
   * @return EdgeOptions for edge browser
   */
  public EdgeOptions edgeDesired() {

    EdgeOptions options = new EdgeOptions();
    options.setHeadless(properties.getHeadlessBrowser());
    options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    options.addArguments("--start-maximized");
    if (properties.getHeadlessBrowser()) {
      options.addArguments("--window-size=1280,800");
    }
    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("profile.default_content_setting_values.notifications", 2);

    options.setExperimentalOption("prefs", prefs);
    return options;

  }

  /**
   * This method is used set to Internet explore capability.
   * @return the InternetExploreOptions for Internet Explore Browser
   */
  public InternetExplorerOptions internetExploreDesired() {
    InternetExplorerOptions ieo = new InternetExplorerOptions();
    //ieo.setCapability(headless, ConstantVariable.HeadlessBrowser);

    return ieo;
  }

  /**
   * This method is used to set Safari capability.
   * @return the safariOptions for Safari browser
   */
  public SafariOptions safariDesired() {
    SafariOptions so = new SafariOptions();
    so.setCapability(headless, properties.getHeadlessBrowser());
    return so;
  }

  /**
   * This Method is used to set the system browser compatible to Mobile.
   * @return ChromeOptions
   */
  public ChromeOptions mobileSystembrowser() {
    ChromeOptions chromeOptions = new ChromeOptions();

    Map<String, String> mobileemulation = new HashMap<String, String>();
    mobileemulation.put("deviceName", "Nexus 5");
    chromeOptions.setExperimentalOption("mobileEmulation", mobileemulation);
    
    chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    chromeOptions.addExtensions(new File(properties.getChromeextension()));
    
    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("profile.default_content_setting_values.notifications", 2);

    chromeOptions.setExperimentalOption("prefs", prefs);
    return chromeOptions;
  }
  
  /**
   * This method is used to set the desired capabilities of Browser stack.
   * @return the DesiredCapabilities of browser stack
   */
  public DesiredCapabilities browserStack() {
    
    DesiredCapabilities dc = new DesiredCapabilities();

    ConfigurationReader cr = new ConfigurationReader();
    cr.readConfig(properties.getBrowserStack());

    Iterator<String> browserkeys = cr.getAllKeys();

    while (browserkeys.hasNext()) {
      String key = browserkeys.next();
      String value = cr.getConfigurationStringValue(key);
      log.debug("key " + key);
      log.debug("value " + value);
      dc.setCapability(key, value);
    }
    return dc;
  }

}
