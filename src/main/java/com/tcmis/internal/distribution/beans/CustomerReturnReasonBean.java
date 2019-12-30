package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: CustomerReturnReasonBean <br>
 * @version: 1.0, Jul 16, 2009 <br>
 *****************************************************************************/


public class CustomerReturnReasonBean extends BaseDataBean {

	
	private static final long serialVersionUID = -7835570143438865260L;
	private BigDecimal customerRmaId;
	private String returnReasonId;


	//constructor
	public CustomerReturnReasonBean() {
	}

	//setters
	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}
	public void setReturnReasonId(String returnReasonId) {
		this.returnReasonId = returnReasonId;
	}


	//getters
	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}
	public String getReturnReasonId() {
		return returnReasonId;
	}
}