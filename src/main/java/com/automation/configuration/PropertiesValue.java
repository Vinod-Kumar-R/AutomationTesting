package com.automation.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * this class is used for general properties file configuration. 
 * @author Vinod Kumar R
 *
 */
@Configuration
@Component
@Getter
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
  
  @Value("${testcase:${automation}/testscript/Testcase.xlsx}")
  private String testcase;
  
  @Value("${testData:${automation}/testscript/TestData.xlsx}")
  private String testdata;
  
  @Value("${testobject:${automation}/testscript/TestObject.csv}")
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
  
  @Value("${docker}")
  @Setter
  private boolean isDocker;

  @Setter
  private String extentreportlocation;
  
  @Setter
  private String templocation;
  
  @Setter
  private String resultfolder;
  
  @Value("${buildInfo:XXX}")
  @Setter
  private String automationBuildInfo;
  
  @Value("${reportlink:Report}")
  @Setter
  private String jenkinsReport;
  
  @Value("${docker_video_recording}")
  private boolean isRecording;
  
  @Value("${automation_type:true}")
  @Setter
  private boolean automationType;
}
