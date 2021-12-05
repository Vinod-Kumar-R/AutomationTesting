package com.automation.beanclass;

import com.opencsv.bean.CsvBindByName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * This is the Bean class to read Object file. 
 * @author Vinod Kumar R
 *
 */
@Component
@Entity
@Table(name = "object")
@Getter
@Setter
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

}
