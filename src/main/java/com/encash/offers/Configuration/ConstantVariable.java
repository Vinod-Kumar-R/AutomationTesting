package com.encash.offers.Configuration;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.encash.offers.BeanClass.RepositoryBean;
import com.encash.offers.Utility.ExcelReader;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * This class the constant variable data which is used during execution of script 
 * @author Vinod Kumar R
 *
 */
public class ConstantVariable {
	static Logger logger = Logger.getLogger(ConstantVariable.class);
	/**
	 * This variable contain the Browser name in which test script need to execute
	 */
	public static String BrowserName;
	/**
	 * This variable the contain application URL in which test script need to executed 
	 */
	public static String URL;
	public static String LogFile;
	/**
	 * This variable contain the Test Script file which need to executed
	 */
	public static String TestDatas;
	/**
	 * This variable contain test case file (test case ID) which which need to executed 
	 */
	public static String TestCases;
	public static String TestObjectsWeb;
	public static String TestObjectsMobile;
	public static String ExtentReportsLocation;
	public static String ExtentReportsPropeties;
	public static String ScreenShotlocation;
	public static int ExplictWait;
	public static int polling;
	public static HashMap<String,Integer> TestDataRowNumber;
	public static HashMap<String,String> GetObject;
	public static boolean HeadlessBrowser;
	public static String MobileEmulation;
	public static String Environment;
	public static String DesiredAndroidCapability;
	public static String AppiumURL;
	
	private String Configurationfile = "C:\\Vinod\\configfolder\\config.properties";

	/**
	 * This is the Constructor which is used to initialized the static variable
	 */
	public ConstantVariable ()  {
		ConfigurationReader cr = new ConfigurationReader();
		cr.ReadConfig(Configurationfile);
		LogFile = cr.getConfigurationStringValue("log4j");
		PropertyConfigurator.configure(LogFile);
		BrowserName = cr.getConfigurationStringValue("browsername");
		URL = cr.getConfigurationStringValue("url");
		TestDatas = cr.getConfigurationStringValue("testData");
		TestCases = cr.getConfigurationStringValue("testcase");
		TestObjectsWeb = cr.getConfigurationStringValue("testobjectweb");
		TestObjectsMobile = cr.getConfigurationStringValue("testobjectmobile");
		ExtentReportsLocation = cr.getConfigurationStringValue("ResultFileLocation");
		ExtentReportsPropeties = cr.getConfigurationStringValue("extentreportconfiguration");
		ExplictWait = cr.getConfigurationIntValue("explictwait");
		polling = cr.getConfigurationIntValue("polling");
		HeadlessBrowser = cr.getConfigurationBooleanValue("headlessbrowser");
		ScreenShotlocation = cr.getConfigurationStringValue("screenshotlocation");
		MobileEmulation = cr.getConfigurationStringValue("mobileemulation");
		Environment = cr.getConfigurationStringValue("environment");
		DesiredAndroidCapability = cr.getConfigurationStringValue("androidedesiredcapability");
		AppiumURL = cr.getConfigurationStringValue("appiumServerurl");
	}

	/**
	 * @author Vinod Kumar R
	 * This method read the Test case Excel sheet and store the data of test case ID 
	 * starting number in an HashMap so that search can be faster 
	 * @throws Exception
	 */

	public void SearchTestData () throws Exception  {

		int index =0;
		ExcelReader STD = new ExcelReader(ConstantVariable.TestDatas,0);

		this.TestDataRowNumber = new HashMap<String,Integer>();

		for(int i =0;i<STD.Rowcout(0);i++) {
			if(STD.GetCellData(i,0) != null 
					&& !STD.GetCellData(i,0).equalsIgnoreCase("End")
					&& !STD.GetCellData(i,0).equalsIgnoreCase("EndTestCase")) {
				this.TestDataRowNumber.put(STD.GetCellData(i,0), i);	
				logger.info(STD.GetCellData(i,0));
			}
		}

		STD.CloseWorkbook();
		logger.info("found the testcase in the TestDatas" + ConstantVariable.TestDatas +
				"in the row number " +index );
	}

	/**
	 * This Method is used to read the Object Repository file which is csv file with header 
	 * ObjectName and ObjectValue
	 * this file contain key and value pair of data where 
	 * ObjectName is key and it is unique for human readable format 
	 * ObjectValue is the value which contain the xpath values used to identified location 
	 *<p> csv file are read the stored in the HashMap<String,String> so that during execution
	 * we will get the xpath for an element 
	 *
	 * @throws Exception
	 */
	public void ObjectRepository() throws Exception {

		String Key, Value;
		this.GetObject = new HashMap<String,String>();
		FileReader file = null;
		
		if(Environment.equalsIgnoreCase("webbrowser")) {
			file = new FileReader(TestObjectsWeb);
			
		}
		
		if(Environment.equalsIgnoreCase("mobilebrowser")) {
			file = new FileReader(TestObjectsMobile);
			
		}
		
		
		List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
				.withType(RepositoryBean.class).build().parse();

		for(RepositoryBean ro : repositoryobject ) {
			Key = ro.getObjectName();
			Value = ro.getObjectValue();
			logger.info("Key ---> "+ Key +"  Value ----> "+Value);
			GetObject.put(Key, Value);
		}

	}

}
