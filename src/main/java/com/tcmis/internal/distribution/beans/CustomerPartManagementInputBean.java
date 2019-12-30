package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerPartManagementInputBean <br>
 * @version: 1.0, Sep 2, 2009 <br>
 *****************************************************************************/


public class CustomerPartManagementInputBean extends BaseDataBean {

	private BigDecimal customerId;
	private String customerName;
	private String searchBy;
	private String searchType;
	private String searchText;
	private String activeOnly;
	private BigDecimal itemId;
	private String partDescription;
	private String specList;
	private String ok;
	private String customerPartNo;
	private String status;
	private Date dateInserted;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private BigDecimal lastSearchCustomerId;
	private String oldCustomerPartNo;
	private String oldStatus;

	//constructor
	public CustomerPartManagementInputBean() {
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getActiveOnly() {
		return activeOnly;
	}

	public void setActiveOnly(String activeOnly) {
		this.activeOnly = activeOnly;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public String getSpecList() {
		return specList;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getCustomerPartNo() {
		return customerPartNo;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
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

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getLastSearchCustomerId() {
		return lastSearchCustomerId;
	}

	public void setLastSearchCustomerId(BigDecimal lastSearchCustomerId) {
		this.lastSearchCustomerId = lastSearchCustomerId;
	}

	public String getOldCustomerPartNo() {
		return oldCustomerPartNo;
	}

	public void setOldCustomerPartNo(String oldCustomerPartNo) {
		this.oldCustomerPartNo = oldCustomerPartNo;
	}

	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
} //end of method