package com.automation.beanclass;

import com.aventstack.extentreports.model.Category;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.context.NamedAttributeContext;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.Data;
import org.joda.time.Period;
import org.springframework.stereotype.Component;

/**
 * This class is used to set the value to email report.
 * @author Vinod Kumar R
 *
 */
@Component
@Data
public class ExtentReportBean {
  
  private Date startExecutiontime;
  private Date endExecutiontime;
  private Period duration;
  private int totaltestcase;
  private int passtestcase;
  private int failtestcase;
  private int skiptestcase;
  private String buildnumber;
  private String os;
  private String platform;
  private String encashUrl;
  private String adminUrl;
  private String browsername;
  private String browserversion;
  private File automationresult;
  private Set<NamedAttributeContext<Category>> categeorys;
  private List<Test> failTest;
  private List<Test> passTest;
  private List<Test> skipTest;
  private String reportLink;

}
