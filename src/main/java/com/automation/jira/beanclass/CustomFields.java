package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomFields {

  @JsonProperty("Automation") 
  public String automation;
  
  @JsonProperty("Categeory")
  public String categeory;

  public String getCategeory() {
    return categeory;
  }

  public void setCategeory(String categeory) {
    this.categeory = categeory;
  }

  public String getAutomation() {
    return automation;
  }

  public void setAutomation(String automation) {
    this.automation = automation;
  }
}
