package com.tcmis.client.report.beans;

import java.math.*;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: PartMrYearlyLeadTimeViewBean <br>
 * @version: 1.0, May 25, 2006 <br>
 *****************************************************************************/

public class PartMrYearlyLeadTimeViewBean
    extends BaseDataBean {

  private BigDecimal issueId;
  private BigDecimal prNumber;
  private String lineItem;
  private String deliveryType;
  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private String inventoryGroup;
  private BigDecimal receiptId;
  private String hub;
  private BigDecimal itemId;
  private Date releaseDate;
  private Date dateDelivered;
  private Date dateOfReceipt;
  private Date XDate;
  private BigDecimal leadTime;
  private BigDecimal tenth;
  private BigDecimal median;
  private BigDecimal nintieth;
  private String catalogCompanyId;

  //constructor
  public PartMrYearlyLeadTimeViewBean() {
  }

  //setters
  public void setIssueId(BigDecimal issueId) {
    this.issueId = issueId;
  }

  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setDeliveryType(String deliveryType) {
    this.deliveryType = deliveryType;
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

  public void setReceiptId(BigDecimal receiptId) {
    this.receiptId = receiptId;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setDateDelivered(Date dateDelivered) {
    this.dateDelivered = dateDelivered;
  }

  public void setDateOfReceipt(Date dateOfReceipt) {
    this.dateOfReceipt = dateOfReceipt;
  }

  public void setXDate(Date xDate) {
    this.XDate = xDate;
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

  public void setCatalogCompanyId(String catalogCompanyId) {
	  this.catalogCompanyId = catalogCompanyId;
  }

  //getters
  public BigDecimal getIssueId() {
    return issueId;
  }

  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getLineItem() {
    return lineItem;
  }

  public String getDeliveryType() {
    return deliveryType;
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

  public BigDecimal getReceiptId() {
    return receiptId;
  }

  public String getHub() {
    return hub;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public Date getDateDelivered() {
    return dateDelivered;
  }

  public Date getDateOfReceipt() {
    return dateOfReceipt;
  }

  public Date getXDate() {
    return XDate;
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

  public String getCatalogCompanyId() {
	  return catalogCompanyId;
  }
}