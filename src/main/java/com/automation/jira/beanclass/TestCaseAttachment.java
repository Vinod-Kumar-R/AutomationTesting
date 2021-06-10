package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseAttachment {
  public int id;
  public String url;
  public String filename;
  public int filesize;
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getFilename() {
    return filename;
  }
  
  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  public int getFilesize() {
    return filesize;
  }
  
  public void setFilesize(int filesize) {
    this.filesize = filesize;
  }
}
