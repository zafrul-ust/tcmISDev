package com.tcmis.internal.distribution.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SalesQuoteViewBean <br>
 * @version: 1.0, Aug 21, 2009 <br>
 *****************************************************************************/


public class OrderCsrViewBean extends BaseDataBean {

	
	private BigDecimal csrPersonnelId;
	
	private String opsCompanyId;
	private String opsEntityId;
	private String csrName;

	//constructor
	public OrderCsrViewBean() {
	}

	public String getCsrName() {
		return csrName;
	}

	public void setCsrName(String csrName) {
		this.csrName = csrName;
	}

	public BigDecimal getCsrPersonnelId() {
		return csrPersonnelId;
	}

	public void setCsrPersonnelId(BigDecimal csrPersonnelId) {
		this.csrPersonnelId = csrPersonnelId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

}