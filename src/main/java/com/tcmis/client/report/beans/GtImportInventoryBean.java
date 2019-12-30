package com.tcmis.client.report.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Customer.GtImportInventoryBean <br>
 * @version: 1.0, Aug 23, 2012 <br>
 *****************************************************************************/


public class GtImportInventoryBean extends BaseDataBean {

	private String workAreaName;
	private BigDecimal dataLoadRowId;
	private BigDecimal materialId;
	private String customerMsdsOrMixtureNo;
	private String msdsUnitOfMeasure;
	private BigDecimal msdsUnitAmount;
	private String partNo;
	private BigDecimal partAmount;
	private String purchaseSource;
	private Date storageDate;
	private String entryType;
	private Date insertDate;
	private BigDecimal insertedBy;
	private String companyId;
	private String application;
	private String customerMsdsDb;
	private BigDecimal usageLb;
	private String facilityId;
	private BigDecimal uploadSeqId;
	private String insertedByName;
	private String purchaseSourceName;
	private BigDecimal totUsageInLbs;
	private String ok;
	private String tradeName;
    private String customerMsdsNumber;
	private BigDecimal wtLb;


	//constructor
	public GtImportInventoryBean() {
	}

	//setters
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setWorkAreaName(String workAreaName) {
		this.workAreaName = workAreaName;
	}
	public void setDataLoadRowId(BigDecimal dataLoadRowId) {
		this.dataLoadRowId = dataLoadRowId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
		this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
	}
	public void setMsdsUnitOfMeasure(String msdsUnitOfMeasure) {
		this.msdsUnitOfMeasure = msdsUnitOfMeasure;
	}
	public void setMsdsUnitAmount(BigDecimal msdsUnitAmount) {
		this.msdsUnitAmount = msdsUnitAmount;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public void setPartAmount(BigDecimal partAmount) {
		this.partAmount = partAmount;
	}
	public void setPurchaseSource(String purchaseSource) {
		this.purchaseSource = purchaseSource;
	}
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	public void setEntryType(String entryType) {
		this.entryType = entryType;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public void setInsertedBy(BigDecimal insertedBy) {
		this.insertedBy = insertedBy;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}
	public void setUsageLb(BigDecimal usageLb) {
		this.usageLb = usageLb;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
    public void setCustomerMsdsNumber(String customerMsdsNumber) {
        this.customerMsdsNumber = customerMsdsNumber;
    }
	public void setWtLb(BigDecimal wtLb) {
		this.wtLb = wtLb;
	}
	
	//getters
	public BigDecimal getWtLb() {
		return wtLb;
	}
    public String getCustomerMsdsNumber() {
        return customerMsdsNumber;
    }
	public String getTradeName() {
		return tradeName;
	}
	public String getWorkAreaName() {
		return workAreaName;
	}
	public BigDecimal getDataLoadRowId() {
		return dataLoadRowId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getCustomerMsdsOrMixtureNo() {
		return customerMsdsOrMixtureNo;
	}
	public String getMsdsUnitOfMeasure() {
		return msdsUnitOfMeasure;
	}
	public BigDecimal getMsdsUnitAmount() {
		return msdsUnitAmount;
	}
	public String getPartNo() {
		return partNo;
	}
	public BigDecimal getPartAmount() {
		return partAmount;
	}
	public String getPurchaseSource() {
		return purchaseSource;
	}
	public Date getStorageDate() {
		return storageDate;
	}
	public String getEntryType() {
		return entryType;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public BigDecimal getInsertedBy() {
		return insertedBy;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getApplication() {
		return application;
	}
	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}
	public BigDecimal getUsageLb() {
		return usageLb;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getInsertedByName() {
		return insertedByName;
	}
	public void setInsertedByName(String insertedByName) {
		this.insertedByName = insertedByName;
	}
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public BigDecimal getUploadSeqId() {
		return uploadSeqId;
	}
	public void setUploadSeqId(BigDecimal uploadSeqId) {
		this.uploadSeqId = uploadSeqId;
	}
	public String getPurchaseSourceName() {
		return purchaseSourceName;
	}
	public void setPurchaseSourceName(String purchaseSourceName) {
		this.purchaseSourceName = purchaseSourceName;
	}
	public BigDecimal getTotUsageInLbs() {
		return totUsageInLbs;
	}
	public void setTotUsageInLbs(BigDecimal totUsageInLbs) {
		this.totUsageInLbs = totUsageInLbs;
	}

}