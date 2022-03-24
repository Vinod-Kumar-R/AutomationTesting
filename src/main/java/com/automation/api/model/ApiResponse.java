package com.automation.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class contain POJO detail of API Response.
 * @author Vinod Kumar R
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {

  int code;
  String type;
  String message;
}
