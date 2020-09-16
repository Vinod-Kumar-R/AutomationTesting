package com.encash.offers.Utility;

import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.encash.offers.Configuration.ConstantVariable;

/**
 * This class is used to Generated the Extent report
 * @author Vinod Kumar R
 *
 */
public class ExtentReport {

	public ExtentHtmlReporter htmlreporter;
	//public ExtentHtmlReporter htmlreporter;
	//public ExtentSparkReporter spark ;
	
	public ExtentReports extent;
	public ExtentTest extenttest;

	public ExtentReport()  {

		this.htmlreporter = new ExtentHtmlReporter(ConstantVariable.ExtentReportsLocation);
		this.htmlreporter.loadXMLConfig(ConstantVariable.ExtentReportsPropeties);
		//this.spark = new ExtentSparkReporter(ConstantVariable.ExtentReportsLocation) ;
		//this.spark.loadXMLConfig(ConstantVariable.ExtentReportsPropeties);
		this.extent = new ExtentReports();
		this.extent.setSystemInfo("Organization", "Enchashes");
		this.extent.setSystemInfo("Browser", ConstantVariable.BrowserName);
		this.extent.setSystemInfo("Operation System ", System.getProperty("os.name"));
		this.extent.setSystemInfo("OS Version number", System.getProperty("os.version"));
		this.extent.setSystemInfo("Build Version No", "Need to fetch the Build Number");
		this.extent.attachReporter(this.htmlreporter);
	}

	public void CreateTest(String TestName) {
		this.extenttest = this.extent.createTest(TestName);
		//this.extent.setAnalysisStrategy(strategy);
		
	}

	public void CreateTest(String TestName, String Description) {
		this.extenttest = this.extent.createTest(TestName, Description);
	}

	public void WriteLog(Status status, String details) {
		this.extenttest.log(status, details);
	}

	public void flushlog() {
		this.extent.flush();
	}

	public void AttachScreenshot(String imagePath) throws IOException {
		this.extenttest.addScreenCaptureFromPath(imagePath);
	}

	public void WriteInfo(String details) {
		this.extenttest.info(details);
	}

	public void WriteLog(Status status,String details, String ImageFilelocation) throws IOException {
		this.extenttest.log(status, details, MediaEntityBuilder.createScreenCaptureFromPath(ImageFilelocation).build());
	}

	public void Categeory(String category) {
		this.extenttest.assignCategory(category);
	}

	public void author(String author) {
		this.extenttest.assignAuthor(author);
		
		
	}
	
	

}
