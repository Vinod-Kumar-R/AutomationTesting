package com.automation.mobile.encash;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MobileEncash {
  
  private static Logger logger = LogManager.getLogger(MobileEncash.class.getName());
  @Autowired
  private WaitMethod waitmethod;
  @Autowired
  private GenericMethod genericmethod;


  /**
   * This method is used to register new user. 
   * @param dataParam dataParam[0] contain the Mobile number 
   *     dataParam[1] contain the OTP
   */
  public void registerUsingMobileNumber(List<String> dataParam) {

    List<String> menu = new ArrayList<String>();
    menu.add("m_register");
    logger.debug("click on the menu button");
    encashMenu(menu);


    logger.debug("waiting for mobile number visiable");
    waitmethod.waitForElementPresent("mobilenumber");
    
    logger.debug("Entering the mobile number");
    WebElement element = genericmethod.getElement("mobilenumber");
    element.sendKeys(dataParam.get(0));


    logger.debug("clicking on the continue button");
    element = genericmethod.getElement("continue");
    element.click();

    logger.debug("waiting for loder class request complete");
    //waitMethod.waitForElementPresent("loderclass");
    waitmethod.waitForElementInvisible("loderclass");

    char[] otp = dataParam.get(1).toCharArray();

    logger.debug("Waiting for the OTP element");
    waitmethod.waitForElementPresent("otp");
    
    List<WebElement> elements = genericmethod.getElements("otp");
    int index = 0;
    for (WebElement otpElement : elements) {
      logger.debug("Entering the OPT for element " + otp[index]);
      
      otpElement.sendKeys(Character.toString(otp[index]));
      index++;
    }

    logger.debug("click on the continue Button");
    element = genericmethod.getElement("otpcontinue");
    element.click();

    logger.debug("waiting for loder class request complete");
    waitmethod.waitForElementInvisible("loderclass");
  }
  
  /**
   * This method is used for mobile encash menu bar
   * and click on the required option.
   * @param dataParam contian which option need to select
   */
  public void encashMenu(List<String> dataParam) {
    
    logger.debug("waiting for the navibar button");
    waitmethod.waitForElementVisible("navibar");
    
    logger.debug("click on the navibar button");
    WebElement element = genericmethod.getElement("navibar");
    element.click();
    
    logger.debug("waiting for button enable " + dataParam.get(0));
    waitmethod.waitForElementPresent("mobile_listmenu");
    waitmethod.waitThread();
    
    logger.debug("click on the button " + dataParam.get(0));
    element = genericmethod.getElement(dataParam.get(0));
    waitmethod.waitForElementClickable(element);
    element.click();
    
  }
  
  
  /**
   * This method is used to click on the participate button.
   */
  public void competationparticpate() {
    
    logger.debug("wait for competition button present");
    waitmethod.waitForElementPresent("competition_play");
    
    logger.debug("click on the Participate button");
    WebElement element = genericmethod.getElement("competition_play");
    
    logger.debug("scroll to element view");
    genericmethod.scrolltoelementBottom(element);
    
    logger.debug("click on the Participate button");
    waitmethod.waitForElementClickable(element);
    element.click();
    
    logger.debug("Waiting for the lodercontainer invisiable");
    waitmethod.waitForElementInvisible("lodercontainer");
  }
  
}
