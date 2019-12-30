package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/**
 * OrderEntryInputBean.
 * 
 * 
 */
public class QuotationHistoryViewBean extends BaseDataBean {

	private static final long serialVersionUID = -4727485877895112245L;
	private String action;
	private String alternateName;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String companyId;
	private String currencyId;
	private BigDecimal customerId;
	private String customerName;
	private String customerPartNo;
	private BigDecimal expectedUnitCost;
	private String inventoryGroup;
	private BigDecimal itemId;
	private BigDecimal limitDays;
	private String lineItem;
	private String notes;
	private String opsRegionName;
	private String partDescription;
	private String partNote;
	private String partShortName;
	private BigDecimal prNumber;
	private Date promisedDate;
	private BigDecimal quantity;
	private Date quoteExpireDate;
	private String region;
	private Date requestDate;
	private BigDecimal requiredShelfLife;
	private String searchKey;
	private String specList;
	private BigDecimal startDays;
	private String status;
	private Date submittedDate;
	private BigDecimal unitPrice;
	private BigDecimal warehouseOnHand;
	private String paymentTerms;
	private String incoTerms;
	private String inventoryGroupName;
	private BigDecimal origSalesQuoteCount;
	
	private String opsEntityId;

	public QuotationHistoryViewBean() {
	}

	public String getAction() {
		return action;
	}

	/**
	 * @return the alternateName
	 */
	public String getAlternateName() {
		return alternateName;
	}

	/**
	 * @return the catalogCompanyId
	 */
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	/**
	 * @return the catalogId
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * @return the catPartNo
	 */
	public String getCatPartNo() {
		return catPartNo;
	}

	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}

	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}

	/**
	 * @return the customerId
	 */
	public BigDecimal getCustomerId() {
		return customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @return the customerPartNo
	 */
	public String getCustomerPartNo() {
		return customerPartNo;
	}

	/**
	 * @return the expectedUnitCost
	 */
	public BigDecimal getExpectedUnitCost() {
		return expectedUnitCost;
	}

	/**
	 * @return the inventoryGroup
	 */
	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public BigDecimal getLimitDays() {
		return limitDays;
	}

	/**
	 * @return the lineItem
	 */
	public String getLineItem() {
		return lineItem;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	public String getOpsRegionName() {
		return opsRegionName;
	}

	/**
	 * @return the partDescription
	 */
	public String getPartDescription() {
		return partDescription;
	}

	/**
	 * @return the partNote
	 */
	public String getPartNote() {
		return partNote;
	}

	/**
	 * @return the partShortName
	 */
	public String getPartShortName() {
		return partShortName;
	}

	/**
	 * @return the prNumber
	 */
	public BigDecimal getPrNumber() {
		return prNumber;
	}

	/**
	 * @return the promisedDate
	 */
	public Date getPromisedDate() {
		return promisedDate;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @return the quoteExpireDate
	 */
	public Date getQuoteExpireDate() {
		return quoteExpireDate;
	}

	public String getRegion() {
		return region;
	}

	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}

	/**
	 * @return the requiredShelfLife
	 */
	public BigDecimal getRequiredShelfLife() {
		return requiredShelfLife;
	}

	public String getSearchKey() {
		return searchKey;
	}

	/**
	 * @return the specList
	 */
	public String getSpecList() {
		return specList;
	}

	public BigDecimal getStartDays() {
		return startDays;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the submittedDate
	 */
	public Date getSubmittedDate() {
		return submittedDate;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @param alternateName the alternateName to set
	 */
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	/**
	 * @param catalogCompanyId the catalogCompanyId to set
	 */
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	/**
	 * @param catalogId the catalogId to set
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * @param catPartNo the catPartNo to set
	 */
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @param customerPartNo the customerPartNo to set
	 */
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	/**
	 * @param expectedUnitCost the expectedUnitCost to set
	 */
	public void setExpectedUnitCost(BigDecimal expectedUnitCost) {
		this.expectedUnitCost = expectedUnitCost;
	}

	/**
	 * @param inventoryGroup the inventoryGroup to set
	 */
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLimitDays(BigDecimal limitDays) {
		this.limitDays = limitDays;
	}

	/**
	 * @param lineItem the lineItem to set
	 */
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOpsRegionName(String opsRegionName) {
		this.opsRegionName = opsRegionName;
	}

	/**
	 * @param partDescription the partDescription to set
	 */
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	/**
	 * @param partNote the partNote to set
	 */
	public void setPartNote(String partNote) {
		this.partNote = partNote;
	}

	/**
	 * @param partShortName the partShortName to set
	 */
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}

	/**
	 * @param prNumber the prNumber to set
	 */
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	/**
	 * @param promisedDate the promisedDate to set
	 */
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @param quoteExpireDate the quoteExpireDate to set
	 */
	public void setQuoteExpireDate(Date quoteExpireDate) {
		this.quoteExpireDate = quoteExpireDate;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	/**
	 * @param requiredShelfLife the requiredShelfLife to set
	 */
	public void setRequiredShelfLife(BigDecimal requiredShelfLife) {
		this.requiredShelfLife = requiredShelfLife;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 * @param specList the specList to set
	 */
	public void setSpecList(String specList) {
		this.specList = specList;
	}

	public void setStartDays(BigDecimal startDays) {
		this.startDays = startDays;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param submittedDate the submittedDate to set
	 */
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getWarehouseOnHand() {
		return warehouseOnHand;
	}

	public void setWarehouseOnHand(BigDecimal warehouseOnHand) {
		this.warehouseOnHand = warehouseOnHand;
	}

	public String getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public BigDecimal getOrigSalesQuoteCount() {
		return origSalesQuoteCount;
	}

	public void setOrigSalesQuoteCount(BigDecimal origSalesQuoteCount) {
		this.origSalesQuoteCount = origSalesQuoteCount;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

}