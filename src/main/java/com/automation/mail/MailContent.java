package com.automation.mail;

import com.automation.beanclass.ExtentReportBean;
import com.automation.configuration.PropertiesValue;
import com.automation.utility.ExcelReader;
import com.automation.utility.ExtentReport;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Class is used for creating content of email report.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class MailContent {
  
  @Autowired
  private ExtentReport extentreport;
  
  @Autowired
  private PropertiesValue properties;
  @Autowired
  @Qualifier("excelresult")
  private ExcelReader excelreport;
  
  
  /**
  * This method is used to set the Mail content data.
  * @param excelreport :- location of Excel report file
  * @return Pojo file content of ExtentReportBean
  */
  public ExtentReportBean maildata(Path excelreport) {

    log.debug("Setting the Email body content");
    
    // zip the result file
    /*Path resultfile = genericMethod.zip(ConstantVariable.ResultLocation, 
                   ConstantVariable.ResultBaseLocation + File.separator 
                    + ConstantVariable.Foldername + ".zip");
     */   
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
    report.setBuildnumber(properties.getAutomationBuildInfo());
    report.setAutomationresult(excelreport.toFile());
    report.setBrowsername(extentreport.getSystemInfo("Browser Name"));
    report.setBrowserversion(extentreport.getSystemInfo("Browser Version"));
    report.setOs(System.getProperty("os.name"));
    report.setCategeorys(extentreport.categeoryctx());
    //report.setPassTest(extentreport.getTestDetail(Status.PASS));
    report.setFailTest(extentreport.getTestDetail(Status.FAIL));
    //report.setSkipTest(extentreport.getTestDetail(Status.SKIP));
    report.setReportLink(properties.getJenkinsReport());
    
    return report;
  }
  
  /**
   * Create a Test Excel Report.
   * @return file location in Path.
   */
  public Path excelReport() {
    try {
      excelreport.createExcelFile(true);
      summaryReport();
      testReport();

      File excelfilename = new File(properties.getResultfolder() 
                      + File.separator 
                      + "AutomatonResult.xlsx");
      excelreport.writeWorkbook(excelfilename);
      excelreport.closeWorkbook();
      return excelfilename.toPath();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * Method is used to write the summary data to excel sheet.
   */
  private void summaryReport() {
    
    CellStyle passStyle = excelreport.getStatusCell(Status.PASS);
    CellStyle failStyle = excelreport.getStatusCell(Status.FAIL);
    CellStyle skipStyle = excelreport.getStatusCell(Status.SKIP);
    final CellStyle headerstyle = excelreport.getHeaderCellStyle();
    CellStyle generalStyle = excelreport.generalStyle();
    int summaryReportRow = 0;
    
    //Excel Header for Sheet Summary Report
    excelreport.setExcelSheet("Summary Report");
    //comment the Build info data
    /*
    //Build Info Header
    excelreport.setCreateRow(summaryReportRow);
    excelreport.setMergecell(summaryReportRow, ++summaryReportRow, 0, 6);
    excelreport.setCellData(0, "BuildInfo", generalStyle);

    excelreport.setCreateRow(++summaryReportRow);

    for (BuildInfo build : BuildInfo.values()) {
      excelreport.setCellData(build.getcolumn(), build.name(), generalStyle);
    }

    excelreport.setCreateRow(++summaryReportRow);
    excelreport.setCellData(BuildInfo.Module.getcolumn(), "CEM", generalStyle);
    excelreport.setCellData(BuildInfo.BuildNo.getcolumn(), "Build no", generalStyle);
    excelreport.setCellData(BuildInfo.Branch.getcolumn(), "Avenger", generalStyle);
    excelreport.setCellData(BuildInfo.Environment.getcolumn(), "Stagging", generalStyle);
    excelreport.setCellData(BuildInfo.URL.getcolumn(), "DEV2", generalStyle);
     */
    //TEST Summary
    //Leave the 2 row blank     
    //summaryReportRow = summaryReportRow + 2;
    int startBroderRow = 0;
    startBroderRow = summaryReportRow;
    excelreport.setCreateRow(summaryReportRow);
    excelreport.setMergecell(summaryReportRow, ++summaryReportRow, 0, 11);
    excelreport.setCellData(0, "TEST SUMMARY", headerstyle);
    int mergeRowColumn = summaryReportRow;

    int i = 0;
    for (TestSummary summary : TestSummary.values()) {
      if (i % 2 == 0) {
        excelreport.setCreateRow(++summaryReportRow);
      }
      excelreport.setMergecell(summaryReportRow, summaryReportRow,
                      summary.getColumn(), summary.getColumn() + 2);
      excelreport.setCellData(summary.getColumn(), summary.getName(), generalStyle);

      summaryData(summary, summaryReportRow, summary.getNextColumn());
      i++;
    }
    
    
    //end broder 
    int middleColumn = TestSummary.TotalTestcaseExecuted.getNextColumn() + 2;
    //Merge the column.
    excelreport.setMergecell(mergeRowColumn + 1, summaryReportRow, middleColumn, middleColumn);

    excelreport.setBorder(startBroderRow, summaryReportRow, 0, 11);

    //Test Category
    //Leave the 2 row blank
    summaryReportRow = summaryReportRow + 3;
    startBroderRow = summaryReportRow;
    excelreport.setCreateRow(summaryReportRow);
    excelreport.setMergecell(summaryReportRow, ++summaryReportRow, 0, 7);
    excelreport.setCellData(0, "TEST CATEGORY", headerstyle);

    //header for Test Category
    excelreport.setCreateRow(++summaryReportRow);
    for (TestCategory testCategory : TestCategory.values()) {
      if (testCategory.getColumn() == 0) {
        excelreport.setMergecell(summaryReportRow, summaryReportRow, 
                        testCategory.getColumn(), testCategory.getColumn() + 1);
        excelreport.setCellData(testCategory.getColumn(), 
                        testCategory.getColumnHeader(), headerstyle);
      } else {
        excelreport.setCellData(testCategory.getColumn(), 
                        testCategory.getColumnHeader(), headerstyle);
      }

    }
    
    //for loop
    float totalTestCaseCategory = 0;
    int pass = 0;
    int fail = 0;
    int skip = 0;
    Set<NamedAttributeContext<Category>> categoryct = extentreport.categeoryctx();
    for (NamedAttributeContext<Category> category : categoryct) {
      excelreport.setCreateRow(++summaryReportRow);
      pass = category.getPassed();
      fail = category.getFailed();
      skip = category.getSkipped();
      totalTestCaseCategory = pass + fail + skip;

      excelreport.setMergecell(summaryReportRow, summaryReportRow, 
                      TestCategory.NAME.getColumn(), TestCategory.NAME.getColumn() + 1);
      excelreport.setCellData(TestCategory.NAME.getColumn(), 
                      category.getAttr().getName(), generalStyle);

      if (pass > 0) {

        excelreport.setCellData(TestCategory.PASSED.getColumn(), 
                        Integer.toString(pass), passStyle);

        excelreport.setCellData(TestCategory.PASSED_PERCENTAGE.getColumn(), 
                        Float.toString((pass / totalTestCaseCategory) * 100),
                        passStyle);
      } else {
        
        excelreport.setCellData(TestCategory.PASSED.getColumn(), 
                        Integer.toString(pass), generalStyle);

        excelreport.setCellData(TestCategory.PASSED_PERCENTAGE.getColumn(), 
                        Float.toString((pass / totalTestCaseCategory) * 100),
                        generalStyle);
      }

      if (fail > 0) {

        excelreport.setCellData(TestCategory.FAILED.getColumn(), 
                        Integer.toString(fail), failStyle);

        excelreport.setCellData(TestCategory.FAILED_PERCENTAGE.getColumn(), 
                        Float.toString((fail / totalTestCaseCategory) * 100), 
                        failStyle);
      } else {
        
        excelreport.setCellData(TestCategory.FAILED.getColumn(), 
                        Integer.toString(fail), generalStyle);

        excelreport.setCellData(TestCategory.FAILED_PERCENTAGE.getColumn(), 
                        Float.toString((fail / totalTestCaseCategory) * 100), 
                        generalStyle);
      }

      if (skip > 0) {
        excelreport.setCellData(TestCategory.SKIPPED.getColumn(), 
                        Integer.toString(skip), skipStyle);

        excelreport.setCellData(TestCategory.SKIPPED_PERCENTAGE.getColumn(), 
                        Float.toString((skip / totalTestCaseCategory) * 100), 
                        skipStyle);
      } else {
        
        excelreport.setCellData(TestCategory.SKIPPED.getColumn(), 
                        Integer.toString(skip), generalStyle);

        excelreport.setCellData(TestCategory.SKIPPED_PERCENTAGE.getColumn(), 
                        Float.toString((skip / totalTestCaseCategory) * 100), 
                        generalStyle);
      }
    }

    excelreport.setBorder(startBroderRow, summaryReportRow, 0, 7);
    
  }
  
  /**
   * Method is used to write the test case detail to excel sheet.
   */
  private void testReport() {
    
    CellStyle passStyle = excelreport.getStatusCell(Status.PASS);
    CellStyle failStyle = excelreport.getStatusCell(Status.FAIL);
    CellStyle skipStyle = excelreport.getStatusCell(Status.SKIP);
    CellStyle headerstyle = excelreport.getHeaderCellStyle();
    CellStyle generalStyle = excelreport.generalStyle();
    CellStyle generalTextStyle = excelreport.generalTextStyle();
    CellStyle datetimeStyle = excelreport.getdatetimeCell();
    
    //sheet name
    excelreport.setExcelSheet("Test Report");
    
    excelreport.setCreateRow(0);
    //Excel header name for Test Report
    for (Report report : Report.values()) {
      excelreport.setCellData(report.getColumn(), report.getHeader(), headerstyle);
      excelreport.setWidthHeight(report.getColumn(), report.getWidth());
    }
    
    int row = 1;
    Set<NamedAttributeContext<Category>> categoryctx = extentreport.categeoryctx();

    //Excel Data.
    for (NamedAttributeContext<Category> category : categoryctx) {

      List<Test> tests = category.getTestList();
      for (Test test : tests) {
        excelreport.setCreateRow(row);
        excelreport.setCellData(Report.TESTCASEID.getColumn(), test.getName(), generalStyle);
        excelreport.setCellData(Report.DESCRIPTION.getColumn(), 
                        test.getDescription(), generalTextStyle);
        excelreport.setCellData(Report.CATEGEORY.getColumn(), 
                        category.getAttr().getName(), generalStyle);

        if (test.getStatus().equals(Status.PASS)) {
          excelreport.setCellData(Report.STATUS.getColumn(), test.getStatus().getName(),
                          passStyle);            
        }
        if (test.getStatus().equals(Status.FAIL)) {
          excelreport.setCellData(Report.STATUS.getColumn(), test.getStatus().getName(),
                          failStyle);            
        }
        if (test.getStatus().equals(Status.SKIP)) {
          excelreport.setCellData(Report.STATUS.getColumn(), test.getStatus().getName(),
                          skipStyle);            
        }

        excelreport.setCellData(Report.STARTTIME.getColumn(), test.getStartTime(), datetimeStyle);
        excelreport.setCellData(Report.ENDTIME.getColumn(), test.getEndTime(), datetimeStyle);

        Interval interval = new Interval(test.getStartTime().getTime(),
                        test.getEndTime().getTime());
        String diff =  interval.toPeriod().getHours() + " H " 
                        + interval.toPeriod().getMinutes() + " M "
                        + interval.toPeriod().getSeconds() + " S";

        excelreport.setCellData(Report.EXECUTIONTIME.getColumn(), diff, generalStyle);
        row++;
      }
    }
    excelreport.freezePane(0, 1);
    excelreport.setBorder(0, row - 1, 0, 6);
  }
  
  /**
   * method is used to update the summary report in excel. 
   * @param summar is an Enum of type TestSummary.
   * @param row number in which data has to write.
   * @param column number in which data has to write.
   */
  private void summaryData(TestSummary summar, int row, int column) {

    String data;
    int passFailSkip = 0;
    CellStyle passStyle = excelreport.getStatusCell(Status.PASS);
    CellStyle failStyle = excelreport.getStatusCell(Status.FAIL);
    CellStyle skipStyle = excelreport.getStatusCell(Status.SKIP);
    CellStyle generalStyle = excelreport.generalStyle();

    switch (summar) {
      case TotalTestcaseExecuted: 
        data = Integer.toString(extentreport.getTotalTestcase());
        excelreport.setMergecell(row, row, column, column + 1);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case TotalDuration:
        Period period = extentreport.getDurationTime();
        data = period.getHours() + " H "
                        +  period.getMinutes() + " M " 
                        + period.getSeconds() + " S";
        excelreport.setMergecell(row, row, column, column + 2);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case TotalTestcasePass:
        passFailSkip = extentreport.getTotalTestPasscase(Status.PASS);
        data = Integer.toString(passFailSkip);
        excelreport.setMergecell(row, row, column, column + 1);
        if (passFailSkip > 0) {
          excelreport.setCellData(column, data, passStyle);          
        } else {
          excelreport.setCellData(column, data, generalStyle);
        }
        break;

      case TotalTestcaseFail:
        passFailSkip = extentreport.getTotalTestPasscase(Status.FAIL);
        data = Integer.toString(passFailSkip);
        excelreport.setMergecell(row, row, column, column + 1);
        if (passFailSkip > 0) {
          excelreport.setCellData(column, data, failStyle);          
        } else {
          excelreport.setCellData(column, data, generalStyle);
        }
        break;

      case TotalSkipTestcase:
        passFailSkip = extentreport.getTotalTestPasscase(Status.SKIP);
        data = Integer.toString(passFailSkip);
        excelreport.setMergecell(row, row, column, column + 1);
        if (passFailSkip > 0) {
          excelreport.setCellData(column, data, skipStyle);          
        } else {
          excelreport.setCellData(column, data, generalStyle);
        }
        break;

      case StartTime:
        data = extentreport.dateToString(extentreport.getStartTime());
        excelreport.setMergecell(row, row, column, column + 2);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case EndTime:
        data = extentreport.dateToString(extentreport.getEndTime());
        excelreport.setMergecell(row, row, column, column + 2);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case Browser:
        data =  extentreport.getSystemInfo("Browser Name");
        excelreport.setMergecell(row, row, column, column + 2);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case OSVersion:
        data = System.getProperty("os.name");
        excelreport.setMergecell(row, row, column, column + 1);
        excelreport.setCellData(column, data, generalStyle);
        break;

      case BrowserVersion:
        data = extentreport.getSystemInfo("Browser Version");
        excelreport.setMergecell(row, row, column, column + 2);
        excelreport.setCellData(column, data, generalStyle);
        break;

      default:
        data = "NULL";
        excelreport.setCellData(column, data, generalStyle);
        break;          
    }

  }

}
