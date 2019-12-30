package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

public class UserCustomerViewBean extends BaseDataBean {
	

	private BigDecimal personnelId;
	private BigDecimal customerId;
	private String customerName;
	private String companyId;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	//constructor
	public UserCustomerViewBean() {
	}




}