package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Execution {
  
  @JsonProperty("result")
  public String testResult;
  
  @JsonProperty("testCase") 
  public TestStatus teststatus;
}
