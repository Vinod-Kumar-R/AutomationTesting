package com.automation.api.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.automation.api.controller.PetStoreController;
import com.automation.api.controller.PetStoreException;
import com.automation.api.model.Pet;
import com.automation.utility.GenericMethod;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
      
      assertThat(response.getBody().getId())
      .as("fetch Pet Id is not same as requested Pet ID")
          .isEqualTo(Integer.parseInt(data.get(0)));
      
      assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

    } catch (PetStoreException e) {
      
      assertThat(e.getStatus()).isEqualTo(Integer.parseInt(data.get(1)));
      assertThat(e.getMessage()).isEqualTo(data.get(2));
      assertThat(e.getType()).isEqualTo(data.get(3));
      assertThat(e.getCode()).isEqualTo(Integer.parseInt(data.get(4)));

    }
  }
}
