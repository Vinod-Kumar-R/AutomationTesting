# Automation Framework for testing Web Application and Mobile Application using Selenium WebDriver 
Keyword Driver Framework is a Test Data Driven (TDD) and it's been implemented using JAVA and Spring Core.
Main method start from class "Mainfunction.java" under package "com.automation.baseframework"

**Below feature are supported by framework**
 1. Web Browser, Mobile Browser and Browser stack
 2. Support all type of Browser
 3. ObjectRepository for storing of DOM element are support in 2 way and it configurable 
    i. CSV file are used to store ObjectRepository
    ii.MySql Database are used to store ObjectRepository
 4. Integration bewteen JIRA and Automation Framwork
 5. Extent Report and KLOV Report (historical) for Test Result
 6. Summary status report of test script are send to mail id after complete execution. 

**Software Requirement**
|   SlNo |   Software | Version |
|---|---|---|
|1|Maven |3.5.4|
|2|JAVA|1.8|
|3|Eclipse|User choice|
|4|Microsoft Office (Excel )|2007|
|5|Browser ( Testing Application )|User choice|
|6|Zephyr Scale Test Manager  in JIRA( Optional )| |
|7|Android SDK  (Optional )| |
|8|Appium Server (Optional )|1.18.0 |
|9|KLOV Server ( Optional )| |
|10|MySql Server (Optional)|5.6 |

**Setup Automation framework**
1. Create a environment variable with **Variable Name** field **“automation”** and **Variable Value** field **“folder location”**
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/environment.JPG?raw=true)
2. Open GIT Bash and change the current working directory to the location where you want cloned directory
3. Type below command to clone the repository
git clone https://github.com/Vinod-Kumar-R/AutomationTesting.git
4. From window Explore navigate to the clone folder i.e.“<clone folder>/AutomationTesting/ConfigurationFolder/” and copy all the file and paste to new created environment variable folder
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/folder.JPG?raw=true)
5.  Inside the properties folder, open config.properties file and update folder location of testcase, testdata etc.
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/config.JPG?raw=true)
6. update the other properites if you are required such mobile_device, if you want to test in Mobile. etc
7. restart the system.

**Configuration of ObjectRepository**
1. update the key value of "repository" to "false" in config.properties to read the Object Repository from CSV file else "true" to read from data base (MySql Server).
2. Script file can be found in Configurationfolder, with file name "scriptfile.sql"
3. update "database.properties" with database username,password and IP address.

**Automation Execution Step**
Automation Script can be exeucted in 2 way's 
- Executing Script from Eclipse IDE
- Executing Script from MAVEN

**Executing Script from Eclipse IDE**
1. From eclipse, import the existing maven project
2. From project explore,  Open file “Mainfuncation.java” under the package **“com.automation.baseframework”**
3. Righ click on the eclipse editor and select  “Run As” as “Java Application”

**Executing Script from MAVEN**
1. Open the command prompt and navigate to source code
2. Type below command 
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/mvnstart.JPG?raw=true)

3. To override the properties, such as test_execution, sendemail, klov etc, then we have to use -DpropertieKey=propertieValue
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/mvnstartparameter.JPG?raw=true)

**Test Execution Control**

There are 2 Excel file and 1 CSV file are input to automation framework and Excel file are used to maintain **"Test Case"**, **"Test Script"** and **"Object Repository"**
Below Diagram show High Level diagram of Autoamtion Framework
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/FrameworkDiagram.jpg?raw=true)

- **Test Case Excel File**
Automation Code read test case excel file in format ".xlsx" and executed each test case based on the condition, By default sheeet name should be "Sheet1"
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/Testcase.JPG?raw=true)

1st row is a header consistence of 4 columns

**Test Case ID**:- Is an Alpha numeric number used to identify the Test case. 
**Test Case Description**:- is a short summary of Test case. 
**Test Category**:- is used to category the test case it belong to such as “Regression”, “Smoke” etc 
**Executed**:- It hold any one of the value i.e. “Yes” or “no”,
    - If particular test case row is set to “yes”, then corresponding automation script are execute in excel file “TestData.xlsx”.
    - if particular test case row is set to “no”, then that test script will not execute.

- **Test Scirpt Excel File**

Automation code read the Test Script or Test Data file in the format “.xlsx” and executed the corresponding the test case ID script which is read from test case and by default sheet name should be “Sheet1”
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testscript.JPG?raw=true)

In Test Script file, 
**Column A**:- is consider as Start and End of the Test script for particular Test case ID. The Test case ID column data in the Test Case Excel file should match in Test script file and it is considered as the start of the script. If column A contains the data as “End” then it considered as End of script for Test case ID.
**Column B**:- is considering as Keyword and name should match the enum of class “KeywordType” in JAVA code. Refer java documentation for more understanding of keyword
If keyword type is “comment” then we will ignore the particular column and this row is used the data type required for next row.
**Rest of Column**:- From column C onwards we are consider as the data for the keyword. 

- **Test Object CSV File**

Test Object csv file is used as Object Repository file to store the DOM element which is used during automation script execution.
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testobject.JPG?raw=true)
1st row in Test Object csv is consider as header and it has 3 column
**ObjectName**:-  It consider as unique name in column and used to replace the ObjectName with ObjectValue in  Automation code.
**ObjectType**:- It is consider as Locating element in an HTML page and should be any one locator of enum  of type class “ByMethod”.
xpath,id,name,classname,paritallinktext,linktext,tagname,angularbuttontext,repeater,exactrepeater,binding,exactbinding,model,options,partialbuttontext, csscontainingtext
**ObjectValue**:- it has the DOM Element, which is used by the Selenium WebDriver during automation.

**Automation Result**
After completion of all test script execution, then automatically result are updated in location :-  <Environmentvarialbe>/Result/
After execution of automation in console we can find the result location. 

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
 
**Generated the JAVA doc** 
1. Goto the folder where source code has been downloaded
2. run the command  "mvn javadoc:javadoc"
3. java doc are generated in .../target/apidocs/index.html

Any help required in setup framework, can reach out to me :- vinodraju26@gmail.com
