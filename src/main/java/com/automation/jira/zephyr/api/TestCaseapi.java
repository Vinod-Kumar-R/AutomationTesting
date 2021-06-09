package com.automation.jira.zephyr.api;

import com.automation.configuration.PropertiesValue;
import com.automation.jira.ApiEndPoints;
import com.automation.jira.RequestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCaseapi {

  @Autowired
  private RequestBuilder rb;

  @Autowired
  private PropertiesValue pv;

  /**
   * This method is used to fetch the specific test case.
   * @param testCase it the testcase Key 
   */
  public void getTestcase(String testCase) {
    RequestSpecification rs = rb.setSingleTestCase(ApiEndPoints.TEST_CASE_FETCH, testCase);
    Response res = RestAssured.given(rs).log().all().when().get();
    System.out.println(res.asPrettyString());
  }

  /**
   * This method is used to fetch the list of test case based on condition given in the parameter.
   * @param queryParam is the filter to fetch only required test case
   * @param startIndex starting index from 
   * @param limit max result to fetch
   * 
   */
  public void getAllTestCase(String queryParam, int startIndex, int limit) {
    RequestSpecification rs = rb.setAllTestCase(ApiEndPoints.TEST_CASE_SEARCH, queryParam,
                    startIndex, limit);
    Response res = RestAssured.given(rs).log().all().when().get();

    System.out.println(res.asPrettyString());


  }


  /**
   * This method is used to get the list of attachment for particular test case.
   * @param testCase is the test key in the jira
   */
  public void getTestCaseAttachmentList(String testCase) {
    RequestSpecification rs = rb.setSingleTestCase(ApiEndPoints.TEST_CASE_ATTACHMENT, testCase);
    Response res = RestAssured.given(rs).log().all().when().get();

    System.out.println(res.asPrettyString());
  }



  /**
   * Method is used to download the attachment from the file URL.  
   * @param fileurl is the link from where it has to download file
   * @param filename has to copy
   */
  public void getDownloadTestCaseFile(String fileurl, String filename) {

    //RequestSpecification rs = rb.setDownloadAttachement("http://192.168.0.109:8080/rest/tests/1.0/attachment/1/");
    RequestSpecification rs = rb.setDownloadAttachement(fileurl);
    Response res = RestAssured.given(rs).log().all().when().get();
    InputStream dowloadedFile = res.asInputStream();
    copyfile(dowloadedFile, filename);
    System.out.println(res.asPrettyString());
  }
  
  
  /**
   * Method used to copy the file form InputStream to file.
   * @param dowloadedFile file in InputStream.
   * @param filename name of the file 
   */
  private void copyfile(InputStream dowloadedFile, String filename) {
    try {
      BufferedInputStream inputStream = new BufferedInputStream(dowloadedFile);
      FileOutputStream fileOS = new FileOutputStream(pv.getConfigLocation() + "/temp/" + filename); 
      byte[] data = new byte[1024];
      int byteContent;
      while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
        fileOS.write(data, 0, byteContent);
      }
    } catch (IOException e) {

    }

  }



}
