package com.automation.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class Pet {

  int id;
  Category category;
  String name;
  List<String> photoUrls;
  List<Tag> tags;
  @JsonProperty("Status")
  Statuss status;
}
