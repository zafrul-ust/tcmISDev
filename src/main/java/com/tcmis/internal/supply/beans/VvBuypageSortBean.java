package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvBuypageSortBean <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VvBuypageSortBean extends BaseDataBean {

	private String sortId;
	private String sortDesc;


	//constructor
	public VvBuypageSortBean() {
	}

	//setters
	public void setSortId(String sortId) {
		this.sortId = sortId;
	}
	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}


	//getters
	public String getSortId() {
		return sortId;
	}
	public String getSortDesc() {
		return sortDesc;
	}
}