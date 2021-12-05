package com.automation.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@Getter
@Setter
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
}
