package com.automation.utility;

import com.automation.configuration.PropertiesValue;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.SystemEnvInfo;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContext;
import com.aventstack.extentreports.model.context.NamedAttributeContextManager;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used to Generated the Extent report.
 * @author Vinod Kumar R
 *
 */

public class ExtentReport {

  private static Logger logger = LogManager.getLogger(ExtentReport.class);
  public ExtentSparkReporter spark;
  public ExtentKlovReporter klov;
  public ExtentReports extent;
  public ExtentTest extenttest;
  private String css1 = ".col-md-4 { -webkit-box-flex: 0; -ms-flex: 0 0 33.333333%; "
                  + "flex: 0 0 33.333333%; max-width: 100%;} "
                  + ".table td, .table th {vertical-align: middle; white-space: nowrap; }";
  @Autowired
  private PropertiesValue properties;


  /**
   * This method is used to initialize extent report.
   */
  public void initializeExtentReport() {
 
    this.klov = new ExtentKlovReporter();
    this.spark = new ExtentSparkReporter(properties.getExtentreportlocation());
    this.spark.config().setCss(css1);
    
    try {
      this.spark.loadXMLConfig(properties.getExtentReportsPropeties());
      if (properties.isKlov()) {
        this.klov.loadInitializationParams(new FileInputStream(
                        new File(properties.getklovrproperties())));
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.extent = new ExtentReports();
    this.extent.setSystemInfo("Organization", "Automation");
    this.extent.setSystemInfo("OS Version number", System.getProperty("os.version"));
    this.extent.setSystemInfo("Build Number", "Need to fetch the Build Number");
    this.extent.setSystemInfo("Encash URL", properties.getEncashUrl());
    this.extent.setSystemInfo("Admin URL", properties.getAdminUrl());
    
    if (properties.isKlov()) {
      this.extent.attachReporter(this.spark, this.klov);
    } else {
      this.extent.attachReporter(this.spark);
    }
  }

  /**
   * This method is used when test execution start.
   * @param testName is String contain the test execution name
   */
  public void createTest(String testName) {
    this.extenttest = this.extent.createTest(testName);
  }

  /**
   * This method is used when test execution start.
   * @param testName is String contain the test execution name
   * @param description test case description
   */
  public void createTest(String testName, String description) {
    this.extenttest = this.extent.createTest(testName, description);
  }
  /**
   * This method is used to write log during execution.
   * @param status contain the "Pass" or fail
   * @param details description of status
   */
  
  public void writeLog(Status status, String details) {
    this.extenttest.log(status, details);
  }

  /**
   * This method is used to write log during execution with extra detail.
   * @param status status contain the "Pass" or fail
   * @param details description of status
   * @param imageFilelocation capture image file location
   */
  public void writeLog(Status status, String details, String imageFilelocation) {
    this.extenttest.log(status, details, 
                    MediaEntityBuilder.createScreenCaptureFromPath(imageFilelocation).build());
  }
  
  /**
   * This method is used to write log in extent report with throwable exception.
   * @param status contain the "Pass" or fail
   * @param t throwable
   */
  public void writeLog(Status status, Throwable t) {
    this.extenttest.log(status, t);
  }

  /**
   * This method is at the end of each test execution so that result write to file.
   */
  public void flushlog() {
    this.extent.flush();
  }
  /**
   * This method is used to attach the screen shot for execution test case.
   * @param imagePath capture image file location
   */
  
  public void attachScreenshot(String imagePath)  {
    this.extenttest.addScreenCaptureFromPath(imagePath);
  }

  /**
   * This method is used to write log in info.
   * @param details Message of information
   */
  public void writeInfo(String details) {
    this.extenttest.info(details);
  }

  /**
   * This method is used to set the categeory for the test case.
   * @param category it contain test execution belong to which category
   */
  public void categeory(String category) {
    this.extenttest.assignCategory(category);
  }

  /**
   * This method is used to set the author for test case.
   * @param author test execution author name
   */
  public void author(String author) {
    this.extenttest.assignAuthor(author);
  }

  /**
   * This method is used to set the system information in extent report.
   * @param key contain the system information of key
   * @param value contain the system information of value
   */
  public void setSystemInfo(String key, String value) {
    this.extent.setSystemInfo(key, value);
  }

  public String getSystemInfo(String envname) {
    List<SystemEnvInfo> envInfo = this.extent.getReport().getSystemEnvInfo();
    for (SystemEnvInfo s : envInfo) {
      if (s.getName().equals(envname)) {
        return s.getValue();
      }
    }
    return "null";
  }
  
  /**
   * this method is used to when test case are skipped.
   * @param testName test case execution name
   * @param description test case description 
   */
  public void skipTest(String testName, String description) {
    createTest(testName, description);
    this.extenttest.log(Status.SKIP, "Skipped the Test Case");
  }

  /**
   * this method is used to get the Category report which will be use full in mail content.
   * @return category in the form of set
   */
  public Set<NamedAttributeContext<Category>> categeoryctx() {

    NamedAttributeContextManager<Category> category = this.spark.getReport().getCategoryCtx();
    return category.getSet();
  }
  
  public Date getStartTime() {
    return this.spark.getReport().getStartTime();
  }
  
  public Date getEndTime() {
    return this.spark.getReport().getEndTime();
    
  }
  
  /**
   * This method is used to get the Duration of Execution time.
   * @return org.joda.time.Period
   */
  public Period getDurationTime() {
    Interval interval = new Interval(this.spark.getReport().getStartTime().getTime(),
                    this.spark.getReport().getEndTime().getTime());
     
    return interval.toPeriod();
  }
  
  public  int getTotalTestcase() {
    return this.spark.getReport().getTestList().size();
  }
  
  /**
   * This method is used to get the total test case status.
   * @param status is an enum constant SKIP,PASS,FAILED,OTHER
   * @return total number of test case depend on Status
   */
  public int getTotalTestPasscase(Status status) {
    List<Test> tests = this.spark.getReport().getTestList();
    int count = 0;
    for (Test test : tests) {
      if (test.getStatus().equals(status)) {
        count++;
      }
    }
    return count;
  }
  
  /**
   * This method is used to get the list of Test detail. 
   * @param status should be any one "PASS","FAILED","SKIP"
   * @return the list of Test Data 
   */
  public List<Test> getTestDetail(Status status) {
    List<Test> tests = this.spark.getReport().getTestList();

    List<Test> testStatus = new ArrayList<Test>();

    for (Test test : tests) {
      if (test.getStatus().equals(status)) {
        testStatus.add(test);
      }
    }
    return testStatus;
  }
  
  public List<Test> getTestDetail() {
    List<Test> tests = this.spark.getReport().getTestList();
    return tests;
  }
}
