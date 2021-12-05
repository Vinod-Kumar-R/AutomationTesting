package com.automation.configuration;

import java.io.File;
import java.util.Properties;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
@Component
@Getter
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
    javaMailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    javaMailProperties.put("mail.debug", "false");
    javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2");

    mailSender.setJavaMailProperties(javaMailProperties);
    return mailSender;
  }

  /**
   * This method is used to FreeMarker configuration.
   * @return the bean for FreeMarker configuration
   */
  @Bean
  public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
    String location = "file:" +  properties.getConfigLocation()
                    + File.separator + "properties";
    FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
    bean.setTemplateLoaderPath(location);
    return bean;
  }
}
