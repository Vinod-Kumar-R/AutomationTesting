package com.automation.api.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is used for Pojo converting the JSON to object and vice-versa for pet store.
 * @author Vinod Kumar R
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

  int id;
  String name;
}
