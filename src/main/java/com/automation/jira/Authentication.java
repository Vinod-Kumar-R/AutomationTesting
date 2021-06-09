package com.automation.jira;

import com.automation.configuration.JiraConfiguration;
import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.internal.AuthenticationSpecificationImpl;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;

public class Authentication {
  
  @Autowired
  private JiraConfiguration jc;
  
  private AuthenticationSpecificationImpl authenticate;
  
  private AuthenticationScheme au;

  
  
 

  /**
   * aba.
   * @return 
   * @return
   */
  public RequestSpecification getToken(RequestSpecification requestspecification) {
    authenticate = new AuthenticationSpecificationImpl(requestspecification);
       
    return authenticate.basic(jc.getJiraUsername(), jc.getJiraPassword());
    //return authenticate.oauth2(jc.getJiratoken());
  }
  
  

}
