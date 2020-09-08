package com.encash.offers.BaseFramework;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.encash.offers.BussinessLogic.Logic;
import com.encash.offers.Configuration.ConstantVariable;
import com.encash.offers.Utility.ExcelReader;
import com.encash.offers.Utility.ExtentReport;
import com.encash.offers.Utility.GenericMethod;
import com.encash.offers.Webdriver.BrowserInitialize;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class BaseClass {
	static Logger logger = Logger.getLogger(BaseClass.class);
	public static int throwincrment =0;
	public static ConstantVariable cv ;
	public static GenericMethod gm;
	public static Logic lg;
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


	}

	/**
	 * Start reading the Test case and Executed it 
	 * @throws Exception 
	 */
	public void Start_run() throws Exception {
		int testcaserownumber =0;
		int testDatarownumber =0;
		//Read the Test case data
		logger.info("Test Case File name "+ConstantVariable.TestCases);
		TestCase = new ExcelReader(ConstantVariable.TestCases,0);
		//is not of end of testcase row
		while(!(TestCase.GetCellData(testcaserownumber, 0).equalsIgnoreCase("End"))) {
			//Search Testcase in Test Data
			logger.info("Test Case ID "+ TestCase.GetCellData(testcaserownumber,0));
			er.CreateTest(TestCase.GetCellData(testcaserownumber,0));
			testDatarownumber = SearchTestData(TestCase.GetCellData(testcaserownumber,0));
			//Execute the Test case ID
			logger.info("Test Case ID found and started executing "+ TestCase.GetCellData(testcaserownumber,0));
			TestRunId(testDatarownumber);
			er.flushlog();

			testcaserownumber++;
		}
		er.flushlog();
		TestCase.CloseWorkbook();

	}

	//Search the TestCase in Test Data
	public int SearchTestData (String TestData) throws Exception {
		ExcelReader STD = new ExcelReader(ConstantVariable.TestDatas,0);
		int index =0;
		while(STD.GetCellData(index, 0) == null || !STD.GetCellData(index, 0).equalsIgnoreCase(TestData)) {
			index++;
		}
		STD.CloseWorkbook();
		logger.info("found the testcase in the TestDatas" + ConstantVariable.TestDatas +
				"in the row number " +index );
		return index;
	}

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
				String[] parameter = null ;
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
					parameter = param.split("~");
				}

				Execute_Keyword(Keyword, parameter);
				Current_Row++;

			}
			TestData.CloseWorkbook();
			
		}catch (Exception e) {
			System.out.println("vinod");
			BrowserInitialize.waitstatus = false;
			er.WriteLog(Status.FAIL, e.getMessage());
			logger.info("testscript error message", e);
			try {
				
				er.AttachScreenshot(BrowserInitialize.takeScreenshot());
				er.flushlog();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			er.flushlog();
			
			//TestData.CloseWorkbook();
			
		}
		
		




	}

	private void Execute_Keyword(String keyword, String[] StringParam) throws Exception {
		String status;
		if(keyword.equalsIgnoreCase("url_open")) {
			er.WriteInfo("Executing key word "+ keyword);
			logger.info("Opening the URL "+ ConstantVariable.URL);
			driver = BrowserInitialize.GetWebDriverInstance();
			ngdriver = BrowserInitialize.GetNgWebDriverInstance();
			driver.get(ConstantVariable.URL);
			driver.manage().window().maximize();
			status = "pass";
			Testresult(status, "Browser Open");
		}

		if(keyword.equalsIgnoreCase("WaitForElementVisible")) {
			logger.info("Waiting for the element visible");
			er.WriteInfo("Executing key word "+ keyword);
			status = BrowserInitialize.WaitForElementVisible(StringParam);
			Testresult(status, keyword);
		}
		if(keyword.equalsIgnoreCase("WaitForTexttVisible")) {
			logger.info("Waiting for the Text visible");
			er.WriteInfo("Executing key word "+ keyword);
			status = BrowserInitialize.WaitForTexttVisible(StringParam);
			Testresult(status, keyword);
		}		
		if(keyword.equalsIgnoreCase("click")) {
			er.WriteInfo("Executing key word "+ keyword);
			logger.info("clicking  on Element");
			status = gm.click(driver,StringParam);
			Testresult(status, keyword);

		}
		if(keyword.equalsIgnoreCase("QuitBrowser")) {
			er.WriteInfo("Executing key word "+ keyword);
			status = BrowserInitialize.QuitBrowser();
			logger.info("QuiteBrowser");
			Testresult(status, keyword);

		}
		if(keyword.equalsIgnoreCase("VerifyText")) {
			er.WriteInfo("Executing key word "+ keyword);
			status = gm.VerifyText(driver, StringParam);
			logger.info("verifying the text");
			Testresult(status,keyword);
		}
		if(keyword.equalsIgnoreCase("ImplictWait")) {
			er.WriteInfo("Executing key word "+ keyword);
			logger.info("Manually waiting ");
			Thread.sleep(Integer.parseInt(StringParam[0]));
		}

	}

	public void Testresult(String status,String message) {
		if(status.equalsIgnoreCase("Pass")) {
			er.WriteLog(Status.PASS, message );
		}
		if(status.equalsIgnoreCase("fail")) {
			er.WriteLog(Status.FAIL, message );
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
