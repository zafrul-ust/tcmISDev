package com.tcmis.client.usgov.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DlaGasTrackingDetailViewBean <br>
 * @version: 1.0, May 13, 2008 <br>
 *****************************************************************************/


public class DlaGasTrackingDetailViewBean extends BaseDataBean {

	private String transactionType;
	private String exportFlag;
	private String customerPoNo;
	private String customerPoLineNo;
	private BigDecimal mr;
	private String mrLine;
	private String catPartNo;
	private BigDecimal quantity;
	private String uom;
	private BigDecimal unitPrice;
	private BigDecimal extendedPrice;
	private Date desiredShipDate;
	private Date desiredDeliveryDate;
	private Date actualShipDate;
	private BigDecimal daysLate;
	private Date dlaOrderIssued;
	private Date dlaOrderReceived;
	private Date dlaOrderInStage;
	private Date internalDpmsRequest;
	private Date internalNolRequest;
	private Date mrCreation;
	private Date buyOrderCreation;
	private Date haasOrderIssued;
	private Date airgas997Issued;
	private Date airgas997Received;
	private Date airgas855Issued;
	private Date airgas855Received;
	private Date poConfirmDate;
	private Date airgas856Issued;
	private Date airgas856Received;
	private Date dla856Created;
	private Date dla856ToEllis;
	private Date dla856ToWawf;
	private Date dla945Created;
	private Date dla945Sent;


	//constructor
	public DlaGasTrackingDetailViewBean() {
	}

	//setters
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLineNo(String customerPoLineNo) {
		this.customerPoLineNo = customerPoLineNo;
	}
	public void setMr(BigDecimal mr) {
		this.mr = mr;
	}
	public void setMrLine(String mrLine) {
		this.mrLine = mrLine;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setExtendedPrice(BigDecimal extendedPrice) {
		this.extendedPrice = extendedPrice;
	}
	public void setDesiredShipDate(Date desiredShipDate) {
		this.desiredShipDate = desiredShipDate;
	}
	public void setDesiredDeliveryDate(Date desiredDeliveryDate) {
		this.desiredDeliveryDate = desiredDeliveryDate;
	}
	public void setActualShipDate(Date actualShipDate) {
		this.actualShipDate = actualShipDate;
	}
	public void setDaysLate(BigDecimal daysLate) {
		this.daysLate = daysLate;
	}
	public void setDlaOrderIssued(Date dlaOrderIssued) {
		this.dlaOrderIssued = dlaOrderIssued;
	}
	public void setDlaOrderReceived(Date dlaOrderReceived) {
		this.dlaOrderReceived = dlaOrderReceived;
	}
	public void setDlaOrderInStage(Date dlaOrderInStage) {
		this.dlaOrderInStage = dlaOrderInStage;
	}
	public void setInternalDpmsRequest(Date internalDpmsRequest) {
		this.internalDpmsRequest = internalDpmsRequest;
	}
	public void setInternalNolRequest(Date internalNolRequest) {
		this.internalNolRequest = internalNolRequest;
	}
	public void setMrCreation(Date mrCreation) {
		this.mrCreation = mrCreation;
	}
	public void setBuyOrderCreation(Date buyOrderCreation) {
		this.buyOrderCreation = buyOrderCreation;
	}
	public void setHaasOrderIssued(Date haasOrderIssued) {
		this.haasOrderIssued = haasOrderIssued;
	}
	public void setAirgas997Issued(Date airgas997Issued) {
		this.airgas997Issued = airgas997Issued;
	}
	public void setAirgas997Received(Date airgas997Received) {
		this.airgas997Received = airgas997Received;
	}
	public void setAirgas855Issued(Date airgas855Issued) {
		this.airgas855Issued = airgas855Issued;
	}
	public void setAirgas855Received(Date airgas855Received) {
		this.airgas855Received = airgas855Received;
	}
	public void setPoConfirmDate(Date poConfirmDate) {
		this.poConfirmDate = poConfirmDate;
	}
	public void setAirgas856Issued(Date airgas856Issued) {
		this.airgas856Issued = airgas856Issued;
	}
	public void setAirgas856Received(Date airgas856Received) {
		this.airgas856Received = airgas856Received;
	}
	public void setDla856Created(Date dla856Created) {
		this.dla856Created = dla856Created;
	}
	public void setDla856ToEllis(Date dla856ToEllis) {
		this.dla856ToEllis = dla856ToEllis;
	}
	public void setDla856ToWawf(Date dla856ToWawf) {
		this.dla856ToWawf = dla856ToWawf;
	}
	public void setDla945Created(Date dla945Created) {
		this.dla945Created = dla945Created;
	}
	public void setDla945Sent(Date dla945Sent) {
		this.dla945Sent = dla945Sent;
	}


	//getters
	public String getTransactionType() {
		return transactionType;
	}
	public String getExportFlag() {
		return exportFlag;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLineNo() {
		return customerPoLineNo;
	}
	public BigDecimal getMr() {
		return mr;
	}
	public String getMrLine() {
		return mrLine;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getUom() {
		return uom;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public BigDecimal getExtendedPrice() {
		return extendedPrice;
	}
	public Date getDesiredShipDate() {
		return desiredShipDate;
	}
	public Date getDesiredDeliveryDate() {
		return desiredDeliveryDate;
	}
	public Date getActualShipDate() {
		return actualShipDate;
	}
	public BigDecimal getDaysLate() {
		return daysLate;
	}
	public Date getDlaOrderIssued() {
		return dlaOrderIssued;
	}
	public Date getDlaOrderReceived() {
		return dlaOrderReceived;
	}
	public Date getDlaOrderInStage() {
		return dlaOrderInStage;
	}
	public Date getInternalDpmsRequest() {
		return internalDpmsRequest;
	}
	public Date getInternalNolRequest() {
		return internalNolRequest;
	}
	public Date getMrCreation() {
		return mrCreation;
	}
	public Date getBuyOrderCreation() {
		return buyOrderCreation;
	}
	public Date getHaasOrderIssued() {
		return haasOrderIssued;
	}
	public Date getAirgas997Issued() {
		return airgas997Issued;
	}
	public Date getAirgas997Received() {
		return airgas997Received;
	}
	public Date getAirgas855Issued() {
		return airgas855Issued;
	}
	public Date getAirgas855Received() {
		return airgas855Received;
	}
	public Date getPoConfirmDate() {
		return poConfirmDate;
	}
	public Date getAirgas856Issued() {
		return airgas856Issued;
	}
	public Date getAirgas856Received() {
		return airgas856Received;
	}
	public Date getDla856Created() {
		return dla856Created;
	}
	public Date getDla856ToEllis() {
		return dla856ToEllis;
	}
	public Date getDla856ToWawf() {
		return dla856ToWawf;
	}
	public Date getDla945Created() {
		return dla945Created;
	}
	public Date getDla945Sent() {
		return dla945Sent;
	}
}