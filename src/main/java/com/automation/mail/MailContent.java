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

    try {
      excelreport.createExcelFile(true);
      excelreport.setExcelSheet("Sheet1");
      CellStyle headerstyle = excelreport.getHeaderCellStyle();
      CellStyle datetimeStyle = excelreport.getdatetimeCell();
      CellStyle passStyle = excelreport.getStatusCell(Status.PASS);
      CellStyle failStyle = excelreport.getStatusCell(Status.FAIL);
      CellStyle skipStyle = excelreport.getStatusCell(Status.SKIP);

      //Excel header name
      excelreport.setCreateRow(0);
      excelreport.setCellData(0, "Test Case ID", headerstyle);
      excelreport.setCellData(1, "Description", headerstyle);
      excelreport.setCellData(2, "Categeory", headerstyle);
      excelreport.setCellData(3, "Status", headerstyle);
      excelreport.setCellData(4, "Image Available", headerstyle);
      excelreport.setCellData(5, "Start Time", headerstyle);
      excelreport.setCellData(6, "End Time", headerstyle);
      excelreport.setCellData(7, "Execution Time", headerstyle);
      int row = 1;
      Set<NamedAttributeContext<Category>> categoryctx = extentreport.categeoryctx();

      for (NamedAttributeContext<Category> category : categoryctx) {

        List<Test> tests = category.getTestList();
        for (Test test : tests) {
          excelreport.setCreateRow(row);
          excelreport.setCellData(0, test.getName());
          excelreport.setCellData(1, test.getDescription());
          excelreport.setCellData(2, category.getAttr().getName());
          
          if (test.getStatus().equals(Status.PASS)) {
            excelreport.setCellData(3, test.getStatus().getName(), passStyle);            
          }
          if (test.getStatus().equals(Status.FAIL)) {
            excelreport.setCellData(3, test.getStatus().getName(), failStyle);            
          }
          if (test.getStatus().equals(Status.SKIP)) {
            excelreport.setCellData(3, test.getStatus().getName(), skipStyle);            
          }
          
          excelreport.setCellData(4, Boolean.toString(test.hasScreenCapture()));
          excelreport.setCellData(5, test.getStartTime(), datetimeStyle);
          excelreport.setCellData(6, test.getEndTime(), datetimeStyle);

          Interval interval = new Interval(test.getStartTime().getTime(),
                          test.getEndTime().getTime());
          String diff =  interval.toPeriod().getHours() + " H " 
                          + interval.toPeriod().getMinutes() + " M "
                          + interval.toPeriod().getSeconds() + " S";

          excelreport.setCellData(7, diff);
          row++;
        }
      }
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
