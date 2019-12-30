package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FreightAdviceViewBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class FreightAdviceViewBean extends BaseDataBean {

	private BigDecimal packingGroupId;
	private String hub;
	private String inventoryGroup;
	private Date scheduledShipDate;
	private String shipVia;
	private String ultimateDodaac;
	private String ultimateShipTo;
	private String carrierName;
	private String carrierTrackingNumber;
	private String transportationMode;
	private String trailerNumber;
	private BigDecimal stopNumber;
	private String mrLine;
	private String companyId;
	private String facPartNo;
	private String quantity;
	private String status;
	private BigDecimal weight;
	private BigDecimal cube;
	private String partShortName;
	private String consolidationNumber;
	private String milstripCode;
	private BigDecimal prNumber;
	private String lineItem;
	private Date dateShipped;
	private Date shipConfirmDate;
	private String notes;


	//constructor
	public FreightAdviceViewBean() {
	}

	//setters
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setScheduledShipDate(Date scheduledShipDate) {
		this.scheduledShipDate = scheduledShipDate;
	}
	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}
	public void setUltimateDodaac(String ultimateDodaac) {
		this.ultimateDodaac = ultimateDodaac;
	}
	public void setUltimateShipTo(String ultimateShipTo) {
		this.ultimateShipTo = ultimateShipTo;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setCarrierTrackingNumber(String carrierTrackingNumber) {
		this.carrierTrackingNumber = carrierTrackingNumber;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}
	public void setStopNumber(BigDecimal stopNumber) {
		this.stopNumber = stopNumber;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public void setCube(BigDecimal cube) {
		this.cube = cube;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setShipConfirmDate(Date shipConfirmDate) {
		this.shipConfirmDate = shipConfirmDate;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}


	//getters
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getHub() {
		return hub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public Date getScheduledShipDate() {
		return scheduledShipDate;
	}
	public String getShipVia() {
		return shipVia;
	}
	public String getUltimateDodaac() {
		return ultimateDodaac;
	}
	public String getUltimateShipTo() {
		return ultimateShipTo;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getCarrierTrackingNumber() {
		return carrierTrackingNumber;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public BigDecimal getStopNumber() {
		return stopNumber;
	}
	public String getMrLine() {
		return mrLine;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getQuantity() {
		return quantity;
	}
	public String getStatus() {
		return status;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public BigDecimal getCube() {
		return cube;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getConsolidationNumber() {
		return consolidationNumber;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public Date getShipConfirmDate() {
		return shipConfirmDate;
	}
	public String getNotes() {
		return notes;
	}
}