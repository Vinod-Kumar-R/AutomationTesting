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
  static Logger logger = LogManager.getLogger(Encash.class.getName());
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

      wm.waitForAttributedPrent(data);
      er.writeLog(Status.PASS, "capture the Screen shot " + stringParam[i], gm.takeScreenshot());
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

}
