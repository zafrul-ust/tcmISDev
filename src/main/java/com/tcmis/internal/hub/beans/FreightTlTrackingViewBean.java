package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FreightTlTrackingViewBean <br>
 * @version: 1.0, Nov 4, 2009 <br>
 *****************************************************************************/


public class FreightTlTrackingViewBean extends BaseDataBean {

	private String consolidationNumber;
	private String carrierName;
	private String carrierCode;
	private String trackingNumber;
	private Date scheduledShipDate;
	private String shipFrom;
	private String shipTo;
	private String transportationMode;
	private String hub;
	private String inventoryGroup;
	private String unitOfIssue;
	private BigDecimal itemWeight;
	private BigDecimal itemsPerBox;
	private BigDecimal itemsPerPallet;
	private BigDecimal unitPalletWeight;
	private BigDecimal shipmentPalletWeigth;
	private BigDecimal shippingWeight;
	private String weightUnit;
	private BigDecimal palletCount;
	private BigDecimal shippingUnits;
	private String hazardousFlag;
	private String transportationPriority;
	private String export;
	private BigDecimal quantity;
	private String orderCount;
	private String ok;
	private String hubName;
	private String originalConsolidationNumber;
	private String originalShippingWeight;
	private String splitLine;
	private BigDecimal originalQuantity;
	private BigDecimal originalPalletCount;
	private String iataIdentification;
	private Date requiredDatetime;

	//constructor
	public FreightTlTrackingViewBean() {
	}

	//setters
	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setScheduledShipDate(Date scheduledShipDate) {
		this.scheduledShipDate = scheduledShipDate;
	}
	public void setShipFrom(String shipFrom) {
		this.shipFrom = shipFrom;
	}
	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setShippingWeight(BigDecimal shippingWeight) {
		this.shippingWeight = shippingWeight;
	}
	public void setPalletCount(BigDecimal palletCount) {
		this.palletCount = palletCount;
	}
	public void setShippingUnits(BigDecimal shippingUnits) {
		this.shippingUnits = shippingUnits;
	}
	public void setHazardousFlag(String hazardousFlag) {
		this.hazardousFlag = hazardousFlag;
	}
	public void setTransportationPriority(String transportationPriority) {
		this.transportationPriority = transportationPriority;
	}
	public void setExport(String export) {
		this.export = export;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setOriginalConsolidationNumber(String originalConsolidationNumber) {
		this.originalConsolidationNumber = originalConsolidationNumber;
	}

	public void setUnitOfIssue(String unitOfIssue) {
		this.unitOfIssue = unitOfIssue;
	}

	public void setItemsPerBox(BigDecimal itemsPerBox) {
		this.itemsPerBox = itemsPerBox;
	}

	public void setItemsPerPallet(BigDecimal itemsPerPallet) {
		this.itemsPerPallet = itemsPerPallet;
	}

	public void setUnitPalletWeight(BigDecimal unitPalletWeight) {
		this.unitPalletWeight = unitPalletWeight;
	}

	public void setShipmentPalletWeigth(BigDecimal shipmentPalletWeigth) {
		this.shipmentPalletWeigth = shipmentPalletWeigth;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public void setItemWeight(BigDecimal itemWeight) {
		this.itemWeight = itemWeight;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setOriginalShippingWeight(String originalShippingWeight) {
		this.originalShippingWeight = originalShippingWeight;
	}

	public void setSplitLine(String splitLine) {
		this.splitLine = splitLine;
	}

	public void setOriginalQuantity(BigDecimal originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public void setOriginalPalletCount(BigDecimal originalPalletCount) {
		this.originalPalletCount = originalPalletCount;
	}

	public void setIataIdentification(String iataIdentification) {
		this.iataIdentification = iataIdentification;
	}

	// /getters
	public String getConsolidationNumber() {
		return consolidationNumber;
	}
	public String getCarrierName() {
		return carrierName;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}
	public Date getScheduledShipDate() {
		return scheduledShipDate;
	}
	public String getShipFrom() {
		return shipFrom;
	}
	public String getShipTo() {
		return shipTo;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public BigDecimal getShippingWeight() {
		return shippingWeight;
	}
	public BigDecimal getPalletCount() {
		return palletCount;
	}
	public BigDecimal getShippingUnits() {
		return shippingUnits;
	}
	public String getHazardousFlag() {
		return hazardousFlag;
	}
	public String getTransportationPriority() {
		return transportationPriority;
	}
	public String getExport() {
		return export;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getOrderCount() {
		return orderCount;
	}

	public String getOk() {
		return ok;
	}

	public String getHub() {
		return hub;
	}

	public String getOriginalConsolidationNumber() {
		return originalConsolidationNumber;
	}

	public String getUnitOfIssue() {
		return unitOfIssue;
	}

	public BigDecimal getItemsPerBox() {
		return itemsPerBox;
	}

	public BigDecimal getItemsPerPallet() {
		return itemsPerPallet;
	}

	public BigDecimal getUnitPalletWeight() {
		return unitPalletWeight;
	}

	public BigDecimal getShipmentPalletWeigth() {
		return shipmentPalletWeigth;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public BigDecimal getItemWeight() {
		return itemWeight;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public String getHubName() {
		return hubName;
	}

	public String getOriginalShippingWeight() {
		return originalShippingWeight;
	}

	public String getSplitLine() {
		return splitLine;
	}

	public BigDecimal getOriginalQuantity() {
		return originalQuantity;
	}

	public BigDecimal getOriginalPalletCount() {
		return originalPalletCount;
	}

	public String getIataIdentification() {
		return iataIdentification;
	}

	public Date getRequiredDatetime() {
		return requiredDatetime;
	}

	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}
}