package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerCarrierInfo Bean <br>
 * @version: 1.0, Aug 17, 2009 <br>
 *****************************************************************************/


public class WriteOnRequestInputBean extends BaseDataBean {
	
	
	private static final long serialVersionUID = -2652942960556090934L;
	private BigDecimal receiptId;
	private String action;	
	private BigDecimal quantity; 
	private String requestorComment;
	
	//constructor
	public WriteOnRequestInputBean() {
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setRequestorComment(String requestorComment) {
		this.requestorComment = requestorComment;
	}

	public String getRequestorComment() {
		return requestorComment;
	}

	


}