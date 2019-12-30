package com.tcmis.internal.distribution.beans;

import com.tcmis.common.framework.BaseDataBean;

public class CustomerValidCurrencyInputBean extends BaseDataBean 
{
	private String uAction;
	private String customerId;
	private String opsEntityId;

	public CustomerValidCurrencyInputBean()
	{
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getUAction() {
		return uAction;
	}

	public void setUAction(String action) {
		uAction = action;
	}


}