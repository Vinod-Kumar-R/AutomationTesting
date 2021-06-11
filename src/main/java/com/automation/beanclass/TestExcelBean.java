package com.automation.beanclass;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("Sheet1")
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
  
  public String getTestDatalocation() {
    return testDatalocation;
  }

  public void setTestDatalocation(String testDatalocation) {
    this.testDatalocation = testDatalocation;
  }

  public String getTestcaseId() {
    return testcaseId;
  }

  public void setTestcaseId(String testcaseId) {
    this.testcaseId = testcaseId;
  }

  public String getTestcaseDescription() {
    return testcaseDescription;
  }

  public void setTestcaseDescription(String testcaseDescription) {
    this.testcaseDescription = testcaseDescription;
  }

  public String getTestCatgeory() {
    return testCatgeory;
  }

  public void setTestCatgeory(String testCatgeory) {
    this.testCatgeory = testCatgeory;
  }

  public String getTestExecute() {
    return testExecute;
  }

  public void setTestExecute(String testExecute) {
    this.testExecute = testExecute;
  }
}
