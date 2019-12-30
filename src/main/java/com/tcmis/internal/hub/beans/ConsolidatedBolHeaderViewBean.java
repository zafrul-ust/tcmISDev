package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ConsolidatedBolHeaderViewBean <br>
 * @version: 1.0, Mar 9, 2006 <br>
 *****************************************************************************/


public class ConsolidatedBolHeaderViewBean extends BaseDataBean {

	private BigDecimal shipmentId;
	private String carrierName;
	private String trackingNumber;
	private Date dateDelivered;
	private String fromAddress;
	private String toAddress;

	//constructor
	public ConsolidatedBolHeaderViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}


	//getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
}