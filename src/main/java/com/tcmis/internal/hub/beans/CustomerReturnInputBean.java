package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;

public class CustomerReturnInputBean extends BaseInputBean {
	private String inventoryGroup;
	private String hub;
	private String lineItem;
	private String opsEntityId;
	private BigDecimal prNumber;
	private BigDecimal prNumberByReceiptId;
	
	private String companyId;
	private BigDecimal customerRmaId;
	
	public CustomerReturnInputBean() {}

	public CustomerReturnInputBean(ActionForm form, Locale tcmISLocale) {
		super(form, tcmISLocale);
	}

	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("inventoryGroup");
		addHiddenFormField("hub");
		addHiddenFormField("opsEntityId");
		addHiddenFormField("prNumber");
		addHiddenFormField("prNumberByReceiptId");
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public BigDecimal getPrNumberByReceiptId() {
		return prNumberByReceiptId;
	}

	public void setPrNumberByReceiptId(BigDecimal prNumberByReceiptId) {
		this.prNumberByReceiptId = prNumberByReceiptId;
	}

	public String getLineItem() {
		return lineItem;
	}

	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}