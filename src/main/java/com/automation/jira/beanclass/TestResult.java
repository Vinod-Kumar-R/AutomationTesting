package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResult {
  
  @JsonProperty("version")
  public int version;
  
  @JsonProperty("executions")
  public List<Execution> executions;

}
