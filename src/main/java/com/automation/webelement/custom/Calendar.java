package com.automation.webelement.custom;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class is used to custom calender to select on webpage.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class Calendar {
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private WaitMethod waitmethod;
  @Autowired
  private Table table;
  
  WebElement calendar;
  

  public void setCalendar(WebElement calendar) {
    this.calendar = calendar;
  }
  
  
  /**
   * This method is used to click on the Year to display it.
   */
  private void monthYearview() {
    log.debug("click on the montyyearview button");
    waitmethod.waitForElementPresent("calender_monthyearview");
    WebElement monthyearview = genericmethod.getWebElement(calendar, "calender_monthyearview");
    waitmethod.waitForElementClickable(monthyearview);
    monthyearview.click();
    log.debug("click on the month year view button");
  }
  
  
  /**
   * This method is used to select the particular year. 
   * @param year should me in YYYY
   */
  private void yearSelect(String year) {
    log.debug("click on the year table");
    waitmethod.waitForElementPresent("calender_years");
    WebElement yeartable = genericmethod.getWebElement(calendar, "calender_years");
    waitmethod.waitForElementClickable(yeartable);
    table.setTable(yeartable);
    table.selectdata(year);
    
  }
  
  /**
   * This method is sued to select the particular month.
   * @param month should be in MMM i.e. JAN
   */
  private void monthSelect(String month) {
    log.debug("click on the Month table");
    waitmethod.waitForElementPresent("calender_month");
    WebElement monthtable = genericmethod.getWebElement(calendar, "calender_month");
    table.setTable(monthtable);
    table.selectdata(month);
  }
  
  /**
   * This method is used to select the date in the calendar.
   * @param date should be in D i.e 1, 5 or 10
   */
  private void dateSelect(String date) {
    log.debug("clicking on the date table");
    waitmethod.waitForElementPresent("calender_date");
    WebElement datetable = genericmethod.getWebElement(calendar, "calender_date");
    table.setTable(datetable);
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
