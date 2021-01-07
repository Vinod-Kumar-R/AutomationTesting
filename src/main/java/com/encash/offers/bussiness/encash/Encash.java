package com.encash.offers.bussiness.encash;


import com.aventstack.extentreports.Status;
import com.encash.offers.configuration.ApplicationStoreValue;
import com.encash.offers.utility.ExtentReport;
import com.encash.offers.utility.GenericMethod;
import com.encash.offers.utility.WaitMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class contain all the Business logic for Automation. 
 * @author Vinod Kumar R
 *
 */

public class Encash {
  private static Logger logger = LogManager.getLogger(Encash.class.getName());
  @Autowired
  private WaitMethod waitMethod;
  @Autowired
  private GenericMethod genericMethod;
  @Autowired
  private ExtentReport extentReport;
  @Autowired
  private ApplicationStoreValue storevalue;


  /**
   * This Method is use to verify the Jishi text when user click on explore. 
   * @param dataParam 
   *     dataParam is a list of String variable which hold data 
   *     dataParam[0] contain the Object i.e xpath rest of the data 
   *     are text which need to verify in jishi page 
   * @return it return the status "pass" if execution success else  "fail" 
   *
   */
  public String jishitext(List<String> dataParam)  {

    for (int i = 1; i < dataParam.size(); i++) {
      List<String> data = new ArrayList<String>();
      data.add(dataParam.get(0));
      data.add(dataParam.get(i));
      waitMethod.waitForTexttVisible(data);
      extentReport.writeLog(Status.PASS, "Taken the screenshot", genericMethod.takeScreenshot());
      genericMethod.verifyText(data);
      logger.debug("Verified the test " + data.get(1));
    }

    return "pass";
  }

  /**
   * This method is used to verify Banner page when user login to encashoffer page. 
   * and take the screen shot of each banner when it is active 
   * @param dataParam
   *     dataParam is list of String array  which contain 
   *     dataParam[0] contain the Object i.e xpath 
   *     rest of the parameter the Banner name which need to verify
   *     
   *     This verified the order of banner displayed by comparing the data from excel sheet
   *     and order in the enchashoffer page
   *
   * @return the status as "pass" if script executed success else "fail"
   */
  public String banner(List<String> dataParam)  {
    //create an List which contain Banner name so that in the end order can be verified 

    List<String> acutalBanner = new ArrayList<String>();
    List<String> expectedBanner = new ArrayList<String>();
    List<WebElement> elementList;

    //fetch all the Banner name with title and stored the name in list 
    elementList = genericMethod.getElements(dataParam.get(0));


    for (WebElement e : elementList) {
      acutalBanner.add(e.getAttribute(dataParam.get(2)).toString());
    }

    for (int i = 3; i < dataParam.size(); i++) {
      List<String> data = new ArrayList<String>();
      data.add(dataParam.get(1));
      data.add(dataParam.get(2));
      data.add(dataParam.get(i));

      expectedBanner.add(dataParam.get(i));

      waitMethod.waitForAttributedContain(data);
      extentReport.writeLog(Status.PASS, "capture the Screen shot " + dataParam.get(i),
                      genericMethod.takeScreenshot());
      genericMethod.verifyAttributedValue(data);
      extentReport.writeLog(Status.PASS, "verified the image " + dataParam.get(i));
    }


    //compare both Actual banner and Expected list are in same order
    if (!acutalBanner.equals(expectedBanner)) {
      logger.debug("Excel Order Banner is not matching with Expected order bannber in UI");
      extentReport.writeLog(Status.FAIL, "Excel Order Banner is not matching with "
                      + "Expected order bannber in UI");
      return "fail";
    }

    return "Pass";
  }

