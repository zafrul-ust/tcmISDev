package com.tcmis.client.operations.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/
public class EdiOrderStatusInputBean extends BaseDataBean 
{
	
	private String   companyId;
	private String   searchArgument;
	private String   searchMode;
	private String 	 searchField;

	public EdiOrderStatusInputBean() 
	{
	}


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getSearchArgument() {
		return searchArgument;
	}


	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}


	public String getSearchField() {
		return searchField;
	}


	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}


	public String getSearchMode() {
		return searchMode;
	}


	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}


}