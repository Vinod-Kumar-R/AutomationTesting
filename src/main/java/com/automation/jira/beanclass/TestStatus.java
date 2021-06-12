package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestStatus {
  
  @JsonProperty("key")
  public String testcaseId;

  public String getTestcaseId() {
    return testcaseId;
  }

  public void setTestcaseId(String testcaseId) {
    this.testcaseId = testcaseId;
  }

}
