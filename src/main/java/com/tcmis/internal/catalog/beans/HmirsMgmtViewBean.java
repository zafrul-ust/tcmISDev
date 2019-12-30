package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class HmirsMgmtViewBean extends BaseDataBean {

	private BigDecimal hmirsRoadMapId;
	private BigDecimal itemId;
	private BigDecimal currentItem;
	private String nsn;
	private String productDesc;
	private String manufacturerName;
	private String manufacturerCageCode;
	private String saicSupplierName;
	private String supplierCageCode;
	private String saicSupplierCode;
	private String saicMsdsId;
	private Date loadDate;
	private Date lastUpdatedDate;
	private String lastUpdatedByName;
	
	public BigDecimal getHmirsRoadMapId() {
		return hmirsRoadMapId;
	}

	public void setHmirsRoadMapId(BigDecimal hmirsRoadMapId) {
		this.hmirsRoadMapId = hmirsRoadMapId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(BigDecimal currentItem) {
		this.currentItem = currentItem;
	}
	
	public boolean isMappingEstablished() {
		boolean established = false;
		if (currentItem == null && itemId == null) {
			established = true;
		}
		else if ( ! (currentItem == null || itemId == null)){
			established = currentItem.compareTo(itemId) == 0;
		}
		return established;
	}

	public String getNsn() {
		return nsn;
	}

	public void setNsn(String nsn) {
		this.nsn = nsn;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getManufacturerCageCode() {
		return manufacturerCageCode;
	}

	public void setManufacturerCageCode(String manufacturerCageCode) {
		this.manufacturerCageCode = manufacturerCageCode;
	}

	public String getSaicSupplierName() {
		return saicSupplierName;
	}

	public void setSaicSupplierName(String saicSupplierName) {
		this.saicSupplierName = saicSupplierName;
	}

	public String getSupplierCageCode() {
		return supplierCageCode;
	}

	public void setSupplierCageCode(String supplierCageCode) {
		this.supplierCageCode = supplierCageCode;
	}

	public String getSaicSupplierCode() {
		return saicSupplierCode;
	}

	public void setSaicSupplierCode(String saicSupplierCode) {
		this.saicSupplierCode = saicSupplierCode;
	}

	public String getSaicMsdsId() {
		return saicMsdsId;
	}

	public void setSaicMsdsId(String saicMsdsId) {
		this.saicMsdsId = saicMsdsId;
	}

	public Date getLoadDate() {
		return loadDate;
	}

	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}
}
