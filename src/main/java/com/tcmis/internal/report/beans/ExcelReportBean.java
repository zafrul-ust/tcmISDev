package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ExcelReportBean
    extends BaseDataBean {
  public ExcelReportBean() {
  }

  private String query;
  private String comment;
  private String submit;
  private String saveQuery;
  private String viewQuery;

  public void setQuery(String query) {
    this.query = query;
  }

  public String getQuery() {
    return query;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public void setSubmit(String submit) {
    this.submit = submit;
  }

  public String getSubmit() {
    return submit;
  }

  public void setSaveQuery(String saveQuery) {
    this.saveQuery = saveQuery;
  }

  public String getSaveQuery() {
    return saveQuery;
  }

  public void setViewQuery(String viewQuery) {
    this.viewQuery = viewQuery;
  }

  public String getViewQuery() {
    return viewQuery;
  }

}