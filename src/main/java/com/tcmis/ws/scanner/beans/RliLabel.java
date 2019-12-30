package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

public class RliLabel extends BaseDataBean {
	
	private BigDecimal issueId;
	private BigDecimal receiptId;
	private String facPartNo;
	
	public RliLabel() {
		
	}

	public BigDecimal getIssueId() {
		return this.issueId;
	}

	public BigDecimal getReceiptId() {
		return this.receiptId;
	}

	public String getFacPartNo() {
		return this.facPartNo;
	}

	public void setIssueId(BigDecimal issueId) {
		this.issueId = issueId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	
	public boolean hasFacPartNo() {
		return StringUtils.isNotBlank(facPartNo);
	}
}
