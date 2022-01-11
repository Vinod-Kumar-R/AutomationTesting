package com.automation.api.service;

import com.automation.api.controller.PetStoreController;
import com.automation.api.model.Pet;
import com.automation.utility.GenericMethod;
import feign.FeignException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PetStoreService {
  
  @Autowired
  private PetStoreController petStoreController;
  @Autowired
  private GenericMethod genericMethod;

  /**
   * Method is used to create a new Pet.
   * @param data is a json data of type Pet
   */
  public void createPet(List<String> data) {
    
    Pet pet =  genericMethod.fromJson(data.get(0), Pet.class);
    
    ResponseEntity<Pet> response =  petStoreController.newPet(pet);
    System.out.println(response.getStatusCode());
  }
  
  /**
   * Method is used to fetch the particular Pet.
   * @param data petID
   */
  public void getPet(List<String> data) {
    int petid = Integer.parseInt(data.get(0));
    try {
      ResponseEntity<Pet> response = petStoreController.fetchPet(petid);
      System.out.print(response.getBody().getId());
    } catch (FeignException e) {
      System.out.println(e.getMessage());
    }
    
  }
}
