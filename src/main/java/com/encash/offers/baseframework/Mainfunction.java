package com.encash.offers.baseframework;

import com.encash.offers.configuration.PropertiesValue;
import com.encash.offers.custom.exception.DuplicateValueException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <h1> From this class execution start. </h1>
 * @author Vinod Kumar R
 *
 */
public class Mainfunction {

  private static Logger logger = LogManager.getLogger(Mainfunction.class);
  /**
   * This is the main method.
   * @param args this the main arguments
   */
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub

    ApplicationContext context = new ClassPathXmlApplicationContext("springfile.xml");
    
    /*
    PropertiesValue pro = context.getBean("properties", PropertiesValue.class);
    logger.debug("propertives vinod " + pro.toString());
    System.out.println(pro.getEncashUrl());
    */
    BaseClass bc = context.getBean("base", BaseClass.class);
    try {
      
      logger.debug("Start Executing Test cases");
      bc.startRun();
      logger.debug("Complete Executing Test cases");
      bc.emailTestResult();
      logger.debug("Email has been sent");
      
    } catch (EncryptedDocumentException | DuplicateValueException | IOException e) {
      // TODO Auto-generated catch block
      logger.error(e);
      e.printStackTrace();
      bc.extentReport.flushlog();
    }
  }
}
