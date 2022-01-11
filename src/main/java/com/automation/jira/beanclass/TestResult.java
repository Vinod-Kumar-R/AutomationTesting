package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;


@Data
public class TestResult {
  
  @SerializedName("version")
  @JsonProperty("version")
  public int version;
  
  @SerializedName("executions")
  @JsonProperty("executions")
  public List<Execution> executions;

}
