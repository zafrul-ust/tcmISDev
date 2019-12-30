package com.tcmis.internal.report.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ReportParameterBean <br>
 * @version: 1.0, Feb 26, 2004 <br>
 *****************************************************************************/
public class ReportParameterBean
    extends BaseDataBean {

  private String fileType;
  private String action;
  private String facility;
  private String workArea;
  private String begmonth;
  private String begyear;
  private String endmonth;
  private String endyear;
  private String searchType;
  private String listId;
  private String keyword;
  private String casNum;
  private String chemDesc;
  private String userId;
  private String reportName;
  private String sortBy;
  private String groupBy;
  private String currentScreen;
  private String groupMatrix;
  private String uniqueId;
  private String status;
  private Date dateRequested;

  //constructor
  public ReportParameterBean() {
  }

  //setters
  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public void setFacility(String facility) {
    this.facility = facility;
  }

  public void setWorkArea(String workArea) {
    this.workArea = workArea;
  }

  public void setBegmonth(String begmonth) {
    this.begmonth = begmonth;
  }

  public void setBegyear(String begyear) {
    this.begyear = begyear;
  }

  public void setEndmonth(String endmonth) {
    this.endmonth = endmonth;
  }

  public void setEndyear(String endyear) {
    this.endyear = endyear;
  }

  public void setSearchType(String searchType) {
    this.searchType = searchType;
  }

  public void setListId(String listId) {
    this.listId = listId;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public void setCasNum(String casNum) {
    this.casNum = casNum;
  }

  public void setChemDesc(String chemDesc) {
    this.chemDesc = chemDesc;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setReportName(String reportName) {
    this.reportName = reportName;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public void setGroupBy(String groupBy) {
    this.groupBy = groupBy;
  }

  public void setCurrentScreen(String currentScreen) {
    this.currentScreen = currentScreen;
  }

  public void setGroupMatrix(String groupMatrix) {
    this.groupMatrix = groupMatrix;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setDateRequested(Date dateRequested) {
    this.dateRequested = dateRequested;
  }

  //getters
  public String getFileType() {
    return fileType;
  }

  public String getAction() {
    return action;
  }

  public String getFacility() {
    return facility;
  }

  public String getWorkArea() {
    return workArea;
  }

  public String getBegmonth() {
    return begmonth;
  }

  public String getBegyear() {
    return begyear;
  }

  public String getEndmonth() {
    return endmonth;
  }

  public String getEndyear() {
    return endyear;
  }

  public String getSearchType() {
    return searchType;
  }

  public String getListId() {
    return listId;
  }

  public String getKeyword() {
    return keyword;
  }

  public String getCasNum() {
    return casNum;
  }

  public String getChemDesc() {
    return chemDesc;
  }

  public String getUserId() {
    return userId;
  }

  public String getReportName() {
    return reportName;
  }

  public String getSortBy() {
    return sortBy;
  }

  public String getGroupBy() {
    return groupBy;
  }

  public String getCurrentScreen() {
    return currentScreen;
  }

  public String getGroupMatrix() {
    return groupMatrix;
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public String getStatus() {
    return status;
  }

  public Date getDateRequested() {
    return dateRequested;
  }
}