package com.automation.configuration;

import java.io.File;
import java.util.Iterator;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * This Class is used to read the Configuration file. 
 * @author Vinod Kumar R
 *
 */
@Component
public class ConfigurationReader {
  private static Logger logger = LogManager.getLogger(ConfigurationReader.class.getName());
  private Configurations configs = new Configurations();
  private Configuration config;

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
   * @return the value for the specified key
   */
  public String getConfigurationStringValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getString(key));
    return config.getString(key);

  }

  /**
   * This method is used the value for key in the Integer format.
   * @param key in the properties file
   * @return the value for the specified key
   */
  public Integer getConfigurationIntValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getInt(key));
    return config.getInt(key);
  }

  /**
   * This method is used the value for key in the Boolean format.
   * @param key in the properties file
   * @return the value for the specified key
   */
  public Boolean getConfigurationBooleanValue(String key) {
    logger.debug("Value for the key " + key + " ------> " + config.getBoolean(key));
    return config.getBoolean(key);
  }

  /**
   * This method is used to read all the key in the configuration file.
   * @return all the key in file
   */
  public Iterator<String> getAllKeys() {
    return config.getKeys();
  }

}
