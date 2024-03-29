# Automation Framework for testing Web Application, Mobile Application and API Testing using Selenium Web Driver, Appium and Spring cloud open feign.  
Keyword Driven Framework is a Test Data Driven (TDD) and it's has been implemented using JAVA and Spring boot.
Main method start from class "Mainfunction.java" under package "com.automation.baseframework"

**Below feature are support by framework**
 1. Web Browser, Mobile Browser, Browser Stack and API
 2. Support all type of Browser
 3. Object Repository for storing the DOM element are support in 2 ways and it configurable 
    1. CSV file
	2. MySql Database
 4. Integration between JIRA and Automation Framework (where test script are fetched from Jira test management).
 5. Extent Report and KLOV Report (historical) for Automation Test Result.
 6. Summary e-mail report of Automation Script.
 7. Jenkins Integration for Continuous CI/CD.
 8. Support Dockers for automatically download required browser and execution of Automation script.
 9. Assertion using assertj-core, support both Hard and Soft Assertion.
 10. Video Recording of test execution for failed test case using Docker.

**Software Requirement**
|   SlNo |   Software | Version |
|---|---|---|
|1|Maven |3.5.4|
|2|JAVA|1.8|
|3|Eclipse|User choice|
|4|Microsoft Office (Excel )|2007|
|5|Browser ( Testing Application )|User choice|
|6|Zephyr Scale Test Manager  in JIRA( Optional )|
|7|Android SDK  (Optional )|
|8|Appium Server (Optional )|1.18.0 |
|9|KLOV Server ( Optional )|
|10|MySql Server (Optional)|5.6 |
|11|Jenkins (Optional)|2.289.3|
|12|Docker in Ubuntu 20.04 (Optional) for Android Mobile testing|
|13|Docker for automatic download Browser (Optional)|
|14|Lombok (pluging to eclipse) required |1.18.22|

**Setup Automation framework**
1. Create a environment variable with **Variable Name** field **“automation”** and **Variable Value** field **“folder location”**

![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/environment.JPG?raw=true)

2. Open GIT Bash and change the current working directory to the location where you want cloned directory
3. Type below command to clone the repository

	git clone https://github.com/Vinod-Kumar-R/AutomationTesting.git
	
4. From window Explorer navigate to the clone folder i.e.“<clone folder>/AutomationTesting/ConfigurationFolder/” and copy all the file and paste to newly created environment variable folder
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/folder.JPG?raw=true)
5.  Inside the properties folder, open config.properties file and update folder location of test case, test data etc.
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/config.JPG?raw=true)
6. Update the other properties file as per requirement such as Mobile, Jira, Sendemail etc.
7. Restart the system.

**Configuration of Object Repository**
1. update the key value of "repository" to "false" in config.properties to read the Object Repository from CSV file else "true" to read from data base (MySql Server).
2. Script file can be found in Configurationfolder, with file name "scriptfile.sql"
3. Update "database.properties" with database username, password and IP address.

**Automation Execution Step**

Automation Script can be executed in 3 ways 
- Eclipse IDE
- MAVEN
- Jenkins

**Executing Script from Eclipse IDE**
1. From eclipse, import the existing maven project
2. In project explore, Open file “Mainfuncation.java” under the package **“com.automation.baseframework”**
3. Right click on the eclipse editor and select “Run As” as “Java Application”

**Executing Script from MAVEN**
1. Open the command prompt and navigate to source code
2. Type below command

	![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/mvnstart.JPG?raw=true)
3. To override the properties, such as test_execution, sendemail, klov etc, then we have to use \-DpropertieKey=propertieValue
  
    ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/mvnstartparameter.JPG?raw=true)

**Test Execution Control**

There are 2 Excel file and 1 CSV file are input to automation framework and Excel file are used to maintain **"Test Case"**, **"Test Script"** and **"Object Repository"**  

Below Diagram show High Level diagram of Automation Framework  
  
  ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/FrameworkDiagram.jpg?raw=true)

