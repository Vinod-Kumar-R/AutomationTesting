package com.automation.beanclass;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

/**
 * This pojo class is used to read the test case excel file.
 * @author Vinod Kumar R
 *
 */
@ExcelSheet("Sheet1")
@Getter
@Setter
public class TestExcelBean {
  
  @ExcelCellName("Test Case ID")
  private String testcaseId;
  
  @ExcelCellName("Test Case Description")
  private String testcaseDescription;
  
  @ExcelCellName("Test Categeory")
  private String testCatgeory;
  
  @ExcelCellName("Executed")
  private String testExecute;
  
  @ExcelCellName("Test Data Location")
  private String testDatalocation;
}
