package com.tcmis.supplier.shipping.beans;

import com.tcmis.common.framework.BaseDataBean;

public class TransTypeStatusViewBean extends BaseDataBean {
	private String transactionType;
	private String status;
	
	public String getTransactionType() {
		return transactionType;
	}
	
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}