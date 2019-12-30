package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

public class PwcOrderLookupViewBean {

	private String ok;
	private String acknowledgementCode;
	private String customerPoNo;
	private String customerPoLineNo;
	private BigDecimal prNumber;
	private String lineItem;
	private Date dateInserted;
	private String docType;
	private BigDecimal docNum;
	private BigDecimal itemId;
	private String allocationStatus;
	private String documentControlNumber;
	private BigDecimal orderQuantity;
	private BigDecimal allocatedQuantity;
	private String unitOfSale;
	private BigDecimal catalogPrice;
	private BigDecimal orderPrice;
	private String changeOrderSequence;
	private Date dateToDeliver;
	private Date requestedDeliveryDate;
	private String facPartNo;
	private String itemDesc;
	private BigDecimal allocationBatch;
	private String status;
	private String statusDetail;
	private String radianPo;
	private BigDecimal poPrice;
	private String poCurrency;
	private Date datePoConfirmed;
	private BigDecimal markupPercent;
	private BigDecimal currentMarkupPrice;
	private BigDecimal exchangeRate;
	private BigDecimal acknowledgedPrice;
	private Date acknowledgedDate;
	private BigDecimal ackPriceExchangeRate;
	private String ackSent;
	private String inventoryGroup;
	private BigDecimal orderPriceToday;
	
	public PwcOrderLookupViewBean() {
		
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public String getAcknowledgementCode() {
		return acknowledgementCode;
	}

	public void setAcknowledgementCode(String acknowledgementCode) {
		this.acknowledgementCode = acknowledgementCode;
	}

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}

	public String getCustomerPoLineNo() {
		return customerPoLineNo;
	}

	public void setCustomerPoLineNo(String customerPoLineNo) {
		this.customerPoLineNo = customerPoLineNo;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public Date getDateInserted() {
		return dateInserted;
	}

	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getAllocationStatus() {
		return allocationStatus;
	}

	public void setAllocationStatus(String allocationStatus) {
		this.allocationStatus = allocationStatus;
	}

	public String getDocumentControlNumber() {
		return documentControlNumber;
	}

	public void setDocumentControlNumber(String documentControlNumber) {
		this.documentControlNumber = documentControlNumber;
	}

	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public BigDecimal getAllocatedQuantity() {
		return allocatedQuantity;
	}

	public void setAllocatedQuantity(BigDecimal allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}

	public String getUnitOfSale() {
		return unitOfSale;
	}

	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}

	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}

	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getChangeOrderSequence() {
		return changeOrderSequence;
	}

	public void setChangeOrderSequence(String changeOrderSequence) {
		this.changeOrderSequence = changeOrderSequence;
	}

	public Date getDateToDeliver() {
		return dateToDeliver;
	}

	public void setDateToDeliver(Date dateToDeliver) {
		this.dateToDeliver = dateToDeliver;
	}

	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}

	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public BigDecimal getAllocationBatch() {
		return allocationBatch;
	}

	public void setAllocationBatch(BigDecimal allocationBatch) {
		this.allocationBatch = allocationBatch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDetail() {
		return statusDetail;
	}

	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}

	public String getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(String radianPo) {
		this.radianPo = radianPo;
	}

	public BigDecimal getPoPrice() {
		return poPrice;
	}

	public void setPoPrice(BigDecimal poPrice) {
		this.poPrice = poPrice;
	}

	public String getPoCurrency() {
		return poCurrency;
	}

	public void setPoCurrency(String poCurrency) {
		this.poCurrency = poCurrency;
	}

	public Date getDatePoConfirmed() {
		return datePoConfirmed;
	}

	public void setDatePoConfirmed(Date datePoConfirmed) {
		this.datePoConfirmed = datePoConfirmed;
	}

	public BigDecimal getMarkupPercent() {
		return markupPercent;
	}

	public void setMarkupPercent(BigDecimal markupPercent) {
		this.markupPercent = markupPercent;
	}

	public BigDecimal getCurrentMarkupPrice() {
		return currentMarkupPrice;
	}

	public void setCurrentMarkupPrice(BigDecimal currentMarkupPrice) {
		this.currentMarkupPrice = currentMarkupPrice;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getAcknowledgedPrice() {
		return acknowledgedPrice;
	}

	public void setAcknowledgedPrice(BigDecimal acknowledgedPrice) {
		this.acknowledgedPrice = acknowledgedPrice;
	}

	public Date getAcknowledgedDate() {
		return acknowledgedDate;
	}

	public void setAcknowledgedDate(Date acknowledgedDate) {
		this.acknowledgedDate = acknowledgedDate;
	}

	public BigDecimal getAckPriceExchangeRate() {
		return ackPriceExchangeRate;
	}

	public void setAckPriceExchangeRate(BigDecimal ackPriceExchangeRate) {
		this.ackPriceExchangeRate = ackPriceExchangeRate;
	}

	public String getAckSent() {
		return ackSent;
	}

	public void setAckSent(String ackSent) {
		this.ackSent = ackSent;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getOrderPriceToday() {
		return orderPriceToday;
	}

	public void setOrderPriceToday(BigDecimal orderPriceToday) {
		this.orderPriceToday = orderPriceToday;
	}
}
