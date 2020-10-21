package com.encash.offers.Webdriver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariOptions;

import com.encash.offers.Configuration.ConfigurationReader;
import com.encash.offers.Configuration.ConstantVariable;

public class Desired {


/**
 * This Method is used to configured the Chrome Options before execution
 * @return
 */
	public ChromeOptions ChromeDesired() {

		Map<String,String> mobileemulation = new HashMap<String,String>();

		ChromeOptions chrome = new ChromeOptions();

		/**if(!ConstantVariable.MobileEmulation.equalsIgnoreCase("null") && !ConstantVariable.Environment.equalsIgnoreCase("WebBrowser")) {
			mobileemulation.put("deviceName", ConstantVariable.MobileEmulation);
			chrome.setExperimentalOption("mobileEmulation", mobileemulation);
		}**/

		chrome.setHeadless(ConstantVariable.HeadlessBrowser);
		chrome.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		//chrome.add_argument("--enable-javascript");
		chrome.addArguments("--start-maximized");
		chrome.addArguments("enable-javascript");
		//chrome.addArguments("user-data-dir=C:\\Users\\HP\\AppData\\Local\\Google\\Chrome\\User Data\\Profile 1");
		//chrome.addArguments("--profile-directory=Profile 2");
		
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);

		chrome.setExperimentalOption("prefs", prefs);
		return chrome;
	}
	
	/**
	 * This method is used configured the Firefox options before executing firefox
	 * @return
	 */
	public FirefoxOptions FirefoxDesired() {

		FirefoxOptions options = new FirefoxOptions();
		options.setHeadless(ConstantVariable.HeadlessBrowser);
		return options;
	}

/**
 * This method is used to set the Desired Capabilities for Android Devices before execution of test
 * @return
 */
	public  DesiredCapabilities AndroidDesired() {
		DesiredCapabilities dc = new DesiredCapabilities();

		if(ConstantVariable.Test_Execution.equalsIgnoreCase("ANDROID_CHROME")) {
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
	
	public  ChromeOptions MobileChromedriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		//chromeOptions.setExperimentalOption("androidActivity", "com.google.android.apps.chrome.Main");
	    chromeOptions.setExperimentalOption("androidPackage", "com.android.chrome");
	    //chromeOptions.setExperimentalOption("androidDeviceSerial", "04895b1943876a3e");
	    chromeOptions.setExperimentalOption("androidDeviceSerial", "emulator-5554");
	   
	    return chromeOptions;
	}
	
	public OperaOptions OperaDesired() {
		OperaOptions oo = new OperaOptions();
		oo.setCapability("headless", ConstantVariable.HeadlessBrowser);
		return oo;
	}
	
	public EdgeOptions EdgeDesired() {
		DesiredCapabilities Dc = new DesiredCapabilities();
		Dc.setCapability("headless", ConstantVariable.HeadlessBrowser);
		
		EdgeOptions eo = new EdgeOptions();
		eo.merge(Dc);
		
		return eo;
		
	}
	
	public InternetExplorerOptions InternetExploreDesired() {
		InternetExplorerOptions ieo = new InternetExplorerOptions();
		//ieo.setCapability("headless", ConstantVariable.HeadlessBrowser);
		
		return ieo;
	}
	
	public SafariOptions SafariDesired() {
		SafariOptions so = new SafariOptions();
		so.setCapability("headless", ConstantVariable.HeadlessBrowser);
		return so;
	}

}
