package com.tcmis.internal.report.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/***************************************************************************
 * Bean for Material Matrix report
 **************************************************************************/
public class MaterialMatrixReportBean
    extends BaseDataBean {

  private String userID;
  private String endYear;
  private int endMonth;
  private String beginYear;
  private int beginMonth;
  private String partNo;
  private String type;
  private String workAreaName;
  private String facilityID;
  private String method;
  private Collection reportFields;
  private int format;
  private Collection chemicalReportFields;

  public MaterialMatrixReportBean() {
  }

  //setter methods
  public void setUserId(String userID) {
    this.userID = userID;
  }

  public void setEndYear(String endYear) {
    this.endYear = endYear;
  }

  public void setEndMonth(int endMonth) {
    this.endMonth = endMonth;
  }

  public void setBeginYear(String beginYear) {
    this.beginYear = beginYear;
  }

  public void setBeginMonth(int beginMonth) {
    this.beginMonth = beginMonth;
  }

  public void setPartNo(String partNo) {
    this.partNo = partNo;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setWorkAreaName(String workAreaName) {
    this.workAreaName = workAreaName;
  }

  public void setFacilityID(String facilityID) {
    this.facilityID = facilityID;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setReportFields(Collection reportFields) {
    this.reportFields = reportFields;
  }

  public void setChemicalReportFields(Collection chemicalReportFields) {
    this.chemicalReportFields = chemicalReportFields;
  }

  public void setFormat(int format) {
    this.format = format;
  }

  //getter methods
  public String getUserID() {
    return this.userID;
  }

  public String getEndYear() {
    return this.endYear;
  }

  public int getEndMonth() {
    return this.endMonth;
  }

  public String getBeginYear() {
    return this.beginYear;
  }

  public int getBeginMonth() {
    return this.beginMonth;
  }

  public String getPartNo() {
    return this.partNo;
  }

  public String getType() {
    return this.type;
  }

  public String getWorkAreaName() {
    return this.workAreaName;
  }

  public String getFacilityID() {
    return this.facilityID;
  }

  public String getMethod() {
    return this.method;
  }

  public Collection getReportFields() {
    return this.reportFields;
  }

  public Collection getChemicalReportFields() {
    return this.chemicalReportFields;
  }

  public int getFormat() {
    return this.format;
  }
}
