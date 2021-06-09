package com.automation.jira;

import java.net.URI;

public class ApiEndPoints {
  
  public static final String TEST_CASE_CREATE = "/testcase";
  public static String TEST_CASE_FETCH = "/testcase/{testCaseKey}";
  public static String TEST_CASE_ATTACHMENT = "/testcase/{testCaseKey}/attachments";
  public static String TEST_CASE_LATEST_RESULT = "/testcase/{testCaseKey}/testresult/latest";
  public static String TEST_CASE_SEARCH = "/testcase/search";
  

}
