package com.tcmis.internal.report.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/***************************************************************************
 * Bean for Ad hoc waste report
 **************************************************************************/
public class AdhocWasteReportBean
    extends BaseDataBean {

  private String userID;
  private String endYear;
  private int endMonth;
  private String beginYear;
  private int beginMonth;
  private String type;
  private String hubWaste;
  private String facilityID;
  private String workAreaName;
  private String method;
  private Collection reportFields;
  private String vendor;
  private String accumePt;
  private String profileId;
  private String mgmtOption;
  private String mgmtOptionDesc;

  public AdhocWasteReportBean() {
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

  public void setType(String type) {
    this.type = type;
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

  public void setHubWaste(String hubWaste) {
    this.hubWaste = hubWaste;
  }

  public void setWorkAreaName(String workAreaName) {
    this.workAreaName = workAreaName;
  }

  public void setVendor(String vendor) {
    this.vendor = vendor;
  }

  public void setAccumePt(String accumePt) {
    this.accumePt = accumePt;
  }

  public void setProfileId(String profileId) {
    this.profileId = profileId;
  }

  public void setMgmtOption(String mgmtOption) {
    this.mgmtOption = mgmtOption;
  }

  public void setMgmtOptionDesc(String mgmtOptionDesc) {
    this.mgmtOptionDesc = mgmtOptionDesc;
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

  public String getType() {
    return this.type;
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

  public String getHubWaste() {
    return this.hubWaste;
  }

  public String getWorkAreaName() {
    return this.workAreaName;
  }

  public String getVendor() {
    return this.vendor;
  }

  public String getAccumePt() {
    return this.accumePt;
  }

  public String getProfileId() {
    return this.profileId;
  }

  public String getMgmtOption() {
    return this.mgmtOption;
  }

  public String getMgmtOptionDesc() {
    return this.mgmtOptionDesc;
  }
}
