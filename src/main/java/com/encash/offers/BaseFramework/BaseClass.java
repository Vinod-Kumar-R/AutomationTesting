package com.encash.offers.BaseFramework;

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
			logger.info("Test Case Description "+ TestCase.GetCellData(testcaserownumber,1));
			
			//er.CreateTest(TestCase.GetCellData(testcaserownumber,0));
			er.CreateTest(TestCase.GetCellData(testcaserownumber,0),TestCase.GetCellData(testcaserownumber,1));
			//er.author("<h5> vinod Kumar R </h5>");
			er.Categeory(TestCase.GetCellData(testcaserownumber, 2));
			testDatarownumber = SearchTestData(TestCase.GetCellData(testcaserownumber,0));
			//Execute the Test case ID
			logger.info("Test Case ID found and started executing "+ TestCase.GetCellData(testcaserownumber,0));
			TestRunId(testDatarownumber);
			er.flushlog();

			testcaserownumber++;
		}
		er.flushlog();
		TestCase.CloseWorkbook();
		logger.info("Completed Exeuction of all the Test Case i.e "+ (TestCase.Rowcout(0)-1));
		

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

	private void Execute_Keyword(String keyword, String[] StringParam) throws Exception {
		String status;
		if(keyword.equalsIgnoreCase("url_open")) {
			er.WriteInfo("Executing key word --->"+ keyword);
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
			er.WriteInfo("Executing key word --->"+ keyword);
			status = gm.WaitForElementVisible(driver,StringParam);
			Testresult(status, keyword);
		}
		if(keyword.equalsIgnoreCase("WaitForTexttVisible")) {
			logger.info("Waiting for the Text visible");
			er.WriteInfo("Executing key word --->"+ keyword);
			status = gm.WaitForTexttVisible(driver,StringParam);
			Testresult(status, keyword);
		}		
		if(keyword.equalsIgnoreCase("click")) {
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.info("clicking  on Element");
			status = gm.click(driver,StringParam);
			logger.info("clicked  on Element");
			Testresult(status, keyword);

		}
		if(keyword.equalsIgnoreCase("QuitBrowser")) {
			er.WriteInfo("Executing key word --->"+ keyword);
			status = BrowserInitialize.QuitBrowser();
			logger.info("QuiteBrowser");
			Testresult(status, keyword);

		}
		if(keyword.equalsIgnoreCase("VerifyText")) {
			er.WriteInfo("Executing key word --->"+ keyword);
			status = gm.VerifyText(driver, StringParam);
			logger.info("verified the text");
			Testresult(status,keyword);
		}
		if(keyword.equalsIgnoreCase("ImplictWait")) {
			er.WriteInfo("Executing key word --->"+ keyword);
			logger.info("Manually waiting ");
			Thread.sleep(Integer.parseInt(StringParam[0]));
		}
		if(keyword.equalsIgnoreCase("JishiText")) {
			er.WriteInfo("Executing key word --->"+ keyword);
			status = lg.JishiText(driver, StringParam);
			logger.info("verified the text");
			Testresult(status,keyword);
		}
		if(keyword.equalsIgnoreCase("takeScreenshot")) {
			er.WriteInfo("Executing key word ---> "+ keyword);
			//status = BrowserInitialize.takeScreenshot();
			er.AttachScreenshot(gm.takeScreenshot(driver));
			//er.flushlog();
			status = "pass";
			logger.info("taken the screen shot");
			Testresult(status,keyword);
		}
		if(keyword.equalsIgnoreCase("WaitForAttributedPrent")) {
			er.WriteInfo("Executing key word ---> "+ keyword);
			//status = gm.VerifyText(driver, StringParam);
			status = gm.WaitForAttributedPrent(driver,StringParam);
			logger.info("Waited for An Attibuted ");
			Testresult(status,keyword);
		}
		
		if(keyword.equalsIgnoreCase("verifyAttributedValue")) {
			er.WriteInfo("Executing key word ---> "+ keyword);
			//status = gm.VerifyText(driver, StringParam);
			status = gm.verifyAttributedValue(driver,StringParam);
			logger.info("verified the attributed Value");
			Testresult(status,keyword);
		}
		
		if(keyword.equalsIgnoreCase("Banner")) {
			er.WriteInfo("Executing key word ---> "+ keyword);
			//status = gm.VerifyText(driver, StringParam);
			status = lg.Banner(driver,StringParam);
			logger.info("verifed the Banner");
			Testresult(status,keyword);
		}

	}

	public void Testresult(String status,String message) throws  Exception {
		if(status.equalsIgnoreCase("Pass")) {
			er.WriteLog(Status.PASS, message );
		}
		if(status.equalsIgnoreCase("fail")) {
			er.AttachScreenshot(gm.takeScreenshot(driver));
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
