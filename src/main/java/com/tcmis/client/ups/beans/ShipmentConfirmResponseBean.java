package com.tcmis.client.ups.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jan 21, 2008
 * Time: 2:58:10 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ShipmentConfirmResponseBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ShipmentConfirmResponseBean extends BaseDataBean {

	private String internalKey;
    private String xpciVersion;
    private String responseStatus;
    private String responseStatusDescription;
    private BigDecimal transportationCharges;
    private BigDecimal serviceOptionsCharges;
    private BigDecimal totalCharges;
    private BigDecimal billingWeight;
    private String shipmentIdentificationNumber;
    private String shipmentDigest;
    private String errorCode;
    private String errorDescription;

    //constructor
	public ShipmentConfirmResponseBean() {
	}

	//setters
	public void setInternalKey(String s) {
		this.internalKey = s;
	}

	public void setXpciVersion(String s) {
		this.xpciVersion = s;
	}

	public void setResponseStatus(String s) {
		this.responseStatus = s;
	}

	public void setResponseStatusDescription(String s) {
		this.responseStatusDescription = s;
	}

	public void setTransportationCharges(BigDecimal b) {
		this.transportationCharges = b;
	}

	public void setServiceOptionsCharges(BigDecimal b) {
		this.serviceOptionsCharges = b;
	}

	public void setTotalCharges(BigDecimal b) {
		this.totalCharges = b;
	}

	public void setBillingWeight(BigDecimal b) {
		this.billingWeight = b;
	}

    public void setShipmentIdentificationNumber(String s) {
		this.shipmentIdentificationNumber = s;
	}

	public void setShipmentDigest(String s) {
		this.shipmentDigest = s;
	}

    public void setErrorCode(String s) {
		this.errorCode = s;
	}

    public void setErrorDescription(String s) {
		this.errorDescription = s;
	}
    //getters
	public String getInternalKey() {
		return this.internalKey;
	}

	public String getXpciVersion() {
		return this.xpciVersion;
	}

	public String getResponseStatus() {
		return this.responseStatus;
	}

	public String getResponseStatusDescription() {
		return this.responseStatusDescription;
	}

    public BigDecimal getTransportationCharges() {
        return this.transportationCharges;
    }

    public BigDecimal getServiceOptionsCharges() {
        return this.serviceOptionsCharges;
    }

    public BigDecimal getTotalCharges() {
        return this.totalCharges;
    }

    public BigDecimal getBillingWeight() {
        return this.billingWeight;
    }

    public String getShipmentIdentificationNumber() {
		return this.shipmentIdentificationNumber;
	}

	public String getShipmentDigest() {
		return this.shipmentDigest;
	}

    public String getErrorCode() {
		return this.errorCode;
	}

    public String getErrorDescription() {
		return this.errorDescription;
	}
}