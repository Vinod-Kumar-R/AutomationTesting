package com.automation.api.model;

/**
 * This class is used for Pojo converting the JSON to object and vice-versa for pet store.
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

  int id;
  String name;
}
