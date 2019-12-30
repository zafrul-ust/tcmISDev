package com.tcmis.client.openCustomer.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class OrderMsdsBean extends BaseDataBean {
	private String customerName;
	private String facPartNo;
	private BigDecimal itemId;
	private String lineItem;
	private String partDescription;
	private BigDecimal prNumber;

	public String getCustomerName() {
		return customerName;
	}

	public String getFacPartNo() {
		return facPartNo;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
}
