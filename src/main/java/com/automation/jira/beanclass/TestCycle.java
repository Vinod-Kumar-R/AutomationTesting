package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class TestCycle {
  
  @JsonProperty("id")
  public int id;
  
  @JsonProperty("key") 
  public String key;
  
  @JsonProperty("url")
  public String url;
}
