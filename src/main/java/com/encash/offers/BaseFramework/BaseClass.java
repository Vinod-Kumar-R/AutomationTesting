package com.encash.offers.BaseFramework;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.encash.offers.BussinessLogic.Logic;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.ExcelReader;
import com.encash.offers.Utility.ExtentReport;
import com.encash.offers.Utility.GenericMethod;
import com.encash.offers.Webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;


/**
 * <h1> This is Main class in which execution Start </h1>
 * <p> Main call the constructor and initial all the variable required such as 
 * URL, test case file, Log properties files and Result file  </p>
 * 
 * {@docRoot}
 * @serial 16-09-2020
 * @author Vinod Kumar R
 * @version 1.0
 * 
 */

public class BaseClass {
	//static Logger logger = Logger.getLogger(BaseClass.class);
	static Logger logger = LogManager.getLogger(BaseClass.class);
	public static int throwincrment =0;
	public static ConstantVariable cv ;
	public static GenericMethod gm;
	public static Logic lg;
	public static KeywordExecution ke ;

	private WebDriver driver;
	private NgWebDriver ngdriver;
	ExcelReader TestData;
	ExcelReader TestCase;
	public static ExtentReport er;

	public BaseClass() {
		cv = new ConstantVariable();
		gm = new GenericMethod();
		er = new ExtentReport();
		lg = new Logic();
		ke = new KeywordExecution();

	}

	/**
	 * This Method start reading the Test case xlsx file  row by row 
	 * <p> Test case file contain column header i.e. </p>
	 * <tr>
	 * <th> Test case ID </th>
	 * <th> Test Case Description </th>
	 * <th> Test Category </th>
	 * </tr>
	 * <p> and last row of first column should contain the text as "END" to indicate
	 * that there is no more test case to executed  
	 *  
	 * @throws Exception 
	 * <p> this is the main which accept all the Exception </p>
	 */
	public void Start_run() throws Exception {
		
		//Initialize the method
		cv.SearchTestData();
		cv.ObjectRepository();
		BrowserInitialize.BrowserInfo();

		int testcaserownumber =1;
		int testDatarownumber =0;
		//Read the Test case data
		logger.debug("Test Case File name "+ConstantVariable.TestCases);
		TestCase = new ExcelReader(ConstantVariable.TestCases,0);
		//is not of end of testcase row
		while(!(TestCase.GetCellData(testcaserownumber, 0).equalsIgnoreCase("End"))) {
			//if Executed column is yes then executed else skip test skip
			if((TestCase.GetCellData(testcaserownumber, 3).equalsIgnoreCase("yes"))) {
			String Test_Case_ID = TestCase.GetCellData(testcaserownumber,0);
			String Test_Case_Description = TestCase.GetCellData(testcaserownumber,1);
			String Test_Case_Categeory = TestCase.GetCellData(testcaserownumber, 2);
            logger.info("Started Executing Test Case ID " + Test_Case_ID);
			logger.debug("Test Case ID "+ Test_Case_ID);
			logger.debug("Test Case Description "+ Test_Case_Description);
			logger.debug("Test Case Categeory "+ Test_Case_Categeory);


			er.CreateTest(Test_Case_ID,Test_Case_Description);
			er.Categeory(Test_Case_Categeory);

			logger.debug("Test case found in the test data file at----> "+ ConstantVariable.TestDataRowNumber.get(Test_Case_ID));
			testDatarownumber = ConstantVariable.TestDataRowNumber.get(Test_Case_ID);

			//Execute the Test case ID
			logger.debug("Test Case ID found and started executing "+ Test_Case_ID);
			TestRunId(testDatarownumber);
			er.flushlog();

			
			}
			testcaserownumber++;
		}

		er.flushlog();
		TestCase.CloseWorkbook();
		logger.debug("Completed Exeuction of all the Test Case i.e "+ (TestCase.Rowcout(0)-2));
		logger.info("Completed Exeuction of all the Test Case i.e "+ (TestCase.Rowcout(0)-2));
	}

	/**
	 * This method read the Test script excel file i.e. Test Data
	 * which contain few column 
	 * <tr>
	 * <td> Test case ID </td>
	 * <td> Keyword which need to executed </td>
	 * <td> rest of the column are parameter to the keyword method which is required
	 * data for execution </td>
	 * 
	 * <p> Test Script start from the Test Case ID and end when particular row and  column contain text as "END"
	 * 
	 * @param RowStartfrom 
	 * This parameter take the integer number , which indicate from which row test script has to executed  
	 */

	public void TestRunId(int RowStartfrom)  {
		int Current_Row = RowStartfrom;
		int Current_Col = 1;
		String Keyword;

		try {
			TestData = new ExcelReader(ConstantVariable.TestDatas, 0);

			while(TestData.GetCellData(Current_Row, 0) == null || !TestData.GetCellData(Current_Row, 0).equalsIgnoreCase("End")) {
				Keyword = TestData.GetCellData(Current_Row, Current_Col);
				logger.debug("Got the Key word from excel sheet " + Keyword);

				//Read the all the parameter for keywork and add delimitor ~
				int Column =2;
				String[] StringParam = null ;
				String param = "";
				int incrementarraydata = 0;
				while(TestData.GetCellData(Current_Row, Column) != null) {
					logger.debug("current row number "+ Current_Row + "Column number "+Column);
					logger.debug("Data got from Cell "+TestData.GetCellData(Current_Row, Column));
					logger.debug("value of incrementarraydata "+incrementarraydata);
					if(param.equals("")) {
						param = TestData.GetCellData(Current_Row, Column);
					}
					else {
						param = param+"~"+TestData.GetCellData(Current_Row, Column);
					}

					Column++;
				}
				//copy all the parameter to an array of string
				if(!param.equals("")) {
					StringParam = param.split("~");
				}

				ke.setvalue(KeywordType.valueOf(Keyword));
				logger.info("Executing the Keyword " +Keyword);
				ke.Executed(StringParam);
				//KeywordType.
				Current_Row++;

			}
			TestData.CloseWorkbook();

		}catch (Exception e) {
			
			GenericMethod.waitstatus = false;
			er.WriteLog(Status.FAIL, e.getMessage());
			logger.error("testscript error message", e);
			try {
				TestData.CloseWorkbook();
				er.AttachScreenshot(gm.takeScreenshot(driver));
				BrowserInitialize.QuitBrowser();
				//er.flushlog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("exception message", e1);
			}
			er.flushlog();

			//TestData.CloseWorkbook();

		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseClass BC = new BaseClass();
		try {
			BC.Start_run();
			er.flushlog();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			er.flushlog();
			logger.error(e.getStackTrace().toString());
			e.printStackTrace();
		}

	}
}
