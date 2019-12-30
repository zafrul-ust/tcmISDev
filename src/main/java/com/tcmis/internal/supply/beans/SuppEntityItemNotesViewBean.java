package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SuppEntityItemNotesViewBean <br>
 * @version: 1.0, Apr 27, 2010 <br>
 *****************************************************************************/


public class SuppEntityItemNotesViewBean extends BaseDataBean {

	private String opsEntityId;
	private String operatingEntityName;
	private String supplier;
	private String supplierName;
	private BigDecimal personnelId;
	private String enteredByName;
	private BigDecimal itemId;
	private String itemDesc;
	private String comments;
	private Date transactionDate;
	private BigDecimal recordNo;
	private Date lastUpdated;
	private boolean okDoUpdate;
	
	//constructor
	public SuppEntityItemNotesViewBean() {
	}

	//setters
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setEnteredByName(String enteredByName) {
		this.enteredByName = enteredByName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setRecordNo(BigDecimal recordNo) {
		this.recordNo = recordNo;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public void setOkDoUpdate(boolean okDoUpdate) {
		this.okDoUpdate = okDoUpdate;
	}

	//getters
	
	public boolean getOkDoUpdate() {
		return okDoUpdate;
	}
	
	public boolean isOkDoUpdate () {
		 return okDoUpdate;
	}
	
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getEnteredByName() {
		return enteredByName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getComments() {
		return comments;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public BigDecimal getRecordNo() {
		return recordNo;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
}