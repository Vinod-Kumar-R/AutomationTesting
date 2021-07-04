package com.automation.jira;

import com.automation.configuration.JiraConfiguration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

public class RequestBuilder {

  @Autowired
  private JiraConfiguration jc;
  @Autowired
  private Authentication authentication;

  private RequestSpecBuilder requestbuilder;

 
  /**
   * Method is common for all request.
   */
  private void init() {
    requestbuilder =  new RequestSpecBuilder();
    requestbuilder.setBaseUri(jc.getJiraurl());
    requestbuilder.addHeader("authorization", authentication.credentcial());
  }

  /**
   * Method used to set the pre-request for fetch test case.
   * @param path is resource locator
   * @param testcasekey is test key in jira
   * @return RequestSpecification
   */
  public RequestSpecification setSingleTestCase(String path, String testcasekey) {
    init();
    requestbuilder.addHeader("accept", "application/json");
    requestbuilder.addPathParam("testCaseKey", testcasekey);
    requestbuilder.setBasePath(path);
    return requestbuilder.build();
  }
  
  /**
   * Method used to pre-request the request method for download file.
   * @param url download file link
   * @return RequestSpecification
   */
  public RequestSpecification setDownloadAttachement(String url) {
    init();
    requestbuilder.setBaseUri(url);
    return requestbuilder.build();
    
    
  }

  /**
   * Method is used to set pre-request header and query parameter for request.
   * @param path is resource path
   * @param query is to filter test case
   * @param startIndex  starting index from 
   * @param limit  max result to fetch
   * @return of type RequestSpecification
   */
  public RequestSpecification setAllTestCase(String path, String query,
                  int startIndex, int limit) {
    init();
    requestbuilder.addHeader("accept", "application/json");
    requestbuilder.addQueryParam("query", query);
    requestbuilder.addQueryParam("startAt", startIndex);
    requestbuilder.addQueryParam("maxResults", limit);
    requestbuilder.setBasePath(path);
    return requestbuilder.build();
  }
  
  /**
   * method used to file upload.
   * @param filelocation file location to send to Jira.
   * @param projectkey is unique key in jira which test case below to project
   * @param path is BasePath to which request has to send
   * @return of type RequestSpecification
   */
  public RequestSpecification postDataResult(String path, File filelocation, String projectkey) {
    init();
    requestbuilder.setBasePath(path);
    requestbuilder.addMultiPart(filelocation);
    requestbuilder.addHeader("accept", "application/json");
    requestbuilder.addPathParam("projectKey", projectkey);
    return requestbuilder.build();
  }

}
