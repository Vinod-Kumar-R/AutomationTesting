package com.encash.offers.webelement.custom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Table {
  
  private static Logger logger = LogManager.getLogger(Table.class);
  WebElement table;

  public Table(WebElement table) {
    super();
    this.table = table;
  }
  
  
  public int getRowCount() {
    int row = table.findElements(By.tagName("tr")).size();
    return row;
  }
  
  public int getColumnCount() {
    int column = table.findElements(By.xpath("tr[1]/td")).size();
    return column;
  }
  
  public int getColumnCount(int i) {
    int column = table.findElements(By.xpath("tr[" + i + "]/td")).size();
    return column;
  }
 
  /**
   * This method is used to click on the data in table.
   * @param data in which user has to click on
   */
  public void selectdata(String data) {
    
    WebElement celldata;
    logger.debug("row count " + getRowCount());
    logger.debug("column count " + getColumnCount());
    row:
    for (int r = 1; r <= getRowCount(); r++) {
      column:
      for (int c = 1; c <= getColumnCount(r); c++) {
        celldata = table.findElement(By.xpath("//tr[" + r + "]/td[" + c + "]"));
        logger.debug(" row " + r + " column " + c + " data " + celldata.getText());
        if (celldata.getText().equals(data)) {
          celldata.click();
          break row;
        }
      }
    }
  }
}
