package com.automation.configuration;

import com.automation.beanclass.RepositoryBean;
import com.automation.custom.exception.DuplicateValueException;
import com.automation.dao.RepositoryDao;
import com.automation.utility.ExcelReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This class the constant variable data which is used during execution of script. 
 * @author Vinod Kumar R
 *
 */
@Component
@Log4j2
public class ConstantVariable {

  private String extentReportsLocation;
  public static String screenShotlocation;
  public static HashMap<String, Integer> testDataRowNumber;
  private HashMap<String, List<String>> objects;
  private String resultBaseLocation;
  public static String resultLocation;
  private String resultLocation1;
  private String resultDatelocaton;
  public static String foldername = "AutomationResult";
  private String dateformat = "dd_MMM_yyyy";
  private String timeformat = "HH_mm_ss";
  private String date;
  private String time;
  private String tempBaseLocation;
  
  @Autowired
  @Qualifier("testdata")
  private ExcelReader std;
  @Autowired
  private PropertiesValue properties;
  @Autowired
  private RepositoryDao respository;

  
  /**
   * This method is used to initialize the environment variable.
   * @throws IOException 
   * @throws DuplicateValueException 
   */
  @PostConstruct
  public void initializeVariable() throws DuplicateValueException, IOException {

    //setting the properties value 
    date = dateTime(dateformat);
    time = dateTime(timeformat);
    resultBaseLocation = properties.getConfigLocation() + File.separator + "Result";
    resultDatelocaton = dateTime(date, resultBaseLocation);
    resultLocation = dateTime(time, resultDatelocaton);
    resultLocation1 = resultLocation + File.separator + foldername;
    properties.setResultfolder(resultLocation1);
    extentReportsLocation = resultLocation1 + File.separator + "automation.html";
    properties.setExtentreportlocation(extentReportsLocation);
    screenShotlocation = folderCreation(resultLocation1, "ScreenShot");
    
    //create temp folder 
    if (properties.isJiraIntegration()) {
      tempBaseLocation = properties.getConfigLocation() + File.separator + "temp";
      String tempDateLocation = dateTime(date, tempBaseLocation);
      String templocation = dateTime(time, tempDateLocation);
      properties.setTemplocation(templocation);
    }
    
    if (!properties.isObjectRepository() 
                    && properties.isAutomationType()) {
      objectRepository();
    }
  }

  /**
   * This method read the Test case Excel sheet and store the data of test case ID. 
   * starting number in an HashMap so that search can be faster
   * @throws IOException  if excel file is already open then it throw IOException
   * @throws EncryptedDocumentException if excel file is encrypted then it
   *                 throw EncryptedDocumentException
   */

  public void searchTestData() throws EncryptedDocumentException, IOException   {

    ConstantVariable.testDataRowNumber = new HashMap<String, Integer>();
    
    for (int i = 0; i < std.rowCout(0); i++) {
      if (std.getCellData(i, 0) != null 
                      && !std.getCellData(i, 0).equalsIgnoreCase("End")
                      && !std.getCellData(i, 0).equalsIgnoreCase("EndTestCase")) {
        ConstantVariable.testDataRowNumber.put(std.getCellData(i, 0), i);
        log.debug(std.getCellData(i, 0));
      }
    }

    std.closeWorkbook();
    log.debug("found the testcase in the TestDatas" + properties.getTestdata() 
                    + "in the row number ");
  }

