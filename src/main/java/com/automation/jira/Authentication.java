package com.automation.jira;

import com.automation.configuration.JiraConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Authentication {
  
  @Autowired
  private JiraConfiguration jc;
  
  /**
   * Credential for authentication.
   * @return token
   */
  public String credentcial() {
    
    if (!jc.getJiratoken().isEmpty()) {
      return jc.getJiratoken();
    } else {
      // get the token from user name and pass
      //code
    }
    return null;
    
  }
  
  

}
