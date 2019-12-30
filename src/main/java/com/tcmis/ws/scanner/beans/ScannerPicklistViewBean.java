package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class ScannerPicklistViewBean extends BaseDataBean {
	
	private BigDecimal receiptId;
	private BigDecimal quantity;
	private BigDecimal issueId;
	
	public ScannerPicklistViewBean() {
		
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

	public BigDecimal getIssueId() {
		return issueId;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

}
