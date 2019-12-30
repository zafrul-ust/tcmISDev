package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SuppExcAddPaytermsViewBean <br>
 * @version: 1.0, Oct 29, 2009 <br>
 *****************************************************************************/


public class SuppExcAddPaytermsViewBean extends BaseDataBean {

	private BigDecimal supplierRequestId;
	private String inventoryGroup;
	private String paymentTerms;
	private String inventoryGroupName;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal approvedBy;
	private Date approvedOn;
	private String approvedByName;
	private String supplier;
	private String currentPaymentTerms;
	private String excOpsEntityId;

	//constructor
	public SuppExcAddPaytermsViewBean() {
	}

	//setters
	public void setSupplierRequestId(BigDecimal supplierRequestId) {
		this.supplierRequestId = supplierRequestId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setApprovedBy(BigDecimal approvedBy) {
		this.approvedBy = approvedBy;
	}
	public void setApprovedOn(Date approvedOn) {
		this.approvedOn = approvedOn;
	}
	public void setApprovedByName(String approvedByName) {
		this.approvedByName = approvedByName;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCurrentPaymentTerms(String currentPaymentTerms) {
		this.currentPaymentTerms = currentPaymentTerms;
	}
	public String getExcOpsEntityId() {
		return excOpsEntityId;
	}


	//getters
	public BigDecimal getSupplierRequestId() {
		return supplierRequestId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public BigDecimal getApprovedBy() {
		return approvedBy;
	}
	public Date getApprovedOn() {
		return approvedOn;
	}
	public String getApprovedByName() {
		return approvedByName;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getCurrentPaymentTerms() {
		return currentPaymentTerms;
	}
    public void setExcOpsEntityId(String excOpsEntityId) {
		this.excOpsEntityId = excOpsEntityId;
	}
}