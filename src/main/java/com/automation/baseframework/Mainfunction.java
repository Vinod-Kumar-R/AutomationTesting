package com.automation.baseframework;

import com.automation.configuration.PropertiesValue;
import com.automation.custom.exception.DuplicateValueException;
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
    BaseClass bc = context.getBean("base", BaseClass.class);
    PropertiesValue propertiesvalue = context.getBean("properties", PropertiesValue.class);
    
    try {
      logger.debug("Start Executing Test cases");
      bc.startRun();
      logger.debug("Complete Executing Test cases");
      System.gc();

    } catch (EncryptedDocumentException | IOException 
                    | DuplicateValueException  e) {
      // TODO Auto-generated catch block
      logger.error(e);
      e.printStackTrace();
      bc.extentReport.flushlog();
      
    } finally {

      if (propertiesvalue.isSendemail()) {
        logger.info("Email method calling");
        bc.emailTestResult();
        logger.info("Email sent successfully");
      }
      
      ((ClassPathXmlApplicationContext) context).close();
    }
  }
}
