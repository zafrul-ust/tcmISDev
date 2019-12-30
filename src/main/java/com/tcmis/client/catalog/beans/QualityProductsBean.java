package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;


public class QualityProductsBean extends BaseDataBean {

	  private String companyId;
	  private String catPartNo;
	  private String status;
	  private BigDecimal itemId;
	  private String kitMsdsNumber;
	  private String materialDesc;
	  private String packaging;
	  private BigDecimal mfgId;
	  private String mfgDesc;
	  private String mfgPartNo;
	  private String compMsds;
	  private Collection catPartColl;
	  private BigDecimal shelfLifeDays;
	  private String shelfLifeBasis;
	  private String jspLabel;
	  private String customerTemperatureId;
	  private BigDecimal maxRecertNumber;
	  private BigDecimal roomTempOutTime;
	  private BigDecimal rowSpan;
	  private String displayTemp;
	  private String customerMixtureNumber;
	  private String catalogCompanyId;
	  
	  public QualityProductsBean() {
		}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	

	public String getKitMsdsNumber() {
		return kitMsdsNumber;
	}

	public void setKitMsdsNumber(String kitMsdsNumber) {
		this.kitMsdsNumber = kitMsdsNumber;
	}

	

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public BigDecimal getMfgId() {
		return mfgId;
	}

	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	
	

	public String getMfgDesc() {
		return mfgDesc;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public String getCompMsds() {
		return compMsds;
	}

	public void setCompMsds(String compMsds) {
		this.compMsds = compMsds;
	}

	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}

	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}

	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}

	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}

	public String getJspLabel() {
		return jspLabel;
	}

	public void setJspLabel(String jspLabel) {
		this.jspLabel = jspLabel;
	}

	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}

	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}

	public BigDecimal getMaxRecertNumber() {
		return maxRecertNumber;
	}

	public void setMaxRecertNumber(BigDecimal maxRecertNumber) {
		this.maxRecertNumber = maxRecertNumber;
	}

	public BigDecimal getRoomTempOutTime() {
		return roomTempOutTime;
	}

	public void setRoomTempOutTime(BigDecimal roomTempOutTime) {
		this.roomTempOutTime = roomTempOutTime;
	}

	public BigDecimal getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(BigDecimal rowSpan) {
		this.rowSpan = rowSpan;
	}

	public Collection getCatPartColl() {
		return catPartColl;
	}

	public void setCatPartColl(Collection catPartColl) {
		this.catPartColl = catPartColl;
	}

	public String getDisplayTemp() {
		return displayTemp;
	}

	public void setDisplayTemp(String displayTemp) {
		this.displayTemp = displayTemp;
	}


	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}

	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
}
