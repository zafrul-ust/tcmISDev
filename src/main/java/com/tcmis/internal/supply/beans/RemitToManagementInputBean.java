package com.tcmis.internal.supply.beans;

import java.util.Locale;

import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.BaseInputBean;
import com.tcmis.common.util.BeanHandler;

public class RemitToManagementInputBean extends BaseInputBean {
	private String status;
	private String supplier;
	private String supplierName;
	
	private String callerName;

	public RemitToManagementInputBean()	{}
	
	public RemitToManagementInputBean(ActionForm form, Locale locale) throws Exception {
        try {
            BeanHandler.copyAttributes(form, this, locale);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
	
	@Override
	public void setHiddenFormFields() {
		addHiddenFormField("status");
		
		removeHiddenFormField("uAction");
		removeHiddenFormField("totalLines");
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCallerName() {
		return callerName;
	}

	public void setCallerName(String callerName) {
		this.callerName = callerName;
	}

}
