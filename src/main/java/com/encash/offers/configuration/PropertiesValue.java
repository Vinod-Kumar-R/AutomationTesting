package com.encash.offers.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesValue {
  
  @Value("${encashurl}")
  private String encashUrl;
  
  @Value("${sendemail}")
  private boolean sendemail;

  public String getEncashUrl() {
    
    return encashUrl;
  }

  public boolean isSendemail() {
    return sendemail;
  }

  



  



 

}
