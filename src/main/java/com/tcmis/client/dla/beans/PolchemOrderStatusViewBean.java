package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FreightAdviceViewBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class PolchemOrderStatusViewBean extends BaseDataBean {
	
	private String companyId;
	private String poNumber;
	private String currentStatus;
	private Date currentStatusDate;
	private BigDecimal prNumber;
	private String lineItem;
	private String facPartNo;
	private BigDecimal quanty;
	private String ownerCompanyId;
	private Date orderReceivedDate;
	private String orderStatus;
	private String inventoryGroup;
	private Date orderCreatedDate;
	private Date allocatedDate;
	private Date backorderedDate;
	private Date releasedDate;
	private Date faSentDate;
	private Date faReceivedDate;
	private Date picklistPrintDate;
	private Date packedDate;
	private Date shippedDate;
	private Date scSentDate;
		
	//constructor
	public PolchemOrderStatusViewBean() {
	}

	public Date getAllocatedDate() {
		return allocatedDate;
	}

	public void setAllocatedDate(Date allocatedDate) {
		this.allocatedDate = allocatedDate;
	}

	public Date getBackorderedDate() {
		return backorderedDate;
	}

	public void setBackorderedDate(Date backorderedDate) {
		this.backorderedDate = backorderedDate;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getCurrentStatusDate() {
		return currentStatusDate;
	}

	public void setCurrentStatusDate(Date currentStatusDate) {
		this.currentStatusDate = currentStatusDate;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public Date getFaReceivedDate() {
		return faReceivedDate;
	}

	public void setFaReceivedDate(Date faReceivedDate) {
		this.faReceivedDate = faReceivedDate;
	}

	public Date getFaSentDate() {
		return faSentDate;
	}

	public void setFaSentDate(Date faSentDate) {
		this.faSentDate = faSentDate;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public Date getOrderCreatedDate() {
		return orderCreatedDate;
	}

	public void setOrderCreatedDate(Date orderCreatedDate) {
		this.orderCreatedDate = orderCreatedDate;
	}

	public Date getOrderReceivedDate() {
		return orderReceivedDate;
	}

	public void setOrderReceivedDate(Date orderReceivedDate) {
		this.orderReceivedDate = orderReceivedDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOwnerCompanyId() {
		return ownerCompanyId;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		this.ownerCompanyId = ownerCompanyId;
	}

	public Date getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(Date packedDate) {
		this.packedDate = packedDate;
	}

	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}

	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public BigDecimal getQuanty() {
		return quanty;
	}

	public void setQuanty(BigDecimal quanty) {
		this.quanty = quanty;
	}

	public Date getReleasedDate() {
		return releasedDate;
	}

	public void setReleasedDate(Date releasedDate) {
		this.releasedDate = releasedDate;
	}

	public Date getScSentDate() {
		return scSentDate;
	}

	public void setScSentDate(Date scSentDate) {
		this.scSentDate = scSentDate;
	}

	
}