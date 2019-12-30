package com.tcmis.client.kilfrost.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: KilfrostDeliveryConfirmationBean <br>
 * @version: 1.0, Sep 25, 2008 <br>
 *****************************************************************************/


public class KilfrostDeliveryConfirmationBean extends BaseDataBean {

	private String companyId;
	private String kilfrostOrderNumber;
	private String carrier;
	private String trackingNumber;
	private Date dateShipped;
	private BigDecimal quantityShipped;
	private String batchNumber;
	private BigDecimal transactionNumber;
	private BigDecimal freightCost;
	private String freightCostCurrencyId;
	private String orderStatus;


	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	//constructor
	public KilfrostDeliveryConfirmationBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setKilfrostOrderNumber(String kilfrostOrderNumber) {
		this.kilfrostOrderNumber = kilfrostOrderNumber;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setDateShipped(Date dateShipped) {
		this.dateShipped = dateShipped;
	}
	public void setQuantityShipped(BigDecimal quantityShipped) {
		this.quantityShipped = quantityShipped;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public void setTransactionNumber(BigDecimal transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public void setFreightCost(BigDecimal freightCost) {
		this.freightCost = freightCost;
	}
	public void setFreightCostCurrencyId(String freightCostCurrencyId) {
		this.freightCostCurrencyId = freightCostCurrencyId;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getKilfrostOrderNumber() {
		return kilfrostOrderNumber;
	}
	public String getCarrier() {
		return carrier;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public Date getDateShipped() {
		return dateShipped;
	}
	public BigDecimal getQuantityShipped() {
		return quantityShipped;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public BigDecimal getTransactionNumber() {
		return transactionNumber;
	}
	public BigDecimal getFreightCost() {
		return freightCost;
	}
	public String getFreightCostCurrencyId() {
		return freightCostCurrencyId;
	}
}