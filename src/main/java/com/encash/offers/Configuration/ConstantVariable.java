package com.encash.offers.Configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.encash.offers.BeanClass.RepositoryBean;
import com.encash.offers.Utility.ExcelReader;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * This class the constant variable data which is used during execution of script 
 * @author Vinod Kumar R
 *
 */
public class ConstantVariable {
	static Logger logger = LogManager.getLogger(ConstantVariable.class.getName());
	/**
	 * This variable contain the Browser name in which test script need to execute
	 */
	public static String Browser_Binary_Name;
	/**
	 * This variable the contain application URL in which test script need to executed 
	 */
	public static String EncashURL;
	public static String AdminURL;
	public static String LogFile;
	public static String Test_Execution;
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
	private String dateformat = "dd_MMM_yyyy";
	private String timeformat = "HH_mm_ss";
	public static String ResultBaseLocation;
	public static String ResultLocation;
	public static String ResultDatelocaton;
	public static String Configlocation;
	
	

	/**
	 * This is the Constructor which is used to initialized the static variable
	 */
	public ConstantVariable ()  {
		Configlocation =ReadEnvironmnetVariable("encashoffers");
		//Read the properties file
		ConfigurationReader cr = new ConfigurationReader();
		cr.ReadConfig(Configlocation+File.separator+"config.properties");
		
		//Setting the logger context
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(Configlocation+File.separator+"log4j2.xml");
		context.setConfigLocation(file.toURI());
		
		//setting the properties value 
		ResultBaseLocation = Configlocation+File.separator+"Result";
		Test_Execution = cr.getConfigurationStringValue("test_execution");
		ResultDatelocaton = DateTime(dateformat,ResultBaseLocation);
		 ResultLocation = DateTime(timeformat,ResultDatelocaton);
		ExtentReportsLocation = ResultLocation+File.separator+"encashoffer.html";
		ScreenShotlocation = folderCreation(ResultLocation, "ScreenShot");
		Browser_Binary_Name = cr.getConfigurationStringValue("browser_Binary_file");
		EncashURL = cr.getConfigurationStringValue("encashurl");
		AdminURL = cr.getConfigurationStringValue("adminurl");
		TestDatas = cr.getConfigurationStringValue("testData");
		TestCases = cr.getConfigurationStringValue("testcase");
		TestObjectsWeb = cr.getConfigurationStringValue("testobjectweb");
		TestObjectsMobile = cr.getConfigurationStringValue("testobjectmobile");
		ExtentReportsPropeties = Configlocation+File.separator+"extentreportpropertes.xml";
		ExplictWait = cr.getConfigurationIntValue("explictwait");
		polling = cr.getConfigurationIntValue("polling");
		HeadlessBrowser = cr.getConfigurationBooleanValue("headlessbrowser");
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
				logger.debug(STD.GetCellData(i,0));
			}
		}

		STD.CloseWorkbook();
		logger.debug("found the testcase in the TestDatas" + ConstantVariable.TestDatas +
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
		
		if(Test_Execution.equalsIgnoreCase("ANDROID_CHROME")|
				Test_Execution.equalsIgnoreCase("IOS_SAFARI")) {
			file = new FileReader(TestObjectsMobile);
		}
		else {
			file = new FileReader(TestObjectsWeb);
		}
				
		List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
				.withType(RepositoryBean.class).build().parse();

		for(RepositoryBean ro : repositoryobject ) {
			Key = ro.getObjectName();
			Value = ro.getObjectValue();
			logger.debug("Key ---> "+ Key +"  Value ----> "+Value);
			GetObject.put(Key, Value);
		}

	}
	
	/**
	 * This method is used to created the folder structure based on the 
	 * date and time format 
	 * @param timeformat
	 * @param BaseLocation
	 * @return  AbsolutePath of folder is return back  
	 */
	
	public String DateTime(String timeformat, String BaseLocation) {
		SimpleDateFormat formatter = new SimpleDateFormat(timeformat); 
			
		Date date = new Date();
		
		File file = new File(BaseLocation+File.separator+formatter.format(date));
		try {
			FileUtils.forceMkdir(file);
			logger.debug("Folder created at location "+file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			logger.error("Folder cann't be created", e);
		}
		
		return file.getAbsolutePath();
	}
	
	
	/**
	 * This method is used folder based on the given base folder location 
	 * @param BaseLocation
	 * @param foldername
	 * @return AbsolutePath of folder is return back
	 */
	public String folderCreation (String BaseLocation, String foldername) {
		
		File file = new File(BaseLocation+File.separator+foldername);
		try {
			FileUtils.forceMkdir(file);
			logger.debug("Folder created at location "+file.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Folder cann't be created", e);
		}
		
		return file.getAbsolutePath();
	}
	
	/**
	 * This method is used to read the environment variable
	 * @return
	 */
	public String  ReadEnvironmnetVariable(String Key) {
		String configuration;
		configuration = System.getenv(Key);
		return configuration;
	}

}
