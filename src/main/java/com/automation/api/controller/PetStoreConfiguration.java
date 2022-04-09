package com.automation.api.controller;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;

import java.util.List;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class PetStoreConfiguration {

  final Decoder decoder = new GsonDecoder();
  
  /**
   * Setting the API KEY header for each request.
   * @return RequestInterceptor.
   */
  @Bean
  public RequestInterceptor apiKey() {
    return new RequestInterceptor() {
      
      @Override
      public void apply(RequestTemplate template) {
        template.header("api_key", "special-key");
      }
    };
  }
  
  @Bean
  public CustomErrorDecoder errorDecoder() {
    return new CustomErrorDecoder(new ResponseEntityDecoder(decoder));
    
  }
}
