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
public class TestStatus {
  
  @SerializedName("key")
  @JsonProperty("key")
  public String testcaseId;

}
