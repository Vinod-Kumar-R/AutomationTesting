package com.automation.configuration;

import javax.sql.DataSource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Getter
public class ApplicationDataBaseConfiguration {

  @Value("${spring.app-db.username}")
  private String appDataBaseUserName;
  
  @Value("${spring.app-db.password}")
  private String appDataBasePassword;
  
  @Value("${spring.app-db.jdbcUrl}")
  private String appDataBaseUrl;
  
  @Value("${spring.app-db.driverClassName}")
  private String appDataBaseDriverClassName;
  
  /**
   * Method is used to setup the DataSource with user name and password.
   * @return DataSource
   */
  @Bean(name = "appdatabase")
  @Lazy
  public DataSource cemDataSource() {
    DriverManagerDataSource drivermanager = new DriverManagerDataSource();
    drivermanager.setDriverClassName(getAppDataBaseDriverClassName());
    drivermanager.setUrl(getAppDataBaseUrl());
    drivermanager.setUsername(getAppDataBaseUserName());
    drivermanager.setPassword(getAppDataBasePassword());
    return drivermanager;
  }
  
  @Bean(name = "jdbcCemTemplate")
  public JdbcTemplate jdbcCemTemplate(@Qualifier("appdatabase")DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
  
  
}
