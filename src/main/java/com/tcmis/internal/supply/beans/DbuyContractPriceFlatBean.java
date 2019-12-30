package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceFlatBean <br>
 * @version: 1.0, Oct 1, 2017 <br>
 *****************************************************************************/


public class DbuyContractPriceFlatBean extends BaseDataBean {

	private String opsCompanyId;
	private String opsEntityId;
	private String operatingEntityName;
	private BigDecimal dbuyContractId;
	private BigDecimal itemId;
	private String itemDesc;
	private String packaging;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String inventoryGroupHub;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String shipToAddress;
	private BigDecimal priority;
	private String supplier;
	private String supplierName;
	private String carrier;
	private String carrierName;
	private BigDecimal buyer;
	private String buyerName;
	private BigDecimal sourcer;
	private String sourcerName;
	private String status;
	private String freightOnBoard;
	private BigDecimal multipleOf;
	private String supplierPartNo;
	private BigDecimal supplierContactId;
	private BigDecimal remainingShelfLifePercent;
	private String criticalOrderCarrier;
	private String criticalOrderCarrierName;
	private String refClientPartNo;
	private String refClientPoNo;
	private String roundToMultiple;
	private String consignment;
	private BigDecimal leadTimeDays;
	private BigDecimal transitTimeInDays;
	private String contractReferenceComments;
	private String loadingComments;
	private String pricingType;
	private String supplyPath;
	private Date loadingDate;
	private String buyerCompanyId;
	private String refDeliveryPoint;
	private String refMaterialRequest;
	private String refCustomerContactInfo;
	private String additionalLineNotes;
	private String purchaserReview;
	private BigDecimal updatedBy;
	private Date updatedDate;
	private String updateByName;
	private String defaultShipmentOriginState;
	private Date startDate;
	private BigDecimal unitPrice;
	private BigDecimal breakUnitPrice;
	private String currencyId;
	private String priceComments;
	private BigDecimal minBuyQuantity;
	private BigDecimal minBuyValue;
	private String supplierContactName;
	private BigDecimal defCustomerServiceRepId;
	private String customerServiceRepName;
	private BigDecimal priceUpdatedBy;
	private String priceUpdatedByName;
	private Date priceUpdatedDate;
	private String breakPriceUpdatedBy;
	private Date breakPriceUpdatedDate;
	private BigDecimal breakQuantity;

	//constructor
	public DbuyContractPriceFlatBean() {
	}


	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}

	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}

	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
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

	public String getInventoryGroupHub() {
		return inventoryGroupHub;
	}

	public void setInventoryGroupHub(String inventoryGroupHub) {
		this.inventoryGroupHub = inventoryGroupHub;
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

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public BigDecimal getPriority() {
		return priority;
	}

	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public BigDecimal getBuyer() {
		return buyer;
	}

	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public BigDecimal getSourcer() {
		return sourcer;
	}

	public void setSourcer(BigDecimal sourcer) {
		this.sourcer = sourcer;
	}

	public String getSourcerName() {
		return sourcerName;
	}

	public void setSourcerName(String sourcerName) {
		this.sourcerName = sourcerName;
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

	public String getCriticalOrderCarrierName() {
		return criticalOrderCarrierName;
	}

	public void setCriticalOrderCarrierName(String criticalOrderCarrierName) {
		this.criticalOrderCarrierName = criticalOrderCarrierName;
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

	public String getRefDeliveryPoint() {
		return refDeliveryPoint;
	}

	public void setRefDeliveryPoint(String refDeliveryPoint) {
		this.refDeliveryPoint = refDeliveryPoint;
	}

	public String getRefMaterialRequest() {
		return refMaterialRequest;
	}

	public void setRefMaterialRequest(String refMaterialRequest) {
		this.refMaterialRequest = refMaterialRequest;
	}

	public String getRefCustomerContactInfo() {
		return refCustomerContactInfo;
	}

	public void setRefCustomerContactInfo(String refCustomerContactInfo) {
		this.refCustomerContactInfo = refCustomerContactInfo;
	}

	public String getAdditionalLineNotes() {
		return additionalLineNotes;
	}

	public void setAdditionalLineNotes(String additionalLineNotes) {
		this.additionalLineNotes = additionalLineNotes;
	}

	public String getPurchaserReview() {
		return purchaserReview;
	}

	public void setPurchaserReview(String purchaserReview) {
		this.purchaserReview = purchaserReview;
	}

	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}

	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getBreakUnitPrice() {
		return breakUnitPrice;
	}

	public void setBreakUnitPrice(BigDecimal breakUnitPrice) {
		this.breakUnitPrice = breakUnitPrice;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public String getPriceComments() {
		return priceComments;
	}

	public void setPriceComments(String priceComments) {
		this.priceComments = priceComments;
	}

	public BigDecimal getMinBuyQuantity() {
		return minBuyQuantity;
	}

	public void setMinBuyQuantity(BigDecimal minBuyQuantity) {
		this.minBuyQuantity = minBuyQuantity;
	}

	public BigDecimal getMinBuyValue() {
		return minBuyValue;
	}

	public void setMinBuyValue(BigDecimal minBuyValue) {
		this.minBuyValue = minBuyValue;
	}

	public String getSupplierContactName() {
		return supplierContactName;
	}

	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}

	public BigDecimal getDefCustomerServiceRepId() {
		return defCustomerServiceRepId;
	}

	public void setDefCustomerServiceRepId(BigDecimal defCustomerServiceRepId) {
		this.defCustomerServiceRepId = defCustomerServiceRepId;
	}

	public String getCustomerServiceRepName() {
		return customerServiceRepName;
	}

	public void setCustomerServiceRepName(String customerServiceRepName) {
		this.customerServiceRepName = customerServiceRepName;
	}

	public BigDecimal getPriceUpdatedBy() {
		return priceUpdatedBy;
	}

	public void setPriceUpdatedBy(BigDecimal priceUpdatedBy) {
		this.priceUpdatedBy = priceUpdatedBy;
	}

	public String getPriceUpdatedByName() {
		return priceUpdatedByName;
	}

	public void setPriceUpdatedByName(String priceUpdatedByName) {
		this.priceUpdatedByName = priceUpdatedByName;
	}

	public Date getPriceUpdatedDate() {
		return priceUpdatedDate;
	}

	public void setPriceUpdatedDate(Date priceUpdatedDate) {
		this.priceUpdatedDate = priceUpdatedDate;
	}

	public String getBreakPriceUpdatedBy() {
		return breakPriceUpdatedBy;
	}

	public void setBreakPriceUpdatedBy(String breakPriceUpdatedBy) {
		this.breakPriceUpdatedBy = breakPriceUpdatedBy;
	}

	public Date getBreakPriceUpdatedDate() {
		return breakPriceUpdatedDate;
	}

	public void setBreakPriceUpdatedDate(Date breakPriceUpdatedDate) {
		this.breakPriceUpdatedDate = breakPriceUpdatedDate;
	}

	public BigDecimal getBreakQuantity() {
		return breakQuantity;
	}

	public void setBreakQuantity(BigDecimal breakQuantity) {
		this.breakQuantity = breakQuantity;
	}
}