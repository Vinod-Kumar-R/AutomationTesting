package com.encash.offers.Webdriver;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import com.aventstack.extentreports.Status;
import com.encash.offers.BaseFramework.BaseClass;

/**
 * This Class is used for WebDriver Event Listener 
 * @author Vinod Kumar R
 *
 */
public class EventListener extends AbstractWebDriverEventListener {

	static Logger logger = LogManager.getLogger(EventListener.class);

	/**
	 * wait scenario throw an exception on polling time until time out exception
	 * so we are getting still timeout exception if element are not found
	 */	 
	public void onException(Throwable arg0, WebDriver arg1) {

		logger.debug("waiting for the element----> "+ arg0.getMessage()); 
		if(!BaseClass.gm.waitstatus && 
				arg0.getClass().equals("org.openqa.selenium.NoSuchElementException")) {

			logger.debug("Waiting for the element "+ arg0.getMessage());
			//do nothing 
		}

		if(BaseClass.gm.waitstatus) {
			logger.debug("got an exception--> "+ arg0.getMessage());
		}

	}

}
