package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvBuyOrderStatusBean <br>
 * @version: 1.0, Mar 10, 2006 <br>
 *****************************************************************************/


public class VvBuyOrderStatusBean extends BaseDataBean {

	private String status;
	private String buypageAssignable;
	private String lockStatus;
	private BigDecimal displaySort;


	//constructor
	public VvBuyOrderStatusBean() {
	}

	//setters
	public void setStatus(String status) {
		this.status = status;
	}
	public void setBuypageAssignable(String buypageAssignable) {
		this.buypageAssignable = buypageAssignable;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
	public void setDisplaySort(BigDecimal displaySort) {
		this.displaySort = displaySort;
	}


	//getters
	public String getStatus() {
		return status;
	}
	public String getBuypageAssignable() {
		return buypageAssignable;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public BigDecimal getDisplaySort() {
		return displaySort;
	}
}