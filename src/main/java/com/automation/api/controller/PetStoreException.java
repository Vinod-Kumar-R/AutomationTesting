package com.automation.api.controller;

import feign.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor

public class PetStoreException extends RuntimeException {
  
  Integer code;
  String type;
  String message;
  Integer status;
  Response response;

}
