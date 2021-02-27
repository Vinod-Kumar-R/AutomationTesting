package com.encash.offers.mobile.encash;

import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;


public class MobileEncash {
  
  private static Logger logger = LogManager.getLogger(MobileEncash.class.getName());
  @Autowired
  private WaitMethod waitMethod;
  @Autowired
  private GenericMethod genericMethod;


  /**
   * This method is used to register new user. 
   * @param dataParam dataParam[0] contain the Mobile number 
   *     dataParam[1] contain the OTP
   * @return the status as "pass" if script executed success else "fail"
   */
  public String registerUsingMobileNumber(List<String> dataParam) {

    List<String> menu = new ArrayList<String>();
    menu.add("m_register");
    logger.debug("click on the menu button");
    encashMenu(menu);


    logger.debug("waiting for mobile number visiable");
    waitMethod.waitForElementPresent("mobilenumber");
    
    logger.debug("Entering the mobile number");
    WebElement element = genericMethod.getElement("mobilenumber");
    element.sendKeys(dataParam.get(0));


    logger.debug("clicking on the continue button");
    element = genericMethod.getElement("continue");
    element.click();

    logger.debug("waiting for loder class request complete");
    //waitMethod.waitForElementPresent("loderclass");
    waitMethod.waitForElementInvisible("loderclass");

    char[] otp = dataParam.get(1).toCharArray();

    logger.debug("Waiting for the OTP element");
    waitMethod.waitForElementPresent("otp");
    
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
  
  /**
   * This method is used for mobile encash menu bar
   * and click on the required option.
   * @param dataParam contian which option need to select
   */
  public void encashMenu(List<String> dataParam) {
    
    logger.debug("waiting for the navibar button");
    waitMethod.waitForElementVisible("navibar");
    
    logger.debug("click on the navibar button");
    WebElement element = genericMethod.getElement("navibar");
    element.click();
    
    logger.debug("waiting for button enable " + dataParam.get(0));
    waitMethod.waitForElementPresent("mobile_listmenu");
    waitMethod.waitThread();
    
    logger.debug("click on the button " + dataParam.get(0));
    element = genericMethod.getElement(dataParam.get(0));
    waitMethod.waitForElementClickable(element);
    element.click();
    
  }

}
