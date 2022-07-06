package com.automation.baseframework;

import com.automation.api.jira.Zephyr;
import com.automation.beanclass.TestExcelBean;
import com.automation.configuration.ConstantVariable;
import com.automation.configuration.JiraConfiguration;
import com.automation.configuration.PropertiesValue;
import com.automation.custom.exception.DuplicateValueException;
import com.automation.jira.beanclass.QueryParamSearch;
import com.automation.jira.beanclass.TestCase;
import com.automation.jira.beanclass.TestCaseAttachment;
import com.automation.jira.beanclass.TestResult;
import com.automation.mail.MailContent;
import com.automation.mail.MailServiceImpl;
import com.automation.utility.ExcelReader;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.webdriver.BrowserInitialize;
import com.aventstack.extentreports.Status;
import com.poiji.bind.Poiji;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * <h1> In this class start executing test case Start. </h1>
 * {@docRoot}
 * @serial 16-09-2020
 * @author Vinod Kumar R
 * @version 1.0
 */
@Component
@Log4j2
public class BaseClass {
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
  protected ExtentReport extentReport;
  @Autowired
  private BrowserInitialize browserinitialize;
  @Autowired
  private MailServiceImpl mail;
  @Autowired
  private MailContent content;
  @Autowired
  private PropertiesValue properties;
  @Autowired
  private JiraConfiguration jiraconfiguration;
  @Autowired
  private Zephyr zephyr;
  private String recordingFilename;


  /**
   * This Method initialize all variable and classes such as extent, excel etc. 
   *  
   * @throws IOException  File not found exception 
   * @throws EncryptedDocumentException file has been excypted  
   <pre> this is the main which accept all the Exception </pre>
   * @throws DuplicateValueException duplicate key are found in ObjectRepository file
   * @throws URISyntaxException is used to form the download URL.
   */
  public void startRun() throws DuplicateValueException, EncryptedDocumentException,
          IOException, URISyntaxException  {
    
    if (properties.isAutomationType()) {
      browserinitialize.getWebDriverInstance();
      browserinitialize.browserInfo();    
    }

    if (properties.isJiraIntegration()) {
      String testScriptFile = testdatapreparation();
      executetestcase(testScriptFile);
      //prepare test result
      TestResult jiraResult = extentReport.getJiraResult();
      
      File jsonFile = new File(properties.getTemplocation() + File.separator + "result.json");
      String jiraResultLocation = properties.getTemplocation() + File.separator + "result.zip";
      
      genericMethod.toJsonFile(jiraResult, jsonFile.getAbsolutePath());
      
      File jiraResultZip = genericMethod.zipFile(jiraResultLocation, jsonFile.toPath());
      log.debug("updating result to jira " + jiraResultZip.getAbsolutePath());
      
      MultipartFile multipart = new MockMultipartFile("file", 
                      FileUtils.readFileToByteArray(jiraResultZip));
      zephyr.uploadAutomationResult(jiraconfiguration.getJiraProjectkey(),
                      multipart); 
      log.debug("Result uploaded to jira complete");
    } else {
      //initialize the excel file for testdata and stored all the row number of testdata start
      log.info("Excel file name " + properties.getTestdata());
      testData.setExcelfilename(properties.getTestdata());
      testData.getExcelsheetindex(0);
      constantVariable.searchTestData();
      testData.closeWorkbook();
      executetestcase(properties.getTestcase());
    }
  }

