package com.tcmis.internal.hub.beans;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 2:35:28 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class FreightAdviseInputBean
    extends BaseDataBean
{
	private String 	 hub;
    private String 	 inventoryGroup;
	private Date 	 shipDate;
	private String   shipStatus;


	public FreightAdviseInputBean() 
	{
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date d) {
		this.shipDate = d;
	}

	public String getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(String s) {
		this.shipStatus = s;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

}
