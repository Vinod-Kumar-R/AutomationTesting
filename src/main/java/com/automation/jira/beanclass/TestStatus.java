package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestStatus {
  
  @JsonProperty("key")
  public String testcaseId;

}
