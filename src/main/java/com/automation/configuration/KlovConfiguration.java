package com.automation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class KlovConfiguration {
  
  @Value("${project.name}")
  private String projectName;
  
  @Value("${report.name}")
  private String reportName;
  
  @Value("${mongodb.host}")
  private String mongoUrl;
  
  @Value("${mongodb.port}")
  private int mongoPort;
  
  @Value("${klov.host}")
  private String klovUrl;
  
  @Value("${klov.port}")
  private int klovPort;

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public String getReportName() {
    return reportName;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public String getMongoUrl() {
    return mongoUrl;
  }

  public void setMongoUrl(String mongoUrl) {
    this.mongoUrl = mongoUrl;
  }

  public int getMongoPort() {
    return mongoPort;
  }

  public void setMongoPort(int mongoPort) {
    this.mongoPort = mongoPort;
  }

  public int getKlovPort() {
    return klovPort;
  }

  public void setKlovPort(int klovPort) {
    this.klovPort = klovPort;
  }

  public String getKlovUrl() {
    return klovUrl;
  }

  public void setKlovUrl(String klovUrl) {
    this.klovUrl = klovUrl;
  }
}
