package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class TabletShipment extends BaseDataBean {
	private Date		plannedShipDate;
	private BigDecimal	shipmentId;
	private String		tabletShipmentId;

	public Date getPlannedShipDate() {
		return this.plannedShipDate;
	}

	public BigDecimal getShipmentId() {
		return this.shipmentId;
	}

	public String getTabletShipmentId() {
		return this.tabletShipmentId;
	}

	public void setPlannedShipDate(Date plannedShipDate) {
		this.plannedShipDate = plannedShipDate;
	}

	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}

	public void setTabletShipmentId(String tabletShipmentId) {
		this.tabletShipmentId = tabletShipmentId;
	}
}
