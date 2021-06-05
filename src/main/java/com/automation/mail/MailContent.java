package com.automation.mail;

import com.automation.beanclass.ExtentReportBean;
import com.automation.configuration.ConstantVariable;
import com.automation.configuration.PropertiesValue;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.webdriver.BrowserInitialize;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

public class MailContent {
  
  private static Logger logger = LogManager.getLogger(MailContent.class);
  
  @Autowired
  private ExtentReport extentreport;
  
  @Autowired
  private GenericMethod genericMethod;
  
  @Autowired
  private BrowserInitialize browserinitialize;
  
  @Autowired
  private PropertiesValue properties;
  
  EventFiringWebDriver driver;
  
  
  /**
   * This method is used to set the Mail content data.
   * @return Pojo file content of ExtentReportBean
   * @throws IOException if file not found
   */
  public ExtentReportBean maildata() throws IOException {
    logger.debug("Setting the Email body content");
    
    //driver = (EventFiringWebDriver) browserinitialize.getWebDriverInstance();
    
    // zip the result file
    Path resultfile = genericMethod.zip(ConstantVariable.ResultLocation, 
                   ConstantVariable.ResultBaseLocation + File.separator 
                    + ConstantVariable.Foldername + ".zip");
        
    ExtentReportBean report = new ExtentReportBean();
    
    report.setStartExecutiontime(extentreport.getStartTime());
    report.setEndExecutiontime(extentreport.getEndTime());
    report.setDuration(extentreport.getDurationTime());
    report.setPasstestcase(extentreport.getTotalTestPasscase(Status.PASS));
    report.setFailtestcase(extentreport.getTotalTestPasscase(Status.FAIL));
    report.setSkiptestcase(extentreport.getTotalTestPasscase(Status.SKIP));
    report.setTotaltestcase(extentreport.getTotalTestcase());
    report.setPlatform(extentreport.getSystemInfo("Platform"));
    report.setEncashUrl(properties.getEncashUrl());
    report.setAdminUrl(properties.getAdminUrl());
    report.setBuildnumber("XXXX");
    report.setAutomationresult(resultfile.toFile());
    report.setBrowsername(extentreport.getSystemInfo("Browser Name"));
    report.setBrowserversion(extentreport.getSystemInfo("Browser Version"));
    report.setOs(System.getProperty("os.name"));
    report.setCategeorys(extentreport.categeoryctx());
    report.setPassTest(extentreport.getTestDetail(Status.PASS));
    report.setFailTest(extentreport.getTestDetail(Status.FAIL));
    report.setSkipTest(extentreport.getTestDetail(Status.SKIP));
    
    return report;
  }
 
}