  /**
  * This method read the test data excel file row by row and execute the test case.
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
  * @param testfile is a location of test data file in which is read
  */
  private void executetestcase(String testfile) {
    int testDatarownumber = 0;

    //Read the Test case data
    log.debug("Test Case File name " + testfile);
    List<TestExcelBean> testcases = Poiji.fromExcel(new File(testfile),
                    TestExcelBean.class);  
    for (TestExcelBean testcase : testcases) {
      if (testcase.getTestExecute().equalsIgnoreCase("yes")) {
        log.info("Started Executing Test Case ID " + testcase.getTestcaseId());
        log.debug("Test Case ID " + testcase.getTestcaseId());
        log.debug("Test Case Description " + testcase.getTestcaseDescription());
        log.debug("Test Case Categeory " + testcase.getTestCatgeory());
        
        //check API or WEB Automation 
        if (properties.isAutomationType()) {
          //check if browser is closed or quite due to some exception then start the browser
          if (browserinitialize.browserStatus()) {
            browserinitialize.getWebDriverInstance();
          }
          //print the Docker URL for each test case
          browserinitialize.dockerUrl();
        } 

        recordingFilename = testcase.getTestcaseId();
        extentReport.createTest(testcase.getTestcaseId(), testcase.getTestcaseDescription());
        extentReport.categeory(testcase.getTestCatgeory());

        //check if jira integration is enable then from first row is the testcaseID
        if (properties.isJiraIntegration()) {
          //Execute the Test case ID
          log.debug("Test Case ID found and started executing " + testcase.getTestcaseId());
          testRunId(0, testcase.getTestDatalocation());
          extentReport.flushlog();
        } else {
          log.debug("Test case found in the test data file at----> "
                          + ConstantVariable.testDataRowNumber.get(testcase.getTestcaseId()));
          testDatarownumber = ConstantVariable.testDataRowNumber.get(testcase.getTestcaseId());

          //Execute the Test case ID
          log.debug("Test Case ID found and started executing " + testcase.getTestcaseId());
          testRunId(testDatarownumber, properties.getTestdata());
          extentReport.flushlog();
        }
      } else {
        log.info("Skiped the Test case " + testcase.getTestcaseId());
        extentReport.skipTest(testcase.getTestcaseId(), testcase.getTestcaseDescription());
        extentReport.categeory(testcase.getTestCatgeory());
        extentReport.flushlog();
      }
    }
    extentReport.flushlog();
    log.info("Completed Exeuction of all the Test Case i.e " + testcases.size());
    log.info("Result file are located in :- " + properties.getExtentreportlocation());
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
   * @param testdatafile is test case file which contain the keyword has to executed
   */

  private void testRunId(int rowStartfrom, String testdatafile) {

    int currentRow = rowStartfrom;
    int currentCol = 1;
    String keyword = null;

    try {

      testData.setExcelfilename(testdatafile);
      testData.getExcelsheetindex(0);

      while (testData.getCellData(currentRow, 0) == null
                      || !testData.getCellData(currentRow, 0).equalsIgnoreCase("End")) {
        keyword = testData.getCellData(currentRow, currentCol);
        log.debug("Got the Key word from excel sheet " + keyword);

        //skipping the step if keyword is "comment"
        if (!keyword.equalsIgnoreCase("comment")) {

          //Read the all the parameter for keyword
          int column = 2;
          String param = "";
          List<String> dataParam = new ArrayList<String>();

          while (testData.getCellData(currentRow, column) != null) {
            log.debug("current row number " + currentRow + " Column number " + column);
            log.debug("Data from Cell " + testData.getCellData(currentRow, column));

            param = testData.getCellData(currentRow, column);
            dataParam.add(param);
            //increment column
            column++;
          }

          keywordExecution.setvalue(KeywordType.valueOf(keyword.toUpperCase()));
          log.info("Executing the Keyword " + keyword.toUpperCase());
          keywordExecution.executed(dataParam);
          
          /*After keyword execution successfully then only below statement is executed
           else any exception are capture in catch block.
           */
          extentReport.writeLog(Status.PASS, KeywordType.valueOf(keyword.toUpperCase()).toString());
        } 
        //increment the row
        currentRow++;
      }
      
      testData.closeWorkbook();
      if (properties.isAutomationType()) {
        browserinitialize.browserRecording(recordingFilename, false);
        browserinitialize.quitBrowser();
      }
      
    } catch (AssertionError | Exception e) {
      try {
        testData.closeWorkbook();
        log.error("Got an exception while executing keyword --> " + keyword, e);
        extentReport.writeLog(Status.FAIL, "Failed executing Keyword ---> " + keyword);
        extentReport.writeLog(Status.FAIL, e);
        if (properties.isAutomationType()) {
          //need to think how to add the video link to extent report
          browserinitialize.browserRecording(recordingFilename, true);
          //extentReport.attachScreenshotBase64(genericMethod.takeScreenshot());
          extentReport.attachScreenshotPath(genericMethod.takeScreenshot());
          browserinitialize.quitBrowser();
        }
        extentReport.flushlog();
        
      } catch (IOException e1) {
        log.error("Problem while " + e1);
      }
    }
  }


  /**
   * Method is used to create a test case excel file based on the result fetch from jira.
   * @return the file location of the created test case excel file 
   * @throws IOException not able to write to file
   * @throws URISyntaxException is used to form the download URL.
   */
  private String testdatapreparation() throws IOException, URISyntaxException {

    //Prepare the test date
    //create a excel sheet with header and close the workbook
    testcaseCreation.createExcelFile(true);
    testcaseCreation.setExcelSheet("Report");
    //Excel header name
    testcaseCreation.setCreateRow(0);
    for (TestCreation creation : TestCreation.values()) {
      testcaseCreation.setCellData(creation.getColumn(), creation.getHeader());
    }
    
    List<TestCase> testcases = new ArrayList<>();
    int cellrow = 1;
    //check whether all test case are read from api 
    int startindex = 0;
    
    do {
      QueryParamSearch query =  QueryParamSearch
                       .builder()
                       .withMaxResults(jiraconfiguration.getTestcaseMaxresult())
                       .withQuery(jiraconfiguration.getTestcaseQuery())
                       .withStartAt(startindex)
                       .build();
      
      //make a request call to Jira to fetch the list of testcase
      testcases = zephyr.fetchSearchTestCase(query).getBody();
      //for next loop increase the start index 
      startindex = startindex + jiraconfiguration.getTestcaseMaxresult();
      String testscriptlocation;
      //loop the TestCase
      for (TestCase testcase : testcases) {

        //download the testscript from jira
        log.debug("testcase ID " + testcase.getTestcaseId()
            + "-----" + "projectID " + testcase.getProjectId());
        List<TestCaseAttachment> attachments = 
                        zephyr.fetchTestcaseAttachment(testcase.getTestcaseId()).getBody();
        for (TestCaseAttachment attachment : attachments) {
          if (attachment.getFilename().equalsIgnoreCase("Automation.xlsx") 
                          && attachment.getFilesize() > 0) {
            log.debug("Script file need to download");
            //download the test script assuming file has download
            testscriptlocation = properties.getTemplocation() + File.separator 
                            + testcase.testcaseId.replace("-", "") + attachment.getFilename();
            byte[] filedownload = zephyr.dowloadfile(new URI(attachment.getUrl())).getBody();
            genericMethod.copyfile(new ByteArrayInputStream(filedownload), testscriptlocation);
            // write to exel sheet 
            log.debug("writing to excel sheet");
            testcaseCreation.setCreateRow(cellrow);
            testcaseCreation.setCellData(TestCreation.TESTCASEID.getColumn(), 
                            testcase.getTestcaseId());
            testcaseCreation.setCellData(TestCreation.TESTDESCRIPTION.getColumn(), 
                            testcase.getTestCaseDescription());
            testcaseCreation.setCellData(TestCreation.TESTCATEGEORY.getColumn(), 
                            testcase.getCustomField().getCategeory());
            testcaseCreation.setCellData(TestCreation.TESTEXECUTE.getColumn(), "Yes");
            testcaseCreation.setCellData(TestCreation.TESTLOCATION.getColumn(), testscriptlocation);
            cellrow++;
          } 
        }
      }
    } while (testcases.size() == jiraconfiguration.getTestcaseMaxresult());  

    //write data from buffer to file
    String excelfilename = properties.getTemplocation() + File.separator + "automation.xlsx";
    testcaseCreation.writeWorkbook(excelfilename);
    testcaseCreation.closeWorkbook();
    //return the created testfile  
    return excelfilename;
  }

  /**
  * This method is used to prepare for email content configuration and send email.
  */
  public void emailTestResult() {
    Path excelreport = content.excelReport();
    Object object = content.maildata(excelreport);
    mail.sendEmail(object);
  }
}
