package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

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
