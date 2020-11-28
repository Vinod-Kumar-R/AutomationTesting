package com.encash.offers.mobile.encash;

import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import com.encash.offers.webdriver.BrowserInitialize;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;


public class MobileEncash {
  
  private static Logger logger = LogManager.getLogger(MobileEncash.class.getName());
  private WaitMethod waitMethod;
  private GenericMethod genericMethod;
  private ExtentReport extentReport;

  /**
   * This method is constructor used to initialize variable.
   */
  public MobileEncash() {
    waitMethod = new WaitMethod();
    genericMethod = new GenericMethod();
    extentReport = BrowserInitialize.getExtentReportInstance();
  }
  
  /**
   * This method is used to register new user. 
   * @param stringParam StringParam[0] contain the Mobile number 
   *     StringParam[1] contain the OTP
   * @return the status as "pass" if script executed success else "fail"
   */
  public String registerUsingMobileNumber(String[] stringParam) {

    logger.debug("waiting for register button enable");
    waitMethod.waitForElementClickable("m_register");

    logger.debug("clicking on the Register button");
    WebElement element = genericMethod.getElement("m_register");
    element.click();


    logger.debug("waiting for mobile number visiable");
    waitMethod.waitForElementVisible("mobilenumber");

    logger.debug("Entering the mobile number");
    element = genericMethod.getElement("mobilenumber");
    element.sendKeys(stringParam[0]);


    logger.debug("clicking on the continue button");
    element = genericMethod.getElement("continue");
    element.click();

    logger.debug("waiting for loder class request complete");
    waitMethod.waitForElementInvisible("loderclass");

    char[] otp = stringParam[1].toCharArray();

    List<WebElement> elements = genericMethod.getElements("otp");
    int index = 0;
    for (WebElement otpElement : elements) {
      logger.debug("Entering the OPT for element " + otp[index]);
      otpElement.sendKeys(Character.toString(otp[index]));
      index++;
    }

    logger.debug("click on the continue Button");
    element = genericMethod.getElement("otpcontinue");
    element.click();

    logger.debug("waiting for loder class request complete");
    waitMethod.waitForElementInvisible("loderclass");

    return "pass";

  }

}
