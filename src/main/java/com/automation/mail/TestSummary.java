package com.automation.mail;

/**
 * This enum is used to set the summary data in excel of column name and column data.
 * @author Vinod Kumar R
 *
 */
public enum TestSummary {
  
  TotalTestcaseExecuted(0, 3, "Total Test case Executed"),
  TotalDuration(6, 9, "Total Duration"),
  TotalTestcasePass(0, 3, "Total Test case Pass"),
  StartTime(6, 9, "Start Time"),
  TotalTestcaseFail(0, 3, "Total Test case Fail"),
  EndTime(6, 9, "End Time"),
  TotalSkipTestcase(0, 3,  "Total Test case Skip"),
  Browser(6, 9, "Browser"),
  OSVersion(0, 3, "OS Version"),
  BrowserVersion(6, 9, "Browser Version");
  
  
  private final String summar;
  private final int column;
  private final int nextcolumn;
  
  /**
   * constructor is used to set the value of column,nextcolumn and summar data.
   * @param column number
   * @param nextcolumn number
   * @param summar column data.
   */
  TestSummary(int column, int nextcolumn, String summar) {
    this.summar = summar;
    this.column = column;
    this.nextcolumn = nextcolumn;
  }
  
  public String getName() {
    return summar;
  }
  
  public int getColumn() {
    return column;
  }
  
  public int getNextColumn() {
    return nextcolumn;
  }

}
