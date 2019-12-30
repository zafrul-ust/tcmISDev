package com.tcmis.supplier.dbuy.beans;

import java.util.Date; 
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractBean <br>
 * @version: 1.0, May 3, 2006 <br>
 *****************************************************************************/


public class DbuyContractBean extends BaseDataBean {

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


	//constructor
	public DbuyContractBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setSourcer(BigDecimal sourcer) {
		this.sourcer = sourcer;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setFreightOnBoard(String freightOnBoard) {
		this.freightOnBoard = freightOnBoard;
	}
	public void setMultipleOf(BigDecimal multipleOf) {
		this.multipleOf = multipleOf;
	}
	public void setSupplierPartNo(String supplierPartNo) {
		this.supplierPartNo = supplierPartNo;
	}
	public void setSupplierContactId(BigDecimal supplierContactId) {
		this.supplierContactId = supplierContactId;
	}
	public void setRemainingShelfLifePercent(BigDecimal remainingShelfLifePercent) {
		this.remainingShelfLifePercent = remainingShelfLifePercent;
	}
	public void setCriticalOrderCarrier(String criticalOrderCarrier) {
		this.criticalOrderCarrier = criticalOrderCarrier;
	}
	public void setRefClientPartNo(String refClientPartNo) {
		this.refClientPartNo = refClientPartNo;
	}
	public void setRefClientPoNo(String refClientPoNo) {
		this.refClientPoNo = refClientPoNo;
	}
	public void setRoundToMultiple(String roundToMultiple) {
		this.roundToMultiple = roundToMultiple;
	}
	public void setConsignment(String consignment) {
		this.consignment = consignment;
	}
	public void setLeadTimeDays(BigDecimal leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}
	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}
	public void setTransitTimeInDays(BigDecimal transitTimeInDays) {
		this.transitTimeInDays = transitTimeInDays;
	}
	public void setContractReferenceComments(String contractReferenceComments) {
		this.contractReferenceComments = contractReferenceComments;
	}
	public void setLoadingComments(String loadingComments) {
		this.loadingComments = loadingComments;
	}
	public void setPricingType(String pricingType) {
		this.pricingType = pricingType;
	}
	public void setSupplyPath(String supplyPath) {
		this.supplyPath = supplyPath;
	}
	public void setLoadingDate(Date loadingDate) {
		this.loadingDate = loadingDate;
	}
	public void setBuyerCompanyId(String buyerCompanyId) {
		this.buyerCompanyId = buyerCompanyId;
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public BigDecimal getPriority() {
		return priority;
	}
	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getCarrier() {
		return carrier;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public BigDecimal getSourcer() {
		return sourcer;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getStatus() {
		return status;
	}
	public String getFreightOnBoard() {
		return freightOnBoard;
	}
	public BigDecimal getMultipleOf() {
		return multipleOf;
	}
	public String getSupplierPartNo() {
		return supplierPartNo;
	}
	public BigDecimal getSupplierContactId() {
		return supplierContactId;
	}
	public BigDecimal getRemainingShelfLifePercent() {
		return remainingShelfLifePercent;
	}
	public String getCriticalOrderCarrier() {
		return criticalOrderCarrier;
	}
	public String getRefClientPartNo() {
		return refClientPartNo;
	}
	public String getRefClientPoNo() {
		return refClientPoNo;
	}
	public String getRoundToMultiple() {
		return roundToMultiple;
	}
	public String getConsignment() {
		return consignment;
	}
	public BigDecimal getLeadTimeDays() {
		return leadTimeDays;
	}
	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}
	public BigDecimal getTransitTimeInDays() {
		return transitTimeInDays;
	}
	public String getContractReferenceComments() {
		return contractReferenceComments;
	}
	public String getLoadingComments() {
		return loadingComments;
	}
	public String getPricingType() {
		return pricingType;
	}
	public String getSupplyPath() {
		return supplyPath;
	}
	public Date getLoadingDate() {
		return loadingDate;
	}
	public String getBuyerCompanyId() {
		return buyerCompanyId;
	}
}