package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PwcOrderNotifyErrorViewBean <br>
 * @version: 1.0, Apr 21, 2009 <br>
 *****************************************************************************/


public class PwcOrderNotifyErrorViewBean extends BaseDataBean {

	private static final long serialVersionUID = -5043075173720727515L;
	
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
	private BigDecimal changeOrderSequence;
	private Date dateToDeliver;
	private Date requestedDeliveryDate;
	private String facPartNo;
	private String itemDesc;
	private BigDecimal allocationBatch;
	private String status;
	private String statusDetail;
	private String ok;
	private String inventoryGroup;


	//constructor
	public PwcOrderNotifyErrorViewBean() {
	}

	//setters
	public void setAcknowledgementCode(String acknowledgementCode) {
		this.acknowledgementCode = acknowledgementCode;
	}
	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
	public void setCustomerPoLineNo(String customerPoLineNo) {
		this.customerPoLineNo = customerPoLineNo;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setDateInserted(Date dateInserted) {
		this.dateInserted = dateInserted;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setAllocationStatus(String allocationStatus) {
		this.allocationStatus = allocationStatus;
	}
	public void setDocumentControlNumber(String documentControlNumber) {
		this.documentControlNumber = documentControlNumber;
	}
	public void setOrderQuantity(BigDecimal orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public void setAllocatedQuantity(BigDecimal allocatedQuantity) {
		this.allocatedQuantity = allocatedQuantity;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
		this.changeOrderSequence = changeOrderSequence;
	}
	public void setDateToDeliver(Date dateToDeliver) {
		this.dateToDeliver = dateToDeliver;
	}
	public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
		this.requestedDeliveryDate = requestedDeliveryDate;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setAllocationBatch(BigDecimal allocationBatch) {
		this.allocationBatch = allocationBatch;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusDetail(String statusDetail) {
		this.statusDetail = statusDetail;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	//getters
	public String getAcknowledgementCode() {
		return acknowledgementCode;
	}
	public String getCustomerPoNo() {
		return customerPoNo;
	}
	public String getCustomerPoLineNo() {
		return customerPoLineNo;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public Date getDateInserted() {
		return dateInserted;
	}
	public String getDocType() {
		return docType;
	}
	public BigDecimal getDocNum() {
		return docNum;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getAllocationStatus() {
		return allocationStatus;
	}
	public String getDocumentControlNumber() {
		return documentControlNumber;
	}
	public BigDecimal getOrderQuantity() {
		return orderQuantity;
	}
	public BigDecimal getAllocatedQuantity() {
		return allocatedQuantity;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public BigDecimal getChangeOrderSequence() {
		return changeOrderSequence;
	}
	public Date getDateToDeliver() {
		return dateToDeliver;
	}
	public Date getRequestedDeliveryDate() {
		return requestedDeliveryDate;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getAllocationBatch() {
		return allocationBatch;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusDetail() {
		return statusDetail;
	}

	/**
	 * @param ok the ok to set
	 */
	public void setOk(String ok) {
		this.ok = ok;
	}

	/**
	 * @return the ok
	 */
	public String getOk() {
		return ok;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
}