package com.automation.custom.exception;

/**
 * This used for custom exception for duplicate value.
 * @author Vinod Kumar R
 *
 */
public class DuplicateValueException extends Exception {
  
  /**
   * This classed is used to define custom Exception.
   */
  private static final long serialVersionUID = -8754330977320393271L;

  public DuplicateValueException(String message) {
    super(message);
  }

}
