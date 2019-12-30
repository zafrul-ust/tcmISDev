package com.tcmis.internal.report.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;

public class InventoryGroupOverview extends BaseDataBean {
	private String		hub;
	private String		hubName;
	private BigDecimal		inventoryGroupId;
	private String		inventoryGroupName;
	private String		status;
	private boolean	useContainerLabels;
	private boolean	useItemCounting;

	public String getHub() {
		return hub;
	}

	public String getHubName() {
		return hubName;
	}

	public BigDecimal getInventoryGroupId() {
		return inventoryGroupId;
	}

	public String getInventoryGroupName() {
		return inventoryGroupName;
	}

	public String getStatus() {
		return status;
	}

	public boolean isUseContainerLabels() {
		return useContainerLabels;
	}

	public boolean isUseItemCounting() {
		return useItemCounting;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public void setHubName(String hubName) {
		this.hubName = hubName;
	}

	public void setInventoryGroupId(BigDecimal inventoryGroupId) {
		this.inventoryGroupId = inventoryGroupId;
	}

	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUseContainerLabels(boolean useContainerLabels) {
		this.useContainerLabels = useContainerLabels;
	}

	public void setUseItemCounting(boolean useItemCounting) {
		this.useItemCounting = useItemCounting;
	}

}
