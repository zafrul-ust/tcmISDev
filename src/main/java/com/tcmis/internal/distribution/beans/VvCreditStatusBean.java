package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: Global.vvCreditStatusBean <br>
 * @version: 1.0, Jul 28, 2009 <br>
 *****************************************************************************/


public class VvCreditStatusBean extends BaseDataBean {

	private String creditStatus;
	private String creditStatusDesc;
	private String creditStatusLabel;


	//constructor
	public VvCreditStatusBean() {
	}

	//setters
	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}
	public void setCreditStatusDesc(String creditStatusDesc) {
		this.creditStatusDesc = creditStatusDesc;
	}
	public void setCreditStatusLabel(String creditStatusLabel) {
		this.creditStatusLabel = creditStatusLabel;
	}


	//getters
	public String getCreditStatus() {
		return creditStatus;
	}
	public String getCreditStatusDesc() {
		return creditStatusDesc;
	}
	public String getCreditStatusLabel() {
		return creditStatusLabel;
	}

}