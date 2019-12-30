package com.tcmis.internal.supply.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SuppEntityPaymentTermsViewBean <br>
 * @version: 1.0, Sep 1, 2009 <br>
 *****************************************************************************/


public class SuppEntityPaymentTermsViewBean extends BaseDataBean {

	
	private static final long serialVersionUID = 24692952017580789L;
	private String supplier;
	private String opsEntityId;
	private String paymentTerms;
	private String operatingEntityName;
	private BigDecimal approvedBy;
	private Date approvedOn;
	private String approvedByName;
	private String supplierRequestId;
	private String paymentTermStatus;

	//constructor
	public SuppEntityPaymentTermsViewBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
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


	//getters
	public String getSupplier() {
		return supplier;
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
	public String getSupplierRequestId() {
		return supplierRequestId;
	}
	public void setSupplierRequestId(String supplierRequestId) {
		this.supplierRequestId = supplierRequestId;
	}

	public void setPaymentTermStatus(String paymentTermStatus) {
		this.paymentTermStatus = paymentTermStatus;
	}

	public String getPaymentTermStatus() {
		return paymentTermStatus;
	}

}