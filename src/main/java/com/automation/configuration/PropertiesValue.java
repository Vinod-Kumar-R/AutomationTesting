package com.automation.configuration;

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
  
  @Value("${browserstackurl}")
  private String browserStackUrl;
  
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
  
  @Value("${automation}/properties/extentreportpropertes.xml")
  private String extentReportsPropeties;
  
  @Value("${automation}")
  private String configLocation;
  
  @Value("${automation}/properties/klov.properties")
  private String klovrproperties;
  
  @Value("${klov}")
  private boolean isKlov;
  
  @Value("${jiraintegration}")
  private boolean isJiraIntegration;
  
  @Value("${amazonurl}")
  private String amazonurl;
  
  @Value("${repository}")
  private boolean isObjectRepository;

  public boolean isObjectRepository() {
    return isObjectRepository;
  }

  public String getAmazonurl() {
    return amazonurl;
  }

  private String extentreportlocation;
  
  private String templocation;
  
  public String getTemplocation() {
    return templocation;
  }

  public void setTemplocation(String templocation) {
    this.templocation = templocation;
  }

  public String getExtentreportlocation() {
    return extentreportlocation;
  }

  public void setExtentreportlocation(String extentreportlocation) {
    this.extentreportlocation = extentreportlocation;
  }

  public boolean isKlov() {
    return isKlov;
  }
  
  public boolean isJiraIntegration() {
    return isJiraIntegration;
  }
  
  public String getBrowserStackUrl() {
    return browserStackUrl;
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
