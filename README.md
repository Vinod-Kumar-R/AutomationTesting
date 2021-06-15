# AutomationTesting (Framework used to test Web Application in all Browser, Mobile Browser and Browser stack)
Keyword Driver Framework is a test Data Driven (TDD) and implemented using JAVA and Spring Core.
Main method start from class "Mainfunction.java" under package "com.automation.baseframework"

Below feature are supported by framework
 1. Web Browser, Mobile Browser and Browser stack
 2. Support all type of Browser
 3. Integration bewteen JIRA and Automation Framwork
 4. Extent Report and KLOV Report (historical) for Test Result
 5. Summar status report of test script are send to mail id after complete execution. 

Setup of Automation framework

Step 1 :- Create a environment variable called "automation" and copy the content inside the "ConfigurationFolder" to newly created enviroment variable

Step 2 :- restart the system

Step 3 :- update the config.properties file in "<Environmentvarialbe>/properties".

Run the program from command line 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn compile exec:java"

To generated the JAVA doc 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn javadoc:javadoc"

step 3 :- java doc are generated in .../target/apidocs/index.html

Configuration of Email

Step 1:- update the key value of "sendemail" to "true" in config.properties to enable mail to trigger.

Step 2:- Open the mail.properties file and update corresponding properties 

Important Note By default Gmail account is highly secured.

1.Login to Gmail.

2.Access the URL as https://www.google.com/settings/security/lesssecureapps

3.Select "Turn on"

Note :- to change the email template or format, update the "mailTemplate.txt" 

Configuration of KLOV

Step 1:- Update the key value of "klov" to "true" in config.properties to enable klov report

Step 2:- Open klov.properties file and updated all value.

Note :- KLOV server has to up and running and it required mongo database

Configuration of JIRA

Step 1:- update the key value of "jiraintegration" to true in config.properties to enable jira integration

Step 2:- Open jira.properties file and update all the value.

Note 1:- jira should support the below API for integration and it should have Zephyr scale test management enable.
https://support.smartbear.com/zephyr-scale-server/api-docs/v1/

Note 2:- In jira, 2 custom field has to be created under testcase 
      i. Automation  (is single selection from dropdown)
     ii. Categeory   (is Multiple selection from dropdown)

Automation custom filed is used to know, particular test case has been automated or not (it is single selection from dorpdown and it value should be "DONE" and "NOT" ) and it should be mandatary filed

Categeory custom filed is used to know that, particular test case below to "Smoke test", or "Regression Test" etc ( it is multiple selection from dropdown and it value should be different type of test name.

Note 3:- If particular test is automated, then TestScript has to attach in the name "Automation.xlsx".
 
Configuration of Mobile Testing
Step 1:- Android SDK has to be install in the system for testing Android Mobile 
Step 2:- Appium server has to installed 
Step 3:- update the Mobile_devices.properties which is key value pare (any new proprties can also be added)
Step 4:- In config.properties file udpate the key value i.e "appiumServerurl" to Appium server detail for example :- http://<IPADDRES>:<PORT>/wd/hub
 

Automation Result

After completion of all test script execution, then automation result are updated in location :-  <Environmentvarialbe>/Result/dateformatfolder/timeformatfolder/AutomationResult/automation.html

sample testscript can be find in folder "sampleTestScript"
 
Run the program from command line 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn compile exec:java"

To generated the JAVA doc 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn javadoc:javadoc"

step 3 :- java doc are generated in .../target/apidocs/index.html

Any help required in setup up framework, can reach out to me :- vinodraju26@gmail.com
