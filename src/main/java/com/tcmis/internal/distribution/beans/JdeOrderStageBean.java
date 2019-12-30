package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: JdeOrderStageBean <br>
 * @version: 1.0, Apr 5, 2016 <br>
 *****************************************************************************/


public class JdeOrderStageBean extends BaseDataBean {

	private BigDecimal jdeOrderStageId;
	private BigDecimal jdeSequenceNo;
	private String companyId;
	private BigDecimal jdeOrderLine;
	private String jdeOrderType;
	private BigDecimal jdeAddressBookNo;
	private BigDecimal personnelId;
	private String jdeSalesRepName;
	private String customerPoNo;
	private BigDecimal customerBillingNo;
	private String customerName;
	private BigDecimal itemId;
	private String catPartDesc;
	private String catPartSpecList;
	private BigDecimal remainingShelfLifePercent;
	private String catPartNo;
	private String catalogCompanyId;
	private String catalogId;
	private String specList;
	private String specDetailList;
	private String specCocList;
	private String specCoaList;
	private String specLibraryList;
	private String processingStatus;
	private String processingErrorMsg;
	private BigDecimal prNumber;
	private String lineItem;
	private Date insertedDate;
	private Date lastUpdateDate;
	private String jdeAddressBookShipTo;
	private String shipToAddress1;
	private String shipToAddress2;
	private String shipToAddress3;
	private String shipToAddress4;
	private String shipToCity;
	private String shipToState;
	private String shipToZip;
	private String shipToCountry;
	private String facilityId;
	private String opsEntityId;
	private String jdeAddressBookBillTo;
	private String soldToAddress1;
	private String soldToAddress2;
	private String soldToAddress3;
	private String soldToAddress4;
	private String soldToCity;
	private String soldToState;
	private String soldToZip;
	private String soldToCountry;
	private BigDecimal customerId;
	private BigDecimal unitPrice;
	private Date requiredDate;
	private Date promisedDate;
	private String customerPartNo;
	private String customerLineNo;


	//constructor
	public JdeOrderStageBean() {
	}

