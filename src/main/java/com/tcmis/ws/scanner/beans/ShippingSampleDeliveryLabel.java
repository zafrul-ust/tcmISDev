package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class ShippingSampleDeliveryLabel extends BaseDataBean {
	private String barcode;
	private Date datePrinted;
	private String deliveryPointDesc;
	private String facilityName;
	private String hub;
	private String hubName;
	private BigDecimal pickingUnitId;
	private BigDecimal quantity;
	private BigDecimal receiptId;
	private String shipToLocationId;
	
	// constructor
	public ShippingSampleDeliveryLabel() {
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Date getDatePrinted() {
		return datePrinted;
	}

	public void setDatePrinted(Date datePrinted) {
		this.datePrinted = datePrinted;
	}

	public String getDeliveryPointDesc() {
		return deliveryPointDesc;
	}

	public void setDeliveryPointDesc(String deliveryPointDesc) {
		this.deliveryPointDesc = deliveryPointDesc;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
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

	public BigDecimal getPickingUnitId() {
		return pickingUnitId;
	}

	public void setPickingUnitId(BigDecimal pickingUnitId) {
		this.pickingUnitId = pickingUnitId;
	}

	/**
	 * @return the shipToLocationId
	 */
	public String getShipToLocationId() {
		return shipToLocationId;
	}

	/**
	 * @param shipToLocationId the shipToLocationId to set
	 */
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
}