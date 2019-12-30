package com.tcmis.internal.accountspayable.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvVoucherStatusBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/


public class VvVoucherStatusBean extends BaseDataBean {

	private String voucherStatus;


	//constructor
	public VvVoucherStatusBean() {
	}

	//setters
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}


	//getters
	public String getVoucherStatus() {
		return voucherStatus;
	}
}