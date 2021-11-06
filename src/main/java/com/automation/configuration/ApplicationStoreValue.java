package com.automation.configuration;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;

public class ApplicationStoreValue {
  
  /**
   * This is used to stored the window name.
   */
  public Map<String, String> windowHandle = new HashMap<String, String>();
  
  /**
   * This is used to stored Email OTP read in the mailinator.
   */
  public String storedOtp;
  
  /**
   * This is used to stored the Driver instance. 
   */
  public Map<String, WebDriver> driverinstance = new HashMap<String, 
                  WebDriver>();

}
