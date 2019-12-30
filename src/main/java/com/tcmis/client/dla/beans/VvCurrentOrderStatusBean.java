package com.tcmis.client.dla.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvFreightAdviceStatusBean <br>
 * @version: 1.0, Feb 12, 2008 <br>
 *****************************************************************************/


public class VvCurrentOrderStatusBean extends BaseDataBean {

	private String currentStatus;
	private String description;


	//constructor
	public VvCurrentOrderStatusBean() {
	}


	public String getCurrentStatus() {
		return currentStatus;
	}


	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	
}