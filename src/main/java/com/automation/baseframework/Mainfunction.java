package com.automation.baseframework;

import com.automation.configuration.PropertiesValue;
import com.automation.custom.exception.DuplicateValueException;
import java.io.IOException;
import java.net.URISyntaxException;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * <h1> From this class execution start. </h1>
 * @author Vinod Kumar R
 *
 */
@SpringBootApplication
@ImportResource("classpath:springfile.xml")
@ComponentScan(basePackages = "com.automation")
@EnableFeignClients(basePackages = "com.automation.api")
@Log4j2
public class Mainfunction {
  
  /**
   * This is the main method.
   * @param args this the main arguments
   */

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Mainfunction.class, args);
    BaseClass baseClass = context.getBean(BaseClass.class);
    PropertiesValue propertiesvalue = context.getBean(PropertiesValue.class);

    try {
      log.debug("Start Executing Test cases");
      baseClass.startRun();
      log.debug("Complete Executing Test cases");

    } catch (EncryptedDocumentException | IOException 
                    | DuplicateValueException | URISyntaxException  e) {
      log.error(e);
    } finally {
      
      baseClass.extentReport.flushlog();
      if (propertiesvalue.isSendemail()) {
        log.info("Email method calling");
        baseClass.emailTestResult();
        log.info("Email sent successfully");
      } 
    }
  }
}
