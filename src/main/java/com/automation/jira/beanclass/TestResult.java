package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TestResult {
  
  @JsonProperty("version")
  public int version;
  
  @JsonProperty("executions")
  public List<Execution> executions;

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public List<Execution> getExecutions() {
    return executions;
  }

  public void setExecutions(List<Execution> executions) {
    this.executions = executions;
  }

}
