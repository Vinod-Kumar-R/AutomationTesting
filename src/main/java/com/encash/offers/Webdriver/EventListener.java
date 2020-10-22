package com.encash.offers.Webdriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import com.encash.offers.Utility.WaitMethod;

/**
 * This Class is used for WebDriver Event Listener 
 * @author Vinod Kumar R
 *
 */
public class EventListener extends AbstractWebDriverEventListener {

	static Logger logger = LogManager.getLogger(EventListener.class);
	private WaitMethod wm;
	public EventListener() {
		wm = new WaitMethod();
	}

	/**
	 * wait scenario throw an exception on polling time until time out exception
	 * so we are getting still timeout exception if element are not found
	 */	 
	public void onException(Throwable arg0, WebDriver arg1) {

		logger.debug("waiting for the element----> "+ arg0.getMessage()); 
		if(!wm.waitstatus && 
				arg0.getClass().equals("org.openqa.selenium.NoSuchElementException")) {

			logger.debug("Waiting for the element "+ arg0.getMessage());
			//do nothing 
		}

		if(wm.waitstatus) {
			logger.debug("got an exception--> "+ arg0.getMessage());
		}
		
		
		
		
	}

}
