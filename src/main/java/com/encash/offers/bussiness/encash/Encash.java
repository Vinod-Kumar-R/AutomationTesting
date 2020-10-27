package com.encash.offers.bussiness.encash;


import com.aventstack.extentreports.Status;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

/**
 * This class contain all the Business logic for Automation. 
 * @author Vinod Kumar R
 *
 */

public class Encash {
  private static Logger logger = LogManager.getLogger(Encash.class.getName());
  private WaitMethod wm;
  private GenericMethod gm;
  private ExtentReport er;

  /**
   * In constructor variable are initialize.
   */
  public Encash() {
    wm = new WaitMethod();
    gm = new GenericMethod();
    er = BrowserInitialize.getExtentReportInstance();
  }

  /**
   * This Method is use to verify the Jishi text when user click on explore. 
   * @param stringParam 
   *     StringParam is a array of String variable which hold data 
   *     StringParam[0] contain the Object i.e xpath rest of the data 
   *     are text which need to verify in jishi page 
   * @return it return the status "pass" if execution success else  throw an exception 
   * @throws Exception throw an generic excpetion
   */
  public String jishitext(String[] stringParam) throws Exception {

    for (int i = 1; i < stringParam.length; i++) {
      String[] data = new String[2];
      data[0] = stringParam[0];
      data[1] = stringParam[i];
      wm.waitForTexttVisible(data);
      er.writeLog(Status.PASS, "Taken the screenshot", gm.takeScreenshot());
      gm.verifyText(data);
      logger.debug("Verified the test " + data[1]);
    }

    return "pass";
  }

  /**
   * This method is used to verify Banner page when user login to encashoffer page. 
   * and take the screen shot of each banner when it is active 
   * @param stringParam
   *     StringParam is array of String which contain 
   *     StringParam[0] contain the Object i.e xpath 
   *     rest of the parameter the Banner name which need to verify
   *     
   *     This verified the order of banner displayed by comparing the data from excel sheet
   *     and order in the enchashoffer page
   *
   * @return the status as "pass" if script executed success else through an exception 
   * @throws Exception throw an generic exception
   */
  public String banner(String[] stringParam) throws Exception {
    //create an List which contain Banner name so that in the end order can be verified 

    List<String> acutalBanner = new ArrayList<String>();
    List<String> expectedBanner = new ArrayList<String>();
    List<WebElement> elementList;

    //fetch all the Banner name with title and stored the name in list 
    elementList = gm.getElements(stringParam[0]);


    for (WebElement e : elementList) {
      acutalBanner.add(e.getAttribute(stringParam[2]).toString());
    }

    for (int i = 3; i < stringParam.length; i++) {
      String[] data = new String[3];
      data[0] = stringParam[1];
      data[1] = stringParam[2];
      data[2] = stringParam[i];

      expectedBanner.add(stringParam[i]);

      wm.waitForAttributedContain(data);
      er.writeLog(Status.PASS, "capture the Screen shot " + stringParam[i],
                      gm.takeScreenshot());
      gm.verifyAttributedValue(data);
      er.writeLog(Status.PASS, "verified the image " + stringParam[i]);
    }


    //compare both Actual banner and Expected list are in same order
    if (!acutalBanner.equals(expectedBanner)) {
      logger.debug("Excel Order Banner is not matching with Expected order bannber in UI");
      er.writeLog(Status.FAIL, "Excel Order Banner is not matching with "
                      + "Expected order bannber in UI");
      return "fail";
    }

    return "Pass";
  }
  
  /**
   * This method is used to create a new Registration in encash page.
   * @param stringParam contain all the required data for registration
   * @return
   */
  public String newRegistration(String[] stringParam) {
    logger.debug("waiting for loder class request complete");
    wm.waitForElementInvisible("loderclass");
    
    logger.debug("Wait for element presnet");
    wm.waitForElementVisible("persondetail_title");
    
    logger.debug("seleting the title from dorp down");
    String[] title = new String[]{"persondetail_title", stringParam[0]};
    gm.selectByVisibleText(title);
   
    logger.debug("enter the frist Name");
    WebElement element = gm.getElement("persondetail_firstname");
    element.sendKeys(stringParam[1]);
    
    logger.debug("enter the last Name");
    element = gm.getElement("personaldetail_lastname");
    element.sendKeys(stringParam[2]);

    logger.debug("Enter the Email address");
    element = gm.getElement("personaldetail_email");
    element.sendKeys(stringParam[3]);
    
    logger.debug("Select the Geneder");
    
    if (stringParam[4].equalsIgnoreCase("male")) {
      element = gm.getElement("personaldetail_male");
      element.click();
    } else if (stringParam[4].equalsIgnoreCase("female")) {
      element = gm.getElement("personaldetail_female");
      element.click();
    }
    
    logger.debug("Select date from BirthDate");
    String[] date = new String[] {"personaldetail_date", stringParam[5]};
    gm.selectByVisibleText(date);
    
    logger.debug("Select Month from BirthDate");
    String[] month = new String[] {"personaldetail_month", stringParam[6]};
    gm.selectByVisibleText(month);
    
    logger.debug("Select Year from BirthDate");
    String[] year = new String[] {"personaldetail_year", stringParam[7]};
    gm.selectByVisibleText(year);
    
    logger.debug("Enter the Password");
    element = gm.getElement("personaldetail_password");
    element.sendKeys(stringParam[8]);
    
    logger.debug("Enter the confirm password");
    element = gm.getElement("personaldetail_cofirmpassword");
    element.sendKeys(stringParam[9]);
    
    logger.debug("Enter the Display name");
    element = gm.getElement("personaldetail_displayname");
    element.sendKeys(stringParam[10]);
    
    logger.debug("Enter the Postal code");
    element = gm.getElement("personaldetail_postalcode");
    element.sendKeys(stringParam[11]);
    
    logger.debug("click on the find address");
    element = gm.getElement("personaldetail_findadress");
    element.click();
    
    logger.debug("wait for load all the address");
    String[] notpresent = new String[] {"personaldetail_findadress", "disabled"};
    wm.waitForElementAttributeNotPresent(notpresent);
    
    logger.debug("select the address from visible text");
    String[] address = new String[] {"personaldetail_address", stringParam[12]};
    gm.selectByVisibleText(address);
  
    return "pass";
  }
  
  
  /**
   * This method is used to enter the OTP password. 
   * @param stringParam contain the OTP data
   * @return
   */
  public String enterOtp(String[] stringParam) {
    
    logger.debug("waiting for class loder request complete");
    wm.waitForElementInvisible("loderclass");
    List<WebElement> otp = gm.getElements("otp");
    
    int index = 0;
    for (WebElement element : otp) {
      logger.debug("Entering the OPT for element " + stringParam[index]);
      element.sendKeys(stringParam[index]);
      index++;
    }
    
    return "pass";
  }

}
