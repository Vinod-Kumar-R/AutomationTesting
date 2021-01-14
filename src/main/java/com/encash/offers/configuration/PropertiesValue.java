package com.encash.offers.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:${encashoffers}/properties/config1.properties")
public class PropertiesValue {
  
  @Autowired
  Environment env;
  
  @Value("${encashurl}")
  private String encashUrl;

  public String getEncashUrl() {
    
    return encashUrl;
  }

  @Override
  public String toString() {
    return "PropertiesValue [env=" + env + ", encashUrl=" + encashUrl + ", getEncashUrl()="
                    + getEncashUrl() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
                    + ", toString()=" + super.toString() + "]";
  }



  



 

}
