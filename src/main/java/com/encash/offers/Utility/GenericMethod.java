package com.encash.offers.Utility;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.encash.offers.Configuration.ConstantVariable;

public class GenericMethod {
	static Logger logger = Logger.getLogger(GenericMethod.class);
	public static boolean waitstatus = true;

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


	public  String WaitForElementVisible(WebDriver driver,String[] string) throws Exception {
		String ObjectName = GenericMethod.GetObjectName(string[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);	

		logger.info("Waiting for the Element Visibility "+ ObjectName);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ObjectName)));

		waitstatus = true;
		return "pass";
	}

	public  String WaitForTexttVisible(WebDriver driver, String[] string) throws Exception {
		String ObjectName = GenericMethod.GetObjectName(string[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.info("Waiting for the Text to be present "+ ObjectName);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(ObjectName), string[1]));

		waitstatus = true;
		return "pass";
	}

	public  String WaitForAttributedPrent(WebDriver driver,String[] StringParam) throws Exception {
		String ObjectName = GenericMethod.GetObjectName(StringParam[0]);
		waitstatus = false;
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)    
				.withTimeout(Duration.ofMinutes(ConstantVariable.ExplictWait))   
				.pollingEvery(Duration.ofSeconds(ConstantVariable.polling))   
				.ignoring(NoSuchElementException.class);

		logger.info("Waiting for the attributed presnt and value "+ ObjectName);
		logger.info("attribute --------->" +StringParam[1]);
		logger.info("Value --------->" +StringParam[2]);
		//wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(ObjectName), string[1]));

		wait.until(ExpectedConditions.attributeContains(By.xpath(ObjectName), StringParam[1], StringParam[2]));

		waitstatus = true;
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

	public  String takeScreenshot(WebDriver driver) throws Exception {
		long Filename = System.currentTimeMillis();
		if (driver instanceof TakesScreenshot) {
			File tempFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(tempFile, new File(ConstantVariable.ScreenShotlocation + "/" +Filename + ".png"));


		}
		return ConstantVariable.ScreenShotlocation + "/" +Filename + ".png";
	}

	public String verifyAttributedValue (WebDriver driver, String[] StringParam) throws Exception {
		String ObjectData = GetObjectName(StringParam[0]);
		if(driver.findElement(By.xpath(ObjectData)).getAttribute(StringParam[1]).equalsIgnoreCase(StringParam[2])) {
			return "pass";
		}
		else {
			return "false";
		}
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