  /**
   * This Method is used to read the Object Repository file which is csv file with header.
   * ObjectName and ObjectValue
   * this file contain key and value pair of data where 
   * ObjectName is key and it is unique for human readable format 
   * ObjectValue is the value which contain the xpath values used to identified location 
   *
   *<p>csv file are read the stored in the HashMap&lt;String,String&gt; so that during execution
   * we will get the xpath for an element 
   * @throws DuplicateValueException duplicate value are found in the Repository file
   * @throws IOException , excel file not found i.e. FileNotFoundException
   */
  public void objectRepository() throws DuplicateValueException, IOException  {

    String key;
    Set<String> duplicateValue = new HashSet<String>();
    objects = new HashMap<String, List<String>>();
    
    BufferedReader file = Files.newBufferedReader(Paths.get(properties.getTestobject()));

    List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
                    .withType(RepositoryBean.class).build().parse();

    for (RepositoryBean ro : repositoryobject) {
      List<String> object = new ArrayList<String>();
      key = ro.getObjectName();
      object.add(ro.getObjectType());
      object.add(ro.getObjectValue());
      object.add(ro.getAttributeName());
      object.add(ro.getAttributeValue());
      
      log.debug("Key ---> " + key + "  Type ----> " + ro.getObjectType() 
          + "  Value-----> " + ro.getObjectValue() 
          + " AttributeName------> " + ro.getAttributeName()
          + " AttributeValue ---------> " + ro.getAttributeValue());
      
      if (!duplicateValue.contains(key)) {
        duplicateValue.add(key);
        objects.put(key, object);
      } else {
        throw new DuplicateValueException("Duplicate name in column "
                        + "'ObjectName' file Object Repository file  ---> " + key);
      }
    }

  }
  
  
  /**
   * method is used to get the date and time format.
   * @param timeformat format type
   * @return string type
   */
  private String dateTime(String timeformat) {
    SimpleDateFormat formatter = new SimpleDateFormat(timeformat); 
    Date date = new Date();
    return formatter.format(date);
  }

  /**
   * This method is used to created the folder structure based on the date and time format. 
   * @param baseLocation this contain the location in which it has to create
   * @param foldername it a name in which folder has to created
   * @return  AbsolutePath of folder is return back  
   */

  public String dateTime(String foldername, String baseLocation) {

    File file = new File(baseLocation + File.separator + foldername);
    try {
      FileUtils.forceMkdir(file);
      log.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
      log.error("Folder cann't be created", e);
    }

    return file.getAbsolutePath();
  }


  /**
   * This method is used folder based on the given base folder location. 
   * @param baseLocation contain the location in which folder has to create
   * @param foldername contain the folder name in which has to create
   * @return AbsolutePath of folder is return back
   */
  public String folderCreation(String baseLocation, String foldername) {

    File file = new File(baseLocation + File.separator + foldername);
    try {
      FileUtils.forceMkdir(file);
      log.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
     
      log.error("Folder cann't be created", e);
    }

    return file.getAbsolutePath();
  }

  /**
   * This method is used to read the environment variable.
   * @param key take the environment key value
   * @return method return the environment value for specified key
   */
  public String  readEnvironmnetVariable(String key) {
    String configuration;
    configuration = System.getenv(key);
    return configuration;
  }
  
  /**
   * Method is used to get the ObjectValue from csv file or from database based on the condition.
   * @param object is the primary key to fetch the data.
   * @return data.
   */
  public List<String> getObject(String object) {
    
    if (properties.isObjectRepository()) {
      return respository.searchValue(object);
    } else {
      return this.objects.get(object);
    }
  }
  
  /**
   * Method is used to fetch the attribute name and attribute value for given object
   * @param object is unique in object repository csv file or database
   * @return List contain attribute name and attribute value
   */
  public List<String> getObjectAttibute(String object) {

    if (properties.isObjectRepository()) {
      return respository.searchAttribute(object);
    } else  {
      List<String> attribute = new ArrayList<>();
      attribute.add(this.objects.get(object).get(2));
      attribute.add(this.objects.get(object).get(3));
      return attribute;
    }
  }
  
 
  /*
   * method is used to insert the data to database and when every it required uncomment it.
   * @throws FileNotFoundException file not found
   */
  /**
  public void writetodata() throws FileNotFoundException {
    FileReader file = new FileReader(properties.getTestobject());

    List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
                    .withType(RepositoryBean.class).build().parse();
    
    for (RepositoryBean csvobject : repositoryobject) {
      respository.insertValue(csvobject);
    }
    
  }
 */ 
}
