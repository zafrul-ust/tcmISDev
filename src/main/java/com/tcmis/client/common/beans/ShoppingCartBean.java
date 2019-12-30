package com.tcmis.client.common.beans;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

/******************************************************************************
 * CLASSNAME: ShoppingCartBean <br>
 * 
 * @version: 1.0, Jan 9, 2008 <br>
 *****************************************************************************/

public class ShoppingCartBean extends BaseDataBean {

	private String		catalogCompanyId;
	private String		catalogId;
	private String		catPartNo;
	private BigDecimal	partGroupNo;
	private String		partDescription;
	private BigDecimal	catalogPrice;
	private BigDecimal	baselinePrice;
	private String		currencyId;
	private String		itemId;
	private String		exampleItemId;
	private String		itemType;
	private String		examplePackaging;
	private String		workArea;
	private BigDecimal	quantity;
	private Date		dateNeeded;
	private String		notes;
	private String		critical;
	private String		orderQuantityRule;
	private String		inventoryGroup;
	private String		facilityId;
	private BigDecimal	prNumber;
	private String		lineItem;
	private String		application;
	private String		chargeType;
	private String		poNumber;
	private String		releaseNumber;
	private String		chargeNumber;
	private String		posWorkAreaOption;
	private String		stockingMethod;

	private String allOrNone;
	private String allocateByMfgLot;
	private Date allocateAfter;
	private String sourceFileName;
	private String customerRequisitionNumber;
	
	private BigDecimal projectedLeadTime;
	private String partApproved;

	private String applicationDisplay;
	private String originalApplication;

	// constructor
	public ShoppingCartBean() {
	}

	// setters
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatPartNo(String catPartNo) {
		if (!StringHandler.isBlankString(catPartNo)) {
			this.catPartNo = StringEscapeUtils.unescapeHtml(catPartNo);
		}
	}

	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public void setBaselinePrice(BigDecimal baselinePrice) {
		this.baselinePrice = baselinePrice;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setExampleItemId(String exampleItemId) {
		this.exampleItemId = exampleItemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setExamplePackaging(String examplePackaging) {
		this.examplePackaging = examplePackaging;
	}

	public void setWorkArea(String workArea) {
		this.workArea = workArea;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public void setOrderQuantityRule(String orderQuantityRule) {
		this.orderQuantityRule = orderQuantityRule;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	public void setPosWorkAreaOption(String posWorkAreaOption) {
		this.posWorkAreaOption = posWorkAreaOption;
	}

	public void setStockingMethod(String stockingMethod) {
		this.stockingMethod = stockingMethod;
	}

	// getters
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public BigDecimal getBaselinePrice() {
		return baselinePrice;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getExampleItemId() {
		return exampleItemId;
	}

	public String getItemType() {
		return itemType;
	}

	public String getExamplePackaging() {
		return examplePackaging;
	}

	public String getWorkArea() {
		return workArea;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public Date getDateNeeded() {
		return dateNeeded;
	}

	public String getNotes() {
		return notes;
	}

	public String getCritical() {
		return critical;
	}

	public String getOrderQuantityRule() {
		return orderQuantityRule;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getApplication() {
		return application;
	}

	public String getChargeType() {
		return chargeType;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public String getReleaseNumber() {
		return releaseNumber;
	}

	public String getChargeNumber() {
		return chargeNumber;
	}

	public String getPosWorkAreaOption() {
		return posWorkAreaOption;
	}

	public String getStockingMethod() {
		return stockingMethod;
	}

	public String getAllOrNone() {
		return allOrNone;
	}

	public void setAllOrNone(String allOrNone) {
		this.allOrNone = allOrNone;
	}

	public String getAllocateByMfgLot() {
		return allocateByMfgLot;
	}

	public void setAllocateByMfgLot(String allocateByMfgLot) {
		this.allocateByMfgLot = allocateByMfgLot;
	}

	public Date getAllocateAfter() {
		return allocateAfter;
	}

	public void setAllocateAfter(Date allocateAfter) {
		this.allocateAfter = allocateAfter;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getCustomerRequisitionNumber() {
		return customerRequisitionNumber;
	}

	public void setCustomerRequisitionNumber(String customerRequisitionNumber) {
		this.customerRequisitionNumber = customerRequisitionNumber;
	}

	public String getPartApproved() {
		return partApproved;
	}

	public void setPartApproved(String partApproved) {
		this.partApproved = partApproved;
	}

	public BigDecimal getProjectedLeadTime() {
		return projectedLeadTime;
	}

	public void setProjectedLeadTime(BigDecimal projectedLeadTime) {
		this.projectedLeadTime = projectedLeadTime;
	}

	public String getApplicationDisplay() {
		return applicationDisplay;
	}

	public void setApplicationDisplay(String applicationDisplay) {
		this.applicationDisplay = applicationDisplay;
	}

	public String getOriginalApplication() {
		return originalApplication;
	}

	public void setOriginalApplication(String originalApplication) {
		this.originalApplication = originalApplication;
	}
} // end of class