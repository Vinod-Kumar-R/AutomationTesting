package com.encash.offers.Configuration;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.encash.offers.Utility.ExcelReader;

public class ConstantVariable {
	static Logger logger = Logger.getLogger(ConstantVariable.class);
	public static String BrowserName;
	public static String URL;
	public static String LogFile;
	public static String TestDatas;
	public static String TestCases;
	public static String TestObjects;
	public static String ExtentReportsLocation;
	public static String ExtentReportsPropeties;
	public static String ScreenShotlocation;
	public static int ExplictWait;
	public static int polling;
	public static HashMap<String,Integer> TestDataRowNumber;
	private String Configurationfile = "D:\\Vinod\\encashoffers\\config.properties";

	public ConstantVariable ()  {
		ConfigurationReader cr = new ConfigurationReader();
		cr.ReadConfig(Configurationfile);
		LogFile = cr.getConfigurationStringValue("log4j");
		PropertyConfigurator.configure(LogFile);
		BrowserName = cr.getConfigurationStringValue("browsername");
		URL = cr.getConfigurationStringValue("url");
		TestDatas = cr.getConfigurationStringValue("testData");
		TestCases = cr.getConfigurationStringValue("testcase");
		TestObjects = cr.getConfigurationStringValue("testObject");
		ExtentReportsLocation = cr.getConfigurationStringValue("ResultFileLocation");
		ExtentReportsPropeties = cr.getConfigurationStringValue("extentreportconfiguration");
		ExplictWait = cr.getConfigurationIntValue("explictwait");
		polling = cr.getConfigurationIntValue("polling");
		ScreenShotlocation = cr.getConfigurationStringValue("screenshotlocation");
	}
	
	

	public void SearchTestData () throws Exception  {
		ExcelReader STD = null;
		int index =0;
		STD = new ExcelReader(ConstantVariable.TestDatas,0);


		this.TestDataRowNumber = new HashMap<String,Integer>();
	
		for(int i =0;i<STD.Rowcout(0);i++) {
			logger.info(STD.GetCellData(i,0));
			if(STD.GetCellData(i,0) != null 
					&& !STD.GetCellData(i,0).equalsIgnoreCase("End")
					&& !STD.GetCellData(i,0).equalsIgnoreCase("EndTestCase")) {
				this.TestDataRowNumber.put(STD.GetCellData(i,0), i);	
			}
		}

		STD.CloseWorkbook();
		logger.info("found the testcase in the TestDatas" + ConstantVariable.TestDatas +
				"in the row number " +index );


	}


}
