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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/*
 * used for creating the Mail content preparation.
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
   * @return Pojo file content of ExtentReportBean
   * @throws IOException if file not found
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
    report.setBuildnumber("XXXX");
    report.setAutomationresult(excelreport.toFile());
    report.setBrowsername(extentreport.getSystemInfo("Browser Name"));
    report.setBrowserversion(extentreport.getSystemInfo("Browser Version"));
    report.setOs(System.getProperty("os.name"));
    report.setCategeorys(extentreport.categeoryctx());
    //report.setPassTest(extentreport.getTestDetail(Status.PASS));
    report.setFailTest(extentreport.getTestDetail(Status.FAIL));
    //report.setSkipTest(extentreport.getTestDetail(Status.SKIP));
    
    return report;
  }
  
  /**
   * Create a Test Excel Report.
   * @return file location in Path.
   */
  public Path excelReport() {

    int testIdColumn = 0;
    int descriptionColumn = 1;
    int categoryColumn = 2;
    int statusColumn = 3;
    int starttimeColumn = 4;
    int endtimeColumn = 5;
    int executionColumn = 6;
    
    try {
      excelreport.createExcelFile(true);
      excelreport.setExcelSheet("Sheet1");
      CellStyle headerstyle = excelreport.getHeaderCellStyle();
      CellStyle datetimeStyle = excelreport.getdatetimeCell();
      CellStyle passStyle = excelreport.getStatusCell(Status.PASS);
      CellStyle failStyle = excelreport.getStatusCell(Status.FAIL);
      CellStyle skipStyle = excelreport.getStatusCell(Status.SKIP);
      CellStyle generalStyle = excelreport.generalStyle();
      CellStyle generalTextStyle = excelreport.generalTextStyle();

      //Excel header name
      excelreport.setCreateRow(0);
      excelreport.setCellData(testIdColumn, "Test Case ID", headerstyle);
      excelreport.setCellData(descriptionColumn, "Description", headerstyle);
      excelreport.setCellData(categoryColumn, "Categeory", headerstyle);
      excelreport.setCellData(statusColumn, "Status", headerstyle);
      excelreport.setCellData(starttimeColumn, "Start Time", headerstyle);
      excelreport.setCellData(endtimeColumn, "End Time", headerstyle);
      excelreport.setCellData(executionColumn, "Execution Time", headerstyle);
      int row = 1;
      Set<NamedAttributeContext<Category>> categoryctx = extentreport.categeoryctx();

      for (NamedAttributeContext<Category> category : categoryctx) {

        List<Test> tests = category.getTestList();
        for (Test test : tests) {
          excelreport.setCreateRow(row);
          excelreport.setCellData(testIdColumn, test.getName(), generalStyle);
          excelreport.setCellData(descriptionColumn, test.getDescription(), generalTextStyle);
          excelreport.setCellData(categoryColumn, category.getAttr().getName(), generalStyle);
          
          if (test.getStatus().equals(Status.PASS)) {
            excelreport.setCellData(statusColumn, test.getStatus().getName(),
                            passStyle);            
          }
          if (test.getStatus().equals(Status.FAIL)) {
            excelreport.setCellData(statusColumn, test.getStatus().getName(),
                            failStyle);            
          }
          if (test.getStatus().equals(Status.SKIP)) {
            excelreport.setCellData(statusColumn, test.getStatus().getName(),
                            skipStyle);            
          }
          
          excelreport.setCellData(starttimeColumn, test.getStartTime(), datetimeStyle);
          excelreport.setCellData(endtimeColumn, test.getEndTime(), datetimeStyle);

          Interval interval = new Interval(test.getStartTime().getTime(),
                          test.getEndTime().getTime());
          String diff =  interval.toPeriod().getHours() + " H " 
                          + interval.toPeriod().getMinutes() + " M "
                          + interval.toPeriod().getSeconds() + " S";

          excelreport.setCellData(executionColumn, diff, generalStyle);
          row++;
        }
      }
      excelreport.setWidthHeight();
      excelreport.freezePane(0, 1);
      excelreport.setBorder(0, row - 1, 0, 6);
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

}
