package com.automation.beanclass;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

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
