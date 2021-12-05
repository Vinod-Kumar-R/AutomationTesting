package com.automation.jira.beanclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseAttachment {
  public int id;
  public String url;
  public String filename;
  public int filesize;
}
