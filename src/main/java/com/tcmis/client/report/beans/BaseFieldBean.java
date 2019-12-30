package com.tcmis.client.report.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: BaseFieldBean <br>
 * @version: 1.0, Mar 1, 2006 <br>
 *****************************************************************************/

public class BaseFieldBean
    extends BaseDataBean {

  private String companyId;
  private String reportType;
  private String baseField;
  private BigDecimal displayOrder;
  private String columnSpec;
  private String aggregate;
  private String compatibility;
  private String fromClause;
  private String whereClause;
  private String description;

  //constructor
  public BaseFieldBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setReportType(String reportType) {
    this.reportType = reportType;
  }

  public void setBaseField(String baseField) {
    this.baseField = baseField;
  }

  public void setDisplayOrder(BigDecimal displayOrder) {
    this.displayOrder = displayOrder;
  }

  public void setColumnSpec(String columnSpec) {
    this.columnSpec = columnSpec;
  }

  public void setAggregate(String aggregate) {
    this.aggregate = aggregate;
  }

  public void setCompatibility(String compatibility) {
    this.compatibility = compatibility;
  }

  public void setFromClause(String fromClause) {
    this.fromClause = fromClause;
  }

  public void setWhereClause(String whereClause) {
    this.whereClause = whereClause;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getReportType() {
    return reportType;
  }

  public String getBaseField() {
    return baseField;
  }

  public BigDecimal getDisplayOrder() {
    return displayOrder;
  }

  public String getColumnSpec() {
    return columnSpec;
  }

  public String getAggregate() {
    return aggregate;
  }

  public String getCompatibility() {
    return compatibility;
  }

  public String getFromClause() {
    return fromClause;
  }

  public String getWhereClause() {
    return whereClause;
  }

  public String getDescription() {
    return description;
  }
}