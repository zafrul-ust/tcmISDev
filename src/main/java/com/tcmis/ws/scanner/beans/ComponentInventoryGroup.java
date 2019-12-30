package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

public class ComponentInventoryGroup extends BaseDataBean {
	private String		inventoryGroup;
	private BigDecimal	itemId;
	private Date		lastModified;
	private BigDecimal	partId;
	private boolean		qualityControlItem;
	private String		shelfLifeBasis;
	private BigDecimal	shelfLifeDays;
	private String		source;
	private String		storageTemp;

	public ComponentInventoryGroup() {
		super();
	}

	public String getInventoryGroup() {
		return this.inventoryGroup;
	}

	public BigDecimal getItemId() {
		return this.itemId;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public BigDecimal getPartId() {
		return this.partId;
	}

	public String getSource() {
		return this.source;
	}

	public String getStorageTemp() {
		return this.storageTemp;
	}

	public boolean isQualityControlItem() {
		return this.qualityControlItem;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public void setQualityControlItem(boolean qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public String getShelfLifeBasis() {
		return this.shelfLifeBasis;
	}

	public BigDecimal getShelfLifeDays() {
		return this.shelfLifeDays;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
}
