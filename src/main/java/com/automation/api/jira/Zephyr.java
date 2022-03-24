package com.automation.api.jira;

import com.automation.jira.beanclass.JiraResult;
import com.automation.jira.beanclass.QueryParamSearch;
import com.automation.jira.beanclass.TestCase;
import com.automation.jira.beanclass.TestCaseAttachment;
import java.net.URI;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class contain the Method Signature of JIRA API.
 * @author Vinod Kumar R
 *
 */
@FeignClient(url = "${jira.url}", name = "jira", configuration = JiraApiConfiguration.class)
public interface Zephyr {

  @RequestMapping(method = RequestMethod.GET, value = "/testcase/search")
  ResponseEntity<List<TestCase>> fetchSearchTestCase(@SpringQueryMap QueryParamSearch query);
  
  @RequestMapping(method = RequestMethod.GET, value = "/testcase/{testCaseKey}/attachments")
  ResponseEntity<List<TestCaseAttachment>> fetchTestcaseAttachment(
                  @PathVariable("testCaseKey") String testCaseid);
  
  @RequestMapping(method = RequestMethod.GET)
  ResponseEntity<byte[]> dowloadfile(URI baseUri);
  
  @RequestMapping(method = RequestMethod.POST, value = "/automation/execution/{projectKey}", 
                  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<JiraResult> uploadAutomationResult(@PathVariable("projectKey") String key, 
                  @RequestPart MultipartFile file);
  
}
