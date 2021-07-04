package com.automation.configuration;

import com.automation.beanclass.RepositoryBean;
import com.automation.custom.exception.DuplicateValueException;
import com.automation.dao.RepositoryDao;
import com.automation.utility.ExcelReader;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * This class the constant variable data which is used during execution of script. 
 * @author Vinod Kumar R
 *
 */
public class ConstantVariable {

  private String extentReportsLocation;
  public static String ScreenShotlocation;
  public static HashMap<String, Integer> TestDataRowNumber;
  private HashMap<String, List<String>> objects;
  private String resultBaseLocation;
  private String resultLocation;
  private String resultLocation1;
  private String resultDatelocaton;
  public static String Foldername = "AutomationResult";
  private String environment = "automation";
  private static Logger logger = LogManager.getLogger(ConstantVariable.class.getName());
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
   * This is the Constructor which is used to initialized the static variable.
   */
  public ConstantVariable()  {
  
    String location = readEnvironmnetVariable(environment);
    //Setting the logger context
    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) 
                    LogManager.getContext(false);
    File file = new File(location + File.separator + "properties" 
                    + File.separator + "log4j2.xml");
    context.setConfigLocation(file.toURI());
    //initializeVariable();
  }
  
  /**
   * This method is used to initialize the environment variable.
   */
  public void initializeVariable() {

    //setting the properties value 
    date = dateTime(dateformat);
    time = dateTime(timeformat);
    resultBaseLocation = properties.getConfigLocation() + File.separator + "Result";
    resultDatelocaton = dateTime(date, resultBaseLocation);
    resultLocation = dateTime(time, resultDatelocaton);
    resultLocation1 = resultLocation + File.separator + Foldername;
    extentReportsLocation = resultLocation1 + File.separator + "automation.html";
    properties.setExtentreportlocation(extentReportsLocation);
    ScreenShotlocation = folderCreation(resultLocation1, "ScreenShot");
    
    //create temp folder 
    if (properties.isJiraIntegration()) {
      tempBaseLocation = properties.getConfigLocation() + File.separator + "temp";
      String tempDateLocation = dateTime(date, tempBaseLocation);
      String templocation = dateTime(time, tempDateLocation);
      properties.setTemplocation(templocation);
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

    ConstantVariable.TestDataRowNumber = new HashMap<String, Integer>();
    
    for (int i = 0; i < std.rowCout(0); i++) {
      if (std.getCellData(i, 0) != null 
                      && !std.getCellData(i, 0).equalsIgnoreCase("End")
                      && !std.getCellData(i, 0).equalsIgnoreCase("EndTestCase")) {
        ConstantVariable.TestDataRowNumber.put(std.getCellData(i, 0), i);
        logger.debug(std.getCellData(i, 0));
      }
    }

    std.closeWorkbook();
    logger.debug("found the testcase in the TestDatas" + properties.getTestdata() 
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
   * @throws FileNotFoundException , if excel file is not found in specified location 
   *     then it throw FileNotFoundException 
   * @throws DuplicateValueException duplicate value are found in the Repository file
   */
  public void objectRepository() throws FileNotFoundException, DuplicateValueException  {

    String key;
    Set<String> duplicateValue = new HashSet<String>();
    objects = new HashMap<String, List<String>>();
    
    FileReader file = new FileReader(properties.getTestobject());

    List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
                    .withType(RepositoryBean.class).build().parse();

    for (RepositoryBean ro : repositoryobject) {
      List<String> object = new ArrayList<String>();
      key = ro.getObjectName();
      object.add(ro.getObjectType());
      object.add(ro.getObjectValue());
      
      logger.debug("Key ---> " + key + "  Type ----> " + ro.getObjectType() 
          + "  Value-----> " + ro.getObjectValue());
      
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
      logger.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
      logger.error("Folder cann't be created", e);
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
      logger.debug("Folder created at location " + file.getAbsolutePath());
    } catch (IOException e) {
     
      logger.error("Folder cann't be created", e);
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
   * method is used to insert the data to database and when every it required uncomment it
   * @throws FileNotFoundException file not found
   */
  /*
  public void writetodata() throws FileNotFoundException {
    FileReader file = new FileReader(properties.getTestobject());

    List<RepositoryBean> repositoryobject = new CsvToBeanBuilder<RepositoryBean>(file)
                    .withType(RepositoryBean.class).build().parse();
    
    for (RepositoryBean csvobject : repositoryobject) {
      respository.insertValue(csvobject);
    }
    
  }*/
  
}
