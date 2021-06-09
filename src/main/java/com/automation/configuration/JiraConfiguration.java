package com.automation.configuration;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JiraConfiguration {
  
  @Value("${jira.username}")
  private String jiraUsername;
  
  @Value("${jira.password}")
  private String jiraPassword;
  
  @Value("${jira.url}")
  private String jiraurl;
  
  @Value("${jira.token}")
  private String jiratoken;
  
  @Value("${jira.port}")
  private int jiraport;

  public String getJiratoken() {
    return jiratoken;
  }

  public void setJiratoken(String jiratoken) {
    this.jiratoken = jiratoken;
  }

  public int getJiraport() {
    return jiraport;
  }

  public void setJiraport(int jiraport) {
    this.jiraport = jiraport;
  }

  public String getJiraUsername() {
    return jiraUsername;
  }

  public void setJiraUsername(String jiraUsername) {
    this.jiraUsername = jiraUsername;
  }

  public String getJiraPassword() {
    return jiraPassword;
  }

  public void setJiraPassword(String jiraPassword) {
    this.jiraPassword = jiraPassword;
  }

  public String getJiraurl() {
    return jiraurl;
  }

  public void setJiraurl(String jiraurl) {
    this.jiraurl = jiraurl;
  }
  
  public URI getBaseurl() {
    URI baseurl = null;
    try {
      baseurl = new URI("http://" + "192.168.0.109" + ":" + getJiraport() + "/rest/atm/1.0");
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return baseurl;
  }
  
  

}
