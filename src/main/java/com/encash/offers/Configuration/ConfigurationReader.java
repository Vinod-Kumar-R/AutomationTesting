package com.encash.offers.Configuration;

import java.io.File;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;

/**
 * This Class is used to read the Configuration file 
 * @author Vinod Kumar R
 *
 */
public class ConfigurationReader {
	static Logger logger = Logger.getLogger(ConfigurationReader.class);
	Configurations configs = new Configurations();
	Configuration config;
	
	public void ReadConfig (String filename) {
		try {
			config = configs.properties(new File(filename));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getConfigurationStringValue(String key) {
		logger.info(key+"configuratino file");
		return config.getString(key);
		
	}
	public Integer getConfigurationIntValue(String key) {
		logger.info(key + "configuration file");
		return config.getInt(key);
	}

}
