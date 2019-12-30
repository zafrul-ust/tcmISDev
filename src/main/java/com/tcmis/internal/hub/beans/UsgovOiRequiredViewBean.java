package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UsgovOiRequiredViewBean <br>
 * @version: 1.0, Nov 18, 2009 <br>
 *****************************************************************************/


public class UsgovOiRequiredViewBean extends BaseDataBean {

	private static final long serialVersionUID = 613061944608997590L;
	private String carrierCode;
	private String trackingNumber;
	private String usgovTcn;
	private BigDecimal prNumber;
	private String lineItem;
	private String transactionRefNum;
	private String companyId;
	private String originInspectionRequired;
	private String shipFromLocationId;
	private String boxId;
	private BigDecimal packingGroupId;


	//constructor
	public UsgovOiRequiredViewBean() {
	}

	//setters
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setUsgovTcn(String usgovTcn) {
		this.usgovTcn = usgovTcn;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setTransactionRefNum(String transactionRefNum) {
		this.transactionRefNum = transactionRefNum;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}


	//getters
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getUsgovTcn() {
		return usgovTcn;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getTransactionRefNum() {
		return transactionRefNum;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getBoxId() {
		return boxId;
	}

	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
}