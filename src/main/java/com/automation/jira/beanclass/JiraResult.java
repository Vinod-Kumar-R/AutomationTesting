package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JiraResult {
  
  @JsonProperty("testCycle") 
  public TestCycle testCycle;
}
