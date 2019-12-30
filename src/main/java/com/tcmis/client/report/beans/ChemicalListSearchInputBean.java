package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FormattedUsageInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class ChemicalListSearchInputBean extends BaseDataBean {

	private String searchText;


	//constructor
	public ChemicalListSearchInputBean() {
	}

	//setters
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	//getters
	public String getSearchText() {
		return searchText;
	}
}