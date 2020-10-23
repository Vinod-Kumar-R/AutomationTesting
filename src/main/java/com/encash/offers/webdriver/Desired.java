package com.encash.offers.webdriver;

import com.encash.offers.configuration.ConfigurationReader;
import com.encash.offers.configuration.ConstantVariable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;


public class Desired {


  /**
   * This Method is used to configured the Chrome Options before execution.
   * @return
   */
  public ChromeOptions chromeDesired() {

    Map<String, String> mobileemulation = new HashMap<String, String>();

    ChromeOptions chrome = new ChromeOptions();
    
    /*
    if(!ConstantVariable.MobileEmulation.equalsIgnoreCase("null") 
    && !ConstantVariable.Environment.equalsIgnoreCase("WebBrowser")) {
        mobileemulation.put("deviceName", ConstantVariable.MobileEmulation);
        chrome.setExperimentalOption("mobileEmulation", mobileemulation);
       }
     */

    chrome.setHeadless(ConstantVariable.HeadlessBrowser);
    chrome.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
    //chrome.add_argument("--enable-javascript");
    chrome.addArguments("--start-maximized");
    chrome.addArguments("enable-javascript");
    /*
    chrome.addArguments("user-data-dir=C:\\Users\\HP\\AppData\\Local"
                    + "\\Google\\Chrome\\User Data\\Profile 1");
    chrome.addArguments("--profile-directory=Profile 2");
    */
    Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);

    chrome.setExperimentalOption("prefs", prefs);
    return chrome;
  }

  /**
   * This method is used configured the Firefox options before executing firefox.
   * @return
   */
  public FirefoxOptions firefoxDesired() {

    FirefoxOptions options = new FirefoxOptions();
    options.setHeadless(ConstantVariable.HeadlessBrowser);
    return options;
  }

  /**
   * This method is used to set the Desired Capabilities for Android Devices 
   * before execution of test.
   * @return
   */
  public  DesiredCapabilities androidDesired() {
    DesiredCapabilities dc = new DesiredCapabilities();

    if (ConstantVariable.Test_Execution.equalsIgnoreCase("ANDROID_CHROME")) {
      ConfigurationReader cr = new ConfigurationReader();
      cr.readConfig(ConstantVariable.DesiredAndroidCapability);

      Iterator<String> androidkeys = cr.getAllKeys();

      while (androidkeys.hasNext()) {
        String key = androidkeys.next();
        String value = cr.getConfigurationStringValue(key);
        dc.setCapability(key, value);
      }

    }
    return dc;
  }

  /**
   * This method is used to set the desired capability for Mobile Chrome driver.
   * @return
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
   * @return
   */
  public OperaOptions operaDesired() {
    OperaOptions oo = new OperaOptions();
    oo.setCapability("headless", ConstantVariable.HeadlessBrowser);
    return oo;
  }

  /**
   * This method is used to set Edge capability.
   * @return
   */
  public EdgeOptions edgeDesired() {
    DesiredCapabilities dc = new DesiredCapabilities();
    dc.setCapability("headless", ConstantVariable.HeadlessBrowser);

    EdgeOptions eo = new EdgeOptions();
    eo.merge(dc);

    return eo;

  }

  /**
   * This method is used set to Internet explore capability.
   * @return
   */
  public InternetExplorerOptions internetExploreDesired() {
    InternetExplorerOptions ieo = new InternetExplorerOptions();
    //ieo.setCapability("headless", ConstantVariable.HeadlessBrowser);

    return ieo;
  }

  /**
   * This method is used to set Safari capability.
   * @return
   */
  public SafariOptions safariDesired() {
    SafariOptions so = new SafariOptions();
    so.setCapability("headless", ConstantVariable.HeadlessBrowser);
    return so;
  }

}
