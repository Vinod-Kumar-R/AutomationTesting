# Automation Framework for Testing (Framework used to test Web Application in all Browser, Mobile Browser and Browser stack)
Keyword Driver Framework is a Test Data Driven (TDD) and it's been implemented using JAVA and Spring Core.
Main method start from class "Mainfunction.java" under package "com.automation.baseframework"

**Below feature are supported by framework**
 1. Web Browser, Mobile Browser and Browser stack
 2. Support all type of Browser
 3. Integration bewteen JIRA and Automation Framwork
 4. Extent Report and KLOV Report (historical) for Test Result
 5. Summary status report of test script are send to mail id after complete execution. 

**Setup Automation framework**
1. Create a environment variable called "automation" and copy the content inside the "ConfigurationFolder" to newly created folder
2. restart the system
3. update config.properties file in "%automation%/properties".

**Configuration of Email**
1. update the key value of "sendemail" to "true" in config.properties to enable mail to trigger.
2. Open Mail.properties file and update corresponding values.

*Important Note By default Gmail account is highly secured.*
1. Login to Gmail.
2. Access the URL as https://www.google.com/settings/security/lesssecureapps
3. Select "Turn on"

*Note* :- To change the email template or format, update the "mailTemplate.txt" file 

**Configuration of KLOV**
1. Update the key value of "klov" to "true" in config.properties to enable klov report
2. Open klov.properties file and updated all value.

*Note* :- KLOV server has to up and running and it required mongo database

**Configuration of JIRA**
1. update the key value of "jiraintegration" to true in config.properties to enable jira integration
2. Open jira.properties file and update all the value.

*Note* 1 :- jira should support the below API for integration and it should have Zephyr scale test management enable.
https://support.smartbear.com/zephyr-scale-server/api-docs/v1/

*Note* 2:- In jira, 2 custom field has to be created under testcase 
      i. **Automation**  (is single selection from dropdown)
     ii. **Categeory**   (is Multiple selection from dropdown)

Automation custom filed is used to know, Particular test case has been automated or not (it is single selection from dorpdown and it value should be "DONE" or "NOT" ) and it's mandatary field

Categeory custom filed is used to know that, particular test case belongs to "Smoke test", or "Regression Test" etc ( it is multiple selection from dropdown and it value should be different type of test name). and it's mandatory field

*Note* 3:- If particular test case are automated, then test case should have a attachment of test script in the name "Automation.xlsx".
 
**Configuration of Mobile Testing**
1. Android SDK has to be install in the system for testing Android Mobile 
2. Appium server should be installed and running. 
3. update the Mobile_devices.properties which is key value pare (any new proprties can also be added)
4. In config.properties file udpate the key value i.e "appiumServerurl" to Appium server detail for example :- http://&lt;IPADDRES&gt;:&lt;PORT&gt;/wd/hub
 
**Automation Result**
After completion of all test script execution, then automatically result are updated in location :-  <Environmentvarialbe>/Result/dateformatfolder/timeformatfolder/AutomationResult/automation.html
sample testscript can be find in folder "sampleTestScript"
 
**Run program from command line** 
1. Goto the folder where source code has been downloaded
2. run the command  "mvn compile exec:java"

**Generated the JAVA doc** 
1. Goto the folder where source code has been downloaded
2. run the command  "mvn javadoc:javadoc"
3. java doc are generated in .../target/apidocs/index.html

Any help required in setup framework, can reach out to me :- vinodraju26@gmail.com