  /**
   * This method is used to create a new Registration in encash page.
   * @param dataParam contain all the required data for registration
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String registrationForm(List<String> dataParam) {
    logger.debug("waiting for loder class request complete");
    waitMethod.waitForElementInvisible("loderclass");

    logger.debug("Wait for element presnet");
    waitMethod.waitForElementVisible("persondetail_title");

    logger.debug("seleting the title from dorp down");
    
    List<String> title = new ArrayList<String>();
    title.add("persondetail_title");
    title.add(dataParam.get(0));
    
    genericMethod.selectByVisibleText(title);

    logger.debug("enter the frist Name");
    WebElement element = genericMethod.getElement("persondetail_firstname");
    element.sendKeys(dataParam.get(1));

    logger.debug("enter the last Name");
    element = genericMethod.getElement("personaldetail_lastname");
    element.sendKeys(dataParam.get(2));

    logger.debug("Enter the Email address");
    element = genericMethod.getElement("personaldetail_email");
    element.sendKeys(dataParam.get(3));

    logger.debug("Select the Geneder");

    if (dataParam.get(4).equalsIgnoreCase("male")) {
      element = genericMethod.getElement("personaldetail_male");
      element.click();
    } else if (dataParam.get(4).equalsIgnoreCase("female")) {
      element = genericMethod.getElement("personaldetail_female");
      element.click();
    }

    logger.debug("Select date from BirthDate");
    
    List<String> date = new ArrayList<String>();
    date.add("personaldetail_date");
    date.add(dataParam.get(5));
    
    genericMethod.selectByVisibleText(date);

    logger.debug("Select Month from BirthDate");
    
    List<String> month = new ArrayList<String>();
    month.add("personaldetail_month");
    month.add(dataParam.get(6));
    
    genericMethod.selectByVisibleText(month);

    logger.debug("Select Year from BirthDate");
    List<String> year = new ArrayList<String>();
    year.add("personaldetail_year");
    year.add(dataParam.get(7));
    
    genericMethod.selectByVisibleText(year);

    logger.debug("Enter the Password");
    element = genericMethod.getElement("personaldetail_password");
    element.sendKeys(dataParam.get(8));

    logger.debug("Enter the confirm password");
    element = genericMethod.getElement("personaldetail_cofirmpassword");
    element.sendKeys(dataParam.get(9));

    logger.debug("Enter the Display name");
    element = genericMethod.getElement("personaldetail_displayname");
    element.sendKeys(dataParam.get(10));

    logger.debug("Enter the Postal code");
    element = genericMethod.getElement("personaldetail_postalcode");
    element.sendKeys(dataParam.get(11));

    logger.debug("click on the find address");
    element = genericMethod.getElement("personaldetail_findadress");
    element.click();

    logger.debug("wait for load all the address");
    waitMethod.waitForElementAttributeNotPresent("personaldetail_findadress", "disabled");

    logger.debug("select the address from visible text");
   
    List<String> address = new ArrayList<String>();
    address.add("personaldetail_address");
    address.add(dataParam.get(12));
   
    genericMethod.selectByVisibleText(address);

    return "pass";
  }

  /**
   * This method is used to register the new user through Mobile number.
   * @param dataParam contain the Mobile number and OTP
   * @return the status as "pass" if script executed success else "fail"
   */
  public String registerUsingMobileNumber(List<String> dataParam) {

    logger.debug("waiting for register button enable");
    waitMethod.waitForElementClickable("register");

    logger.debug("clicking on the Register button");
    WebElement element = genericMethod.getElement("register");
    element.click();


    logger.debug("waiting for mobile number visiable");
    waitMethod.waitForElementVisible("mobilenumber");

    logger.debug("Entering the mobile number");
    element = genericMethod.getElement("mobilenumber");
    element.sendKeys(dataParam.get(0));


    logger.debug("clicking on the continue button");
    element = genericMethod.getElement("continue");
    element.click();

    logger.debug("waiting for loder class request complete");
    waitMethod.waitForElementInvisible("loderclass");

    char[] otp = dataParam.get(1).toCharArray();
    
    List<WebElement> elements = genericMethod.getElements("otp");
    
    //logger.debug("wait for OTP element present");
    //waitMethod.waitForAllElementVisible(elements);
 
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
   * This method is used to check for consent for particular user.
   * @param dataParam contain true or false to check all the consent are checked or unchecked
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String consent(List<String> dataParam) {
    logger.debug("wait for Consent table visible");
    waitMethod.waitForElementPresent("consent_table");

    List<WebElement> elements = genericMethod.getElements("consent_table");
    //Get the number of row in the parent consent table
    for (WebElement element: elements) {
      element.click();
      List<WebElement> child = genericMethod.getWebElements(element, "consent_child");
      logger.debug("no of child row " + child.size());
      //each row has number of client information 
      for (WebElement ele :child) {
        List<WebElement> childconsent = genericMethod.getWebElements(ele, "consent_child_type");
        logger.debug("child consent type count " + childconsent.size());
        //each client information has different consent
        for (WebElement consentchild :childconsent) {
          logger.debug(consentchild.getText());
          WebElement childstatus = genericMethod.getWebElement(consentchild, 
                          "consent_child_status");
          logger.debug(childstatus.getAttribute("aria-checked"));
          String status = childstatus.getAttribute("aria-checked");
          if (!dataParam.get(0).equalsIgnoreCase(status)) {
            return "fail"; 
          }
        }
      }
    }

    //consent save or cancel
    WebElement element;

    if (dataParam.get(1).equalsIgnoreCase("save")) {
      element = genericMethod.getElement("");
      element.click();


    } else if (dataParam.get(1).equalsIgnoreCase("cancel")) {
      element = genericMethod.getElement("consent_cancel");
      element.click();
    }

    return "pass";
  }


  /**
   * This method is used to click on the competition page, 
   * where user searched for competition and then click matched text competition. 
   * @param dataParam 
   * <br> dataParam[0] contain the text which need to enter in the search box in competition page
   *     in which user can enter a partial text in search box
   * <br> dataParam[1] contain the text which need to click on search result competition 
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String searchcompetation(List<String> dataParam) {

    // wait for competition page load
    waitMethod.waitForElementInvisible("home_page");
    waitMethod.waitForElementVisible("competition_search");

    WebElement element = genericMethod.getElement("competition_search");
    element.sendKeys(dataParam.get(0));

    //wait for search result to update in result
    waitMethod.waitForElementInvisible("search_competation_wait");

    List<WebElement> elements = genericMethod.getElements("competiton_search_result");
    for (WebElement competations :elements) {
      List<WebElement> competation = genericMethod.getWebElements(competations, 
                      "competition_search_data");

      genericMethod.scrolltoelement(competations);

      for (WebElement competate : competation) {

        // wm.waitForElementVisible(competate);
        waitMethod.waitForSomeTextPresent(competate);

        logger.debug("Feached text ----> " + competate.getText());
        if (competate.getText().equals(dataParam.get(1))) {
          logger.debug("found matching and clicking on");
          waitMethod.waitForElementClickable(competate);
          competate.click();
        }

      }
    }

    return "pass";

  }

  /**
   * This method is used to answer Mandatory question based on the given answer.
   * <br>By default there are 10 Mandatory question in which first question is drop down 
   * and rest 9 question single choice answer
   * @param dataParam
   * <br> dataParam[0] by default second answer options is choice
   * <br> dataParam[1] to dataParam[9] contain the answer option to select
   * @return the status as "pass" if script executed success else "fail" 
   */
  public String mandatoryquestion(List<String> dataParam) {

    //First question is drop down
    waitMethod.waitForElementClickable("mandatorydropdown");
    WebElement element = genericMethod.getElement("mandatorydropdown");
    element.click();

    element = genericMethod.getElement("mandatorydropdownvalue");
    element.click();

    element = genericMethod.getElement("next");
    element.click();

    waitMethod.waitForElementAttributeNotPresent("waitnextquestion", "data-loading");

    for (int i = 0; i < 9; i++) {

      logger.debug("wait for next question load");
      waitMethod.waitForElementAttributeNotPresent("waitnextquestion", "data-loading");

      List<WebElement> elements = genericMethod.getElements("answeroption");
      for (WebElement answer : elements) {
        logger.debug("Answer options is " + answer.getText());
        if (answer.getText().equalsIgnoreCase(dataParam.get(i + 1))) {

          logger.debug("clicked on the Answer Options");
          answer.click();

          logger.debug("click on Next button");
          element = genericMethod.getElement("next");
          element.click();

          break;

        }
      }

    }

    return "pass";
  }


  /**
   * This method is used to answer the competition question and answer, 
   * also verified the score after answer.
   * @param dataParam is an array of data contain the question type, question,answer and marks 
   * @return all the competition questions are answered then return 'pass' else 'fail'
   */
  public String competationquestion(List<String> dataParam) {

    logger.debug("wait for next question to load");
    waitMethod.waitForElementInvisible("competition_next_question_wait");

    //Single choice question and answer
    if (dataParam.get(0).equalsIgnoreCase("SingleChoice")) {
      logger.debug("Single Choice question and answer");
      WebElement element = genericMethod.getElement("competition_single_choice_question");
      waitMethod.waitForElementVisible(element);
      logger.debug("question is -------> " + element.getText());
      if (dataParam.get(1).equals(element.getText())) {
        //Answer options
        List<WebElement> elements = genericMethod.getElements("competition_single_choice_answer");
        for (WebElement answer : elements) {
          logger.debug("Answer choice are -----> " + answer.getText());
          if (answer.getText().equals(dataParam.get(2))) {
            logger.debug("clicked on answer options --------> " + answer.getText());
            answer.click();
            break;
          }
        }
      } else {
        return "fail";
      }


    }

    if (dataParam.get(0).equalsIgnoreCase("MultipleChoice")) {
      logger.debug("Multiple Choice question and answer");
      waitMethod.waitForElementPresent("competition_multiple_choice_question");
      WebElement element = genericMethod.getElement("competition_multiple_choice_question");
      logger.debug("question is -------> " + element.getText());
      if (dataParam.get(1).equals(element.getText())) {
        //Answer options
        List<WebElement> elements = genericMethod.getElements("competition_multiple_choice_answer");
        for (WebElement answer : elements) {
          logger.debug("Answer choice are -----> " + answer.getText());
          List<String> answerselect = Arrays.asList(dataParam.get(2).split("`"));

          for (String answerse : answerselect) {
            if (answer.getText().equals(answerse)) {
              logger.debug("clicked on answer options --------> " + answer.getText());
              answer.click();
            }
          }
        }
      } else {
        return "fail";
      } 
    }

    // click on next button
    logger.debug("click on the next button");
    WebElement element = genericMethod.getElement("competition_next_question");
    element.click();

    return "pass";
  }
  
  /**
   * This method is used to enter OTP which read from mailinator to encash UI.
   * @return after successful it send "pass"
   */
  public String enterEmailOtp() {
    
    //stored OTP which is read in mailinator
    char[] storedotp = storevalue.storedOtp.toCharArray();
       
    logger.debug("wait for the Email OTP box");
    waitMethod.waitForElementPresent("encash_email_otp");
    
    List<WebElement> otps = genericMethod.getElements("encash_email_otp");
    int index = 0;
    logger.debug("Enter all the OTP");
    
    for (WebElement otp : otps) {
      String stringotp = Character.toString(storedotp[index]);
      otp.sendKeys(stringotp);
      index++;
    }
    return "pass";
  }

}
