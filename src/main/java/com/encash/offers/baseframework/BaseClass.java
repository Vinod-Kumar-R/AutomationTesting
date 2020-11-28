package com.encash.offers.baseframework;


import com.aventstack.extentreports.Status;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.custom.exception.DuplicateValueException;
import com.encash.offers.utility.ExcelReader;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;

/**
 * <h1> This is Main class in which execution Start. </h1>
 * <p> Main call the constructor and initial all the variable required such as
 * URL, test case file, Log properties files and Result file  </p>
 * {@docRoot}
 * @serial 16-09-2020
 * @author Vinod Kumar R
 * @version 1.0
 */

public class BaseClass {
  private static Logger logger = LogManager.getLogger(BaseClass.class);
  private ConstantVariable constantVariable;
  private KeywordExecution keywordExecution;
  private GenericMethod genericMethod;
  private ExcelReader testData;
  private ExcelReader testCase;
  private ExtentReport extentReport;

  /**
   * In Constructor, initializing the required class.
   */
  public BaseClass() {
    constantVariable = new ConstantVariable();
    extentReport = BrowserInitialize.getExtentReportInstance();
    keywordExecution = new KeywordExecution();
    genericMethod = new GenericMethod();
    
  }

  /**
   * This Method start reading the Test case xlsx file  row by row.
   * 
   * <pre> Test case file contain column header i.e. </pre>
   * <table>
   * <caption>Test Case file format</caption>
   * <tr>
   * <th> Test case ID </th>
   * <th> Test Case Description </th>
   * <th> Test Category </th>
   * </tr>
   * <tr>
   * <td> Test case ID </td>
   * <td> Test Case Description </td>
   * <td> Test Category </td>
   * </tr>
   * 
   * </table>
   * <pre>and last row of first column should contain the text as "END" to indicate
   * that there is no more test case to executed </pre> 
   * @throws IOException  File not found exception 
   * @throws EncryptedDocumentException file has been excypted  
   * <pre> this is the main which accept all the Exception </pre>
   * @throws DuplicateValueException duplicate key are found in ObjectRepository file
   */
  public void startRun() throws DuplicateValueException, EncryptedDocumentException, IOException  {

    constantVariable.searchTestData();
    constantVariable.objectRepository();
    BrowserInitialize.getWebDriverInstance();
    BrowserInitialize.browserInfo();

    int testcaserownumber = 1;
    int testDatarownumber = 0;
    int firstColumn = 0;
    int secondColumn = 1;
    int thirdColumn = 2;
    int fourthCoumn = 3;
    //Read the Test case data
    logger.debug("Test Case File name " + ConstantVariable.TestCases);
    testCase = new ExcelReader(ConstantVariable.TestCases, 0);
    //is not of end of testcase row
    while (!(testCase.getCellData(testcaserownumber, firstColumn).equalsIgnoreCase("end"))) {
      //if Executed column is yes then executed else skip test skip
      String testCaseID = testCase.getCellData(testcaserownumber, firstColumn);
      String testCaseDescription = testCase.getCellData(testcaserownumber, secondColumn);
      String testCaseCategeory = testCase.getCellData(testcaserownumber, thirdColumn);
      if (testCase.getCellData(testcaserownumber, fourthCoumn).equalsIgnoreCase("yes")) {
        logger.info("Started Executing Test Case ID " + testCaseID);
        logger.debug("Test Case ID " + testCaseID);
        logger.debug("Test Case Description " + testCaseDescription);
        logger.debug("Test Case Categeory " + testCaseCategeory);

        extentReport.createTest(testCaseID, testCaseDescription);
        extentReport.categeory(testCaseCategeory);

        logger.debug("Test case found in the test data file at----> "
                        + ConstantVariable.TestDataRowNumber.get(testCaseID));
        testDatarownumber = ConstantVariable.TestDataRowNumber.get(testCaseID);

        //Execute the Test case ID
        logger.debug("Test Case ID found and started executing " + testCaseID);
        testRunId(testDatarownumber);
        extentReport.flushlog();

      } else {
        logger.info("Skiped the Test case " + testCaseID);
        extentReport.skipTest(testCaseID, testCaseDescription);
        extentReport.categeory(testCaseCategeory);
      }
      testcaserownumber++;
    }

    extentReport.flushlog();
    testCase.closeWorkbook();
    logger.info("Completed Exeuction of all the Test Case i.e " + (testCase.rowCout(0) - 2));
    logger.info("Result file are located in :- " + ConstantVariable.ExtentReportsLocation);
  }

  /**
   * This method read the Test script excel file i.e. Test Data
   * which contain few column
   * <table>
   * <caption>Test Case data format</caption>
   * <tr>
   * <td> Test case ID </td>
   * <td> Keyword which need to executed </td>
   * <td> rest of the column are parameter to the keyword method which is
   * required data for execution </td>
   * </table>
   * 
   * <p>Test Script start from the Test Case ID and end when particular row
   * and  column contain text as "END"
   * @param rowStartfrom This parameter take the integer number ,
   *     which indicate from which row test script has to executed
   */

  public void testRunId(int rowStartfrom) {
    int currentRow = rowStartfrom;
    int currentCol = 1;
    String keyword = null;


    try {
      testData = new ExcelReader(ConstantVariable.TestDatas, 0);


      while (testData.getCellData(currentRow, 0) == null
                      || !testData.getCellData(currentRow, 0).equalsIgnoreCase("End")) {
        keyword = testData.getCellData(currentRow, currentCol);
        logger.debug("Got the Key word from excel sheet " + keyword);

        //Read the all the parameter for keyword and add delimiter ~
        int column = 2;
        String[] stringParam = null;
        String param = "";

        while (testData.getCellData(currentRow, column) != null) {
          logger.debug("current row number " + currentRow + " Column number " + column);
          logger.debug("Data from Cell " + testData.getCellData(currentRow, column));

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

        keywordExecution.setvalue(KeywordType.valueOf(keyword));
        logger.info("Executing the Keyword " + keyword);
        keywordExecution.executed(stringParam);
        //KeywordType.
        currentRow++;

      }
      testData.closeWorkbook();
    } catch (EncryptedDocumentException e) {

    } catch (IOException e) {

    } catch (Exception e) {

      logger.error("Got an exception while executing keyword --> " + keyword, e);
      e.printStackTrace();
      WaitMethod.waitstatus = false;
      extentReport.writeLog(Status.FAIL, "Failed executing Keyword ---> " + keyword);
      extentReport.writeLog(Status.FAIL, e);
      extentReport.attachScreenshot(genericMethod.takeScreenshot());
      // BrowserInitialize.quitBrowser();
      extentReport.flushlog();
    }


  }

  /**
   * This is the Main method and execution start from here.
   * @param args is used
   */
  public static void main(final String[] args) {
    // TODO Auto-generated method stub
    BaseClass bc = new BaseClass();
    try {
      bc.startRun();
      bc.extentReport.flushlog();
      
    } catch (DuplicateValueException e) {
      logger.error("Found duplicate value ", e);
      
    } catch (Exception e) {
      bc.extentReport.attachScreenshot(bc.genericMethod.takeScreenshot());
      bc.extentReport.flushlog();
      logger.error("got an error", e);
      e.printStackTrace();
    }
  }
}
