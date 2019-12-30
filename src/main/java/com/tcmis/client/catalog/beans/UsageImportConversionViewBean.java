package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UsageImportConversionViewBean <br>
 * @version: 1.0, Dec 9, 2011 <br>
 *****************************************************************************/

public class UsageImportConversionViewBean
 extends BaseDataBean {

 private String companyId;
 private String facilityId;
 private String facilityName;
 private String customerMsdsDb;
 private String customerMsdsOrMixtureNo;
 private String partNumber;
 private String unitOfMeasure;
 private BigDecimal poundsPerUnit;
 private BigDecimal lastModifiedBy;
 private Date lastModifiedDate;
 private BigDecimal createdBy;
 private Date createdDate;
 private String comments;
 private String createdByName;
 private String lastModifiedByName;
 private BigDecimal materialId;
 private String tradeName;
 
 private String originalPartNumber;
 private String originalCustomerMsdsOrMixtureNo;
 private String originalComments;
 private String approvalCode;
 
 private String status;
 private String vocetFugitiveCatId;
 private String vocetFugitiveCatName;
 private String catalogCompanyId;
 private String catalogId;
 
 private String updateStatus;
 private String fugitiveCategoryStatus;
 
 private String tierIIStorage;
 private String tierIIPressure;
 private String tierIITemperature;
 private String materialDesc;
 private String customerMsdsNumber;
 private BigDecimal purchasingMethodId;
 private String packaging;
 private String updatedByName;
 
 
public String getUpdatedByName() {
		return updatedByName;
}

public void setUpdatedByName(String updatedByName) {
	this.updatedByName = updatedByName;
}
 
public BigDecimal getPurchasingMethodId() {
	return purchasingMethodId;
}

public void setPurchasingMethodId(BigDecimal purchasingMethodId) {
	this.purchasingMethodId = purchasingMethodId;
}

public String getPackaging() {
	return packaging;
}
public void setPackaging(String packaging) {
	this.packaging = packaging;
}

public String getOriginalCustomerMsdsOrMixtureNo() {
	return originalCustomerMsdsOrMixtureNo;
}

public void setOriginalCustomerMsdsOrMixtureNo(
		String originalCustomerMsdsOrMixtureNo) {
	this.originalCustomerMsdsOrMixtureNo = originalCustomerMsdsOrMixtureNo;
}

public String getOriginalPartNumber() {
	return originalPartNumber;
}

public String getVocetFugitiveCatId() {
	return vocetFugitiveCatId;
}

public void setVocetFugitiveCatId(String vocetFugitiveCatId) {
	this.vocetFugitiveCatId = vocetFugitiveCatId;
}

public void setOriginalPartNumber(String originalPartNumber) {
	this.originalPartNumber = originalPartNumber;
}

//constructor
public UsageImportConversionViewBean() {
}

public String getCompanyId() {
	return companyId;
}

public void setCompanyId(String companyId) {
	this.companyId = companyId;
}

public String getCustomerMsdsDb() {
	return customerMsdsDb;
}

public void setCustomerMsdsDb(String customerMsdsDb) {
	this.customerMsdsDb = customerMsdsDb;
}

public String getCustomerMsdsOrMixtureNo() {
	return customerMsdsOrMixtureNo;
}

public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
	this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
}

public String getFacilityId() {
	return facilityId;
}

public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
}

public String getPartNumber() {
	return partNumber;
}

public void setPartNumber(String partNumber) {
	this.partNumber = partNumber;
}

public BigDecimal getPoundsPerUnit() {
	return poundsPerUnit;
}

public void setPoundsPerUnit(BigDecimal poundsPerUnit) {
	this.poundsPerUnit = poundsPerUnit;
}

public String getUnitOfMeasure() {
	return unitOfMeasure;
}

public void setUnitOfMeasure(String unitOfMeasure) {
	this.unitOfMeasure = unitOfMeasure;
}

public String getFacilityName() {
	return facilityName;
}

public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getComments() {
	return comments;
}

public void setComments(String comments) {
	this.comments = comments;
}

public BigDecimal getCreatedBy() {
	return createdBy;
}

public void setCreatedBy(BigDecimal createdBy) {
	this.createdBy = createdBy;
}

public String getCreatedByName() {
	return createdByName;
}

public void setCreatedByName(String createdByName) {
	this.createdByName = createdByName;
}

public Date getCreatedDate() {
	return createdDate;
}

public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
}

public BigDecimal getLastModifiedBy() {
	return lastModifiedBy;
}

public void setLastModifiedBy(BigDecimal lastModifiedBy) {
	this.lastModifiedBy = lastModifiedBy;
}

public String getLastModifiedByName() {
	return lastModifiedByName;
}

public void setLastModifiedByName(String lastModifiedByName) {
	this.lastModifiedByName = lastModifiedByName;
}

public Date getLastModifiedDate() {
	return lastModifiedDate;
}

public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
}

public BigDecimal getMaterialId() {
	return materialId;
}

public void setMaterialId(BigDecimal materialId) {
	this.materialId = materialId;
}

public String getTradeName() {
	return tradeName;
}

public void setTradeName(String tradeName) {
	this.tradeName = tradeName;
}

public String getOriginalComments() {
	return originalComments;
}

public void setOriginalComments(String originalComments) {
	this.originalComments = originalComments;
}

public String getApprovalCode() {
	return approvalCode;
}

public void setApprovalCode(String approvalCode) {
	this.approvalCode = approvalCode;
}

public String getUpdateStatus() {
	return updateStatus;
}

public void setUpdateStatus(String updateStatus) {
	this.updateStatus = updateStatus;
}

public String getCatalogCompanyId() {
	return catalogCompanyId;
}

public void setCatalogCompanyId(String catalogCompanyId) {
	this.catalogCompanyId = catalogCompanyId;
}

public String getCatalogId() {
	return catalogId;
}

public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
}

public String getFugitiveCategoryStatus() {
	return fugitiveCategoryStatus;
}

public void setFugitiveCategoryStatus(String fugitiveCategoryStatus) {
	this.fugitiveCategoryStatus = fugitiveCategoryStatus;
}

public String getVocetFugitiveCatName() {
	return vocetFugitiveCatName;
}

public void setVocetFugitiveCatName(String vocetFugitiveCatName) {
	this.vocetFugitiveCatName = vocetFugitiveCatName;
}

public String getTierIIPressure() {
	return tierIIPressure;
}

public void setTierIIPressure(String tierIIPressure) {
	this.tierIIPressure = tierIIPressure;
}

public String getTierIIStorage() {
	return tierIIStorage;
}

public void setTierIIStorage(String tierIIStorage) {
	this.tierIIStorage = tierIIStorage;
}

public String getTierIITemperature() {
	return tierIITemperature;
}

public void setTierIITemperature(String tierIITemperature) {
	this.tierIITemperature = tierIITemperature;
}

public String getMaterialDesc() {
	return materialDesc;
}


public void setMaterialDesc(String materialDesc) {
	this.materialDesc = materialDesc;
}

public String getCustomerMsdsNumber() {
	return customerMsdsNumber;
}


public void setCustomerMsdsNumber(String customerMsdsNumber) {
	this.customerMsdsNumber = customerMsdsNumber;
}

}