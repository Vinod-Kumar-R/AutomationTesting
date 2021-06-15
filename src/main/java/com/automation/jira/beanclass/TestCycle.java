package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestCycle {
  
  @JsonProperty("id")
  public int id;
  
  @JsonProperty("key") 
  public String key;
  
  @JsonProperty("url")
  public String url;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  
  

}
