package com.automation.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
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
}
