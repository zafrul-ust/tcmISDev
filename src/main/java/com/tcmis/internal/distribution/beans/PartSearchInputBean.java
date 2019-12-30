package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TmpPartSearchBean <br>
 * @version: 1.0, Aug 26, 2009 <br>
 *****************************************************************************/


public class PartSearchInputBean extends BaseDataBean {

	private String searchPartNumberMode;
	private String partNumber;
	private String searchPartDescMode;
	private String partDesc;
	private String searchSpecificationMode;
	private String specification;
	private String searchCustomerPartNumberMode;
	private String customerPartNumber;
	private String searchTextMode;
	private String text;
	private String searchAlternateMode;
	private String alternate;
	private String autoAllocationIgMode;
	private String autoAllocationIg;
	private String companyId;
	private String shipToLocationId;
	private String inventoryGroup;
	private BigDecimal customerId;   
	private String priceList; // not use this one for priceGroupId
	
	private String priceGroupId; 
	private String currencyId; 
	private String opsEntityId; 
	private String catalogId;   
	private String catalogCompanyId;  
	
		
	//	constructor
	public PartSearchInputBean() {
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public String getAlternate() {
		return alternate;
	}

	public void setAlternate(String alternate) {
		this.alternate = alternate;
	}

	public String getCustomerPartNumber() {
		return customerPartNumber;
	}

	public void setCustomerPartNumber(String customerPartNumber) {
		this.customerPartNumber = customerPartNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getSearchAlternateMode() {
		return searchAlternateMode;
	}

	public void setSearchAlternateMode(String searchAlternateMode) {
		this.searchAlternateMode = searchAlternateMode;
	}

	public String getSearchCustomerPartNumberMode() {
		return searchCustomerPartNumberMode;
	}

	public void setSearchCustomerPartNumberMode(String searchCustomerPartNumberMode) {
		this.searchCustomerPartNumberMode = searchCustomerPartNumberMode;
	}

	public String getSearchPartNumberMode() {
		return searchPartNumberMode;
	}

	public void setSearchPartNumberMode(String searchPartNumberMode) {
		this.searchPartNumberMode = searchPartNumberMode;
	}

	public String getSearchSpecificationMode() {
		return searchSpecificationMode;
	}

	public void setSearchSpecificationMode(String searchSpecificationMode) {
		this.searchSpecificationMode = searchSpecificationMode;
	}

	public String getSearchTextMode() {
		return searchTextMode;
	}

	public void setSearchTextMode(String searchTextMode) {
		this.searchTextMode = searchTextMode;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPriceList() {
		return priceList;
	}

	public void setPriceList(String priceList) {
		this.priceList = priceList;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getSearchPartDescMode() {
		return searchPartDescMode;
	}

	public void setSearchPartDescMode(String searchPartDescMode) {
		this.searchPartDescMode = searchPartDescMode;
	}

	public String getAutoAllocationIg() {
		return autoAllocationIg;
	}

	public void setAutoAllocationIg(String autoAllocationIg) {
		this.autoAllocationIg = autoAllocationIg;
	}

	public String getAutoAllocationIgMode() {
		return autoAllocationIgMode;
	}
	public void setAutoAllocationIgMode(String autoAllocationIgMode) {
		this.autoAllocationIgMode = autoAllocationIgMode;
	}
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}


}