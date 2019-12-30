package com.tcmis.internal.hub.beans;

import com.tcmis.common.framework.*;

import java.math.BigDecimal;
import java.util.Date;

/******************************************************************************
 * CLASSNAME: PickingInputBean <br>
 * @version: 1.0, Jun 9, 2004 <br>
 *****************************************************************************/

public class PickingInputBean  extends BaseDataBean 
{

  public PickingInputBean() 
  {
  }

  private String hub;
  private String hubName;
  private String inventoryGroup;
  private String facilityId;
  private String facilityName;
  private String prNumber;
  private BigDecimal picklistId;
  private String lineItem;
  private String submitSearch;
  private String confirm;
  private String reverse;
  private String sortBy;
  private String userAction;
  private BigDecimal batchNumber;
  private Date picklistPrintDate;
  private String branchPlant;
  private String companyId;
  
  //setters
  public void setHubName(String hubName) {
    this.hubName = hubName;
  }
  
  public void setHub(String hub) {
	    this.hub = hub;
	  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }
  
  public void setFacilityName(String facilityName) {
	    this.facilityName = facilityName;
	  }

  public void setPrNumber(String prNumber) {
    this.prNumber = prNumber;
  }

  public void setPicklistId(BigDecimal picklist) {
    this.picklistId = picklist;
  }
  
public void setPicklistPrintDate(Date picklistPrintDate) {
	this.picklistPrintDate = picklistPrintDate;
}

public Date getPicklistPrintDate() {
	return picklistPrintDate;
}
public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  //getters

public String getCompanyId() {
    return this.companyId;
  }

public String getBranchPlant() {
    return this.branchPlant;
  }

public String getHubName() {
    return this.hubName;
  }

  public String getHub() {
    return this.hub;
  }

  public String getFacilityId() {
    return this.facilityId;
  }
  
  public String getFacilityName() {
	    return this.facilityName;
	  }

  public String getPrNumber() {
    return this.prNumber;
  }

  public BigDecimal getPicklistId() {
    return this.picklistId;
  }
  
  /**
   * Getter for property inventoryGroup.
   * @return Value of property inventoryGroup.
   */
  public java.lang.String getInventoryGroup() {
     return inventoryGroup;
  }
  
  /**
   * Setter for property inventoryGroup.
   * @param inventoryGroup New value of property inventoryGroup.
   */
  public void setInventoryGroup(java.lang.String inventoryGroup) {
     this.inventoryGroup = inventoryGroup;
  }
  
  /**
   * Getter for property submitSearch.
   * @return Value of property submitSearch.
   */
  public java.lang.String getSubmitSearch() {
     return submitSearch;
  }
  
  /**
   * Setter for property submitSearch.
   * @param submitSearch New value of property submitSearch.
   */
  public void setSubmitSearch(java.lang.String submitSearch) {
     this.submitSearch = submitSearch;
  }
  
  /**
   * Getter for property confirm.
   * @return Value of property confirm.
   */
  public java.lang.String getConfirm() {
     return confirm;
  }
  
  /**
   * Setter for property confirm.
   * @param confirm New value of property confirm.
   */
  public void setConfirm(java.lang.String confirm) {
     this.confirm = confirm;
  }
  
  /**
   * Getter for property lineItem.
   * @return Value of property lineItem.
   */
  public java.lang.String getLineItem() {
     return lineItem;
  }
  
  /**
   * Setter for property lineItem.
   * @param lineItem New value of property lineItem.
   */
  public void setLineItem(java.lang.String lineItem) {
     this.lineItem = lineItem;
  }
  
  /**
   * Getter for property reverse.
   * @return Value of property reverse.
   */
  public java.lang.String getReverse() {
     return reverse;
  }
  
  /**
   * Setter for property reverse.
   * @param reverse New value of property reverse.
   */
  public void setReverse(java.lang.String reverse) {
     this.reverse = reverse;
  }
  
  /**
   * Getter for property sortBy.
   * @return Value of property sortBy.
   */
  public java.lang.String getSortBy() {
     return sortBy;
  }
  
  /**
   * Setter for property sortBy.
   * @param sortBy New value of property sortBy.
   */
  public void setSortBy(java.lang.String sortBy) {
     this.sortBy = sortBy;
  }

  public String getUserAction() 
  {
	return userAction;
  }

  public void setUserAction(String userAction) 
  {
	this.userAction = userAction;
  }

  public BigDecimal getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(BigDecimal batchNumber) {
    this.batchNumber = batchNumber;
  }
}
