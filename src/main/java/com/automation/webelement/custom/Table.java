package com.automation.webelement.custom;

import com.automation.utility.WaitMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Table {
  
  private static Logger logger = LogManager.getLogger(Table.class);

  @Autowired
  private WaitMethod waitmethod;
  
  WebElement table;
  
  public void setTable(WebElement table) {
    this.table = table;
  }

  /**
   * This method is used to get the table row count.
   * @return the int value
   */
  public int getRowCount() {
    logger.debug("wait for the table row to present");
    waitmethod.waitForNestedElementPresence(this.table, By.tagName("tr"));
    int row = table.findElements(By.tagName("tr")).size();
    return row;
  }
  
  /**
   * this method is used get the column count for 1st row.
   * @return the int value
   */
  public int getColumnCount() {
    int column = table.findElements(By.xpath("tr[1]/td")).size();
    return column;
  }
  
  /**
   * This method is used get the column count for particular row.
   * @param i is the row number.
   * @return the int value
   */
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
