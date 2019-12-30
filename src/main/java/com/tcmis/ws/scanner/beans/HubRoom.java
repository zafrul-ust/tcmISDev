package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class HubRoom extends BaseDataBean {

	private Collection<HubBin> bins;
	private String hub;
	private String room;
	private String roomDescription;
	private BigDecimal routeOrder;
	private String	bin;
	private String	binDesc;
	private Date	lastModified;
	private boolean	onSite;
	private String	status;
	private String binType;
	private BigDecimal binRouteOrder;
	
	public HubRoom() {
	}
	
	public Collection<HubBin> getBins() {
		return bins;
	}
	
	public String getHub() {
		return hub;
	}
	
	public String getRoom() {
		return room;
	}
	
	public String getRoomDescription() {
		return roomDescription;
	}
	
	public BigDecimal getRouteOrder() {
		return routeOrder;
	}
	
	public void setBins(Collection<HubBin> bins) {
		this.bins = bins;
	}
	
	public void setHub(String hub) {
		this.hub = hub;
	}
	
	public void setRoom(String room) {
		this.room = room;
	}
	
	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	
	public void setRouteOrder(BigDecimal routeOrder) {
		this.routeOrder = routeOrder;
	}

	public void setBin(String bin) {
		getFirstBin().setBin(bin);
	}

	public void setBinDesc(String binDesc) {
		getFirstBin().setBinDesc(binDesc);
	}

	public void setLastModified(Date lastModified) {
		getFirstBin().setLastModified(lastModified);
	}

	public void setOnSite(boolean onSite) {
		getFirstBin().setOnSite(onSite);
	}

	public void setStatus(String status) {
		getFirstBin().setStatus(status);
	}

	public void setBinType(String binType) {
		getFirstBin().setBinType(binType);
	}
	
	public void setBinRouteOrder(BigDecimal binRouteOrder) {
		getFirstBin().setRouteOrder(binRouteOrder);
	}

	public boolean isSameRoom(HubRoom other) {
		return StringUtils.isNotBlank(room) && room.equals(other.getRoom())
				&& StringUtils.isNotBlank(hub) && hub.equals(other.getHub());
	}
	
	public void addBin(HubRoom room) {
		if (bins == null) {
			setBins(new Vector<HubBin>());
		}
		bins.add(room.getFirstBin());
	}
	
	protected synchronized HubBin getFirstBin() {
		HubBin firstAccount = null;
		if (bins == null || bins.isEmpty()) {
			setBins(new Vector<HubBin>());
			firstAccount = new HubBin();
			bins.add(firstAccount);
		}
		else {
			firstAccount = ((Vector<HubBin>) bins).get(0);
		}
		return firstAccount;
	}
}
