package com.automation.mail;

public enum Report {

  TESTCASEID(0, 13 * 256, "Test Case ID"),
  DESCRIPTION(1, 80 * 256, "Description"),
  CATEGEORY(2, 13 * 256, "Categeory"),
  STATUS(3, 10 * 256, "Status"),
  STARTTIME(4, 20 * 256, "Start Time"),
  ENDTIME(5, 20 * 256, "End Time"),
  EXECUTIONTIME(6, 20 * 256, "Execution Time");

  private int column;
  private String header;
  private int width;

  Report(int column, int width, String header) {
    this.column = column;
    this.header = header;
    this.width = width;
  }

  public int getColumn() {
    return column;
  }

  public String getHeader() {
    return header;
  }
  
  public int getWidth() {
    return width;
  }

}
