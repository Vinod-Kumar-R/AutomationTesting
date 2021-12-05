package com.automation.jira.zephyr.api;

import com.automation.jira.ApiEndPoints;
import com.automation.jira.RequestBuilder;
import com.automation.jira.beanclass.TestCase;
import com.automation.jira.beanclass.TestCaseAttachment;
import com.google.common.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCaseapi {
  private static Logger logger = LogManager.getLogger(TestCaseapi.class);
  @Autowired
  private RequestBuilder requestBuilder;
  
  /**
   * This method is used to fetch the specific test case.
   * @param testCase it the testcase Key 
   */
  public void getTestcase(String testCase) {
    RequestSpecification rs = requestBuilder.setSingleTestCase(ApiEndPoints.TEST_CASE_FETCH,
                    testCase);
    Response res = RestAssured.given(rs).get();
    logger.debug("fetch test case " + res.asString());
    //System.out.println(res.asPrettyString());
  }

  /**
   * This method is used to fetch the list of test case based on condition given in the parameter.
   * @param queryParam is the filter to fetch only required test case
   * @param startIndex starting index from 
   * @param limit max result to fetch
   * @return found test case list are return
   * 
   */
  public List<TestCase> getAllTestCase(String queryParam, int startIndex, int limit) {
    RequestSpecification rs = requestBuilder.setAllTestCase(ApiEndPoints.TEST_CASE_SEARCH,
                    queryParam, startIndex, limit);
    Response res = RestAssured.given(rs).get();
    logger.debug("testcase respone " + res.asString());
    Type type = new TypeToken<List<TestCase>>() {}.getType();
    List<TestCase> testcase   = res.as(type);
    return testcase;
  }


  /**
   * This method is used to get the list of attachment for particular test case.
   * @param testCase is the test key in the jira
   * @return List of TestCaseAttachment for particular testcase ID
   */
  public List<TestCaseAttachment> getTestCaseAttachmentList(String testCase) {
    RequestSpecification rs = requestBuilder.setSingleTestCase(ApiEndPoints.TEST_CASE_ATTACHMENT,
                    testCase);
    Response res = RestAssured.given(rs).get();
    logger.debug(res.asString());
    Type type = new TypeToken<List<TestCaseAttachment>>() {}.getType();
    List<TestCaseAttachment> testcaseattachment = res.as(type);
    return testcaseattachment;
  }



  /**
   * Method is used to download the attachment from the file URL.  
   * @param fileurl is the link from where it has to download file
   * @param filename has to copy
   */
  public void getDownloadTestCaseFile(String fileurl, String filename) {

    RequestSpecification rs = requestBuilder.setDownloadAttachement(fileurl);
    Response res = RestAssured.given(rs).get();
    InputStream dowloadedFile = res.asInputStream();
    copyfile(dowloadedFile, filename);
  }
  
  
  /**
   * Method used to copy the file form InputStream to file.
   * @param dowloadedFile file in InputStream.
   * @param filename name of the file 
   */
  private void copyfile(InputStream dowloadedFile, String filename) {
    try {
      BufferedInputStream inputStream = new BufferedInputStream(dowloadedFile);
      FileOutputStream fileOS = new FileOutputStream(filename); 
      byte[] data = new byte[1024];
      int byteContent;
      while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
        fileOS.write(data, 0, byteContent);
      }
      fileOS.close();
    } catch (IOException e) {
      logger.error(e.getMessage());
      System.out.println(e.getMessage());
    }

  }



}
