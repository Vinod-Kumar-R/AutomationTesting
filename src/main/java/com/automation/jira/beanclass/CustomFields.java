package com.automation.jira.beanclass;

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
public class CustomFields {

  @JsonProperty("Automation") 
  public String automation;
  
  @JsonProperty("Categeory")
  public String categeory;

}
