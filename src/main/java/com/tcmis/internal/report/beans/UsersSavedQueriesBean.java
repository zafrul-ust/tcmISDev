package com.tcmis.internal.report.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UsersSavedQueriesBean <br>
 * @version: 1.0, Apr 13, 2004 <br>
 *****************************************************************************/

public class UsersSavedQueriesBean
    extends BaseDataBean {

  private String companyId;
  private int personnelId;
  private String queryName;
  private String queryNameOld;
  private String query;
  private Date dateSaved;

  //constructor
  public UsersSavedQueriesBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setQueryName(String queryName) {
    this.queryName = queryName;
  }

  public void setQueryNameOld(String queryNameOld) {
    this.queryNameOld = queryNameOld;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public void setDateSaved(Date dateSaved) {
    this.dateSaved = dateSaved;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public int getPersonnelId() {
    return personnelId;
  }

  public String getQueryName() {
    return queryName;
  }

  public String getQueryNameOld() {
    return queryNameOld;
  }

  public String getQuery() {
    return query;
  }

  public Date getDateSaved() {
    return dateSaved;
  }
}