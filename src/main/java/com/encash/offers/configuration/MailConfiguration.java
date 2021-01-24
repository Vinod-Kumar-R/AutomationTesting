package com.encash.offers.configuration;

import java.io.File;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class MailConfiguration {
  
  @Value("${email.host}")
  private String host;
  
  @Value("${email.port}")
  private int port;
  
  @Value("${email.fromid}")
  private String fromEmailId;
  
  @Value("${email.password}")
  private String emailpassword;
  
  @Value("${email.subject}")
  private String emailSubject;
  
  @Value("${email.toid}")
  private String[] toEmailid;
  
  @Value("${email.username}")
  private String emailUsername;
  
  @Autowired
  private PropertiesValue properties;
  
  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getFromEmailId() {
    return fromEmailId;
  }

  public String getEmailpassword() {
    return emailpassword;
  }

  public String getEmailSubject() {
    return emailSubject;
  }

 

  public String getEmailUsername() {
    return emailUsername;
  }
  

  public String[] getToEmailid() {
    return toEmailid;
  }

  /**
   * This method is used to set the configuration of Email.
   * @return the java Mail sender instance 
   */
  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    // Using Gmail SMTP configuration.
    mailSender.setHost(getHost());
    mailSender.setPort(getPort());

    /*
     * Use your gmail id and password
     */
    mailSender.setUsername(getEmailUsername());
    mailSender.setPassword(getEmailpassword());

    Properties javaMailProperties = new Properties();
    javaMailProperties.put("mail.smtp.starttls.enable", "true");
    javaMailProperties.put("mail.smtp.auth", "true");
    javaMailProperties.put("mail.transport.protocol", "smtp");
    javaMailProperties.put("mail.debug", "true");

    mailSender.setJavaMailProperties(javaMailProperties);
    return mailSender;
  }

  /**
   * This method is used to FreeMarker configuration.
   * @return the bean for FreeMarker configuration
   */
  @Bean
  public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
    String location = "file://" +  properties.getConfigLocation()
                    + File.separator + "properties" + File.separator;
    FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
    bean.setTemplateLoaderPath(location);
    return bean;
  }
}
