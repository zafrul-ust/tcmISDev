package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class HubBin extends BaseDataBean {

	private String	bin;
	private String	binDesc;
	private String	hub;
	private Date	lastModified;
	private boolean	onSite;
	private String	room;
	private String	status;
	private String binType;
	private BigDecimal routeOrder;

	// constructor
	public HubBin() {
	}

	public String getBin() {
		return bin;
	}

	public String getBinDesc() {
		return binDesc;
	}

	public String getHub() {
		return hub;
	}

	public Date getLastModified() {
		return lastModified;
	}

	// getters
	public String getRoom() {
		return room;
	}

	public String getStatus() {
		return status;
	}

	public boolean isOnSite() {
		return onSite;
	}

	public void setBin(String hubName) {
		this.bin = hubName;
	}

	public void setBinDesc(String facilityId) {
		this.binDesc = facilityId;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setOnSite(boolean onSite) {
		this.onSite = onSite;
	}

	// setters
	public void setRoom(String companyId) {
		this.room = companyId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBinType() {
		return binType;
	}

	public void setBinType(String binType) {
		this.binType = binType;
	}

	public BigDecimal getRouteOrder() {
		return routeOrder;
	}

	public void setRouteOrder(BigDecimal routeOrder) {
		this.routeOrder = routeOrder;
	}

}