package com.encash.offers.beanclass;

import java.io.File;
import java.util.Date;
import org.joda.time.Period;

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

  public File getAutomationresult() {
    return automationresult;
  }

  public void setAutomationresult(File automationresult) {
    this.automationresult = automationresult;
  }

  public Date getStartExecutiontime() {
    return startExecutiontime;
  }
  
  public void setStartExecutiontime(Date startExecutiontime) {
    this.startExecutiontime = startExecutiontime;
  }
  
  public Date getEndExecutiontime() {
    return endExecutiontime;
  }
  
  public void setEndExecutiontime(Date endExecutiontime) {
    this.endExecutiontime = endExecutiontime;
  }

  public int getPasstestcase() {
    return passtestcase;
  }
  
  public void setPasstestcase(int passtestcase) {
    this.passtestcase = passtestcase;
  }
  
  public int getFailtestcase() {
    return failtestcase;
  }
  
  public void setFailtestcase(int failtestcase) {
    this.failtestcase = failtestcase;
  }
  
  public int getSkiptestcase() {
    return skiptestcase;
  }
  
  public void setSkiptestcase(int skiptestcase) {
    this.skiptestcase = skiptestcase;
  }

  public int getTotaltestcase() {
    return totaltestcase;
  }

  public void setTotaltestcase(int totaltestcase) {
    this.totaltestcase = totaltestcase;
  }

  public String getBuildnumber() {
    return buildnumber;
  }

  public void setBuildnumber(String buildnumber) {
    this.buildnumber = buildnumber;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String string) {
    this.os = string;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getEncashUrl() {
    return encashUrl;
  }

  public void setEncashUrl(String encashUrl) {
    this.encashUrl = encashUrl;
  }

  public String getAdminUrl() {
    return adminUrl;
  }

  public void setAdminUrl(String adminUrl) {
    this.adminUrl = adminUrl;
  }

  public String getBrowsername() {
    return browsername;
  }

  public void setBrowsername(String browsername) {
    this.browsername = browsername;
  }

  public String getBrowserversion() {
    return browserversion;
  }

  public void setBrowserversion(String browserversion) {
    this.browserversion = browserversion;
  }

  public Period getDuration() {
    return duration;
  }

  public void setDuration(Period duration) {
    this.duration = duration;
  }
}
