package com.encash.offers.webdriver;

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
  MOBILE_EMULATION("CHROME");
  
  public final String binaryBrower;
  
  private BrowserExecutionType(String binaryBrower) {
    this.binaryBrower = binaryBrower;
  }

}
