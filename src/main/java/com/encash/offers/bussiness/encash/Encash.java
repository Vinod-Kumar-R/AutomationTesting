package com.encash.offers.bussiness.encash;


import com.aventstack.extentreports.Status;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.io.IOException;
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
   * @return it return the status "pass" if execution success else  "fail" 
   *
   */
  public String jishitext(String[] stringParam)  {

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
   * @return the status as "pass" if script executed success else "fail"
   */
  public String banner(String[] stringParam)  {
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
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String registrationForm(String[] stringParam) {
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
   * This method is used to register the new user through Mobile number.
   * @param stringParam contain the Mobile number and OTP
   * @return the status as "pass" if script executed success else "fail"
   */
  public String registerUsingMobileNumber(String[] stringParam) {

    logger.debug("waiting for register button enable");
    wm.waitForElementClickable("register");

    logger.debug("clicking on the Register button");
    WebElement element = gm.getElement("register");
    element.click();


    logger.debug("waiting for mobile number visiable");
    wm.waitForElementVisible("mobilenumber");

    logger.debug("Entering the mobile number");
    element = gm.getElement("mobilenumber");
    element.sendKeys(stringParam[0]);


    logger.debug("clicking on the continue button");
    element = gm.getElement("continue");
    element.click();

    logger.debug("waiting for loder class request complete");
    wm.waitForElementInvisible("loderclass");

    char[] otp = stringParam[1].toCharArray();

    List<WebElement> elements = gm.getElements("otp");
    int index = 0;
    for (WebElement otpElement : elements) {
      logger.debug("Entering the OPT for element " + otp[index]);
      otpElement.sendKeys(Character.toString(otp[index]));
      index++;
    }

    logger.debug("click on the continue Button");
    element = gm.getElement("otpcontinue");
    element.click();

    logger.debug("waiting for loder class request complete");
    wm.waitForElementInvisible("loderclass");

    return "pass";

  }

  /**
   * This method is used to check for consent for particular user.
   * @param stringParam contain true or false to check all the consent are checked or unchecked
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String consent(String[] stringParam) {
    logger.debug("wait for Consent table visible");
    wm.waitForElementPresent("consent_table");

    List<WebElement> elements = gm.getElements("consent_table");
    //Get the number of row in the parent consent table
    for (WebElement element: elements) {
      element.click();
      List<WebElement> child = gm.getWebElements(element, "consent_child");
      logger.debug("no of child row " + child.size());
      //each row has number of client information 
      for (WebElement ele :child) {
        List<WebElement> childconsent = gm.getWebElements(ele, "consent_child_type");
        logger.debug("child consent type count " + childconsent.size());
        //each client information has different consent
        for (WebElement consentchild :childconsent) {
          logger.debug(consentchild.getText());
          WebElement childstatus = gm.getWebElement(consentchild, "consent_child_status");
          logger.debug(childstatus.getAttribute("aria-checked"));
          String status = childstatus.getAttribute("aria-checked");
          if (!stringParam[0].equalsIgnoreCase(status)) {
            return "fail"; 
          }
        }
      }
    }
    
    //consent save or cancel
    WebElement element;
    
    if (stringParam[1].equalsIgnoreCase("save")) {
      element = gm.getElement("");
      element.click();
      
      
    } else if (stringParam[1].equalsIgnoreCase("cancel")) {
      element = gm.getElement("consent_cancel");
      element.click();
    }
    
    return "pass";
  }
}
