package com.automation.jira;

public class ApiEndPoints {
  
  public static final String TEST_CASE_CREATE = "/testcase";
  public static final String TEST_CASE_FETCH = "/testcase/{testCaseKey}";
  public static final String TEST_CASE_ATTACHMENT = "/testcase/{testCaseKey}/attachments";
  public static final String TEST_CASE_LATEST_RESULT = "/testcase/{testCaseKey}/testresult/latest";
  public static final String TEST_CASE_SEARCH = "/testcase/search";
  
  public static final String TEST_AUTOMATION_RESULT = "/automation/execution/{projectKey}";
  

}
