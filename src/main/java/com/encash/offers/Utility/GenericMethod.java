package com.encash.offers.Utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.encash.offers.Configuration.ConstantVariable;

public class GenericMethod {
	static Logger logger = Logger.getLogger(GenericMethod.class);

	public String click(WebDriver driver,String[] StringParam) throws Exception {
		String ObjectData = GetObjectName(StringParam[0]);
		driver.findElement(By.xpath(ObjectData)).click();
		return "pass";
	}
	
	public String login(WebDriver driver,String[] StringParam) throws Exception {
		String ObjectData = GetObjectName("login_xpath");
		driver.findElement(By.xpath(ObjectData)).sendKeys(StringParam[0]);;
		return "pass";
	}
	
	public String VerifyText(WebDriver driver,String[] StringParam) throws Exception {
		logger.info("Verifying the text-------> "+StringParam[1]);
		String ObjectData = GetObjectName(StringParam[0]);
		if(driver.findElement(By.xpath(ObjectData)).getText().equalsIgnoreCase(StringParam[1])) {
			return "pass";
		}
		return "pass";
	}

	public static String GetObjectName(String ObjectName) throws Exception  {
		ExcelReader ob;
		String ObjectData = null;
		logger.info("Getting the object Name ----->"+ObjectName);
	   	ob = new ExcelReader(ConstantVariable.TestObjects, 0);
			int Row =0;
			while(!ob.GetCellData(Row, 0).equalsIgnoreCase("End")) {
				if(ob.GetCellData(Row, 0).equalsIgnoreCase(ObjectName)) {
					break;
				}
				Row++;
			}
			 ObjectData = ob.GetCellData(Row, 1);
			 
			 logger.info("Return object Name ----->"+ObjectData);
			 ob.CloseWorkbook();
		return ObjectData;
	}

}
