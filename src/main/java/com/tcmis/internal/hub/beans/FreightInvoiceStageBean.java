package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


public class FreightInvoiceStageBean extends BaseDataBean {
	
	private BigDecimal freightInvoiceStageId;

	private String carrier;
	private String comments;
	private String companyId;
	private String errorDetail;
	private String glCode;
	private Date invoiceDate;
	private String invoiceLine;
	private String invoiceNumber;
	private BigDecimal loadId;
	private BigDecimal loadLine;
	private String orderNumber;
	private String orderType;
	private String status;
	private String trackingNumber;
	private String transportationMode;
	private String currencyId;
	
	// input parameters
	private boolean ok;

	//constructor
	public FreightInvoiceStageBean() {
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getErrorDetail() {
		return errorDetail;
	}

	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	public String getGlCode() {
		return glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getInvoiceLine() {
		return invoiceLine;
	}

	public void setInvoiceLine(String invoiceLine) {
		this.invoiceLine = invoiceLine;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getLoadId() {
		return loadId;
	}

	public void setLoadId(BigDecimal loadId) {
		this.loadId = loadId;
	}

	public BigDecimal getLoadLine() {
		return loadLine;
	}

	public void setLoadLine(BigDecimal loadLine) {
		this.loadLine = loadLine;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTransportationMode() {
		return transportationMode;
	}

	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}

	public boolean getOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getFreightInvoiceStageId() {
		return freightInvoiceStageId;
	}

	public void setFreightInvoiceStageId(BigDecimal freightInvoiceStageId) {
		this.freightInvoiceStageId = freightInvoiceStageId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
}