	//setters
	public void setJdeOrderStageId(BigDecimal jdeOrderStageId) {
		this.jdeOrderStageId = jdeOrderStageId;
	}
	public void setJdeSequenceNo(BigDecimal jdeSequenceNo) {
		this.jdeSequenceNo = jdeSequenceNo;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setJdeOrderLine(BigDecimal jdeOrderLine) {
		this.jdeOrderLine = jdeOrderLine;
	}
	public void setJdeOrderType(String jdeOrderType) {
		this.jdeOrderType = jdeOrderType;
	}
	public void setJdeAddressBookNo(BigDecimal jdeAddressBookNo) {
		this.jdeAddressBookNo = jdeAddressBookNo;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setJdeSalesRepName(String jdeSalesRepName) {
		this.jdeSalesRepName = jdeSalesRepName;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerBillingNo(BigDecimal customerBillingNo) {
		this.customerBillingNo = customerBillingNo;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setCatPartDesc(String catPartDesc) {
		this.catPartDesc = catPartDesc;
	}
	public void setCatPartSpecList(String catPartSpecList) {
		this.catPartSpecList = catPartSpecList;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public void setSpecDetailList(String specDetailList) {
		this.specDetailList = specDetailList;
	}
	public void setSpecCocList(String specCocList) {
		this.specCocList = specCocList;
	}
	public void setSpecCoaList(String specCoaList) {
		this.specCoaList = specCoaList;
	}
	public void setSpecLibraryList(String specLibraryList) {
		this.specLibraryList = specLibraryList;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public void setProcessingErrorMsg(String processingErrorMsg) {
		this.processingErrorMsg = processingErrorMsg;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public void setJdeAddressBookShipTo(String jdeAddressBookShipTo) {
		this.jdeAddressBookShipTo = jdeAddressBookShipTo;
	}
	public void setShipToAddress1(String shipToAddress1) {
		this.shipToAddress1 = shipToAddress1;
	}
	public void setShipToAddress2(String shipToAddress2) {
		this.shipToAddress2 = shipToAddress2;
	}
	public void setShipToAddress3(String shipToAddress3) {
		this.shipToAddress3 = shipToAddress3;
	}
	public void setShipToAddress4(String shipToAddress4) {
		this.shipToAddress4 = shipToAddress4;
	}
	public void setShipToCity(String shipToCity) {
		this.shipToCity = shipToCity;
	}
	public void setShipToState(String shipToState) {
		this.shipToState = shipToState;
	}
	public void setShipToZip(String shipToZip) {
		this.shipToZip = shipToZip;
	}
	public void setShipToCountry(String shipToCountry) {
		this.shipToCountry = shipToCountry;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setJdeAddressBookBillTo(String jdeAddressBookBillTo) {
		this.jdeAddressBookBillTo = jdeAddressBookBillTo;
	}
	public void setSoldToAddress1(String soldToAddress1) {
		this.soldToAddress1 = soldToAddress1;
	}
	public void setSoldToAddress2(String soldToAddress2) {
		this.soldToAddress2 = soldToAddress2;
	}
	public void setSoldToAddress3(String soldToAddress3) {
		this.soldToAddress3 = soldToAddress3;
	}
	public void setSoldToAddress4(String soldToAddress4) {
		this.soldToAddress4 = soldToAddress4;
	}
	public void setSoldToCity(String soldToCity) {
		this.soldToCity = soldToCity;
	}
	public void setSoldToState(String soldToState) {
		this.soldToState = soldToState;
	}
	public void setSoldToZip(String soldToZip) {
		this.soldToZip = soldToZip;
	}
	public void setSoldToCountry(String soldToCountry) {
		this.soldToCountry = soldToCountry;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public void setCustomerLineNo(String customerLineNo) {
		this.customerLineNo = customerLineNo;
	}


	//getters
	public BigDecimal getJdeOrderStageId() {
		return jdeOrderStageId;
	}
	public BigDecimal getJdeSequenceNo() {
		return jdeSequenceNo;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getJdeOrderLine() {
		return jdeOrderLine;
	}
	public String getJdeOrderType() {
		return jdeOrderType;
	}
	public BigDecimal getJdeAddressBookNo() {
		return jdeAddressBookNo;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getJdeSalesRepName() {
		return jdeSalesRepName;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public BigDecimal getCustomerBillingNo() {
		return customerBillingNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getCatPartDesc() {
		return catPartDesc;
	}
	public String getCatPartSpecList() {
		return catPartSpecList;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getSpecList() {
		return specList;
	}
	public String getSpecDetailList() {
		return specDetailList;
	}
	public String getSpecCocList() {
		return specCocList;
	}
	public String getSpecCoaList() {
		return specCoaList;
	}
	public String getSpecLibraryList() {
		return specLibraryList;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public String getProcessingErrorMsg() {
		return processingErrorMsg;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public Date getInsertedDate() {
		return insertedDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public String getJdeAddressBookShipTo() {
		return jdeAddressBookShipTo;
	}
	public String getShipToAddress1() {
		return shipToAddress1;
	}
	public String getShipToAddress2() {
		return shipToAddress2;
	}
	public String getShipToAddress3() {
		return shipToAddress3;
	}
	public String getShipToAddress4() {
		return shipToAddress4;
	}
	public String getShipToCity() {
		return shipToCity;
	}
	public String getShipToState() {
		return shipToState;
	}
	public String getShipToZip() {
		return shipToZip;
	}
	public String getShipToCountry() {
		return shipToCountry;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getJdeAddressBookBillTo() {
		return jdeAddressBookBillTo;
	}
	public String getSoldToAddress1() {
		return soldToAddress1;
	}
	public String getSoldToAddress2() {
		return soldToAddress2;
	}
	public String getSoldToAddress3() {
		return soldToAddress3;
	}
	public String getSoldToAddress4() {
		return soldToAddress4;
	}
	public String getSoldToCity() {
		return soldToCity;
	}
	public String getSoldToState() {
		return soldToState;
	}
	public String getSoldToZip() {
		return soldToZip;
	}
	public String getSoldToCountry() {
		return soldToCountry;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public Date getRequiredDate() {
		return requiredDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public String getCustomerLineNo() {
		return customerLineNo;
	}

}