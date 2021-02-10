package com.encash.offers.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesValue {
  
  @Value("${encashurl}")
  private String encashUrl;
  
  @Value("${adminurl}")
  private String adminUrl;
  
  @Value("${mailinatorurl}")
  private String mailinatorUrl;
  
  @Value("${appiumServerurl}")
  private String appiumUrl;
  
  @Value("${testcase}")
  private String testcase;
  
  @Value("${testData}")
  private String testdata;
  
  @Value("${testobject}")
  private String testobject;
  
  @Value("${test_execution}")
  private String testBrowser;
  
  @Value("${headlessbrowser}")
  private Boolean headlessBrowser;
  
  @Value("${explictwait}")
  private int explictwait;
  
  @Value("${polling}")
  private int polling;

  @Value("${sendemail}")
  private boolean sendemail;
  
  @Value("${gmail}")
  private String gmailUrl;
  
  @Value("${firebase}")
  private String firebaseUrl;
  
  @Value("${encashoffers}/properties/extentreportpropertes.xml")
  private String extentReportsPropeties;
  
  @Value("${encashoffers}")
  private String configLocation;
  
  @Value("${encashoffers}/properties/klov.properties")
  private String klovrproperties;
  
  @Value("${klov}")
  private boolean isKlov;
  
  public boolean isKlov() {
    return isKlov;
  }

  public String getklovrproperties() {
    return klovrproperties;
  }

  public String getConfigLocation() {
    return configLocation;
  }

  public String getExtentReportsPropeties() {
    return extentReportsPropeties;
  }

  public String getFirebaseUrl() {
    return firebaseUrl;
  }

  public String getGmailUrl() {
    return gmailUrl;
  }

  public String getAdminUrl() {
    return adminUrl;
  }

  public String getMailinatorUrl() {
    return mailinatorUrl;
  }

  public String getAppiumUrl() {
    return appiumUrl;
  }

  public String getTestcase() {
    return testcase;
  }

  public String getTestdata() {
    return testdata;
  }

  public String getTestobject() {
    return testobject;
  }

  public String getTestBrowser() {
    return testBrowser;
  }

  public Boolean getHeadlessBrowser() {
    return headlessBrowser;
  }

  public int getExplictwait() {
    return explictwait;
  }

  public int getPolling() {
    return polling;
  }

  public String getEncashUrl() {
    return encashUrl;
  }

  public boolean isSendemail() {
    return sendemail;
  }

}
