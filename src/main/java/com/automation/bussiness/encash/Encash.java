package com.automation.bussiness.encash;


import com.automation.configuration.ApplicationStoreValue;
import com.automation.utility.ExtentReport;
import com.automation.utility.GenericMethod;
import com.automation.utility.WaitMethod;
import com.automation.webelement.custom.CompetitionQuestion;
import com.automation.webelement.custom.MandatoryQuestion;
import com.aventstack.extentreports.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This class contain all the Business logic for Automation. 
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class Encash {

  @Autowired
  private WaitMethod waitmethod;
  @Autowired
  private GenericMethod genericmethod;
  @Autowired
  private ExtentReport extentReport;
  @Autowired
  private ApplicationStoreValue storevalue;
  @Autowired
  private CompetitionQuestion competitionquestions; 
  @Autowired
  private MandatoryQuestion mandatoryquestions;


  /**
   * This Method is use to verify the Jishi text when user click on explore. 
   * @param dataParam 
   *     dataParam is a list of String variable which hold data 
   *     dataParam[0] contain the Object i.e xpath rest of the data 
   *     are text which need to verify in jishi page 
   *
   */
  public void jishitext(List<String> dataParam)  {

    for (int i = 1; i < dataParam.size(); i++) {
      List<String> data = new ArrayList<String>();
      data.add(dataParam.get(0));
      data.add(dataParam.get(i));
      waitmethod.waitForTexttVisible(data);
      extentReport.writeLog(Status.PASS, "Taken the screenshot", genericmethod.takeScreenshot());
      genericmethod.verifyText(data);
      log.debug("Verified the test " + data.get(1));
    }

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
   */
  public void banner(List<String> dataParam)  {
    //create an List which contain Banner name so that in the end order can be verified 

    List<String> acutalBanner = new ArrayList<String>();
    List<String> expectedBanner = new ArrayList<String>();
    List<WebElement> elementList;

    //fetch all the Banner name with title and stored the name in list 
    elementList = genericmethod.getElements(dataParam.get(0));


    for (WebElement e : elementList) {
      acutalBanner.add(e.getAttribute(dataParam.get(2)).toString());
    }

    for (int i = 3; i < dataParam.size(); i++) {
      List<String> data = new ArrayList<String>();
      data.add(dataParam.get(1));
      data.add(dataParam.get(2));
      data.add(dataParam.get(i));

      expectedBanner.add(dataParam.get(i));

      waitmethod.waitForAttributedContain(data);
      extentReport.writeLog(Status.PASS, "capture the Screen shot " + dataParam.get(i),
                      genericmethod.takeScreenshot());
      genericmethod.verifyAttributedValue(data);
      extentReport.writeLog(Status.PASS, "verified the image " + dataParam.get(i));
    }

    //compare both Actual banner and Expected list are in same order
    Assertions
      .assertThat(acutalBanner)
      .asList()
      .as("Excel Order Banner is not matching with Expected order bannber in UI")
        .containsExactly(expectedBanner);
  }

  /**
   * This method is used to create a new Registration in encash page.
   * @param dataParam contain all the required data for registration
   */
  public void registrationForm(List<String> dataParam) {
    log.debug("waiting for loder class request complete");
    waitmethod.waitForElementInvisible("loderclass");

    log.debug("Wait for element presnet");
    waitmethod.waitForElementVisible("persondetail_title");

    log.debug("seleting the title from dorp down");

    List<String> title = new ArrayList<String>();
    title.add("persondetail_title");
    title.add(dataParam.get(0));

    genericmethod.selectByVisibleText(title);

    log.debug("enter the frist Name");
    WebElement element = genericmethod.getElement("persondetail_firstname");
    element.sendKeys(dataParam.get(1));

    log.debug("enter the last Name");
    element = genericmethod.getElement("personaldetail_lastname");
    element.sendKeys(dataParam.get(2));

    log.debug("Enter the Email address");
    element = genericmethod.getElement("personaldetail_email");
    element.sendKeys(dataParam.get(3));

    log.debug("Select the Geneder");

    if (dataParam.get(4).equalsIgnoreCase("male")) {
      element = genericmethod.getElement("personaldetail_male");
      element.click();
    } else if (dataParam.get(4).equalsIgnoreCase("female")) {
      element = genericmethod.getElement("personaldetail_female");
      element.click();
    }

    log.debug("Select date from BirthDate");

    List<String> date = new ArrayList<String>();
    date.add("personaldetail_date");
    date.add(dataParam.get(5));

    genericmethod.selectByVisibleText(date);

    log.debug("Select Month from BirthDate");

    List<String> month = new ArrayList<String>();
    month.add("personaldetail_month");
    month.add(dataParam.get(6));

    genericmethod.selectByVisibleText(month);

    log.debug("Select Year from BirthDate");
    List<String> year = new ArrayList<String>();
    year.add("personaldetail_year");
    year.add(dataParam.get(7));

    genericmethod.selectByVisibleText(year);

    log.debug("Enter the Password");
    element = genericmethod.getElement("personaldetail_password");
    element.sendKeys(dataParam.get(8));

    log.debug("Enter the confirm password");
    element = genericmethod.getElement("personaldetail_cofirmpassword");
    element.sendKeys(dataParam.get(9));

    log.debug("Enter the Display name");
    element = genericmethod.getElement("personaldetail_displayname");
    element.sendKeys(dataParam.get(10));

    log.debug("Enter the Postal code");
    element = genericmethod.getElement("personaldetail_postalcode");
    element.sendKeys(dataParam.get(11));

    log.debug("click on the find address");
    element = genericmethod.getElement("personaldetail_findadress");
    element.click();

    log.debug("wait for load all the address");
    waitmethod.waitForElementAttributeNotPresent("personaldetail_findadress", "disabled");

    log.debug("select the address from visible text");

    List<String> address = new ArrayList<String>();
    address.add("personaldetail_address");
    address.add(dataParam.get(12));

    genericmethod.selectByVisibleText(address);

    log.debug("accept the consent");
    element = genericmethod.getElement("accept_consent");
    if (dataParam.get(13).equalsIgnoreCase("yes")) {
      element.click();
    }

    log.debug("save registration form");
    element = genericmethod.getElement("personaldetail_continue");
    element.click();

    log.debug("wait for save registration form");
    waitmethod.waitForElementInvisible("loderclass");

  }

  /**
   * This method is used to register the new user through Mobile number.
   * @param dataParam contain the Mobile number and OTP
   */
  public void registerUsingMobileNumber(List<String> dataParam) {

    log.debug("waiting for register button enable");
    waitmethod.waitForElementClickable("register");

    log.debug("clicking on the Register button");
    WebElement element = genericmethod.getElement("register");
    element.click();


    log.debug("waiting for mobile number visiable");
    waitmethod.waitForElementVisible("mobilenumber");

    log.debug("Entering the mobile number");
    element = genericmethod.getElement("mobilenumber");
    element.sendKeys(dataParam.get(0));


    log.debug("clicking on the continue button");
    element = genericmethod.getElement("continue");
    element.click();

    log.debug("waiting for loder class request complete");
    waitmethod.waitForElementPresent("loderclass");
    waitmethod.waitForElementInvisible("loderclass");

    char[] otp = dataParam.get(1).toCharArray();

    log.debug("wait for OTP element present");
    waitmethod.waitForElementEqualTo("otp", 6);
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
    waitmethod.waitForElementPresent("loderclass");
    waitmethod.waitForElementInvisible("loderclass");
  }

  /**
   * This method is used to check for consent for particular user.
   * @param dataParam contain true or false to check all the consent are checked or unchecked 
   */
  public void consent(List<String> dataParam) {
    log.debug("wait for Consent table visible");
    waitmethod.waitForElementPresent("consent_table");

    List<WebElement> elements = genericmethod.getElements("consent_table");
    //Get the number of row in the parent consent table
    for (WebElement element : elements) {
      element.click();
      List<WebElement> child = genericmethod.getWebElements(element, "consent_child");
      log.debug("no of child row " + child.size());
      //each row has number of client information 
      for (WebElement ele : child) {
        List<WebElement> childconsent = genericmethod.getWebElements(ele, "consent_child_type");
        log.debug("child consent type count " + childconsent.size());
        //each client information has different consent
        for (WebElement consentchild : childconsent) {
          log.debug(consentchild.getText());
          WebElement childstatus = genericmethod.getWebElement(consentchild, 
                          "consent_child_status");
          log.debug(childstatus.getAttribute("aria-checked"));
          String status = childstatus.getAttribute("aria-checked");
          Assertions
              .assertThat(dataParam.get(0))
              .as("Consent are not matching")
              .isEqualToIgnoringCase(status);
        }
      }
    }

    //consent save or cancel
    WebElement element;

    if (dataParam.get(1).equalsIgnoreCase("save")) {
      element = genericmethod.getElement("");
      element.click();


    } else if (dataParam.get(1).equalsIgnoreCase("cancel")) {
      element = genericmethod.getElement("consent_cancel");
      element.click();
    }

  }


  /**
   * This method is used to click on the competition page, 
   * where user searched for competition and then click matched text competition. 
   * @param dataParam 
   <br> dataParam[0] contain the text which need to enter in the search box in competition page
        in which user can enter a partial text in search box
   <br> dataParam[1] contain the text which need to click on search result competition 
   */
  public void searchcompetation(List<String> dataParam) {

    // wait for competition page load
    waitmethod.waitForElementInvisible("home_page");
    waitmethod.waitForElementVisible("competition_search");

    log.debug("Enter in the search text box");
    WebElement element = genericmethod.getElement("competition_search");
    element.sendKeys(dataParam.get(0));

    log.debug("wait for result to update");
    List<WebElement> resultcount = genericmethod.getElements("competiton_search_result");
    waitmethod.waitForElementLessThan("competiton_search_result", resultcount.size() - 1);

    List<WebElement> elements = genericmethod.getElements("competiton_search_result");

    log.debug("fetched result count " + elements.size());

    for (WebElement competations : elements) {
      log.debug("get the list of search list data");
      List<WebElement> competation = genericmethod.getWebElements(competations, 
                      "competition_search_data");
      genericmethod.scrolltoelementTop(competations);

      if (genericmethod.isMobileview()) {
        log.debug("scorll to search result in mobile view");
        genericmethod.scrolltoelementBottom(competations);

      }

      for (WebElement competate : competation) {

        log.debug("waiting for the element visible");
        waitmethod.waitForElementVisible(competate);
        log.debug("waiting for some text present");
        waitmethod.waitForSomeTextPresent(competate);

        log.debug("Feached text ----> " + competate.getText());
        if (competate.getText().equals(dataParam.get(1))) {
          log.debug("found matching and clicking on");
          waitmethod.waitForElementVisible(competate);
          waitmethod.waitForElementClickable(competate);
          competate.click();
        }

      }
    }

    log.debug("waiting for the loadcontainer invisiable");
    waitmethod.waitForElementInvisible("lodercontainer");


  }

  /**
   * This method is used to answer Mandatory question based on the given answer.
   * <br>By default there are 10 Mandatory question in which first question is drop down 
   * and rest 9 question single choice answer
   * @param dataParam
   <br> dataParam[0] by default second answer options is choice
   <br> dataParam[1] to dataParam[9] contain the answer option to select
   */
  @Deprecated
  public void mandatoryquestion(List<String> dataParam) {

    //First question is drop down
    waitmethod.waitForElementClickable("mandatorydropdown");
    WebElement element = genericmethod.getElement("mandatorydropdown");
    element.click();

    element = genericmethod.getElement("mandatorydropdownvalue");
    element.click();

    element = genericmethod.getElement("next");
    element.click();

    waitmethod.waitForElementAttributeNotPresent("waitnextquestion", "data-loading");

    for (int i = 0; i < 9; i++) {

      log.debug("wait for next question load");
      waitmethod.waitForElementAttributeNotPresent("waitnextquestion", "data-loading");

      List<WebElement> elements = genericmethod.getElements("answeroption");
      for (WebElement answer : elements) {
        log.debug("Answer options is " + answer.getText());
        if (answer.getText().equalsIgnoreCase(dataParam.get(i + 1))) {

          log.debug("clicked on the Answer Options");
          answer.click();

          log.debug("click on Next button");
          element = genericmethod.getElement("next");
          element.click();

          break;

        }
      }

    }
  }


  /**
   * This method is used to answer the competition question and answer, 
   * also verified the score after answer.
   * @param dataParam is an array of data contain the question type, question,answer and marks 
   * @return all the competition questions are answered then return 'pass' else 'fail'
   */
  @Deprecated
  public String competationquestion(List<String> dataParam) {

    log.debug("wait for next question to load");
    waitmethod.waitForElementInvisible("competition_next_question_wait");

    //Single choice question and answer
    if (dataParam.get(0).equalsIgnoreCase("SingleChoice")) {
      log.debug("Single Choice question and answer");
      WebElement element = genericmethod.getElement("competition_single_choice_question");
      waitmethod.waitForElementVisible(element);
      log.debug("question is -------> " + element.getText());
      if (dataParam.get(1).equals(element.getText())) {
        //Answer options
        List<WebElement> elements = genericmethod.getElements("competition_single_choice_answer");
        for (WebElement answer : elements) {
          log.debug("Answer choice are -----> " + answer.getText());
          if (answer.getText().equals(dataParam.get(2))) {
            log.debug("clicked on answer options --------> " + answer.getText());
            answer.click();
            break;
          }
        }
      } else {
        return "fail";
      }
    }

    if (dataParam.get(0).equalsIgnoreCase("MultipleChoice")) {
      log.debug("Multiple Choice question and answer");
      waitmethod.waitForElementPresent("competition_multiple_choice_question");
      WebElement element = genericmethod.getElement("competition_multiple_choice_question");
      log.debug("question is -------> " + element.getText());
      if (dataParam.get(1).equals(element.getText())) {
        //Answer options
        List<WebElement> elements = genericmethod.getElements("competition_multiple_choice_answer");
        for (WebElement answer : elements) {
          log.debug("Answer choice are -----> " + answer.getText());
          List<String> answerselect = Arrays.asList(dataParam.get(2).split("`"));

          for (String answerse : answerselect) {
            if (answer.getText().equals(answerse)) {
              log.debug("clicked on answer options --------> " + answer.getText());
              answer.click();
            }
          }
        }
      } else {
        return "fail";
      } 
    }

    // click on next button
    log.debug("click on the next button");
    WebElement element = genericmethod.getElement("competition_next_question");
    element.click();

    return "pass";
  }

  /**
   * This method is used to enter OTP which read from mailinator to encash UI.
   */
  public void enterEmailOtp() {

    //stored OTP which is read in mailinator
    char[] storedotp = storevalue.storedOtp.toCharArray();

    log.debug("wait for the Email OTP box");
    waitmethod.waitForElementPresent("encash_email_otp");

    List<WebElement> otps = genericmethod.getElements("encash_email_otp");
    int index = 0;
    log.debug("Enter all the OTP");

    for (WebElement otp : otps) {
      String stringotp = Character.toString(storedotp[index]);
      otp.sendKeys(stringotp);
      index++;
    }

    log.debug("click on the verify Button");
    WebElement element = genericmethod.getElement("verify_email");
    element.click();
  }

  /**
   * This method is used to verify the email or skip the email verification.
   * @param dataParam contain any of the data i.e. "skip" or "verify"
   */
  public void emailEncash(List<String> dataParam) {

    WebElement element;

    if (dataParam.get(0).equalsIgnoreCase("skip")) {
      waitmethod.waitForElementPresent("skip_email");
      element = genericmethod.getElement("skip_email");
      waitmethod.waitForElementClickable(element);
      element.click();
    }
    if (dataParam.get(0).equalsIgnoreCase("Verify")) {
      enterEmailOtp();

    }
  }

  /**
   * This method is used to click on the participate button.
   */
  public void competationparticpate() {

    log.debug("Wait for Participate button present");
    waitmethod.waitForElementPresent("competition_play");

    log.debug("click on the Participate button");
    WebElement element = genericmethod.getElement("competition_play");
    waitmethod.waitForElementClickable(element);
    element.click();

    log.debug("Waiting for the lodercontainer invisiable");
    waitmethod.waitForElementInvisible("lodercontainer");
  }

  /**
   * This method is used to answer and verify the mandatory question.
   * @param dataParam dataParam[0] contain the question data 
   dataParam remaining contain answer
   */
  public void competitionQuesetion(List<String> dataParam) {

    log.debug("wait for to load question");
    waitmethod.waitForElementPresent("competition_question_base");

    WebElement element = genericmethod.getElement("competition_question_base");
    competitionquestions.setElement(element);

    Boolean status = competitionquestions.verifyQuestion(dataParam.get(0));

    Assertions
        .assertThat(status)
        .as("Question contains does not match")
        .isTrue();

    //remove the first element in the dataParam so that we will get only answer need to select
    List<String> answers = dataParam;
    answers.remove(0);

    for (String answer : answers) {
      status = competitionquestions.selectAnswer(answer);

      Assertions.assertThat(status)
          .as("Answer option not found")
          .isTrue();
    }

    competitionquestions.nextQuestion();
  }

  /**
   * This method is used to answer and verify the mandatory question.
   * @param dataParam dataParam[0] contain the question data 
   dataParam remaining contain answer
   */
  public void mandatoryQuesetion(List<String> dataParam) {

    log.debug("wait for to load question");
    waitmethod.waitForElementPresent("mandatory_question_base");

    WebElement element = genericmethod.getElement("mandatory_question_base");
    mandatoryquestions.setElement(element);

    Boolean status = mandatoryquestions.verifyQuestion(dataParam.get(0));

    Assertions.assertThat(status)
                  .as("Question Content are not correct")
                   .isTrue();

    //remove the first element in the dataParam so that we will get only answer need to select
    List<String> answers = dataParam;
    answers.remove(0);

    for (String answer : answers) {

      status = mandatoryquestions.selectAnswer(answer);

      Assertions.assertThat(status)
            .as("Answer option not found")
            .isTrue();
    }

    mandatoryquestions.nextQuestion();

  }

  /**
   * this method is used to login to encash application using email id and password.
   * @param dataParam contain 
   *     dataParam[0] :- emailid of the user
   *     dataParam[1] :- password for the user
   */
  public void loginReisterUser(List<String> dataParam) {

    log.debug("wait for login button visible");
    waitmethod.waitForElementPresent("login");

    log.debug("click on the login button");
    WebElement element = genericmethod.getElement("login");
    element.click();

    log.debug("wait for email text visible");
    waitmethod.waitForElementPresent("login_email");

    log.debug("enter the email id");
    element = genericmethod.getElement("login_email");
    element.sendKeys(dataParam.get(0));

    log.debug("enter the password");
    element = genericmethod.getElement("login_password");
    element.sendKeys(dataParam.get(1));

    log.debug("click on the continue button");
    element = genericmethod.getElement("login_continue");
    element.submit();

  }


}
