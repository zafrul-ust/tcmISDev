package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class POSupplierInputBean extends BaseDataBean 
{
	private String 	action;
	private String 	searchArgument;
	private String  countryAbbrev;
	private String  activeOnly;
	private BigDecimal supplierId;
	
	private String 	opsEntityId;
	
	public POSupplierInputBean()
	{		  
	}
	

	public String getSearchArgument() {
		return searchArgument;
	}

	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	/**
	 * @param countryAbbrev the countryAbbrev to set
	 */
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

	/**
	 * @return the countryAbbrev
	 */
	public String getCountryAbbrev() {
		return countryAbbrev;
	}


	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}


	public String getActiveOnly() {
		return activeOnly;
}

	public void setActiveOnly(String activeOnly) {
		this.activeOnly = activeOnly;
	}


	public void setSupplierId(BigDecimal supplierId) {
		this.supplierId = supplierId;
	}


	public BigDecimal getSupplierId() {
		return supplierId;
	}


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

}
