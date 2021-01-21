package com.encash.offers.webelement.custom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MatTable {
  
  private static Logger logger = LogManager.getLogger(MatTable.class);
  WebElement table;
  
  public MatTable(WebElement table) {
    super();
    this.table = table;
  }
  
  public int getRowCount() {
    int row = table.findElements(By.tagName("mat-row")).size();
    return row;
  }
  
  public int getColumnCount() {
    int column = table.findElements(By.xpath("mat-row[1]/mat-cell")).size();
    return column;
  }
  
  /**
   * This method is used to click on the text in particular cell.
   * @param data in which it has to click
   */
  public void selectdata(String data) {
    
    WebElement celldata;
    logger.debug("row count " + getRowCount());
    logger.debug("column count " + getColumnCount());
    row:
    for (int r = 1; r <= getRowCount(); r++) {
      column:
      for (int c = 1; c <= getColumnCount(); c++) {
        celldata = table.findElement(By.xpath("//mat-row[" + r + "]/mat-cell[" + c + "]"));
        logger.debug(" row " + r + " column " + c + " data " + celldata.getText());
        if (celldata.getText().equals(data)) {
          celldata.click();
          break row;
        }
      }
    }
  }
  

}
