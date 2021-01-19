package com.encash.offers.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContext;
import com.aventstack.extentreports.model.context.NamedAttributeContextManager;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.encash.offers.configuration.ConstantVariable;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.Period;



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


  /**
   * This method is used to initialize extent report.
   */
  public void initializeExtentReport() {
    this.spark = new ExtentSparkReporter(ConstantVariable.ExtentReportsLocation);
    this.spark.config().setCss(css1);

    try {
      this.spark.loadXMLConfig(ConstantVariable.ExtentReportsPropeties);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.extent = new ExtentReports();
    this.extent.setSystemInfo("Organization", "Enchashes");
    this.extent.setSystemInfo("OS Version number", System.getProperty("os.version"));
    this.extent.setSystemInfo("Build Number", "Need to fetch the Build Number");
    this.extent.setSystemInfo("Encash URL", ConstantVariable.EncashURL);
    this.extent.setSystemInfo("Admin URL", ConstantVariable.AdminURL);
    this.extent.attachReporter(this.spark);
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
   */
  public void categeoryctx() {

    NamedAttributeContextManager<Category> category = this.spark.getReport().getCategoryCtx();
    Set<NamedAttributeContext<Category>>  set =  category.getSet();

    for (NamedAttributeContext<Category> cc :set) {
      logger.debug(cc.getAttr().getName()); 
      logger.debug(cc.getFailed().toString());
      logger.debug(cc.getPassed().toString());
      logger.debug(cc.getSkipped().toString());
      logger.debug(cc.getOthers().toString());
      List<Test> test = cc.getTestList();
      for (Test tes : test) {
        tes.getAncestor();
        tes.getDescription();
      }
    }
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
   * 
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
  
  
}
