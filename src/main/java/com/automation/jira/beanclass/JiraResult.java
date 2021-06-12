package com.automation.jira.beanclass;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JiraResult {
  
  @JsonProperty("testCycle") 
  public TestCycle testCycle;

  public TestCycle getTestCycle() {
    return testCycle;
  }

  public void setTestCycle(TestCycle testCycle) {
    this.testCycle = testCycle;
  }



 

}
