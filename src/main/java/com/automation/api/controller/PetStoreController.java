package com.automation.api.controller;

import com.automation.api.model.Pet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Class contain the method signature of PET Web site.
 * @author Vinod Kumar R
 *
 */
@FeignClient(name = "test", url = "https://petstore.swagger.io/v2")
public interface PetStoreController {

  @RequestMapping(method = RequestMethod.POST, value = "/pet")
  ResponseEntity<Pet> newPet(@RequestBody Pet pet);
  
  @RequestMapping(method = RequestMethod.GET, value = "/pet/{petId}")
  ResponseEntity<Pet> fetchPet(@PathVariable int petid);
}
