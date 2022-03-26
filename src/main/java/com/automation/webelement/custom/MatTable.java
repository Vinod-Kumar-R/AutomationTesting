package com.automation.webelement.custom;

import com.automation.utility.WaitMethod;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to create custom Mat table. 
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class MatTable {
  
  @Autowired
  private WaitMethod waitmethod;
 
  WebElement table;
  

  public void setTable(WebElement table) {
    this.table = table;
  }
  
  /**
   * This method is used to get the row count of a table.
   * @return the int value 
   */
  public int getRowCount() {
    waitmethod.waitForNestedElementPresence(this.table, By.tagName("mat-row"));
    int row = table.findElements(By.tagName("mat-row")).size();
    return row;
  }
  
  /**
  * This method is used get the fist row column count.
  * @return the int value
  */
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
    log.debug("row count " + getRowCount());
    log.debug("column count " + getColumnCount());
    row:
    for (int r = 1; r <= getRowCount(); r++) {
      for (int c = 1; c <= getColumnCount(); c++) {
        celldata = table.findElement(By.xpath("//mat-row[" + r + "]/mat-cell[" + c + "]"));
        log.debug(" row " + r + " column " + c + " data " + celldata.getText());
        if (celldata.getText().equals(data)) {
          celldata.click();
          break row;
        }
      }
    }
  }

}
