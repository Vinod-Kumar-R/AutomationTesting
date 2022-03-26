package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to convert the JSON to Object and wise versa.
 * @author Vinod Kumar R
 *
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
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
}
