package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: CustomerReturnInputBean <br>
 * @version: 1.0, Nov 10, 2006 <br>
 *****************************************************************************/

public class CustomerReturnInputBean extends BaseDataBean {

  private String hub;
  private String facilityId;
  private String inventoryGroup;
  private String receiptId;
  private String sortBy;
  private String itemId;
  private String catPartNo;
  private String mrNumber;
  private String submitSearch;
  private String buttonReceive;
  private String returnForCredit;

  // for excel
  //constructor
  public CustomerReturnInputBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }


  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  //getters
  public String getHub() {
    return hub;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getSortBy() {
    return sortBy;
  }

  /**
   * Getter for property itemId.
   * @return Value of property itemId.
   */
  public java.lang.String getItemId() {
     return itemId;
  }
  
  /**
   * Setter for property itemId.
   * @param itemId New value of property itemId.
   */
  public void setItemId(java.lang.String itemId) {
     this.itemId = itemId;
  }
  
  /**
   * Getter for property catPartNo.
   * @return Value of property catPartNo.
   */
  public java.lang.String getCatPartNo() {
     return catPartNo;
  }
  
  /**
   * Setter for property catPartNo.
   * @param catPartNo New value of property catPartNo.
   */
  public void setCatPartNo(java.lang.String catPartNo) {
     this.catPartNo = catPartNo;
  }
  
  /**
   * Getter for property mrNumber.
   * @return Value of property mrNumber.
   */
  public java.lang.String getMrNumber() {
     return mrNumber;
  }
  
  /**
   * Setter for property prNumber.
   * @param prNumber New value of property mrNumber.
   */
  public void setMrNumber(java.lang.String mrNumber) {
     this.mrNumber = mrNumber;
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
   * Getter for property buttonReceive.
   * @return Value of property buttonReceive.
   */
  public java.lang.String getButtonReceive() {
     return buttonReceive;
  }
  
  /**
   * Setter for property buttonReceive.
   * @param buttonReceive New value of property buttonReceive.
   */
  public void setButtonReceive(java.lang.String buttonReceive) {
     this.buttonReceive = buttonReceive;
  }
  
  /**
   * Getter for property receiptId.
   * @return Value of property receiptId.
   */
  public java.lang.String getReceiptId() {
     return receiptId;
  }
  
  /**
   * Setter for property receiptId.
   * @param receiptId New value of property receiptId.
   */
  public void setReceiptId(java.lang.String receiptId) {
     this.receiptId = receiptId;
  }

  public String getReturnForCredit() {
		return returnForCredit;
  }

  public void setReturnForCredit(String returnForCredit) {
		this.returnForCredit = returnForCredit;
  }

  
}