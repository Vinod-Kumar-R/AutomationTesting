package com.automation.jira.beanclass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "with")
public class QueryParamSearch {
  
  String fields;
  int maxResults;
  String query;
  int startAt;

}
