package com.encash.offers.baseframework;


import com.aventstack.extentreports.Status;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.utility.ExcelReader;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <h1> This is Main class in which execution Start. </h1>
 * <p> Main call the constructor and initial all the variable required such as 
 * URL, test case file, Log properties files and Result file  </p>
 * 
 * {@docRoot}
 * @serial 16-09-2020
 * @author Vinod Kumar R
 * @version 1.0
 * 
 */

public class BaseClass {
  static Logger logger = LogManager.getLogger(BaseClass.class);
  //private static int throwincrment =0;
  private ConstantVariable cv;
  private GenericMethod gm;
  private KeywordExecution ke;
  private ExcelReader testData;
  private ExcelReader testCase;
  private static ExtentReport er;

  /**
   * In Constructor, initializing the required class.
   */
  public BaseClass() {
    cv = new ConstantVariable();
    er = BrowserInitialize.getExtentReportInstance();
    ke = new KeywordExecution();
    gm = new GenericMethod();
  }

  /**
   * This Method start reading the Test case xlsx file  row by row.
   * <p> Test case file contain column header i.e. </p>
   * <tr>
   * <th> Test case ID </th>
   * <th> Test Case Description </th>
   * <th> Test Category </th>
   * </tr>
   * 
   * <p>and last row of first column should contain the text as "END" to indicate
   * that there is no more test case to executed  
   *  
   * @throws Exception 
   * <p> this is the main which accept all the Exception </p>
   */
  public void start_Run() throws Exception {

    cv.searchTestData();
    cv.objectRepository();
    BrowserInitialize.getWebDriverInstance();
    BrowserInitialize.browserInfo();

    int testcaserownumber = 1;
    int testDatarownumber = 0;
    //Read the Test case data
    logger.debug("Test Case File name " + ConstantVariable.TestCases);
    testCase = new ExcelReader(ConstantVariable.TestCases, 0);
    //is not of end of testcase row
    while (!(testCase.getCellData(testcaserownumber, 0).equalsIgnoreCase("End"))) {
      //if Executed column is yes then executed else skip test skip
      String testCaseID = testCase.getCellData(testcaserownumber, 0);
      String testCaseDescription = testCase.getCellData(testcaserownumber, 1);
      String testCaseCategeory = testCase.getCellData(testcaserownumber, 2);
      if ((testCase.getCellData(testcaserownumber, 3).equalsIgnoreCase("yes"))) {
        logger.info("Started Executing Test Case ID " + testCaseID);
        logger.debug("Test Case ID " + testCaseID);
        logger.debug("Test Case Description " + testCaseDescription);
        logger.debug("Test Case Categeory " + testCaseCategeory);

        er.createTest(testCaseID, testCaseDescription);
        er.categeory(testCaseCategeory);

        logger.debug("Test case found in the test data file at----> " 
                        + ConstantVariable.TestDataRowNumber.get(testCaseID));
        testDatarownumber = ConstantVariable.TestDataRowNumber.get(testCaseID);

        //Execute the Test case ID
        logger.debug("Test Case ID found and started executing " + testCaseID);
        testRunId(testDatarownumber);
        er.flushlog();

      } else {
        logger.info("Skiped the Test case " + testCaseID);
        er.skipTest(testCaseID, testCaseDescription);
        er.categeory(testCaseCategeory);
      }
      testcaserownumber++;
    }

    er.flushlog();
    testCase.closeWorkbook();
    logger.info("Completed Exeuction of all the Test Case i.e " + (testCase.rowCout(0) - 2));
    logger.info("Result file are located in :- " + ConstantVariable.ExtentReportsLocation);
  }

  /**
   * This method read the Test script excel file i.e. Test Data
   * which contain few column 
   * <tr>
   * <td> Test case ID </td>
   * <td> Keyword which need to executed </td>
   * <td> rest of the column are parameter to the keyword method which is required
   * data for execution </td>
   * 
   * <p>Test Script start from the Test Case ID and end when particular row 
   * and  column contain text as "END"
   * 
   * @param rowStartfrom This parameter take the integer number , 
   *     which indicate from which row test script has to executed  
   */

  public void testRunId(int rowStartfrom)  {
    int currentRow = rowStartfrom;
    int currentCol = 1;
    String keyword;

    try {
      testData = new ExcelReader(ConstantVariable.TestDatas, 0);

      while (testData.getCellData(currentRow, 0) == null 
                      || !testData.getCellData(currentRow, 0).equalsIgnoreCase("End")) {
        keyword = testData.getCellData(currentRow, currentCol);
        logger.debug("Got the Key word from excel sheet " + keyword);

        //Read the all the parameter for keywork and add delimitor ~
        int column = 2;
        String[] stringParam = null;
        String param = "";
        int incrementarraydata = 0;
        while (testData.getCellData(currentRow, column) != null) {
          logger.debug("current row number " + currentRow + "Column number " + column);
          logger.debug("Data got from Cell " + testData.getCellData(currentRow, column));
          logger.debug("value of incrementarraydata " + incrementarraydata);
          if (param.equals("")) {
            param = testData.getCellData(currentRow, column);
          } else {
            param = param + "~" + testData.getCellData(currentRow, column);
          }

          column++;
        }
        //copy all the parameter to an array of string
        if (!param.equals("")) {
          stringParam = param.split("~");
        }

        ke.setvalue(KeywordType.valueOf(keyword));
        logger.info("Executing the Keyword " + keyword);
        ke.executed(stringParam);
        //KeywordType.
        currentRow++;

      }
      testData.closeWorkbook();

    } catch (Exception e) {

      WaitMethod.waitstatus = false;
      er.writeLog(Status.FAIL, e.getMessage());
      logger.error("testscript error message", e);
      try {
        testData.closeWorkbook();
        er.attachScreenshot(gm.takeScreenshot());
        BrowserInitialize.quitBrowser();
        er.flushlog();
      } catch (Exception e1) {
        // TODO Auto-generated catch block
        logger.error("exception message", e1);
      }
      er.flushlog();
    }
  }

  /**
   * This is the Main method and execution start from here.
   * @param args is used
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    BaseClass bc = new BaseClass();
    try {
      bc.start_Run();
      er.flushlog();
    } catch (Exception e) {
      er.flushlog();
      logger.error(e.getStackTrace().toString());
      e.printStackTrace();
    }
  }
}
