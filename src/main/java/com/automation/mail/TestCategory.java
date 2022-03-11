package com.automation.mail;

public enum TestCategory {
  NAME(0, "NAME"),
  PASSED(2, "PASS"),
  FAILED(3, "FAIL"),
  SKIPPED(4, "SKIP"),
  PASSED_PERCENTAGE(5, "PASS %"),
  FAILED_PERCENTAGE(6, "FAIL %"),
  SKIPPED_PERCENTAGE(7, "SKIP %");
  
  int column;
  String columnHeader;
  
  TestCategory(int column, String columnHeader) {
    this.column = column;
    this.columnHeader = columnHeader;
  }
  
  public int getColumn() {
    return column;
  }
  
  public String getColumnHeader() {
    return columnHeader;
  }

}
