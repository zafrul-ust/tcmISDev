package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvDodaacTypeBean <br>
 * @version: 1.0, Nov 24, 2008 <br>
 *****************************************************************************/


public class VvDodaacTypeBean extends BaseDataBean {

	private String dodaacType;
	private String dodaacTypeDesc;


	//constructor
	public VvDodaacTypeBean() {
	}

	//setters
	public void setDodaacType(String dodaacType) {
		this.dodaacType = dodaacType;
	}
	public void setDodaacTypeDesc(String dodaacTypeDesc) {
		this.dodaacTypeDesc = dodaacTypeDesc;
	}


	//getters
	public String getDodaacType() {
		return dodaacType;
	}
	public String getDodaacTypeDesc() {
		return dodaacTypeDesc;
	}
}