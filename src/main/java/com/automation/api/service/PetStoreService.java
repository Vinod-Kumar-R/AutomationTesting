package com.automation.api.service;

import com.automation.api.controller.PetStoreController;
import com.automation.api.model.Pet;
import com.automation.utility.GenericMethod;
import feign.FeignException;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Class contain the Service layer of PetStore.
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
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
    log.info("Pet Store status code " + response.getStatusCode());
  }
  
  /**
   * Method is used to fetch the particular Pet.
   * @param data petID
   */
  public void getPet(List<String> data) {
    int petid = Integer.parseInt(data.get(0));
    try {
      ResponseEntity<Pet> response = petStoreController.fetchPet(petid);
      log.info("fetch Pet detail " + response.getBody().getId());
    } catch (FeignException e) {
      log.info("fetch Pet detail error" + e.getMessage());
    }
    
  }
}
