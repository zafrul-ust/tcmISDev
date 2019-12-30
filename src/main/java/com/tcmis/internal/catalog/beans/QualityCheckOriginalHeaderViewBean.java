package com.tcmis.internal.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

public class QualityCheckOriginalHeaderViewBean extends BaseDataBean {

	private String supplier;
	private String suggestedVendor;
	private String vendorContactName;
	private String vendorContactEmail;
	private String vendorContactPhone;
	private String vendorContactFax;
	private String partDescription;
	private String comments;
	private String messageToApprovers;
	private String requestorFullName;
	private String requestorPhone;
	private String requestorEmail;
	private String engineeringEvaluation;
	private String companyName;
	private String catalogName;
	private BigDecimal requestId;
	private String catPartNo;


	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSuggestedVendor() {
		return suggestedVendor;
	}

	public void setSuggestedVendor(String suggestedVendor) {
		this.suggestedVendor = suggestedVendor;
	}

	public String getVendorContactName() {
		return vendorContactName;
	}

	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}

	public String getVendorContactEmail() {
		return vendorContactEmail;
	}

	public void setVendorContactEmail(String vendorContactEmail) {
		this.vendorContactEmail = vendorContactEmail;
	}

	public String getVendorContactPhone() {
		return vendorContactPhone;
	}

	public void setVendorContactPhone(String vendorContactPhone) {
		this.vendorContactPhone = vendorContactPhone;
	}

	public String getVendorContactFax() {
		return vendorContactFax;
	}

	public void setVendorContactFax(String vendorContactFax) {
		this.vendorContactFax = vendorContactFax;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMessageToApprovers() {
		return messageToApprovers;
	}

	public void setMessageToApprovers(String messageToApprovers) {
		this.messageToApprovers = messageToApprovers;
	}

	public String getRequestorFullName() {
		return requestorFullName;
	}

	public void setRequestorFullName(String requestorFullName) {
		this.requestorFullName = requestorFullName;
	}

	public String getRequestorPhone() {
		return requestorPhone;
	}

	public void setRequestorPhone(String requestorPhone) {
		this.requestorPhone = requestorPhone;
	}

	public String getRequestorEmail() {
		return requestorEmail;
	}

	public void setRequestorEmail(String requestorEmail) {
		this.requestorEmail = requestorEmail;
	}

	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}

	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
}