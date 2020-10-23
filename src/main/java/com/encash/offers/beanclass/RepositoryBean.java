package com.encash.offers.beanclass;

import com.opencsv.bean.CsvBindByName;
/**
 * This is the Bean class to read Object file. 
 * @author Vinod Kumar R
 *
 */

public class RepositoryBean {
  @CsvBindByName(column = "ObjectName", required = true)
  private String objectName;

  @CsvBindByName(column = "ObjectValue", required = true)
  private String objectValue;

  @CsvBindByName(column = "ObjectType", required = true)
  private String objectType;

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  public String getObjectValue() {
    return objectValue;
  }

  public void setObjectValue(String objectValue) {
    this.objectValue = objectValue;
  }

  public String getObjectType() {
    return objectType;
  }

  public void setObjectType(String objectType) {
    this.objectType = objectType;
  }

  



}
