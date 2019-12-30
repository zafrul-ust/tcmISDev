package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class CatalogAddRequestPendingAssignBean extends BaseDataBean {

	private boolean ok;
	private BigDecimal requestId;
	private String companyId;
	private String catalogId;
	private String facilityId;
	private String manufacturer;
	private String companyName;
	private String catalogDesc;
	private String facilityName;
	private String mfgDesc;
	private String materialDesc;
	private String tradeName;
	private String grade;
	private String dimension;
	private String mfgCatalogId;
	private BigDecimal partId;
	private String sdsPresent;
	private String approvalRole;
	private BigDecimal lineItem;
	private String itemQcStatus;
	private String comments;
	
	public CatalogAddRequestPendingAssignBean() {}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getMfgCatalogId() {
		return mfgCatalogId;
	}

	public void setMfgCatalogId(String mfgCatalogId) {
		this.mfgCatalogId = mfgCatalogId;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public String getSdsPresent() {
		return sdsPresent;
	}

	public void setSdsPresent(String sdsPresent) {
		this.sdsPresent = sdsPresent;
	}

	public String getApprovalRole() {
		return approvalRole;
	}

	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public String getItemQcStatus() {
		return itemQcStatus;
	}

	public void setItemQcStatus(String itemQcStatus) {
		this.itemQcStatus = itemQcStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
