package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PartYearlyLeadTimeViewBean <br>
 * @version: 1.0, Nov 16, 2005 <br>
 *****************************************************************************/

public class PartYearlyLeadTimeViewBean
    extends BaseDataBean {

  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private String inventoryGroup;
  private BigDecimal receiptId;
  private String hub;
  private BigDecimal itemId;
  private Date dateOfReceipt;
  private BigDecimal leadTime;
  private BigDecimal tenth;
  private BigDecimal median;
  private BigDecimal nintieth;
  private Date XDate;
  private String catalogCompanyId;
  private BigDecimal projectedLeadTime;

  //constructor
  public PartYearlyLeadTimeViewBean() {
  }

  //setters
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

  public void setReceiptId(BigDecimal receiptId) {
    this.receiptId = receiptId;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setDateOfReceipt(Date dateOfReceipt) {
    this.dateOfReceipt = dateOfReceipt;
  }

  public void setLeadTime(BigDecimal leadTime) {
    this.leadTime = leadTime;
  }

  public void setTenth(BigDecimal tenth) {
    this.tenth = tenth;
  }

  public void setMedian(BigDecimal median) {
    this.median = median;
  }

  public void setNintieth(BigDecimal nintieth) {
    this.nintieth = nintieth;
  }

  public void setXDate(Date xDate) {
    this.XDate = xDate;
  }

  public void setCatalogCompanyId(String catalogCompanyId) {
	  this.catalogCompanyId = catalogCompanyId;
  }

  //getters
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

  public BigDecimal getReceiptId() {
    return receiptId;
  }

  public String getHub() {
    return hub;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getDateOfReceipt() {
    return dateOfReceipt;
  }

  public BigDecimal getLeadTime() {
    return leadTime;
  }

  public BigDecimal getTenth() {
    return tenth;
  }

  public BigDecimal getMedian() {
    return median;
  }

  public BigDecimal getNintieth() {
    return nintieth;
  }

  public Date getXDate() {
    return XDate;
  }

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
  }

public BigDecimal getProjectedLeadTime() {
	return projectedLeadTime;
}

public void setProjectedLeadTime(BigDecimal projectedLeadTime) {
	this.projectedLeadTime = projectedLeadTime;
}

}