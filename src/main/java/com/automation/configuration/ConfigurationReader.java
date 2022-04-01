package com.automation.configuration;

import java.io.File;
import java.util.Iterator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.stereotype.Component;


/**
 * This Class is used to read the Configuration file. 
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class ConfigurationReader {
  private Configurations configs = new Configurations();
  private Configuration config;

  /**
   * This method is used to read the configuration properties file.
   * @param filename is the configuration file in .properties format
   */
  public void readConfig(String filename) {
    try {
      log.debug("Reading the Configuration file from location ");
      config = configs.properties(new File(filename));
    } catch (ConfigurationException e) {
      // TODO Auto-generated catch block
      log.error(e.getMessage());
    }
  }

  /**
   * This method is used the value for key in the string format.
   * @param key in the properties file
   * @return the value for the specified key
   */
  public String getConfigurationStringValue(String key) {
    log.debug("Value for the key " + key + " ------> " + config.getString(key));
    return config.getString(key);

  }

  /**
   * This method is used the value for key in the Integer format.
   * @param key in the properties file
   * @return the value for the specified key
   */
  public Integer getConfigurationIntValue(String key) {
    log.debug("Value for the key " + key + " ------> " + config.getInt(key));
    return config.getInt(key);
  }

  /**
   * This method is used the value for key in the Boolean format.
   * @param key in the properties file
   * @return the value for the specified key
   */
  public Boolean getConfigurationBooleanValue(String key) {
    log.debug("Value for the key " + key + " ------> " + config.getBoolean(key));
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
