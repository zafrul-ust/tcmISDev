package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvCompassPointBean <br>
 * @version: 1.0, Nov 22, 2011 <br>
 *****************************************************************************/


public class VvCompassPointBean extends BaseDataBean {

	private String shortName;
	private String longName;


	//constructor
	public VvCompassPointBean() {
	}

	//setters
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}


	//getters
	public String getShortName() {
		return shortName;
	}
	public String getLongName() {
		return longName;
	}
}