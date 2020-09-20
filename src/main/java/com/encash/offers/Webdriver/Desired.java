package com.encash.offers.Webdriver;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.encash.offers.Configuration.ConstantVariable;

public class Desired {
	
	
	
	public Desired () {
		
		
	}
	
	public ChromeOptions ChromeDesired() {
		
		Map<String,String> mobileemulation = new HashMap<String,String>();
		
		
		ChromeOptions chrome = new ChromeOptions();
		
		if(!ConstantVariable.MobileEmulation.equalsIgnoreCase("null")) {
			mobileemulation.put("deviceName", ConstantVariable.MobileEmulation);
			chrome.setExperimentalOption("mobileEmulation", mobileemulation);
		}
				
		chrome.setHeadless(ConstantVariable.HeadlessBrowser);
		chrome.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		chrome.addArguments("--start-maximized");
		
		return chrome;
	}
	public FirefoxOptions FirefoxDesired() {
		
		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(ConstantVariable.HeadlessBrowser);
		
		Map<String,String> mobileemulation = new HashMap<String,String>();
		mobileemulation.put("deviceName", "Nexus 5");
		
		
		
		return options;
	}

}
