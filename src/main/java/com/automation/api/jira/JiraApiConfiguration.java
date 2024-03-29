package com.automation.api.jira;

import com.automation.configuration.JiraConfiguration;
import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * Class contain configuration of Jira API.
 * @author Vinod Kumar R
 *
 */
public class JiraApiConfiguration {

  
  @Autowired
  private JiraConfiguration jiraConfiguration;

  /**
   * Setting the Authorization header for each request.
   * @return RequestInterceptor.
   */
  @Bean
  public RequestInterceptor bearerToken() {
    return new RequestInterceptor() {
      
      @Override
      public void apply(RequestTemplate template) {
        template.header(HttpHeaders.AUTHORIZATION, jiraConfiguration.getJiratoken());
      }
    };
  }
  
  /**
   * Method is used uploading the file using multipartform.
   * @return Encoder
   */
  @Bean
  public Encoder multipartFormEncoder() {
    return new SpringFormEncoder(new SpringEncoder(new ObjectFactory<HttpMessageConverters>() {
      @Override
      public HttpMessageConverters getObject() throws BeansException {
        return new HttpMessageConverters(new RestTemplate().getMessageConverters());
      }
    }));
  }
}
