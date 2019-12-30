package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: IgCountDefinitionBean <br>
 * @version: 1.0, May 19, 2011 <br>
 *****************************************************************************/


public class IgCountDefinitionBean extends BaseDataBean {

	private String inventoryGroup;
	private BigDecimal countId;
	private Date startDate;
	private Date endDate;
	private BigDecimal startUser;
	private BigDecimal endUser;
	private Date dateCounted;


	//constructor
	public IgCountDefinitionBean() {
	}

	//setters
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setCountId(BigDecimal countId) {
		this.countId = countId;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setStartUser(BigDecimal startUser) {
		this.startUser = startUser;
	}
	public void setEndUser(BigDecimal endUser) {
		this.endUser = endUser;
	}
	public void setDateCounted(Date dateCounted) {
		this.dateCounted = dateCounted;
	}


	//getters
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getCountId() {
		return countId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public BigDecimal getStartUser() {
		return startUser;
	}
	public BigDecimal getEndUser() {
		return endUser;
	}
	public Date getDateCounted() {
		return dateCounted;
	}
}