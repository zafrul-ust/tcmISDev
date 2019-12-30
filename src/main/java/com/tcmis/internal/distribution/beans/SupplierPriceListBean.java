package com.tcmis.internal.distribution.beans;

import java.util.Date; 

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractBean <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/


public class SupplierPriceListBean extends BaseDataBean {

	private BigDecimal itemId;
	private String inventoryGroup;
	private String shipToCompanyId;
	private String shipToLocationId;
	private BigDecimal priority;
	private BigDecimal dbuyContractId;
	private String supplier;
	private String carrier;
	private BigDecimal buyer;
	private BigDecimal sourcer;
	private String paymentTerms;
	private String status;
	private String freightOnBoard;
	private BigDecimal multipleOf;
	private String supplierPartNo;
	private BigDecimal supplierContactId;
	private BigDecimal remainingShelfLifePercent;
	private String criticalOrderCarrier;
	private String refClientPartNo;
	private String refClientPoNo;
	private String roundToMultiple;
	private String consignment;
	private BigDecimal leadTimeDays;
	private String defaultShipmentOriginState;
	private BigDecimal transitTimeInDays;
	private String contractReferenceComments;
	private String loadingComments;
	private String pricingType;
	private String supplyPath;
	private Date loadingDate;
	private String buyerCompanyId;

	private Date endDate;
	private BigDecimal uptoQuantity;
	private BigDecimal unitPrice;
	private String currencyId;
	private String comments;

	private Date startDate;
	private String itemDesc;

	//constructor
	public SupplierPriceListBean() {
	}

	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public String getInventoryGroup() {
		return inventoryGroup;
	}


	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	public String getShipToCompanyId() {
		return shipToCompanyId;
	}


	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}


	public String getShipToLocationId() {
		return shipToLocationId;
	}


	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}


	public BigDecimal getPriority() {
		return priority;
	}


	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}


	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}


	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}


	public String getSupplier() {
		return supplier;
	}


	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


	public String getCarrier() {
		return carrier;
	}


	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public BigDecimal getBuyer() {
		return buyer;
	}


	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}


	public BigDecimal getSourcer() {
		return sourcer;
	}


	public void setSourcer(BigDecimal sourcer) {
		this.sourcer = sourcer;
	}


	public String getPaymentTerms() {
		return paymentTerms;
	}


	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getFreightOnBoard() {
		return freightOnBoard;
	}


	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}


	public BigDecimal getMultipleOf() {
		return multipleOf;
	}


	public void setMultipleOf(BigDecimal multipleOf) {
		this.multipleOf = multipleOf;
	}


	public String getSupplierPartNo() {
		return supplierPartNo;
	}


	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}


	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}


	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}


	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}


	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}


	public String getCriticalOrderCarrier() {
		return criticalOrderCarrier;
	}


	public void setCriticalOrderCarrier(String criticalOrderCarrier) {
		this.criticalOrderCarrier = criticalOrderCarrier;
	}


	public String getRefClientPartNo() {
		return refClientPartNo;
	}


	public void setRefClientPartNo(String refClientPartNo) {
		this.refClientPartNo = refClientPartNo;
	}


	public String getRefClientPoNo() {
		return refClientPoNo;
	}


	public void setRefClientPoNo(String refClientPoNo) {
		this.refClientPoNo = refClientPoNo;
	}


	public String getRoundToMultiple() {
		return roundToMultiple;
	}


	public void setRoundToMultiple(String roundToMultiple) {
		this.roundToMultiple = roundToMultiple;
	}


	public String getConsignment() {
		return consignment;
	}


	public void setConsignment(String consignment) {
		this.consignment = consignment;
	}


	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}


	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}


	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}


	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}


	public BigDecimal getTransitTimeInDays() {
		return transitTimeInDays;
	}


	public void setTransitTimeInDays(BigDecimal transitTimeInDays) {
		this.transitTimeInDays = transitTimeInDays;
	}


	public String getContractReferenceComments() {
		return contractReferenceComments;
	}


	public void setContractReferenceComments(String contractReferenceComments) {
		this.contractReferenceComments = contractReferenceComments;
	}


	public String getLoadingComments() {
		return loadingComments;
	}


	public void setLoadingComments(String loadingComments) {
		this.loadingComments = loadingComments;
	}


	public String getPricingType() {
		return pricingType;
	}


	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}


	public String getSupplyPath() {
		return supplyPath;
	}


	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}


	public Date getLoadingDate() {
		return loadingDate;
	}


	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}


	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}


	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Date getEndDate() {
		return endDate;
	}


	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public BigDecimal getUptoQuantity() {
		return uptoQuantity;
	}


	public void setUptoQuantity(BigDecimal uptoQuantity) {
		this.uptoQuantity = uptoQuantity;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getCurrencyId() {
		return currencyId;
	}


	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

}