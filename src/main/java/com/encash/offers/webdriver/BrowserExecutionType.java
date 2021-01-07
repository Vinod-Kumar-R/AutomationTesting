package com.encash.offers.webdriver;

public enum BrowserExecutionType {

  SYSTEM_CHROME("CHROME"),
  SYSTEM_FIREFOX("FIREFOX"),
  SYSTEM_OPERA("OPERA"),
  SYSTEM_EDGE("EDGE"),
  SYSTEM_IEXPLORER("IEXPLORER"),
  SYSTEM_CHROMIUM("CHROME"),
  SYSTEM_SAFARI("SAFARI"),
  ANDROID_CHROME("CHROME"),
  IOS_SAFARI("SAFARI"),
  SYSTEM_MOBILE_EMULATION("CHROME");
  
  public final String binaryBrower;
  
  private BrowserExecutionType(String binaryBrower) {
    this.binaryBrower = binaryBrower;
  }

}
