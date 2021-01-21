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
  
  
  /**
   * This method is used to click on the Year to display it.
   */
  private void monthYearview() {
    WebElement monthyearview = genericmethod.getWebElement(calendar, "calender_monthyearview");
    monthyearview.click();
  }
  
  
  /**
   * This method is used to select the particular year. 
   * @param year should me in YYYY
   */
  private void yearSelect(String year) {
    waitmethod.waitForElementPresent("calender_years");
    WebElement yeartable = genericmethod.getWebElement(calendar, "calender_years");
    Table table = new Table(yeartable);
    table.selectdata(year);
    
  }
  
  /**
   * This method is sued to select the particular month.
   * @param month should be in MMM i.e. JAN
   */
  private void monthSelect(String month) {
    waitmethod.waitForElementPresent("calender_month");
    WebElement monthtable = genericmethod.getWebElement(calendar, "calender_month");
    Table table = new Table(monthtable);
    table.selectdata(month);
  }
  
  /**
   * This method is used to select the date in the calendar.
   * @param date should be in D i.e 1, 5 or 10
   */
  private void dateSelect(String date) {
    waitmethod.waitForElementPresent("calender_date");
    WebElement datetable = genericmethod.getWebElement(calendar, "calender_date");
    Table table = new Table(datetable);
    table.selectdata(date);
  }
  
  /**
   * This method is used to select the date month and year in calendar.
   * @param date Date in D
   * @param month Month in MMM
   * @param year Year  in YYYY
   */
  public void selectDate(String date, String month, String year) {
   
    monthYearview();
    yearSelect(year);
    monthSelect(month);
    dateSelect(date);
    
  }
}
