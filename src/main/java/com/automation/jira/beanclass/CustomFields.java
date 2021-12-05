package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomFields {

  @JsonProperty("Automation") 
  public String automation;
  
  @JsonProperty("Categeory")
  public String categeory;

}
