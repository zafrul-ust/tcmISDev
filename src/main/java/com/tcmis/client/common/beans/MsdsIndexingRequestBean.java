package com.tcmis.client.common.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseInputBean;

public class MsdsIndexingRequestBean extends BaseInputBean {

	private BigDecimal requestId;
	private BigDecimal lineItem;
	private String partDescription;
	
	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public void setHiddenFormFields() {
		
	}
}
