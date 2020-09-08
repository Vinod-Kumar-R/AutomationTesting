package com.encash.offers.Configuration;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
    private String Configurationfile = "D:\\Vinod\\encashoffers\\config.properties";
	
	public ConstantVariable () {
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

}
