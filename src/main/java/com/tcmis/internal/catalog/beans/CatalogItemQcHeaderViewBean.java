package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class CatalogItemQcHeaderViewBean extends BaseDataBean {

	private BigDecimal requestId;
	private BigDecimal lineItem;
	private String phone;
	private String companyId;
	private String submitdate;
	private String fullName;
	private String email;
	private String catalogId;
	private String engineeringEvaluation;
	private String catPartNo;
	private String startingView;
	private BigDecimal itemId;
	private BigDecimal itemStorageTemp;
	private BigDecimal categoryId;
	private String itemType;
	private BigDecimal caseQty;
	private String itemVerified;
	private String sampleOnly;
	private boolean inseparableKit;
	private String partDescription;
	
	public BigDecimal getRequestId() {
		return requestId;
	}
	
	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSubmitdate() {
		return submitdate;
	}

	public void setSubmitdate(String submitdate) {
		this.submitdate = submitdate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getEngineeringEvaluation() {
		return engineeringEvaluation;
	}

	public void setEngineeringEvaluation(String engineeringEvaluation) {
		this.engineeringEvaluation = engineeringEvaluation;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getStartingView() {
		return startingView;
	}

	public void setStartingView(String startingView) {
		this.startingView = startingView;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getItemStorageTemp() {
		return itemStorageTemp;
	}

	public void setItemStorageTemp(BigDecimal itemStorageTemp) {
		this.itemStorageTemp = itemStorageTemp;
	}

	public BigDecimal getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(BigDecimal categoryId) {
		this.categoryId = categoryId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getCaseQty() {
		return caseQty;
	}

	public void setCaseQty(BigDecimal caseQty) {
		this.caseQty = caseQty;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	public boolean getInseparableKit() {
		return inseparableKit;
	}

	public void setInseparableKit(boolean inseparableKit) {
		this.inseparableKit = inseparableKit;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getItemVerified() {
		return itemVerified;
	}

	public void setItemVerified(String itemVerified) {
		this.itemVerified = itemVerified;
	}
}
