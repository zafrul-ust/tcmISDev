package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MrAddChargeViewBean <br>
 * @version: 1.0, Jul 21, 2009 <br>
 *****************************************************************************/


public class NewFeesItemsViewBean extends BaseDataBean {
	
	private static final long serialVersionUID = -5009875626139320350L;
	
	private BigDecimal itemId;	
	private String itemDesc;
	private String selectCurrency;
	private BigDecimal newprice;
	//constructor
	public NewFeesItemsViewBean() {
	}


	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}


	public BigDecimal getItemId() {
		return itemId;
	}


	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}


	public String getItemDesc() {
		return itemDesc;
	}


	public void setSelectCurrency(String selectCurrency) {
		this.selectCurrency = selectCurrency;
	}


	public String getSelectCurrency() {
		return selectCurrency;
	}


	public void setNewprice(BigDecimal newprice) {
		this.newprice = newprice;
	}


	public BigDecimal getNewprice() {
		return newprice;
	}

	
}