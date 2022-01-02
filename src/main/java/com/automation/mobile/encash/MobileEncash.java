package com.automation.mobile.encash;

import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MobileEncash {

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
    log.debug("click on the menu button");
    encashMenu(menu);


    log.debug("waiting for mobile number visiable");
    waitmethod.waitForElementPresent("mobilenumber");
    
    log.debug("Entering the mobile number");
    WebElement element = genericmethod.getElement("mobilenumber");
    element.sendKeys(dataParam.get(0));


    log.debug("clicking on the continue button");
    element = genericmethod.getElement("continue");
    element.click();

    log.debug("waiting for loder class request complete");
    //waitMethod.waitForElementPresent("loderclass");
    waitmethod.waitForElementInvisible("loderclass");

    char[] otp = dataParam.get(1).toCharArray();

    log.debug("Waiting for the OTP element");
    waitmethod.waitForElementPresent("otp");
    
    List<WebElement> elements = genericmethod.getElements("otp");
    int index = 0;
    for (WebElement otpElement : elements) {
      log.debug("Entering the OPT for element " + otp[index]);
      
      otpElement.sendKeys(Character.toString(otp[index]));
      index++;
    }

    log.debug("click on the continue Button");
    element = genericmethod.getElement("otpcontinue");
    element.click();

    log.debug("waiting for loder class request complete");
    waitmethod.waitForElementInvisible("loderclass");
  }
  
  /**
   * This method is used for mobile encash menu bar
   * and click on the required option.
   * @param dataParam contian which option need to select
   */
  public void encashMenu(List<String> dataParam) {
    
    log.debug("waiting for the navibar button");
    waitmethod.waitForElementVisible("navibar");
    
    log.debug("click on the navibar button");
    WebElement element = genericmethod.getElement("navibar");
    element.click();
    
    log.debug("waiting for button enable " + dataParam.get(0));
    waitmethod.waitForElementPresent("mobile_listmenu");
    waitmethod.waitThread();
    
    log.debug("click on the button " + dataParam.get(0));
    element = genericmethod.getElement(dataParam.get(0));
    waitmethod.waitForElementClickable(element);
    element.click();
    
  }
  
  
  /**
   * This method is used to click on the participate button.
   */
  public void competationparticpate() {
    
    log.debug("wait for competition button present");
    waitmethod.waitForElementPresent("competition_play");
    
    log.debug("click on the Participate button");
    WebElement element = genericmethod.getElement("competition_play");
    
    log.debug("scroll to element view");
    genericmethod.scrolltoelementBottom(element);
    
    log.debug("click on the Participate button");
    waitmethod.waitForElementClickable(element);
    element.click();
    
    log.debug("Waiting for the lodercontainer invisiable");
    waitmethod.waitForElementInvisible("lodercontainer");
  }
  
}
