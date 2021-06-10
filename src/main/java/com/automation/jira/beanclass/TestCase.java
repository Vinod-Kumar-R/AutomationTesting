package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TestCase {
  
  @JsonProperty("owner")
  public String testcaseOwner;
  
  @JsonProperty("customFields")
  public CustomFields customField;
  
  @JsonProperty("priority")
  public String testcasePriority;
  
  @JsonProperty("projectKey")
  public String projectId;
  
  @JsonProperty("lastTestResultStatus")
  public String previousTestResult;
  
  @JsonProperty("name")
  public String testCaseDescription;
  
  @JsonProperty("key")
  public String testcaseId;
  
  @JsonProperty("status")
  public String testcaseStatus;

  public String getTestcaseOwner() {
    return testcaseOwner;
  }

  public void setTestcaseOwner(String testcaseOwner) {
    this.testcaseOwner = testcaseOwner;
  }

  public CustomFields getCustomField() {
    return customField;
  }

  public void setCustomField(CustomFields customField) {
    this.customField = customField;
  }

  public String getTestcasePriority() {
    return testcasePriority;
  }

  public void setTestcasePriority(String testcasePriority) {
    this.testcasePriority = testcasePriority;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getPreviousTestResult() {
    return previousTestResult;
  }

  public void setPreviousTestResult(String previousTestResult) {
    this.previousTestResult = previousTestResult;
  }

  public String getTestCaseDescription() {
    return testCaseDescription;
  }

  public void setTestCaseDescription(String testCaseDescription) {
    this.testCaseDescription = testCaseDescription;
  }

  public String getTestcaseId() {
    return testcaseId;
  }

  public void setTestcaseId(String testcaseId) {
    this.testcaseId = testcaseId;
  }

  public String getTestcaseStatus() {
    return testcaseStatus;
  }

  public void setTestcaseStatus(String testcaseStatus) {
    this.testcaseStatus = testcaseStatus;
  }
 
}
