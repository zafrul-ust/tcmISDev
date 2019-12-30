package com.tcmis.internal.hub.beans;

import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HubTrackingNumbersViewBean <br>
 * @version: 1.0, Oct 25, 2009 <br>
 *****************************************************************************/


public class HubTrackingNumbersViewBean extends BaseDataBean{

	private String hub;
	private String hubName;
	private String carrierName;
	private BigDecimal batchNumber;
	private BigDecimal firstTrackingNumber;
	private BigDecimal lastTrackingNumber;
	private BigDecimal currTrackingNumber;
	private BigDecimal triggeringNumber;
	private String status;
	private String ok;
	private String carrierAccountId;
	private String trackingNumberPrefix;
	private String checkdigitFunction;
	
	// search parameters
	private boolean includeExpired;
	private boolean lowBatchesOnly;

	//constructor
	public HubTrackingNumbersViewBean() {
	}

	//setters

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public void setBatchNumber(BigDecimal batchNumber) {
		this.batchNumber = batchNumber;
	}

	public void setFirstTrackingNumber(BigDecimal firstTrackingNumber) {
		this.firstTrackingNumber = firstTrackingNumber;
	}

	public void setLastTrackingNumber(BigDecimal lastTrackingNumber) {
		this.lastTrackingNumber = lastTrackingNumber;
	}

	public void setCurrTrackingNumber(BigDecimal currTrackingNumber) {
		this.currTrackingNumber = currTrackingNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}
	public void setCarrierAccountId(String carrierAccountId) {
		this.carrierAccountId = carrierAccountId;
	}

	//getters
	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public BigDecimal getBatchNumber() {
		return batchNumber;
	}

	public BigDecimal getFirstTrackingNumber() {
		return firstTrackingNumber;
	}

	public BigDecimal getLastTrackingNumber() {
		return lastTrackingNumber;
	}

	public BigDecimal getCurrTrackingNumber() {
		return currTrackingNumber;
	}

	public String getStatus() {
		return status;
	}

	public String getOk() {
		return ok;
	}

	public String getCarrierAccountId() {
		return carrierAccountId;
	}

	public boolean isIncludeExpired() {
		return includeExpired;
	}

	public void setIncludeExpired(boolean includeExpired) {
		this.includeExpired = includeExpired;
	}

	public BigDecimal getTriggeringNumber() {
		return triggeringNumber;
	}

	public void setTriggeringNumber(BigDecimal triggeringNumber) {
		this.triggeringNumber = triggeringNumber;
	}

	public boolean isLowBatchesOnly() {
		return lowBatchesOnly;
	}

	public void setLowBatchesOnly(boolean lowBatchesOnly) {
		this.lowBatchesOnly = lowBatchesOnly;
	}

	public String getTrackingNumberPrefix() {
		return trackingNumberPrefix;
	}

	public void setTrackingNumberPrefix(String trackingNumberPrefix) {
		this.trackingNumberPrefix = trackingNumberPrefix;
	}

	public String getCheckdigitFunction() {
		return checkdigitFunction;
	}

	public void setCheckdigitFunction(String checkdigitFunction) {
		this.checkdigitFunction = checkdigitFunction;
	}
}