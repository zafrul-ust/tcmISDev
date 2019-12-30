package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DailyPartIssueSumViewBean <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/

public class DailyPartIssueSumViewBean
    extends BaseDataBean {

  private Date dailyDate;
  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private String inventoryGroup;
  private BigDecimal quantity;
  private String packaging;
  private BigDecimal daysSinceLastIssue;
  private String catalogCompanyId;

  //constructor
  public DailyPartIssueSumViewBean() {
  }

  //setters
  public void setDailyDate(Date dailyDate) {
    this.dailyDate = dailyDate;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setDaysSinceLastIssue(BigDecimal daysSinceLastIssue) {
    this.daysSinceLastIssue = daysSinceLastIssue;
  }

  public void setCatalogCompanyId(String catalogCompanyId) {
	  this.catalogCompanyId = catalogCompanyId;
  }

  //getters
  public Date getDailyDate() {
    return dailyDate;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getPackaging() {
    return packaging;
  }

  public BigDecimal getDaysSinceLastIssue() {
    return daysSinceLastIssue;
  }

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
  }
}