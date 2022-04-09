package com.automation.api.controller;

import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import java.io.IOException;

public class CustomErrorDecoder implements ErrorDecoder {

  final Decoder decoder;
  final ErrorDecoder defaultDecoder = new ErrorDecoder.Default();
  

  public CustomErrorDecoder(Decoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Exception decode(String methodKey, Response response) {
    //capturing error message from response body.
    try {
      int status = response.status();
      
      //setting the status as 200 because in feign 404 method return null. 
      response = response.toBuilder().status(200).build();
      PetStoreException petstoreException = 
                      (PetStoreException) decoder.decode(response, PetStoreException.class);
      petstoreException.setStatus(status);
      
      //setting to original status to response 
      response = response.toBuilder().status(status).build();
      
      //capture in the pojo Response of type feign.response so that in future required 
      //for header verification. 
      petstoreException.setResponse(response);
      
      return  (Exception) petstoreException;

    } catch (final IOException fallbackToDefault) {
      return defaultDecoder.decode(methodKey, response);
    } 
  }
}


