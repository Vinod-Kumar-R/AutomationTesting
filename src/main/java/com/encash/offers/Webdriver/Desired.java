package com.encash.offers.Webdriver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.encash.offers.Configuration.ConfigurationReader;
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


	public  DesiredCapabilities AndroidDesired() {
		DesiredCapabilities dc = new DesiredCapabilities();

		if(ConstantVariable.BrowserName.equalsIgnoreCase("Android")) {
			ConfigurationReader cr = new ConfigurationReader();
			cr.ReadConfig(ConstantVariable.DesiredAndroidCapability);

			Iterator<String> androidkeys = cr.getAllKeys();

			while(androidkeys.hasNext()) {
				String key = androidkeys.next();
				String value = cr.getConfigurationStringValue(key);
				dc.setCapability(key, value);
			}

		}
		return dc;
	}

}
