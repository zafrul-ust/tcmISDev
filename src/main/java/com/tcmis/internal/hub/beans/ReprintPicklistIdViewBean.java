package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ReprintPicklistIdViewBean <br>
 * @version: 1.0, Jan 26, 2007 <br>
 *****************************************************************************/


public class ReprintPicklistIdViewBean extends BaseDataBean {

	private String hub;
	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private BigDecimal packingGroupId;


	//constructor
	public ReprintPicklistIdViewBean() {
	}

	//setters
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {
		this.picklistPrintDate = picklistPrintDate;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}


	//getters
	public String getHub() {
		return hub;
	}
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
}