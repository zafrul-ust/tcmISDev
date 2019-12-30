package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PaymentTermIgExceptionViewBean <br>
 * @version: 1.0, Sep 1, 2009 <br>
 *****************************************************************************/


public class PaymentTermIgExceptionViewBean extends BaseDataBean {

	private String supplier;
	private String inventoryGroup;
	private String defaultPaymentTerms;
	private String inventoryGroupName;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal approvedBy;
	private Date approvedOn;
	private String approvedByName;


	//constructor
	public PaymentTermIgExceptionViewBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setDefaultPaymentTerms(String defaultPaymentTerms) {
		this.defaultPaymentTerms = defaultPaymentTerms;
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


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getDefaultPaymentTerms() {
		return defaultPaymentTerms;
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

}