package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Execution {
  
  @JsonProperty("result")
  public String testResult;
  
  @JsonProperty("testCase") 
  public TestStatus teststatus;

  public String getTestResult() {
    return testResult;
  }

  public void setTestResult(String testResult) {
    this.testResult = testResult;
  }

  public TestStatus getTeststatus() {
    return teststatus;
  }

  public void setTeststatus(TestStatus teststatus) {
    this.teststatus = teststatus;
  }

}
