package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class DbuyContractAddChargeInputBean extends BaseDataBean 
{

	private BigDecimal dbuyContractId;
	private String currencyId;
	private Date startDate;
	private String action;	

	public DbuyContractAddChargeInputBean()
	{
	}

	public BigDecimal getDbuyContractId() {
		return dbuyContractId;
	}

	public void setDbuyContractId(BigDecimal dbuyContractId) {
		this.dbuyContractId = dbuyContractId;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
