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
 * CLASSNAME: SuppAddPaymentTermsViewBean <br>
 * @version: 1.0, Oct 29, 2009 <br>
 *****************************************************************************/


public class SuppAddPaymentTermsViewBean extends BaseDataBean {

	private BigDecimal supplierRequestId;
	private String opsEntityId;
	private String paymentTerms;
	private String operatingEntityName;
	private BigDecimal approvedBy;
	private Date approvedOn;
	private String approvedByName;
	private String supplier;
	private String currentPaymentTerms;
	private String currentStatus;
	private String newStatus;

	//constructor
	public SuppAddPaymentTermsViewBean() {
	}

	//setters
	public void setSupplierRequestId(BigDecimal supplierRequestId) {
		this.supplierRequestId = supplierRequestId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
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
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}


	//getters
	public BigDecimal getSupplierRequestId() {
		return supplierRequestId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getPaymentTerms() {
		return paymentTerms;
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
	public String getCurrentStatus() {
		return currentStatus;
	}
	public String getNewStatus() {
		return newStatus;
	}

}