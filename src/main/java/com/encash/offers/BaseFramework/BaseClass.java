package com.encash.offers.BaseFramework;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.encash.offers.BussinessLogic.Logic;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.ExcelReader;
import com.encash.offers.Utility.ExtentReport;
import com.encash.offers.Utility.GenericMethod;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class BaseClass {
	static Logger logger = Logger.getLogger(BaseClass.class);
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
	 * Start reading the Test case and Executed it 
	 * @throws Exception 
	 */
	public void Start_run() throws Exception {
		//Initialize the method
		cv.SearchTestData();
		
		
		int testcaserownumber =1;
		int testDatarownumber =0;
		//Read the Test case data
		logger.info("Test Case File name "+ConstantVariable.TestCases);
		TestCase = new ExcelReader(ConstantVariable.TestCases,0);
		//is not of end of testcase row
		while(!(TestCase.GetCellData(testcaserownumber, 0).equalsIgnoreCase("End"))) {
			//Search Testcase in Test Data
			logger.info("Test Case ID "+ TestCase.GetCellData(testcaserownumber,0));
			logger.info("Test Case Description "+ TestCase.GetCellData(testcaserownumber,1));
			
			//er.CreateTest(TestCase.GetCellData(testcaserownumber,0));
			er.CreateTest(TestCase.GetCellData(testcaserownumber,0),TestCase.GetCellData(testcaserownumber,1));
			//er.author("<h5> vinod Kumar R </h5>");
			er.Categeory(TestCase.GetCellData(testcaserownumber, 2));
			
			//testDatarownumber = SearchTestData(TestCase.GetCellData(testcaserownumber,0));
			logger.info("Test case found in the test data file at----> "+ ConstantVariable.TestDataRowNumber.get(TestCase.GetCellData(testcaserownumber,0)));
			testDatarownumber = ConstantVariable.TestDataRowNumber.get(TestCase.GetCellData(testcaserownumber,0));
			                  
			//Execute the Test case ID
			logger.info("Test Case ID found and started executing "+ TestCase.GetCellData(testcaserownumber,0));
			TestRunId(testDatarownumber);
			er.flushlog();

			testcaserownumber++;
		}
		er.flushlog();
		TestCase.CloseWorkbook();
		logger.info("Completed Exeuction of all the Test Case i.e "+ (TestCase.Rowcout(0)-2));
		

	}

	//Search the TestCase in Test Data
		
	public void TestRunId(int RowStartfrom)  {
		int Current_Row = RowStartfrom;
		int Current_Col = 1;
		String Keyword;

		try {
			TestData = new ExcelReader(ConstantVariable.TestDatas, 0);

			while(TestData.GetCellData(Current_Row, 0) == null || !TestData.GetCellData(Current_Row, 0).equalsIgnoreCase("End")) {
				Keyword = TestData.GetCellData(Current_Row, Current_Col);
				logger.info("Got the Key word from excel sheet" + Keyword);
				
				//Read the all the parameter for keywork and add delimitor ~
				int Column =2;
				String[] StringParam = null ;
				String param = "";
				int incrementarraydata = 0;
				while(TestData.GetCellData(Current_Row, Column) != null) {
					logger.info("current row number "+ Current_Row + "Column number "+Column);
					logger.info("Data got from Cell "+TestData.GetCellData(Current_Row, Column));
					logger.info("value of incrementarraydata "+incrementarraydata);
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
				ke.Executed(StringParam);
				//KeywordType.
				Current_Row++;

			}
			TestData.CloseWorkbook();
			
		}catch (Exception e) {
			
			GenericMethod.waitstatus = false;
			er.WriteLog(Status.FAIL, e.getMessage());
			logger.info("testscript error message", e);
			try {
				
				er.AttachScreenshot(gm.takeScreenshot(driver));
				//er.flushlog();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
