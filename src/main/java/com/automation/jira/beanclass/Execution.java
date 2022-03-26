package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to convert the JSON to Object and wise versa.
 * @author Vinod Kumar R
 *
 */
@Getter
@Setter
public class Execution {
  @SerializedName("result")
  @JsonProperty("result")
  public String testResult;
  
  @SerializedName("testCase")
  @JsonProperty("testCase") 
  public TestStatus teststatus;
}
