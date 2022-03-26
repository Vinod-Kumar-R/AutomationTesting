package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class is used to convert the JSON to Object and wise versa.
 * @author Vinod Kumar R
 *
 */
@Data
public class TestCycle {
  
  @JsonProperty("id")
  public int id;
  
  @JsonProperty("key") 
  public String key;
  
  @JsonProperty("url")
  public String url;
}
