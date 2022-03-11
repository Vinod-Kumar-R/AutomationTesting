package com.automation.baseframework;

public enum TestCreation {

  TESTCASEID(0, "Test Case ID"),
  TESTDESCRIPTION(1, "Test Case Description"),
  TESTCATEGEORY(2, "Test Categeory"),
  TESTEXECUTE(3, "Executed"),
  TESTLOCATION(4, "Test Data Location");
  
  int column;
  String header;
  
  TestCreation(int column, String header) {
    this.column = column;
    this.header = header;
  }
  
  public int getColumn() {
    return column;
  }
  
  public String getHeader() {
    return header;
  }
}
