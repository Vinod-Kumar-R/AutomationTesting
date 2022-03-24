package com.automation.mail;

/**
 * This class is used to capture detail about the Build info in which automation script are running.
 * @author Vinod Kumar R
 *
 */
public enum BuildInfo {

  Module(0),
  BuildNo(1),
  Branch(2),
  Environment(3),
  URL(4);
  
  private final int column;
  
  BuildInfo(int column) {
    this.column = column;
  }
  
  public int getcolumn() {
    return column;
  }
  
}