- **Test Case Excel File**

	Automation Code read test case excel file in format ".xlsx" and executed each test case based on the condition, by default sheet name should be "Sheet1"  
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/Testcase.JPG?raw=true)  

1st row is a header consistence of 4 columns

 **Test Case ID**:- Is an Alpha numeric number used to identify the Test case.
 
 **Test Case Description**:- is a short summary of Test case.
 
 **Test Category**:- is used to category the test case it belong to such as “Regression”, “Smoke” etc
 
 **Executed**:- It hold any one of the value i.e. “Yes” or “no”,
 
    - If particular test case row is set to “yes”, then corresponding automation script are execute in excel file “TestData.xlsx”.
    - If particular test case row is set to “no”, then that test script will not execute.

- **Test Script Excel File**

Automation code read the Test Script or Test Data file in format “.xlsx” and executed corresponding test case ID script which is read from test case and by default sheet name should be “Sheet1”
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testscript.JPG?raw=true)

In Test Script file,

**Column A**:- is considering as Start and End of the Test script for particular Test case ID. The Test case ID column data in the Test Case Excel file should match in Test script file and it is considered as the start of the script. If column A contains the data as “End” then it considered as End of script for Test case ID.

**Column B**:- is considering as Keyword and name should match the enum of class “KeywordType” in JAVA code. Refer java documentation for more understanding of keyword

If keyword type is “comment” then particular row is ignored.

**Rest of Column**:- From column C onwards it consider as data for keyword. 

- **Test Object CSV File**

Test Object csv file use as Object Repository to store DOM element and used during automation script execution.

![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/testobject.JPG?raw=true)  

1st row in Test Object csv is consider as header and it has 3 column

**ObjectName**:- It considers as unique name in column and used to replace the ObjectName with ObjectValue in Automation code.

**ObjectType**:- It is considered as Locator element in an HTML page and it should be any one of enum type class “ByMethod”.
xpath,id,name,classname,paritallinktext,linktext,tagname,angularbuttontext,repeater,exactrepeater,binding,exactbinding,model,options,partialbuttontext, csscontainingtext

**ObjectValue**:- it has DOM Element, which is used by the Selenium Web Driver during automation.

**Automation Result**

After completion of test script execution, then automatically result is updated in location: - \<Environmentvarialbe\>/Result/

Also location is printed in console. 

**Configuration of Email**
1. Update the key value of "sendemail" to "true" in config.properties to trigger email.
2. Open Mail.properties file and update corresponding values.

*Note: - By default Gmail account is highly secured.*
1. Login to Gmail.
2. make sure app password are enable
3. use the generated app password value in "email.password" (Mail.properties file)  

*Note* :- To change email template or format, update the "mailTemplate.txt" file.
 
**Configuration of KLOV**
1. Update the key value of "klov" to "true" in config.properties to enable klov report
2. Open klov.properties file and updated all value.

*Note* :- KLOV server must be up and running and it required mongo database or use the docker compose file of "klov-server.yml" to start the klov server.
klov-server.yml file are located in the "docker" folder in source code. 

**Configuration of JIRA**
1. Update the key value of "jiraintegration" to true in config.properties to enable jira integration
2. Open jira.properties file and update all the value.

*Note* 1 :- jira should support below API for integration and it should have Zephyr scale test management enable.
https://support.smartbear.com/zephyr-scale-server/api-docs/v1/

*Note* 2:- In jira, 2 custom field has to be created under test case
 
      i. **Automation**  (is single selection from dropdown)
     ii. **Category**   (is Multiple selection from dropdown)

"Automation" custom filed is used to know, Particular test case has been automated or not (it is single selection from dropdown and it value should be "DONE" or "NOT" ) and its mandatory field

"Category" custom filed is used to know that, particular test case belongs to "Smoke test", or "Regression Test" etc ( it is multiple selection from dropdown and it value should be different type of test name). and it's mandatory field

