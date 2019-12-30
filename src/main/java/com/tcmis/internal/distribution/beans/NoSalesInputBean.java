package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: NoSalesViewBean <br>
 * @version: 1.0, Jun 17, 2009 <br>
 *****************************************************************************/


public class NoSalesInputBean extends BaseDataBean {

	private String inventoryGroup;
	private String hub;
	private String opsEntityId;
	private BigDecimal notIssuedIn;
	
	
	//constructor
	public NoSalesInputBean() {
	}


	public String getHub() {
		return hub;
	}


	public void setHub(String hub) {
		this.hub = hub;
	}


	public String getInventoryGroup() {
		return inventoryGroup;
	}


	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}


	public BigDecimal getNotIssuedIn() {
		return notIssuedIn;
	}


	public void setNotIssuedIn(BigDecimal notIssuedIn) {
		this.notIssuedIn = notIssuedIn;
	}


	public String getOpsEntityId() {
		return opsEntityId;
	}


	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}


}