package com.automation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class JiraConfiguration {
  
  @Value("${jira.username}")
  private String jiraUsername;
  
  @Value("${jira.password}")
  private String jiraPassword;
  
  @Value("${jira.url}")
  private String jiraurl;
  
  @Value("${jira.token}")
  private String jiratoken;
  
  @Value("${jira.testcase.query}")
  private String testcaseQuery;
  
  @Value("${jira.testcase.fetchmax}")
  private int testcaseMaxresult;
  
  @Value("${jira.projectkey}")
  private String jiraProjectkey;

  public String getJiraUsername() {
    return jiraUsername;
  }

  public String getJiraPassword() {
    return jiraPassword;
  }

  public String getJiraurl() {
    return jiraurl;
  }

  public String getJiratoken() {
    return jiratoken;
  }

  public String getTestcaseQuery() {
    return testcaseQuery;
  }

  public int getTestcaseMaxresult() {
    return testcaseMaxresult;
  }

  public String getJiraProjectkey() {
    return jiraProjectkey;
  }

}