*Note* 3:- If particular test case are automated, then test case should have an attachment of test script in the name "Automation.xlsx".
 
**Configuration of Mobile Testing**
1. Android SDK has to be install in the system for testing Android Mobile 
2. Appium server should be installed and running. 
3. Update the Mobile_devices.properties which is key value pare (any new properties can also be added)
4. In config.properties file update the key value i.e. "appiumServerurl" to Appium server detail for example: - http://&lt;IPADDRES&gt;:&lt;PORT&gt;/wd/hub

**Running Android Mobile test in Dockers**
1. This is supported and tested on Ubuntu 20.04 OS
2. Docker has to be installed in Ubuntu 
3. Create a pipe line job and configured the SCM with jenking file name "DockerMobileJenkinsFile"
4. Run the job, then automatically all the required software i.e. android SDK and mobile emulator (Nexus 5) are download. 
5. Based on the condition mobile test case script are executed on Mobile Emulator

*Note* :- 
1. Docker and Mobile Emulator are supported only in Linux OS
2. Make sure at least one browser has to be install in Linux OS
3. To access the Mobile Emulator UI :- http://&lt;IPADDRES&gt;:6080

**Running Automation Script in Docker**
1. Update the key value of "docker" to "true" in config.properties.
2. make sure latest docker has been installed 
3. If docker installed in window machine, make sure docker should run in Linux container.
4. update the value of "test_execution" with browser name.

*Note:-*
1. noVNC url are display in the console log (info) to view automation script execution in Docker.

**Generation of JAVA doc** 
1. Go to the folder where source code has been downloaded
2. run the command  "mvn javadoc:javadoc"
3. java doc are generated in .../target/site/javadoc/index.html

**Generation of CheckStyle Report** 
1. Go to the folder where source code has been downloaded
2. run the command  "mvn checkstyle:checkstyle"
3. checkstyle report are generated in .../target/site/checkstyle/checkstyle.html

**Jenkins Integration**

Open the Jenkins URL and make sure below plug-in are installed.
|Sl No|Plugin|Version|
|---|---|---|
|1|Agent Server Parameter |1.1|
|2|Copy Artifact |1.46.1|
|3|HTML Publisher |1.25|
|4|Pipeline Utility Steps|2.8.0|
|5|File Operations |1.11|
|6|Warnings Next Generation Plugin |9.11.1|

Follow below step to create Pipeline Job
1. Create a new Pipeline by providing any job name (job name should not contain space) 
2. Go to pipeline tab as show in below image and perform below step 
    - from drop down list of "Definition", Select 'Pipeline script from SCM'
    - in SCM, select 'GIT' by drop down
    - In Repository URL, type text as 'https://github.com/Vinod-Kumar-R/AutomationTesting.git'
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job4.JPG?raw=true)
3. In Script Path, type text as 'JenkinsJob/jenkinsFile'
![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job5.JPG?raw=true)
4. Click on 'Save' button
5. Build newly created Pipeline job
6. HTML result can be found in current build number as show in below image
 ![alt text](https://github.com/Vinod-Kumar-R/AutomationTesting/blob/master/ConfigurationFolder/images/jenkins_job8.JPG?raw=true)

*Note* :- By default html result CSS file are not loaded and report look ugle, so please follow below link to enable the CSS file to load in html file
 https://stackoverflow.com/questions/35783964/jenkins-html-publisher-plugin-no-css-is-displayed-when-report-is-viewed-in-j

To relax this rule, go to
1.  Manage Jenkins->
2.  Manage Nodes->
3.  Click settings(gear icon)->
4.  click Script console on left and type in the following command:
System.setProperty("hudson.model.DirectoryBrowserSupport.CSP", "")

and Press Run. If you see the output as 'Result:' below "Result" header then the protection disabled. Re-Run your build and you can see that the new HTML files archived will have the CSS enabled.

Any help in understanding of framework, can reach out to me:- vinodraju26@gmail.com
