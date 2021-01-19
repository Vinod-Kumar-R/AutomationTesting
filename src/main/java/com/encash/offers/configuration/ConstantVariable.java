package com.encash.offers.configuration;

import com.encash.offers.beanclass.RepositoryBean;
import com.encash.offers.custom.exception.DuplicateValueException;
import com.encash.offers.utility.ExcelReader;
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


  /**
   * This variable the contain application URL in which test script need to executed. 
   */
  public static String EncashURL;
  public static String AdminURL;
  public static String LogFile;
  public static String Test_Execution;
  /**
   * This variable contain the Test Script file which need to executed.
   */
  public static String TestDatas;
  /**
   * This variable contain test case file (test case ID) which which need to executed. 
   */
  public static String TestCases;
  public static String TestObjects;
  public static String ExtentReportsLocation;
  public static String ExtentReportsPropeties;
  public static String ScreenShotlocation;
  public static int ExplictWait;
  public static int polling;
  public static HashMap<String, Integer> TestDataRowNumber;
  public static HashMap<String, List<String>> GetObject;
  public static boolean HeadlessBrowser;
  public static String DesiredAndroidCapability;
  public static String AppiumURL;
  public static String ResultBaseLocation;
  public static String ResultLocation;
  public static String ResultLocation1;
  public static String ResultDatelocaton;
  public static String Configlocation;
  public static String Mailinatorurl;
  public static String Foldername = "AutomationResult";
  private static Logger logger = LogManager.getLogger(ConstantVariable.class.getName());
  private String dateformat = "dd_MMM_yyyy";
  private String timeformat = "HH_mm_ss";
  private ConfigurationReader cr;
  @Autowired
  @Qualifier("testdata")
  private ExcelReader std;
  @Autowired
  private PropertiesValue propertiesvalue;


  /**
   * This is the Constructor which is used to initialized the static variable.
   */
  public ConstantVariable()  {
  
    Configlocation = readEnvironmnetVariable("encashoffers");
    //Read the properties file
    cr = new ConfigurationReader();
    cr.readConfig(Configlocation + File.separator + "properties" 
                    + File.separator + "config.properties");

    //Setting the logger context
    LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) 
                    LogManager.getContext(false);
    File file = new File(Configlocation + File.separator + "properties" 
                    + File.separator + "log4j2.xml");
    context.setConfigLocation(file.toURI());
    //initializeVariable();
  }
  
  /**
   * This method is used to initialize the environment variable.
   */
  public void initializeVariable() {
   
  

    //setting the properties value 
    ResultBaseLocation = Configlocation + File.separator + "Result";
    Test_Execution = cr.getConfigurationStringValue("test_execution");
    ResultDatelocaton = dateTime(dateformat, ResultBaseLocation);
    ResultLocation = dateTime(timeformat, ResultDatelocaton);
    ResultLocation1 = ResultLocation + File.separator + Foldername;
    ExtentReportsLocation = ResultLocation1 + File.separator + "encashoffer.html";
    ScreenShotlocation = folderCreation(ResultLocation1, "ScreenShot");
    EncashURL = propertiesvalue.getEncashUrl();
    //EncashURL = cr.getConfigurationStringValue("encashurl");
    AdminURL = cr.getConfigurationStringValue("adminurl");
    TestDatas = cr.getConfigurationStringValue("testData");
    TestCases = cr.getConfigurationStringValue("testcase");
    TestObjects = cr.getConfigurationStringValue("testobject");
    ExtentReportsPropeties = Configlocation + File.separator + "properties" 
                    + File.separator + "extentreportpropertes.xml";
    ExplictWait = cr.getConfigurationIntValue("explictwait");
    polling = cr.getConfigurationIntValue("polling");
    HeadlessBrowser = cr.getConfigurationBooleanValue("headlessbrowser");
    AppiumURL = cr.getConfigurationStringValue("appiumServerurl");
    Mailinatorurl = cr.getConfigurationStringValue("mailinatorurl");
    
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
    logger.debug("found the testcase in the TestDatas" + ConstantVariable.TestDatas 
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
    ConstantVariable.GetObject = new HashMap<String, List<String>>();
    
    FileReader file = new FileReader(TestObjects);

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
        GetObject.put(key, object);
      } else {
        throw new DuplicateValueException("Duplicate name in column "
                        + "'ObjectName' file Object Repository file  ---> " + key);
      }
    }

  }

  /**
   * This method is used to created the folder structure based on the date and time format. 
   * @param timeformat it can be any time format
   * @param baseLocation this contain the location in which it has to create
   * @return  AbsolutePath of folder is return back  
   */

  public String dateTime(String timeformat, String baseLocation) {
    SimpleDateFormat formatter = new SimpleDateFormat(timeformat); 

    Date date = new Date();

    File file = new File(baseLocation + File.separator + formatter.format(date));
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

}
