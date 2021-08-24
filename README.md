# Automation Framework for testing Web Application and Mobile Application using Selenium WebDriver 
Keyword Driver Framework is a Test Data Driven (TDD) and it's has been implemented using JAVA and Spring Core.
Main method start from class "Mainfunction.java" under package "com.automation.baseframework"

**Below feature are support by framework**
 1. Web Browser, Mobile Browser and Browser Stack
 2. Support all type of Browser
 3. Object Repository for storing the DOM element are support in 2 way and it configurable
 
    1. CSV file
	2. MySql Database
 4. Integration bewteen JIRA and Automation Framwork (where test script are fetched from Jira test management).
 5. Extent Report and KLOV Report (historical) for Autoamtion Test Result.
 6. Summary e-mail report of Automation Script.
 7. Jenkins Integration for Continous CI/CD. 

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
|11|Jenkins (Optional)|2.289.3|

**Setup Automation framework**
1. Create a environment variable with **Variable Name** field **“automation”** and **Variable Value** field **“folder location”**
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/environment.JPG?raw=true)
2. Open GIT Bash and change the current working directory to the location where you want cloned directory
3. Type below command to clone the repository

	git clone https://github.com/Vinod-Kumar-R/AutomationTesting.git
	
4. From window Explorer navigate to the clone folder i.e.“<clone folder>/AutomationTesting/ConfigurationFolder/” and copy all the file and paste to newly created environment variable folder
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/folder.JPG?raw=true)
5.  Inside the properties folder, open config.properties file and update folder location of testcase, testdata etc.
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/config.JPG?raw=true)
6. update the other properites file as per requirement such as Mobile,Jira,Sendemail etc.
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
3. To override the properties, such as test_execution, sendemail, klov etc, then we have to use \-DpropertieKey=propertieValue
  
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

Automation code read the Test Script or Test Data file in format “.xlsx” and executed corresponding test case ID script which is read from test case and by default sheet name should be “Sheet1”
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testscript.JPG?raw=true)

In Test Script file,

**Column A**:- is consider as Start and End of the Test script for particular Test case ID. The Test case ID column data in the Test Case Excel file should match in Test script file and it is considered as the start of the script. If column A contain the data as “End” then it considered as End of script for Test case ID.

**Column B**:- is considering as Keyword and name should match the enum of class “KeywordType” in JAVA code. Refer java documentation for more understanding of keyword

If keyword type is “comment” then particular row is ignored.

**Rest of Column**:- From column C onwards it consider as data for keyword. 

- **Test Object CSV File**

Test Object csv file use as Object Repository to store DOM element and used during automation script execution.

![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testobject.JPG?raw=true)  

1st row in Test Object csv is consider as header and it has 3 column

**ObjectName**:-  It consider as unique name in column and used to replace the ObjectName with ObjectValue in  Automation code.

**ObjectType**:- It is consider as Locator element in an HTML page and it should be any one of enum type class “ByMethod”.
xpath,id,name,classname,paritallinktext,linktext,tagname,angularbuttontext,repeater,exactrepeater,binding,exactbinding,model,options,partialbuttontext, csscontainingtext

**ObjectValue**:- it has DOM Element, which is used by the Selenium WebDriver during automation.

**Automation Result**

After completion of test script execution, then automatically result are updated in location :- \<Environmentvarialbe\>/Result/

also location are printed in console. 

**Configuration of Email**
1. update the key value of "sendemail" to "true" in config.properties to trigger email.
2. Open Mail.properties file and update corresponding values.

*Note:- By default Gmail account is highly secured.*
1. Login to Gmail.
2. Access the URL https://www.google.com/settings/security/lesssecureapps
3. Select "Turn on"

*Note* :- To change email template or format, update the "mailTemplate.txt" file 
**Configuration of KLOV**
1. Update the key value of "klov" to "true" in config.properties to enable klov report
2. Open klov.properties file and updated all value.

*Note* :- KLOV server has to up and running and it required mongo database

**Configuration of JIRA**
1. update the key value of "jiraintegration" to true in config.properties to enable jira integration
2. Open jira.properties file and update all the value.

*Note* 1 :- jira should support below API for integration and it should have Zephyr scale test management enable.
https://support.smartbear.com/zephyr-scale-server/api-docs/v1/

*Note* 2:- In jira, 2 custom field has to be created under testcase 

      i. **Automation**  (is single selection from dropdown)
     ii. **Categeory**   (is Multiple selection from dropdown)

"Automation" custom filed is used to know, Particular test case has been automated or not (it is single selection from dorpdown and it value should be "DONE" or "NOT" ) and it's mandatary field

"Categeory" custom filed is used to know that, particular test case belongs to "Smoke test", or "Regression Test" etc ( it is multiple selection from dropdown and it value should be different type of test name). and it's mandatory field

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

**Jenkins Integration**

Open the Jenkins URL and make sure below plugin are installed.
|Sl No|Plugin|Version|
|---|---|---|
|1|Agent Server Parameter Plug-In|1.1|
|2|Copy Artifact Plugin|1.46.1|
|3|HTML Publisher plugin|1.25|
|4|Pipeline Utility Steps|2.8.0|
|5|File Operations Plugin|1.11|

Follow below step to create job. 
1. Create new Freestyle Project by Job name as "Automation Build"
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job0.JPG?raw=true)
2. In source code management, select Git radio button and enter the Repository URL as "https://github.com/Vinod-Kumar-R/AutomationTesting" 
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job1.JPG?raw=true) 
3. In Build section, Select the Maven version (Which need to be configured in Jenkins Global configuration) and in Goals type the text as "clean compile install"
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job2.JPG?raw=true)
4. In post-build Actions, type the text as "target/EncashAutomation.jar,target/lib/\*.jar,target/properties/\*.\*,target/extension/\*.\*"
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job6.JPG?raw=true)
5. click on "Save" button

- **Create new Pipeline job**
1. Create a new Pipeline by providing any job name 
2. Go to pipeline tab as show in below image and perform below step 
    - from drop down list of "Definition", Select 'Pipeline script from SCM'
    - in SCM, select 'GIT' by drop down
    - In Repository URL, type text as 'https://github.com/Vinod-Kumar-R/AutomationTesting.git'
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job4.JPG?raw=true)
3. In Script Path, type text as 'JenkinsJob/jenkinsFile'
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job5.JPG?raw=true)
4. click on 'Save' button
5. Build newly created Pipeline job
6. HTML result can be found in current build number as show in below image
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job8.JPG?raw=true)

*Note* :- By default html result CSS file are not loaded and report look ugle, so please follow below link to enable the CSS file to load in html file
 https://stackoverflow.com/questions/35783964/jenkins-html-publisher-plugin-no-css-is-displayed-when-report-is-viewed-in-j
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job7.JPG?raw=true)

Any help required in setup framework, can reach out to me :- vinodraju26@gmail.com
