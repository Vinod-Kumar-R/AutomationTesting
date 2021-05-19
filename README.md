# AutomationTesting (Framework used to test on WebApplication, Mobile Browser application and Browser stack)
Keyword Driver Framework is a test Data Driven (TDD) and implemented using JAVA and Spring Core.
Main method start from class Mainfunction under package "com.encash.offers.baseframework"

the framework support both Web Automation and Mobile Web Browser automation, Most of the code will be reusable for Web Automation and Mobile Web browser. it also support all types of Browser (configuration file).
for testing Mobile Web Browser, then Appium Server is required of version 1.18.0-2 ( tested on this)
 
Test Script are used in the Excel sheet (.xlsx or .xls) and CSV file for Object Repository.
 
After all testcase executed, an summary email report are send to team (configuration) and for Report used Extent Report and KLOV report for historical data

sample image of testcase, testscript, objectRepositorty and email format, file are stored in "ConfigurationFolder/sampleTestScript/"

Cofiguration 

Step 1 :- Create a environment variable called "encashoffers" and copy the content inside the "ConfigurationFolder" to newly created enviroment variable

Step 2 :- restart the system

Step 3 :- update the config.properties file with properly.

Run the program from command line 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn compile exec:java"

To generated the JAVA doc 

Step 1:- Goto the folder where source code has been downloaded

step 2 :- run the command  "mvn javadoc:javadoc"

step 3 :- java doc are generated in .../target/apidocs/index.html

To get an excel sheet (test script) mail me to :- vinodraju26@gmail.com



