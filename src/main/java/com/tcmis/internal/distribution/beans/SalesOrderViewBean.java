package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class SalesOrderViewBean extends BaseDataBean {
	private static final long serialVersionUID = 1L;

	private String opsEntityId;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String customerName;
	private String accountNumber;
	private String customerPoLine;
	private String salesOrder;
	private String requestorName;
	private String facPartNo;
	private String partShortName;
	private BigDecimal poLineQuantity;
	private BigDecimal unitPrice;
	private Date needDate;
	private Date promisedDate;
	private String orderStatus;
	private Date orderDate;
	private BigDecimal orderAge;
	private String comments;
	private String currencyId;	
	private String opsCompanyId;	
	private BigDecimal customerId;
	private BigDecimal margin;
	private BigDecimal qtyShipped;
	private BigDecimal qtyAllocated;
	private BigDecimal qtyBackordered;
	private String poNumber;
	private BigDecimal prNumber;
	private String ok;
	private String lineItem;	
	private String operatingEntityName;
	private String companyId;
	private String critical;
	private BigDecimal totalExtendedPrice;
	private BigDecimal availableCredit;
	private String withinTerms;

	private Date submittedDate;
	private Date mrReleasedDate;
	private Date promiseDate;
	private String alternateName;
	private String partDescription;
	private BigDecimal rliToOpsEntityConvRate;
	private BigDecimal quantity;
	private BigDecimal minimumGrossMargin;
	private BigDecimal maximumGrossMargin;
	private BigDecimal catalogPrice;
	private BigDecimal expectedUnitCost;
	private BigDecimal qtyPicked;
	private BigDecimal qtyInPurchasing;
	private String requestLineStatus;
	private String cancelStatus;
	private BigDecimal customerServiceRepId;
	private String customerServiceRep;
	private String customerServiceRepEmail;
	private String customerServiceRepPhone;
	private String customerPartNo;
	private BigDecimal daysLate;
	
	private String specification;
	
	private String internalNote;
	private String purchasingNote;
	private String specialInstructions;
	private String orderInternalNote;
	private String invoiceNote;
	private String orderShiptoNote;
	
	private String addressLine1Display;
	private String addressLine2Display;
	private String addressLine3Display;
	private String addressLine4Display;
	private String addressLine5Display;
	private String submitterName;
	private String distCustomerPartList;
	
	private String facilityId;
	
	

	public String getAlternateName() {
		return alternateName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public String getCancelStatus() {
		return cancelStatus;
	}
	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public String getCustomerServiceRep() {
		return customerServiceRep;
	}
	public void setCustomerServiceRep(String customerServiceRep) {
		this.customerServiceRep = customerServiceRep;
	}
	public BigDecimal getCustomerServiceRepId() {
		return customerServiceRepId;
	}
	public void setCustomerServiceRepId(BigDecimal customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}
	public BigDecimal getExpectedUnitCost() {
		return expectedUnitCost;
	}
	public void setExpectedUnitCost(BigDecimal expectedUnitCost) {
		this.expectedUnitCost = expectedUnitCost;
	}
	public BigDecimal getMaximumGrossMargin() {
		return maximumGrossMargin;
	}
	public void setMaximumGrossMargin(BigDecimal maximumGrossMargin) {
		this.maximumGrossMargin = maximumGrossMargin;
	}
	public BigDecimal getMinimumGrossMargin() {
		return minimumGrossMargin;
	}
	public void setMinimumGrossMargin(BigDecimal minimumGrossMargin) {
		this.minimumGrossMargin = minimumGrossMargin;
	}
	public Date getMrReleasedDate() {
		return mrReleasedDate;
	}
	public void setMrReleasedDate(Date mrReleasedDate) {
		this.mrReleasedDate = mrReleasedDate;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public BigDecimal getQtyInPurchasing() {
		return qtyInPurchasing;
	}
	public void setQtyInPurchasing(BigDecimal qtyInPurchasing) {
		this.qtyInPurchasing = qtyInPurchasing;
	}
	public BigDecimal getQtyPicked() {
		return qtyPicked;
	}
	public void setQtyPicked(BigDecimal qtyPicked) {
		this.qtyPicked = qtyPicked;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getRequestLineStatus() {
		return requestLineStatus;
	}
	public void setRequestLineStatus(String requestLineStatus) {
		this.requestLineStatus = requestLineStatus;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}  
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public String getHub() {
		return hub;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}		
	public String getHubName() {
		return hubName;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}	
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}	
	public String getCustomerPoLine() {
		return customerPoLine;
	}
	public void setCustomerPoLine(String customerPoLine) {
		this.customerPoLine = customerPoLine;
	}
	public String getSalesOrder() {
		return salesOrder;
	}
	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public BigDecimal getPoLineQuantity() {
		return poLineQuantity;
	}
	public void setPoLineQuantity(BigDecimal poLineQuantity) {
		this.poLineQuantity = poLineQuantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Date getNeedDate() {
		return needDate;
	}
	public void setNeedDate(Date needDate) {
		this.needDate = needDate;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public BigDecimal getOrderAge() {
		return orderAge;
	}
	public void setOrderAge(BigDecimal orderAge) {
		this.orderAge = orderAge;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setMargin(BigDecimal margin) {
		this.margin = margin;
	}
	public BigDecimal getMargin() {
		return margin;
	}
	public void setQtyShipped(BigDecimal qtyShipped) {
		this.qtyShipped = qtyShipped;
	}
	public BigDecimal getQtyShipped() {
		return qtyShipped;
	}
	public void setQtyAllocated(BigDecimal qtyAllocated) {
		this.qtyAllocated = qtyAllocated;
	}
	public BigDecimal getQtyAllocated() {
		return qtyAllocated;
	}
	public void setQtyBackordered(BigDecimal qtyBackordered) {
		this.qtyBackordered = qtyBackordered;
	}
	public BigDecimal getQtyBackordered() {
		return qtyBackordered;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getOk() {
		return ok;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public String getLineItem() {
		return lineItem;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCritical(String critical) {
		this.critical = critical;
	}
	public String getCritical() {
		return critical;
	}
	/**
	 * @return the totalExtendedPrice
	 */
	public BigDecimal getTotalExtendedPrice() {
		return totalExtendedPrice;
	}
	/**
	 * @param totalExtendedPrice the totalExtendedPrice to set
	 */
	public void setTotalExtendedPrice(BigDecimal totalExtendedPrice) {
		this.totalExtendedPrice = totalExtendedPrice;
	}
	/**
	 * @return the availableCredit
	 */
	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}
	/**
	 * @param availableCredit the availableCredit to set
	 */
	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}
	/**
	 * @return the withinTerms
	 */
	public String getWithinTerms() {
		return withinTerms;
	}
	/**
	 * @param withinTerms the withinTerms to set
	 */
	public void setWithinTerms(String withinTerms) {
		this.withinTerms = withinTerms;
	}
	public BigDecimal getRliToOpsEntityConvRate() {
		return rliToOpsEntityConvRate;
	}
	public void setRliToOpsEntityConvRate(BigDecimal rliToOpsEntityConvRate) {
		this.rliToOpsEntityConvRate = rliToOpsEntityConvRate;
	}
	public Date getPromiseDate() {
		return promiseDate;
	}
	public void setPromiseDate(Date promiseDate) {
		this.promiseDate = promiseDate;
	}
	public BigDecimal getDaysLate() {
		return daysLate;
	}
	public void setDaysLate(BigDecimal daysLate) {
		this.daysLate = daysLate;
	}
	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getInternalNote() {
		return internalNote;
	}
	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}
	public String getInvoiceNote() {
		return invoiceNote;
	}
	public void setInvoiceNote(String invoiceNote) {
		this.invoiceNote = invoiceNote;
	}
	public String getOrderInternalNote() {
		return orderInternalNote;
	}
	public void setOrderInternalNote(String orderInternalNote) {
		this.orderInternalNote = orderInternalNote;
	}
	public String getOrderShiptoNote() {
		return orderShiptoNote;
	}
	public void setOrderShiptoNote(String orderShiptoNote) {
		this.orderShiptoNote = orderShiptoNote;
	}
	public String getPurchasingNote() {
		return purchasingNote;
	}
	public void setPurchasingNote(String purchasingNote) {
		this.purchasingNote = purchasingNote;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public String getAddressLine1Display() {
		return addressLine1Display;
	}
	public void setAddressLine1Display(String addressLine1Display) {
		this.addressLine1Display = addressLine1Display;
	}
	public String getAddressLine2Display() {
		return addressLine2Display;
	}
	public void setAddressLine2Display(String addressLine2Display) {
		this.addressLine2Display = addressLine2Display;
	}
	public String getAddressLine3Display() {
		return addressLine3Display;
	}
	public void setAddressLine3Display(String addressLine3Display) {
		this.addressLine3Display = addressLine3Display;
	}
	public String getAddressLine4Display() {
		return addressLine4Display;
	}
	public void setAddressLine4Display(String addressLine4Display) {
		this.addressLine4Display = addressLine4Display;
	}
	public String getAddressLine5Display() {
		return addressLine5Display;
	}
	public void setAddressLine5Display(String addressLine5Display) {
		this.addressLine5Display = addressLine5Display;
	}
	public String getSubmitterName() {
		return submitterName;
	}
	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}
	public String getDistCustomerPartList() {
		return distCustomerPartList;
	}
	public void setDistCustomerPartList(String distCustomerPartList) {
		this.distCustomerPartList = distCustomerPartList;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getCustomerServiceRepEmail() {
		return customerServiceRepEmail;
	}
	public void setCustomerServiceRepEmail(String customerServiceRepEmail) {
		this.customerServiceRepEmail = customerServiceRepEmail;
	}
	public String getCustomerPartNo() {
		return customerPartNo;
	}
	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}
	public String getCustomerServiceRepPhone() {
		return customerServiceRepPhone;
	}
	public void setCustomerServiceRepPhone(String customerServiceRepPhone) {
		this.customerServiceRepPhone = customerServiceRepPhone;
	}
	
		
}
