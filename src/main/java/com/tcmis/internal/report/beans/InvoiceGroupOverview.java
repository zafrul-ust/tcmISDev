package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

public class InvoiceGroupOverview extends BaseDataBean {
	private String	accountSysDesc;
	private String	invoiceGroup;
	private String	paymentTerms;
	private String	pricingMethod;

	public String getAccountSysDesc() {
		return accountSysDesc;
	}

	public String getInvoiceGroup() {
		return invoiceGroup;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public String getPricingMethod() {
		return pricingMethod;
	}

	public void setAccountSysDesc(String accountSysDesc) {
		this.accountSysDesc = accountSysDesc;
	}

	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public void setPricingMethod(String pricingMethod) {
		this.pricingMethod = pricingMethod;
	}
}
