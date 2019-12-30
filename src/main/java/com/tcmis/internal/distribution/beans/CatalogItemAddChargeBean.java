package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogItemAddChargeBean <br>
 * 
 * @version: 1.0, May 6, 2010 <br>
 *****************************************************************************/

public class CatalogItemAddChargeBean extends BaseDataBean {
	private String defaultCurrencyId;
	private BigDecimal defaultPrice;
	private String headerCharge = "N";
	private boolean headerChargeType = false;
	private String itemDescription;
	private BigDecimal itemId;
	private String itemType;
	private Date lastUpdated;
	private BigDecimal lastUpdatedBy;
	private String updateByName;
	private String lineCharge = "N";
	private boolean lineChargeType = false;
	private String opsEntityId;
	private String updated;

	// constructor
	public CatalogItemAddChargeBean() {
	}

	public String getDefaultCurrencyId() {
		return defaultCurrencyId;
	}

	public BigDecimal getDefaultPrice() {
		return defaultPrice;
	}

	public String getHeaderCharge() {
		return headerCharge;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public String getLineCharge() {
		return lineCharge;
	}

	// getters
	public String getOpsEntityId() {
		return opsEntityId;
	}

	public String getUpdated() {
		return updated;
	}

	public boolean isHeaderChargeType() {
		return "Y".equals(headerCharge);
	}

	public boolean isLineChargeType() {
		return "Y".equals(lineCharge);
	}

	public boolean isModified() {
		return "true".equals(updated);
	}

	public void setDefaultCurrencyId(String defaultCurrencyId) {
		this.defaultCurrencyId = defaultCurrencyId;
	}

	public void setDefaultPrice(BigDecimal defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public void setHeaderCharge(String headerCharge) {
		this.headerCharge = headerCharge;
	}

	public void setHeaderChargeType(boolean headerChargeType) {
		setHeaderCharge(headerChargeType ? "Y" : "N");
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public void setUpdateByName(String lastUpdatedByName) {
		this.updateByName = lastUpdatedByName;
	}

	public void setLineCharge(String lineCharge) {
		this.lineCharge = lineCharge;
	}

	public void setLineChargeType(boolean lineChargeType) {
		setLineCharge(lineChargeType ? "Y" : "N");
	}

	// setters
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public void setUpdated(String update) {
		this.updated = update;
	}
}