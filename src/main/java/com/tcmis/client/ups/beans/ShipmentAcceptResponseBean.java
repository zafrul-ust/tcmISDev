package com.tcmis.client.ups.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;


/******************************************************************************
 * CLASSNAME: ShipmentAcceptResponseBean <br>
 * @version: 1.0, Jan 18, 2008 <br>
 *****************************************************************************/


public class ShipmentAcceptResponseBean extends BaseDataBean {

	private String transactionReference;
    private String xpciVersion;
    private String responseStatus;
    private String responseStatusDescription;
    private BigDecimal transportationCharges;
    private BigDecimal serviceOptionsCharges;
    private BigDecimal totalCharges;
    private BigDecimal billingWeight;
    private String shipmentIdentificationNumber;
    private String pickupRequestNumber;
    private String highValueReportGraphicImage;

    private Collection<PackageResultsBean> packageResultsBeanCollection = new Vector();

    //constructor
	public ShipmentAcceptResponseBean() {
	}

	//setters
	public void setTransactionReference(String s) {
		this.transactionReference = s;
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

	public void setPickupRequestNumber(String s) {
		this.pickupRequestNumber = s;
	}

	public void setPackageResultsBeanCollection(Collection c) {
		this.packageResultsBeanCollection = c;
	}

	public void addPackageResultsBean(PackageResultsBean bean) {
		this.packageResultsBeanCollection.add(bean);
	}

    public void setHighValueReportGraphicImage(String s) {
		this.highValueReportGraphicImage = s;
	}
    //getters
	public String getTransactionReference() {
		return this.transactionReference;
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

	public String getPickupRequestNumber() {
		return this.pickupRequestNumber;
	}

    public String getHighValueReportGraphicImage() {
		return this.highValueReportGraphicImage;
	}

    public Collection<PackageResultsBean> getPackageResultsBeanCollection() {
		return this.packageResultsBeanCollection;
	}
}
