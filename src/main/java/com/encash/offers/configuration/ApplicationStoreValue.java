package com.encash.offers.configuration;

import java.util.HashMap;
import java.util.Map;

public class ApplicationStoreValue {
  
  /**
   * This is used to stored the window name.
   */
  public Map<String, String> windowHandle = new HashMap<String, String>();
  
  /**
   * This is used to stored Email OTP read in the mailinator.
   */
  public String storedOtp;

}
