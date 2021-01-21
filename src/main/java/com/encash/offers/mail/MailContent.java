package com.encash.offers.mail;

import com.aventstack.extentreports.Status;
import com.encash.offers.beanclass.ExtentReportBean;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.webdriver.BrowserInitialize;
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
  
  EventFiringWebDriver driver;
  
  
  /**
   * This method is used to set the Mail content data.
   * @return Pojo file content of ExtentReportBean
   * @throws IOException if file not found
   */
  public ExtentReportBean maildata() throws IOException {
    logger.debug("Setting the Email body content");
    
    driver = (EventFiringWebDriver) browserinitialize.getWebDriverInstance();
    
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
    report.setPlatform(driver.getCapabilities().getPlatform().toString());
    report.setEncashUrl(ConstantVariable.EncashURL);
    report.setAdminUrl(ConstantVariable.AdminURL);
    report.setBuildnumber("XXXX");
    report.setAutomationresult(resultfile.toFile());
    report.setBrowsername(driver.getCapabilities().getBrowserName());
    report.setBrowserversion(driver.getCapabilities().getVersion());
    report.setOs(System.getProperty("os.name"));
    
    return report;
  }
 
}
