package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SizeUnitViewBean <br>
 * @version: 1.0, Jan 9, 2008 <br>
 *****************************************************************************/


public class SizeUnitViewBean extends BaseDataBean {

	private String sizeUnit;
	private String netWtRequired;


	//constructor
	public SizeUnitViewBean() {
	}

	//setters
	public void setSizeUnit(String sizeUnit) {
		this.sizeUnit = sizeUnit;
	}
	public void setNetWtRequired(String netWtRequired) {
		this.netWtRequired = netWtRequired;
	}


	//getters
	public String getSizeUnit() {
		return sizeUnit;
	}
	public String getNetWtRequired() {
		return netWtRequired;
	}
}