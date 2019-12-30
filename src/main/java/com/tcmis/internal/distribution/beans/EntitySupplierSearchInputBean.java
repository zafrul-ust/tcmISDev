package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

public class EntitySupplierSearchInputBean extends BaseDataBean 
{
	
	private static final long serialVersionUID = -2886372522297483012L;
	private String 	action;
	private String 	searchArgument;
	private String 	supplierName;
	private String  countryAbbrev;
	private String  activeOnly;
	private BigDecimal supplierId;
	private String opsEntityId;
	
	public EntitySupplierSearchInputBean()
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


	/**
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}


	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	/**
	 * @return the opsEntityId
	 */
	public String getOpsEntityId() {
		return opsEntityId;
	}


	/**
	 * @param opsEntityId the opsEntityId to set
	 */
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

}
