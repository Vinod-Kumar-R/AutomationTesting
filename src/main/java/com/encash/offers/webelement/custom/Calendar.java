package com.encash.offers.webelement.custom;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

public class Calendar {
  private static Logger logger = LogManager.getLogger(Calendar.class);
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  
  WebElement calendar;
  

  public void setCalendar(WebElement calendar) {
    this.calendar = calendar;
  }
  
  
  
  private void monthYearview() {
    WebElement monthyearview = genericmethod.getWebElement(calendar, "calender_monthyearview");
    monthyearview.click();
  }
  
  

  private void yearSelect(String year) {
    waitmethod.waitForElementPresent("calender_years");
    WebElement yeartable = genericmethod.getWebElement(calendar, "calender_years");
    Table table = new Table(yeartable);
    table.selectdata(year);
    
  }
  
  private void monthSelect(String month) {
    waitmethod.waitForElementPresent("calender_month");
    WebElement monthtable = genericmethod.getWebElement(calendar, "calender_month");
    Table table = new Table(monthtable);
    table.selectdata(month);
  }
  
  private void dateSelect(String date) {
    waitmethod.waitForElementPresent("calender_date");
    WebElement datetable = genericmethod.getWebElement(calendar, "calender_date");
    Table table = new Table(datetable);
    table.selectdata(date);
  }
  
  /**
   * This method is used to select the date month and year in calendar.
   * @param dates in String format date month and year
   */
  public void selectDate(String date, String month, String year) {
   
    monthYearview();
    yearSelect(year);
    monthSelect(month);
    dateSelect(date);
    
  }
  

}
