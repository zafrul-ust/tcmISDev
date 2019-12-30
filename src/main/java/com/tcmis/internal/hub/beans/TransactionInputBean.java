package com.tcmis.internal.hub.beans;

import java.math.*;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: TransactionTrackingViewBean <br>
 * @version: 1.0, Sep 29, 2004 <br>
 *****************************************************************************/

public class TransactionInputBean extends BaseDataBean {

  private String inventoryGroup;
  private String branchPlant;
  private String receiptId;
  private String itemId;
  private String issueId;
  private String mfgLot;
  private Date txnOnDate;
  private Date transactionFromDate;
  private Date transactionToDate;
  private String radianPo;
  private String mrNumber;
  private String submitSearch;
  private String daysOld;
  private String sortBy;
  private String transType;
  private String hubName;
  private String trackingNumber;
  
  private String opsEntityId;
  private String hub;

  public String getHub() {
	return hub;
}

public void setHub(String hub) {
	this.hub = hub;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

//constructor
  public TransactionInputBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setBranchPlant(String branchPlant) {
    this.branchPlant = branchPlant;
  }

  public void setReceiptId(String receiptId) {
    this.receiptId = receiptId;
  }

  public void setItemId(String itemId) {
    this.itemId = itemId;
  }

  public void setMfgLot(String mfgLot) {
    this.mfgLot = mfgLot;
  }

  public void setTxnOnDate(Date txnOnDate) {
    this.txnOnDate = txnOnDate;
  }

  public void setRadianPo(String radianPo) {
    this.radianPo = radianPo;
  }

  public void setMrNumber(String mrNumber) {
    this.mrNumber = mrNumber;
  }
  
  public void setTransactionFromDate(Date transactionFromDate) {
	    this.transactionFromDate = transactionFromDate;
}
  public void setTransactionToDate(Date transactionToDate) {
	    this.transactionToDate = transactionToDate;
}



  //getters
  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getBranchPlant() {
    return branchPlant;
  }

  public String getReceiptId() {
    return receiptId;
  }

  public String getItemId() {
    return itemId;
  }

  public String getMfgLot() {
    return mfgLot;
  }

  public Date getTxnOnDate() {
    return txnOnDate;
  }

  public String getRadianPo() {
    return radianPo;
  }

  public String getMrNumber() {
    return mrNumber;
  }
  
  public Date getTransactionFromDate() {
	    return transactionFromDate;
  }
  public Date getTransactionToDate() {
	    return transactionToDate;
  }


  /**
   * Getter for property submitSearch.
   * @return Value of property submitSearch.
   */
  public String getSubmitSearch() {
     return submitSearch;
  }  

  /**
   * Setter for property submitSearch.
   * @param submitSearch New value of property submitSearch.
   */
  public void setSubmitSearch(String submitSearch) {
     this.submitSearch = submitSearch;
  }
  
  /**
   * Getter for property daysOld.
   * @return Value of property daysOld.
   */
  public String getDaysOld() {
     return daysOld;
  }
  
  /**
   * Setter for property daysOld.
   * @param daysOld New value of property daysOld.
   */
  public void setDaysOld(String daysOld) {
     this.daysOld = daysOld;
  }
  
  /**
   * Getter for property sortBy.
   * @return Value of property sortBy.
   */
  public String getSortBy() {
     return sortBy;
  }
  
  /**
   * Setter for property sortBy.
   * @param sortBy New value of property sortBy.
   */
  public void setSortBy(String sortBy) {
     this.sortBy = sortBy;
  }
  
  /**
   * Getter for property transType.
   * @return Value of property transType.
   */
  public java.lang.String getTransType() {
     return transType;
  }
  
  /**
   * Setter for property transType.
   * @param transType New value of property transType.
   */
  public void setTransType(java.lang.String transType) {
     this.transType = transType;
  }

public String getHubName() {
	return hubName;
}

public void setHubName(String hubName) {
	this.hubName = hubName;
}

public String getTrackingNumber() {
	return trackingNumber;
}

public void setTrackingNumber(String trackingNumber) {
	this.trackingNumber = trackingNumber;
}

public String getIssueId() {
	return issueId;
}

public void setIssueId(String issueId) {
	this.issueId = issueId;
}


  
}