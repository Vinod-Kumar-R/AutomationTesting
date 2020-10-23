package com.encash.offers.configuration;

import java.io.File;
import java.util.Iterator;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This Class is used to read the Configuration file. 
 * @author Vinod Kumar R
 *
 */
public class ConfigurationReader {
  static Logger logger = LogManager.getLogger(ConfigurationReader.class.getName());
  Configurations configs = new Configurations();
  Configuration config;

  /**
   * This method is used to read the configuration properties file.
   * @param filename is the configuration file in .properties format
   */
  public void readConfig(String filename) {
    try {
      logger.debug("Reading the Configuration file from location ");
      config = configs.properties(new File(filename));
    } catch (ConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * This method is used the value for key in the string format.
   * @param key in the properties file
   * @return
   */
  public String getConfigurationStringValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getString(key));
    return config.getString(key);

  }

  public Integer getConfigurationIntValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getInt(key));
    return config.getInt(key);
  }

  public Boolean getConfigurationBooleanValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getBoolean(key));
    return config.getBoolean(key);
  }

  public Iterator<String> getAllKeys() {
    return config.getKeys();
  }

}
