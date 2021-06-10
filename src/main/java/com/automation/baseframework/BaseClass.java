package com.automation.baseframework;


import com.automation.beanclass.TestExcelBean;
import com.automation.configuration.ConstantVariable;
import com.automation.configuration.JiraConfiguration;
import com.automation.configuration.PropertiesValue;
import com.automation.custom.exception.DuplicateValueException;
import com.automation.jira.beanclass.TestCase;
import com.automation.jira.beanclass.TestCaseAttachment;
import com.automation.jira.zephyr.api.TestCaseapi;
import com.automation.mail.MailContent;
import com.automation.mail.MailServiceImpl;
import com.automation.utility.ExcelReader;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webdriver.BrowserInitialize;
import com.aventstack.extentreports.Status;
import com.poiji.bind.Poiji;
import java.io.File;
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
  @Qualifier("createtestcase")
  private ExcelReader testcaseCreation;
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
  @Autowired
  private TestCaseapi testcaseapi;
  @Autowired
  private JiraConfiguration jiraconfiguration;

  
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
   * @throws SpreadsheetReadException 
   */
  public void startRun() throws DuplicateValueException, EncryptedDocumentException, IOException  {
    
    constantVariable.initializeVariable();
    extentReport.initializeExtentReport();
    
    //initialize the excel file for testdata
    testData.setExcelfilename(properties.getTestdata());
    testData.getExcelsheetindex(0);
    
    constantVariable.searchTestData();
    constantVariable.objectRepository();
    browserinitialize.getWebDriverInstance();
    browserinitialize.browserInfo();
    
    int testDatarownumber = 0;
    
    //Read the Test case data
    logger.debug("Test Case File name " + properties.getTestcase());
    List<TestExcelBean> testcases = Poiji.fromExcel(new File(properties.getTestcase()),
                    TestExcelBean.class);  
    for (TestExcelBean testca : testcases) {
      if (testca.getTestExecute().equalsIgnoreCase("yes")) {
        logger.info("Started Executing Test Case ID " + testca.getTestcaseId());
        logger.debug("Test Case ID " + testca.getTestcaseId());
        logger.debug("Test Case Description " + testca.getTestcaseDescription());
        logger.debug("Test Case Categeory " + testca.getTestCatgeory());

        extentReport.createTest(testca.getTestcaseId(), testca.getTestcaseDescription());
        extentReport.categeory(testca.getTestCatgeory());

        logger.debug("Test case found in the test data file at----> "
                        + ConstantVariable.TestDataRowNumber.get(testca.getTestcaseId()));
        testDatarownumber = ConstantVariable.TestDataRowNumber.get(testca.getTestcaseId());

        //Execute the Test case ID
        logger.debug("Test Case ID found and started executing " + testca.getTestcaseId());
        testRunId(testDatarownumber);
        extentReport.flushlog();

      } else {
        logger.info("Skiped the Test case " + testca.getTestcaseId());
        extentReport.skipTest(testca.getTestcaseId(), testca.getTestcaseDescription());
        extentReport.categeory(testca.getTestCatgeory());
      }
      
    }

    extentReport.flushlog();
    logger.info("Completed Exeuction of all the Test Case i.e " + testcases.size());
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
      logger.error("EncryptedDocumentException ---> " + e.getMessage());

    } catch (IOException e) {
      logger.error("IOException ---> " + e.getMessage());

    } catch (Exception e) {

      logger.error("Got an exception while executing keyword --> " + keyword, e);
      e.printStackTrace();
      WaitMethod.waitstatus = false;
      extentReport.writeLog(Status.FAIL, "Failed executing Keyword ---> " + keyword);
      extentReport.writeLog(Status.FAIL, e);
      extentReport.attachScreenshot(genericMethod.takeScreenshot());
      browserinitialize.quitBrowser();
      extentReport.flushlog();
    }
  }

  
  /**
   *  abcd.
   * @throws IOException 
   */
  public void testdatapreparation() throws IOException {
   
    //Prepare the test date
    //make a request call to Jira to fetch the list of testcase
    List<TestCase> testcases = testcaseapi.getAllTestCase(
                    jiraconfiguration.getTestcaseQuery(), 0, 
                    jiraconfiguration.getTestcaseMaxresult());
   
    //create a exel sheet with header and close the workbook
    String excelfilename = properties.getConfigLocation() + "/temp/" + "automation.xlsx";
    createExcelSheet(excelfilename);
    
    //now open created file in read write mode 
    int cellrow = 1;
                       
    //loop the TestCase
    for (TestCase testcase : testcases) {
      
      //download the testscript from jira
      logger.debug("testcase ID " + testcase.getTestcaseId()
          + "-----" + "projectID " + testcase.getProjectId());
      List<TestCaseAttachment> attachments = 
                      testcaseapi.getTestCaseAttachmentList(testcase.getTestcaseId());
      for (TestCaseAttachment attachment : attachments) {
        if (attachment.getFilename().equalsIgnoreCase("Automation.xlsx") 
                        && attachment.getFilesize() > 0) {
          logger.debug("testfile need to download need to download");
          //download the test script assuming file has download
          testcaseapi.getDownloadTestCaseFile(attachment.getUrl(), 
                          properties.getConfigLocation() 
                          + "/temp/" 
                          + testcase.testcaseId.replace("-", "")
                          + attachment.getFilename());
          // write to exel sheet 
          logger.debug("writing to excel sheet");
          testcaseCreation.setCreateRow(cellrow);
          testcaseCreation.setCellData(0, testcase.getTestcaseId());
          testcaseCreation.setCellData(1, testcase.getTestCaseDescription());
          testcaseCreation.setCellData(2, testcase.getCustomField().getCategeory());
          testcaseCreation.setCellData(3, "Yes");
          cellrow++;

        } 
      }
    }
    
    //write data in buffer to file 
    testcaseCreation.writeWorkbook(excelfilename);
    testcaseCreation.closeWorkbook();
  }
  
  //
  public void createExcelSheet(String excelfilename) throws IOException {
  //creat a test excel data 
    testcaseCreation.createExcelFile(true);
    testcaseCreation.setExcelSheet("Sheet1");
    //Excel header name
    testcaseCreation.setCreateRow(0);
    testcaseCreation.setCellData(0, "Test Case ID");
    testcaseCreation.setCellData(1, "Test Case Description");
    testcaseCreation.setCellData(2, "Test Categeory");
    testcaseCreation.setCellData(3, "Executed");
    
    //close the workbook
    //not closed because to finish 
    //testcaseCreation.writeWorkbook(excelfilename);
    //testcaseCreation.closeWorkbook();
    
  }

  
  
  /**
   * This method is used to prepare for email content configuration and send email.
   * @throws IOException through an exception if file not found
   */
  public void emailTestResult() throws IOException {
    mail.sendEmail(content.maildata());
  }
}
