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
  
  private String ResultBaseLocation;
  private String ResultDatelocaton;
  private String ResultLocation;
  private String ResultLocation1;
  private String dateformat = "dd_MMM_yyyy";
  private String timeformat = "HH_mm_ss";
  
  
  
 
  public String getResultBaseLocation() {
    return ResultBaseLocation;
  }

  public String getResultDatelocaton() {
    return ResultDatelocaton;
  }

  public String getResultLocation() {
    return ResultLocation;
  }

  public String getResultLocation1() {
    return ResultLocation1;
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
