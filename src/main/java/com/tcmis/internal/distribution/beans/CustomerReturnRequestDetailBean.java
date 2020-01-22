package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class CustomerReturnRequestDetailBean extends BaseDataBean {
	private static final long serialVersionUID = 818526188498368832L;
	
	private BigDecimal customerRmaId;
	private BigDecimal issueId;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private BigDecimal rmaLine;
	private BigDecimal quantity;
	
	public CustomerReturnRequestDetailBean() {}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public BigDecimal getRmaLine() {
		return rmaLine;
	}

	public void setRmaLine(BigDecimal rmaLine) {
		this.rmaLine = rmaLine;
	}
}