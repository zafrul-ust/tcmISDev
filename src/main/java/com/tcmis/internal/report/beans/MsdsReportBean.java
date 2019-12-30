package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/***************************************************************************
 * Bean for MSDS report
 **************************************************************************/
public class MsdsReportBean
    extends BaseDataBean {

  private String facility;
  private String client;
  private String screen;
  private String fileType;
  private String method;
  private int userId;

  public MsdsReportBean() {
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public String getFacility() {
    return this.facility;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getClient() {
    return this.client;
  }

  public void setScreen(String screen) {
    this.screen = screen;
  }

  public String getScreen() {
    return this.screen;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public String getFileType() {
    return this.fileType;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getMethod() {
    return this.method;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return this.userId;
  }

}