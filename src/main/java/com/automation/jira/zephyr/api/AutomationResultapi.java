package com.automation.jira.zephyr.api;

import com.automation.configuration.JiraConfiguration;
import com.automation.configuration.PropertiesValue;
import com.automation.jira.ApiEndPoints;
import com.automation.jira.RequestBuilder;
import com.automation.jira.beanclass.Execution;
import com.automation.jira.beanclass.TestResult;
import com.automation.jira.beanclass.TestStatus;
import com.automation.utility.ExtentReport;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AutomationResultapi {
  @Autowired
  private RequestBuilder requestBuilder;
  @Autowired
  private JiraConfiguration jc;
  @Autowired
  private ExtentReport extentreport;
  @Autowired
  private PropertiesValue properties;

  /**
   * method used to post automation result to jira.
   * @param file contain the zip file need to send
   */
  public void postTestResult(File file) {

    RequestSpecification rs = requestBuilder.postDataResult(ApiEndPoints.TEST_AUTOMATION_RESULT,
                    file, jc.getJiraProjectkey());
    Response res = RestAssured.given(rs).post();
    log.debug(res.asString());
    log.info("Result are posted to Jira " + res.getStatusCode());
    
  }


  /**
   * Method is used to create jira test result after completion of test execution.
   * @param filename  is a string which is used to store the notedpad++ for storing result.
   * @return file path.
   */
  public Path jiraresult(String filename) {

    List<Test> tests =  extentreport.getTestDetail();

    List<Execution> execution = new ArrayList<>();

    for (Test test : tests) {
      Execution ex = new Execution();
      if (test.getStatus().equals(Status.PASS)) {
        ex.setTestResult("Passed");
      } else {
        ex.setTestResult("Failed");
      }
      TestStatus ts = new TestStatus();
      ts.setTestcaseId(test.getName());

      ex.setTeststatus(ts);
      execution.add(ex);
    }

    TestResult result = new TestResult();
    result.setVersion(1);
    result.setExecutions(execution);

    File file = null;
    try {
      //create a json file 
      ObjectMapper resultjson = new ObjectMapper();
      file = new File(properties.getTemplocation() + File.separator + filename);
      resultjson.writeValue(file, result);
    } catch (JsonGenerationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (JsonMappingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return file.toPath();
  }

}
