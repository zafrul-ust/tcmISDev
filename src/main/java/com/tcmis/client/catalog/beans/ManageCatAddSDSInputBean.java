package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;

import java.math.BigDecimal;
import java.util.Locale;

public class ManageCatAddSDSInputBean extends BaseInputBean {
	private BigDecimal requestId;
	private String editable;

	// constructor
	public ManageCatAddSDSInputBean() {}
	
	public ManageCatAddSDSInputBean(ActionForm form, Locale locale) throws Exception {
		try {
			BeanHandler.copyAttributes(form, this, locale);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("requestId");
	}
	
	public BigDecimal getRequestId() {
		return requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}
}