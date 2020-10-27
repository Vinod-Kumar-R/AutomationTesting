package com.encash.offers.utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.encash.offers.configuration.ConstantVariable;
import java.io.IOException;

/**
 * This class is used to Generated the Extent report.
 * @author Vinod Kumar R
 *
 */
public class ExtentReport {

  public ExtentSparkReporter spark;
  public ExtentReports extent;
  public ExtentTest extenttest;
  private String css1 = ".col-md-4 { -webkit-box-flex: 0; -ms-flex: 0 0 33.333333%; "
                  + "flex: 0 0 33.333333%; max-width: 100%;} "
                  + ".table td, .table th {vertical-align: middle; white-space: nowrap; }";


  /**
   * In constructor initialization the extent report configuration.
   */
  public ExtentReport()  {

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
   * @param testName 
   */
  public void createTest(String testName) {
    this.extenttest = this.extent.createTest(testName);
  }

  /**
   * This method is used when test execution start.
   * @param testName
   * @param description
   */
  public void createTest(String testName, String description) {
    this.extenttest = this.extent.createTest(testName, description);
  }
  /**
   * This method is used to write log during execution.
   * @param status
   * @param details
   */
  
  public void writeLog(Status status, String details) {
    this.extenttest.log(status, details);
  }

  /**
   * This method is used to write log during execution with extra detail.
   * @param status
   * @param details
   * @param imageFilelocation
   * @throws IOException
   */
  public void writeLog(Status status, String details, String imageFilelocation) throws IOException {
    this.extenttest.log(status, details, 
                    MediaEntityBuilder.createScreenCaptureFromPath(imageFilelocation).build());
  }
  
  /**
   * 
   * @param status
   * @param t
   */
  public void writeLog(Status status, Throwable t) {
    this.extenttest.log(status, t);
  }

  /**
   * This method is at the end of each test execution so that result write to file
   */
  public void flushlog() {
    this.extent.flush();
  }
  /**
   * This method is used to attach the screen shot for execution test case.
   * @param imagePath
   * @throws IOException
   */
  
  public void attachScreenshot(String imagePath) throws IOException {
    this.extenttest.addScreenCaptureFromPath(imagePath);
  }

  /**
   * This method is used to write log in info 
   * @param details
   */
  public void writeInfo(String details) {
    this.extenttest.info(details);
  }

  /**
   * This method is used to set the categeory for the test case
   * @param category
   */
  public void categeory(String category) {
    this.extenttest.assignCategory(category);
  }

  /**
   * This method is used to set the author for test case.
   * @param author
   */
  public void author(String author) {
    this.extenttest.assignAuthor(author);
  }

  /**
   * This method is used to set the system information in extent report.
   * @param key
   * @param value
   */
  public void setSystemInfo(String key, String value) {
    this.extent.setSystemInfo(key, value);
  }

  /**
   * this method is used to when test case are skiped.
   * @param testName
   * @param description
   */
  public void skipTest(String testName, String description) {
    createTest(testName, description);
    this.extenttest.log(Status.SKIP, "Skipped the Test Case");
  }
}
