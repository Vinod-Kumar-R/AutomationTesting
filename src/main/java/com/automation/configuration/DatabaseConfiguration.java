package com.automation.configuration;

import com.automation.beanclass.RepositoryBean;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class is used to configuration the data base related.
 * @author Vinod Kumar R
 *
 */
@Configuration
@Getter
@EnableTransactionManagement
public class DatabaseConfiguration {
  
  @Value("${jdbc.username}")
  private String username;
  
  @Value("${jdbc.password}")
  private String password;
  
  @Value("${jdbc.databaseurl}")
  private String url;
  
  @Value("${jdbc.driverClassName}")
  private String driverClassName;


  /**
   * Method is used to setup the DataSource with user name and password.
   * @return DataSource
   */
  @Bean
  @Lazy
  public DataSource getDataSource() {
    DriverManagerDataSource drivermanager = new DriverManagerDataSource();
    drivermanager.setDriverClassName(getDriverClassName());
    drivermanager.setUrl(getUrl());
    drivermanager.setUsername(getUsername());
    drivermanager.setPassword(getPassword());
    return drivermanager;
  }
  
  /**
   * create a sessionFactory for Hibernate.
   * @return LocalSessionFactoryBean which is used for HibernateTransactionManager
   */
  @Autowired
  @Bean(name = "sessionFactory")
  @Lazy
  public LocalSessionFactoryBean getSessionFactory() {
    
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(getDataSource());
    sessionFactory.setAnnotatedPackages("com.automation.beanclass");
    sessionFactory.setAnnotatedClasses(RepositoryBean.class);
    Properties hibernateProperties = new Properties();
    hibernateProperties.put("jdbc.dialect", "org.hibernate.dialect.MySQLDialect");
    hibernateProperties.put("hibernate.show_sql", "false");
    hibernateProperties.put("hibernate.connection.pool_size", "10");
    hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
    sessionFactory.setHibernateProperties(hibernateProperties);
    return sessionFactory;
  }
  
  /**
   * Method is used to inject the sessionFactory to HibernateTrasactionManager.
   * @param sessionFactory get from LocalSessionFactoryBean
   * @return transactionManager
   */
  @Bean (name = "transactionManager")
  public HibernateTransactionManager gettransactionManager(SessionFactory sessionFactory) {
    HibernateTransactionManager transactionManager = 
                    new HibernateTransactionManager(sessionFactory);
    return transactionManager;
  }

 
  /**
   * Method used to initializing the HibernateTemplate.
   * @return Hibernate Template.
   */
  @Bean
  @Autowired
  @Lazy
  public HibernateTemplate getHibernateTemplate() {
    HibernateTemplate hibernateTemplate = new HibernateTemplate(getSessionFactory().getObject());
    hibernateTemplate.setCheckWriteOperations(false);
    return hibernateTemplate;
    
  }
}
