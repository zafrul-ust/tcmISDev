package com.tcmis.internal.report.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/***************************************************************************
 * Bean for ad hoc usage report
 **************************************************************************/
public class AdhocUsageReportBean
    extends BaseDataBean {

  private String userID;
  private String endYear;
  private String endMonth;
  private String endDay;
  private String beginYear;
  private String beginMonth;
  private String beginDay;
  private String partNo;
  private String categoryID;
  private String categoryDesc;
  private String type;
  private String manufacturer;
  private String application;
  private String facilityID;
  private String method;
  private String unitOp;
  private String emissionPt;
  private String process;
  private Collection reportFields;
  private String listID;
  private String CASListID;
  private String reportType;
  private String chemType;
  private String loc;
  private String reportCategory;

  public AdhocUsageReportBean() {
  }

  //setter methods
  public void setUserId(String userID) {
    this.userID = userID;
  }

  public void setEndYear(String endYear) {
    this.endYear = endYear;
  }

  public void setEndMonth(String endMonth) {
    this.endMonth = endMonth;
  }

  public void setEndDay(String endDay) {
    this.endDay = endDay;
  }

  public void setBeginYear(String beginYear) {
    this.beginYear = beginYear;
  }

  public void setBeginMonth(String beginMonth) {
    this.beginMonth = beginMonth;
  }

  public void setBeginDay(String beginDay) {
    this.beginDay = beginDay;
  }

  public void setPartNo(String partNo) {
    this.partNo = partNo;
  }

  public void setCategoryID(String categoryID) {
    this.categoryID = categoryID;
  }

  public void setCategoryDesc(String categoryDesc) {
    this.categoryDesc = categoryDesc;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setFacilityID(String facilityID) {
    this.facilityID = facilityID;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public void setUnitOp(String unitOp) {
    this.unitOp = unitOp;
  }

  public void setEmissionPt(String emissionPt) {
    this.emissionPt = emissionPt;
  }

  public void setProcess(String process) {
    this.process = process;
  }

  public void setReportFields(Collection reportFields) {
    this.reportFields = reportFields;
  }

  public void setListID(String listID) {
    this.listID = listID;
  }

  public void setCASListID(String CASlistID) {
    this.CASListID = CASlistID;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public void setChemType(String chemType) {
    this.chemType = chemType;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public void setReportCategory(String reportCategory) {
    this.reportCategory = reportCategory;
  }

  //getter methods
  public String getUserID() {
    return this.userID;
  }

  public String getEndYear() {
    return this.endYear;
  }

  public String getEndMonth() {
    return this.endMonth;
  }

  public String getEndDay() {
    return this.endDay;
  }

  public String getBeginYear() {
    return this.beginYear;
  }

  public String getBeginMonth() {
    return this.beginMonth;
  }

  public String getBeginDay() {
    return this.beginDay;
  }

  public String getPartNo() {
    return this.partNo;
  }

  public String getCategoryID() {
    return this.categoryID;
  }

  public String getCategoryDesc() {
    return this.categoryDesc;
  }

  public String getType() {
    return this.type;
  }

  public String getManufacturer() {
    return this.manufacturer;
  }

  public String getApplication() {
    return this.application;
  }

  public String getFacilityID() {
    return this.facilityID;
  }

  public String getMethod() {
    return this.method;
  }

  public String getUnitOp() {
    return this.unitOp;
  }

  public String getEmissionPt() {
    return this.emissionPt;
  }

  public String getProcess() {
    return this.process;
  }

  public Collection getReportFields() {
    return this.reportFields;
  }

  public String getListID() {
    return this.listID;
  }

  public String getCASListID() {
    return this.CASListID;
  }

  public String getReportType() {
    return this.reportType;
  }

  public String getChemType() {
    return this.chemType;
  }

  public String getReportCategory() {
    return this.reportCategory;
  }

  public String getLoc() {
    return this.loc;
  }
}
