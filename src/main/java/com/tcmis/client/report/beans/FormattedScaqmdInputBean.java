package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ListBean <br>
 * @version: 1.0, Oct 6, 2005 <br>
 *****************************************************************************/

public class FormattedScaqmdInputBean extends BaseDataBean {

  private String reportType;
  private String reportFormat;
  private String beginDate;
  private String endDate;
  private String generateReport;
  private String includeCommentField;
  private String workGroup;
  //constructor
  public FormattedScaqmdInputBean() {
  }

  //setters
  public void setReportType(String type) {
    this.reportType = type;
  }

  public void setReportFormat(String format) {
    this.reportFormat = format;
  }

  public void setBeginDate(String beginDate) {
    this.beginDate = beginDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public void setGenerateReport(String generateReport) {
    this.generateReport = generateReport;
  }

  public void setIncludeCommentField(String includeCommentField) {
    this.includeCommentField = includeCommentField;
  }

  public void setWorkGroup(String workGroup) {
    this.workGroup = workGroup;
  }


  //getters
  public String getReportType() {
    return reportType;
  }

  public String getReportFormat() {
    return reportFormat;
  }

  public String getBeginDate() {
    return beginDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public String getGenerateReport() {
    return generateReport;
  }

  public String getIncludeCommentField() {
    return includeCommentField;
  }

  public String getWorkGroup() {
    return workGroup;
  }

}