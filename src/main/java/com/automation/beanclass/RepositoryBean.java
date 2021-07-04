package com.automation.beanclass;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is the Bean class to read Object file. 
 * @author Vinod Kumar R
 *
 */

@Entity
@Table(name = "object")
public class RepositoryBean {

  @Id
  @Column(name = "ObjectName", nullable = false)
  @CsvBindByName(column = "ObjectName", required = true)
  private String objectName;

  @Column(name = "ObjectValue", nullable = false)
  @CsvBindByName(column = "ObjectValue", required = true)
  private String objectValue;

  @Column(name = "ObjectType", nullable = false)
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
