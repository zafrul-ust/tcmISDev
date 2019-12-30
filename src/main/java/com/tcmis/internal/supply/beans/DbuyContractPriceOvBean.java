package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import radian.tcmis.common.util.StringHandler;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceOvBean <br>
 * @version: 1.0, Nov 16, 2009 <br>
 *****************************************************************************/


public class DbuyContractPriceOvBean extends BaseDataBean implements SQLData {

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
	private Collection<DbuyContractPriceBreakObjBean> priceBreakCollection;
	private Array priceBreakCollectionArray;
	private String buyType;
	private String buyTypeFlag;
	
	private String sqlType = "DBUY_CONTRACT_PRICE_OV";

	// added this myself
	private Date endDate;
	private String isParent;
	private BigDecimal breakQuantity;
	private Date oldStartDate;
	private BigDecimal oldBreakQuantity;
	private String changed;
	private String isGrand;
	private BigDecimal oldUnitPrice;
	private String level2Changed;
	private String level1Changed;
	private String comments;
	
	private String oldRoundToMultiple;
	
	//constructor
	public DbuyContractPriceOvBean() {
	}

	//setters
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}
	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setBuyer(BigDecimal buyer) {
		this.buyer = buyer;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public void setSourcer(BigDecimal sourcer) {
		this.sourcer = sourcer;
	}
	public void setSourcerName(String sourcerName) {
		this.sourcerName = sourcerName;
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
	public void setRefDeliveryPoint(String refDeliveryPoint) {
		this.refDeliveryPoint = refDeliveryPoint;
	}
	public void setRefMaterialRequest(String refMaterialRequest) {
		this.refMaterialRequest = refMaterialRequest;
	}
	public void setRefCustomerContactInfo(String refCustomerContactInfo) {
		this.refCustomerContactInfo = refCustomerContactInfo;
	}
	public void setAdditionalLineNotes(String additionalLineNotes) {
		this.additionalLineNotes = additionalLineNotes;
	}
	public void setPurchaserReview(String purchaserReview) {
		this.purchaserReview = purchaserReview;
	}
	public void setUpdatedBy(BigDecimal updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setDefaultShipmentOriginState(String defaultShipmentOriginState) {
		this.defaultShipmentOriginState = defaultShipmentOriginState;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setPriceComments(String priceComments) {
		this.priceComments = priceComments;
	}
	public void setPriceUpdatedBy(BigDecimal priceUpdatedBy) {
		this.priceUpdatedBy = priceUpdatedBy;
	}

	public void setPriceUpdatedByName(String priceUpdatedByName) {
		this.priceUpdatedByName = priceUpdatedByName;
	}

	public void setPriceUpdatedDate(Date priceUpdatedDate) {
		this.priceUpdatedDate = priceUpdatedDate;
	}

	public void setPriceBreakCollection(Collection coll) {
		this.priceBreakCollection = coll;
	}
	public void setPriceBreakCollectionArray(Array priceBreakCollectionArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) priceBreakCollectionArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setPriceBreakCollection(list);
	}


	//getters
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}
	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipToAddress() {
		return shipToAddress;
	}
	public BigDecimal getPriority() {
		return priority;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public BigDecimal getBuyer() {
		return buyer;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public BigDecimal getSourcer() {
		return sourcer;
	}
	public String getSourcerName() {
		return sourcerName;
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
	public String getRefDeliveryPoint() {
		return refDeliveryPoint;
	}
	public String getRefMaterialRequest() {
		return refMaterialRequest;
	}
	public String getRefCustomerContactInfo() {
		return refCustomerContactInfo;
	}
	public String getAdditionalLineNotes() {
		return additionalLineNotes;
	}
	public String getPurchaserReview() {
		return purchaserReview;
	}
	public BigDecimal getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public String getDefaultShipmentOriginState() {
		return defaultShipmentOriginState;
	}
	public Date getStartDate() {
		return startDate;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public String getPriceComments() {
		return priceComments;
	}
	public BigDecimal getPriceUpdatedBy() {
		return priceUpdatedBy;
	}

	public String getPriceUpdatedByName() {
		return priceUpdatedByName;
	}

	public Date getPriceUpdatedDate() {
		return priceUpdatedDate;
	}

	public Collection getPriceBreakCollection() {
		return this.priceBreakCollection;
	}
	public Array getPriceBreakCollectionArray() {
		return priceBreakCollectionArray;
	}
	public String getSupplierContactName() {
		return supplierContactName;
	}
	public void setSupplierContactName(String supplierContactName) {
		this.supplierContactName = supplierContactName;
	}
	public String getSQLTypeName() {
		return this.sqlType;
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

	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setOpsCompanyId(stream.readString());
			this.setOpsEntityId(stream.readString());
			this.setOperatingEntityName(stream.readString());
			this.setDbuyContractId(stream.readBigDecimal());
			this.setItemId(stream.readBigDecimal());
			this.setItemDesc(stream.readString());
			this.setPackaging(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setInventoryGroupName(stream.readString());
			this.setInventoryGroupHub(stream.readString());
			this.setShipToCompanyId(stream.readString());
			this.setShipToLocationId(stream.readString());
			this.setShipToAddress(stream.readString());
			this.setPriority(stream.readBigDecimal());
			this.setSupplier(stream.readString());
			this.setSupplierName(stream.readString());
			this.setCarrier(stream.readString());
			this.setCarrierName(stream.readString());
			this.setBuyer(stream.readBigDecimal());
			this.setBuyerName(stream.readString());
			this.setSourcer(stream.readBigDecimal());
			this.setSourcerName(stream.readString());
			this.setStatus(stream.readString());
			this.setFreightOnBoard(stream.readString());
			this.setMultipleOf(stream.readBigDecimal());
			this.setSupplierPartNo(stream.readString());
			this.setSupplierContactId(stream.readBigDecimal());
			this.setRemainingShelfLifePercent(stream.readBigDecimal());
			this.setCriticalOrderCarrier(stream.readString());
			this.setRefClientPartNo(stream.readString());
			this.setRefClientPoNo(stream.readString());
			this.setRoundToMultiple(stream.readString());
			this.setConsignment(stream.readString());
			this.setLeadTimeDays(stream.readBigDecimal());
			this.setTransitTimeInDays(stream.readBigDecimal());
			this.setContractReferenceComments(stream.readString());
			this.setLoadingComments(stream.readString());
			this.setPricingType(stream.readString());
			this.setSupplyPath(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setLoadingDate(new Date(dd.getTime()));
			this.setBuyerCompanyId(stream.readString());
			this.setRefDeliveryPoint(stream.readString());
			this.setRefMaterialRequest(stream.readString());
			this.setRefCustomerContactInfo(stream.readString());
			this.setAdditionalLineNotes(stream.readString());
			this.setPurchaserReview(stream.readString());
			this.setUpdatedBy(stream.readBigDecimal());
			 dd = stream.readDate();			if( dd != null ) this.setUpdatedDate(new Date(dd.getTime()));
			this.setUpdateByName(stream.readString()); 
			this.setDefaultShipmentOriginState(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setStartDate(new Date(dd.getTime()));
			this.setUnitPrice(stream.readBigDecimal());
			this.setCurrencyId(stream.readString());
			this.setPriceComments(stream.readString());
			this.setMinBuyQuantity(stream.readBigDecimal());
			this.setMinBuyValue(stream.readBigDecimal());
			this.setSupplierContactName(stream.readString());
			this.setDefCustomerServiceRepId(stream.readBigDecimal());
			this.setCustomerServiceRepName(stream.readString());
			this.setPriceUpdatedBy(stream.readBigDecimal());
			this.setPriceUpdatedByName(stream.readString());
			dd = stream.readDate();			if( dd != null ) this.setPriceUpdatedDate(new Date(dd.getTime()));
			this.setCriticalOrderCarrierName(stream.readString());
			this.setPriceBreakCollectionArray(stream.readArray());
			this.setBuyType(stream.readString());
			this.setBuyTypeFlag(stream.readString());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeString(this.getOpsCompanyId());
			stream.writeString(this.getOpsEntityId());
			stream.writeString(this.getOperatingEntityName());
			stream.writeBigDecimal(this.getDbuyContractId());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getItemDesc());
			stream.writeString(this.getPackaging());
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getInventoryGroupName());
			stream.writeString(this.getInventoryGroupHub());
			stream.writeString(this.getShipToCompanyId());
			stream.writeString(this.getShipToLocationId());
			stream.writeString(this.getShipToAddress());
			stream.writeBigDecimal(this.getPriority());
			stream.writeString(this.getSupplier());
			stream.writeString(this.getSupplierName());
			stream.writeString(this.getCarrier());
			stream.writeString(this.getCarrierName());
			stream.writeBigDecimal(this.getBuyer());
			stream.writeString(this.getBuyerName());
			stream.writeBigDecimal(this.getSourcer());
			stream.writeString(this.getSourcerName());
			stream.writeString(this.getStatus());
			stream.writeString(this.getFreightOnBoard());
			stream.writeBigDecimal(this.getMultipleOf());
			stream.writeString(this.getSupplierPartNo());
			stream.writeBigDecimal(this.getSupplierContactId());
			stream.writeBigDecimal(this.getRemainingShelfLifePercent());
			stream.writeString(this.getCriticalOrderCarrier());
			stream.writeString(this.getRefClientPartNo());
			stream.writeString(this.getRefClientPoNo());
			stream.writeString(this.getRoundToMultiple());
			stream.writeString(this.getConsignment());
			stream.writeBigDecimal(this.getLeadTimeDays());
			stream.writeBigDecimal(this.getTransitTimeInDays());
			stream.writeString(this.getContractReferenceComments());
			stream.writeString(this.getLoadingComments());
			stream.writeString(this.getPricingType());
			stream.writeString(this.getSupplyPath());
			stream.writeDate(new java.sql.Date(this.getLoadingDate().getTime()));
			stream.writeString(this.getBuyerCompanyId());
			stream.writeString(this.getRefDeliveryPoint());
			stream.writeString(this.getRefMaterialRequest());
			stream.writeString(this.getRefCustomerContactInfo());
			stream.writeString(this.getAdditionalLineNotes());
			stream.writeString(this.getPurchaserReview());
			stream.writeBigDecimal(this.getUpdatedBy());
			stream.writeString(this.getUpdateByName());
			stream.writeDate(new java.sql.Date(this.getUpdatedDate().getTime()));
			stream.writeString(this.getDefaultShipmentOriginState());
			stream.writeDate(new java.sql.Date(this.getStartDate().getTime()));
			stream.writeBigDecimal(this.getUnitPrice());
			stream.writeString(this.getCurrencyId());
			stream.writeString(this.getPriceComments());
			stream.writeBigDecimal(this.getMinBuyQuantity());
			stream.writeBigDecimal(this.getMinBuyValue());
			stream.writeString(this.getSupplierContactName());
			stream.writeBigDecimal(this.getDefCustomerServiceRepId());
			stream.writeString(this.getCustomerServiceRepName());
			stream.writeBigDecimal(this.getPriceUpdatedBy());
			stream.writeString(this.getPriceUpdatedByName());
			stream.writeDate(new java.sql.Date(this.getPriceUpdatedDate().getTime()));
			stream.writeString(this.getCriticalOrderCarrierName());
			stream.writeArray(this.getPriceBreakCollectionArray());
			stream.writeString(this.getBuyType());
			stream.writeString(this.getBuyTypeFlag());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public BigDecimal getBreakQuantity() {
		return breakQuantity;
	}

	public void setBreakQuantity(BigDecimal breakQuantity) {
		this.breakQuantity = breakQuantity;
	}

	public Date getOldStartDate() {
		return oldStartDate;
	}

	public void setOldStartDate(Date oldStartDate) {
		this.oldStartDate = oldStartDate;
	}

	public BigDecimal getOldBreakQuantity() {
		return oldBreakQuantity;
	}

	public void setOldBreakQuantity(BigDecimal oldBreakQuantity) {
		this.oldBreakQuantity = oldBreakQuantity;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public String getIsGrand() {
		return isGrand;
	}

	public void setIsGrand(String isGrand) {
		this.isGrand = isGrand;
	}

	public BigDecimal getOldUnitPrice() {
		return oldUnitPrice;
	}

	public void setOldUnitPrice(BigDecimal oldUnitPrice) {
		this.oldUnitPrice = oldUnitPrice;
	}

	public String getLevel2Changed() {
		return level2Changed;
	}

	public void setLevel2Changed(String level2Changed) {
		this.level2Changed = level2Changed;
	}

	public String getLevel1Changed() {
		return level1Changed;
	}

	public void setLevel1Changed(String level1Changed) {
		this.level1Changed = level1Changed;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getInventoryGroupHub() {
		return inventoryGroupHub;
	}

	public void setInventoryGroupHub(String inventoryGroupHub) {
		this.inventoryGroupHub = inventoryGroupHub;
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

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public String getCriticalOrderCarrierName() {
		return criticalOrderCarrierName;
	}

	public void setCriticalOrderCarrierName(String criticalOrderCarrierName) {
		this.criticalOrderCarrierName = criticalOrderCarrierName;
	}

	public String getOldRoundToMultiple() {
		return oldRoundToMultiple;
	}

	public void setOldRoundToMultiple(String oldRoundToMultiple) {
		this.oldRoundToMultiple = oldRoundToMultiple;
	}

	public String getBuyType() {
		return buyType;
	}
	
	public void setBuyType(String buyType) {
		this.buyType = buyType;
	}

	public String getBuyTypeFlag() {
		return buyTypeFlag;
	}

	public void setBuyTypeFlag(String buyTypeFlag) {
		this.buyTypeFlag = buyTypeFlag;
	}
	
	public boolean isBuyTypeAllowed() {
		return (!StringHandler.isBlankString(this.buyTypeFlag) && this.buyTypeFlag.toUpperCase().equals("Y"));
	}
}