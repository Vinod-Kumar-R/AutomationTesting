package com.automation.webdriver;

/**
 * In this Enum releated to browser support by selenium.
 * @author Vinod Kumar R
 *
 */
public enum BrowserExecutionType {

  CHROME("CHROME"),
  FIREFOX("FIREFOX"),
  OPERA("OPERA"),
  EDGE("EDGE"),
  IEXPLORER("IEXPLORER"),
  CHROMIUM("CHROME"),
  SAFARI("SAFARI"),
  ANDROID_CHROME("CHROME"),
  IOS_SAFARI("SAFARI"),
  MOBILE_EMULATION("CHROME"),
  BROWSER_STACK("CHROME");
  
  public final String binaryBrower;
  
  BrowserExecutionType(String binaryBrower) {
    this.binaryBrower = binaryBrower;
  }

}
