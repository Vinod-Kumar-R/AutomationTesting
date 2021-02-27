package com.encash.offers.configuration;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.support.events.EventFiringWebDriver;

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
  public Map<String, EventFiringWebDriver> driverinstance = new HashMap<String, 
                  EventFiringWebDriver>();

}
