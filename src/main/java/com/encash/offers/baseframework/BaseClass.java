package com.encash.offers.baseframework;


import com.aventstack.extentreports.Status;
import com.encash.offers.configuration.ConstantVariable;
import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.custom.exception.DuplicateValueException;
import com.encash.offers.mail.MailContent;
import com.encash.offers.mail.MailServiceImpl;
import com.encash.offers.utility.ExcelReader;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * <h1> In this class start executiong test case Start. </h1>
 * {@docRoot}
 * @serial 16-09-2020
 * @author Vinod Kumar R
 * @version 1.0
 */

public class BaseClass {
  private static Logger logger = LogManager.getLogger(BaseClass.class);
  @Autowired
  private ConstantVariable constantVariable;
  @Autowired
  private KeywordExecution keywordExecution;
  @Autowired
  private GenericMethod genericMethod;
  @Autowired
  @Qualifier("testdata")
  private ExcelReader testData;
  @Autowired
  @Qualifier("testcase")
  private ExcelReader testCase;
  @Autowired
  public ExtentReport extentReport;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private MailServiceImpl mail;
  @Autowired
  private MailContent content;
  @Autowired
  private PropertiesValue properties;

  
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
    
    constantVariable.initializeVariable();
    extentReport.initializeExtentReport();
    
    //initialize the excel file for testdata and testcase
    testData.setExcelfilename(properties.getTestdata());
    testData.setExcelsheetindex(0);
    testCase.setExcelfilename(properties.getTestcase());
    testCase.setExcelsheetindex(0);
    
    
    constantVariable.searchTestData();
    constantVariable.objectRepository();
    browserinitialize.getWebDriverInstance();
    browserinitialize.browserInfo();
 
    int testcaserownumber = 1;
    int testDatarownumber = 0;
    int firstColumn = 0;
    int secondColumn = 1;
    int thirdColumn = 2;
    int fourthCoumn = 3;
    //Read the Test case data
    logger.debug("Test Case File name " + properties.getTestcase());
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
      while (testData.getCellData(currentRow, 0) == null
                      || !testData.getCellData(currentRow, 0).equalsIgnoreCase("End")) {
        keyword = testData.getCellData(currentRow, currentCol);
        logger.debug("Got the Key word from excel sheet " + keyword);

        //skipping the step if keyword is "comment"
        if (!keyword.equalsIgnoreCase("comment")) {

          //Read the all the parameter for keyword and add delimiter ~
          int column = 2;
          String param = "";
          List<String> dataParam = new ArrayList<String>();

          while (testData.getCellData(currentRow, column) != null) {
            logger.debug("current row number " + currentRow + " Column number " + column);
            logger.debug("Data from Cell " + testData.getCellData(currentRow, column));

            param = testData.getCellData(currentRow, column);
            dataParam.add(param);
            //increment column
            column++;
          }
         
          keywordExecution.setvalue(KeywordType.valueOf(keyword));
          logger.info("Executing the Keyword " + keyword);
          keywordExecution.executed(dataParam);
        } 
        //increment the row
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
      //BrowserInitialize.quitBrowser();
      extentReport.flushlog();
    }
  }
  
  /**
   * This method is used to prepare for email content configuration and send email.
   * @throws IOException through an exception if file not found
   */
  public void emailTestResult() throws IOException {

    mail.sendEmail(content.maildata());
   
  }
}
