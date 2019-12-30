package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: TcmisConstantBean <br>
 * @version: 1.0, Nov 23, 2005 <br>
 *****************************************************************************/


public class TcmisConstantBean extends BaseDataBean {

	private String constant;
	private String value;


	//constructor
	public TcmisConstantBean() {
	}

	//setters
	public void setConstant(String constant) {
		this.constant = constant;
	}
	public void setValue(String value) {
		this.value = value;
	}


	//getters
	public String getConstant() {
		return constant;
	}
	public String getValue() {
		return value;
	}
}