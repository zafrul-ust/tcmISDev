package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: CatalogStorageTempBean <br>
 * @version: 1.0, Dec 2, 2010 <br>
 *****************************************************************************/

public class CatalogStorageTempBean extends BaseDataBean {

	private String catalogCompanyId;
 	private String catalogId;
	private String customerTemperatureId;
	private BigDecimal lowTemp;
	private BigDecimal highTemp;
	private String tempUnit;
	private String displayTemp;


 	//constructor
 	public CatalogStorageTempBean() {
 	}

	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public String getCustomerTemperatureId() {
		return customerTemperatureId;
	}

	public void setCustomerTemperatureId(String customerTemperatureId) {
		this.customerTemperatureId = customerTemperatureId;
	}

	public BigDecimal getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(BigDecimal lowTemp) {
		this.lowTemp = lowTemp;
	}

	public BigDecimal getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(BigDecimal highTemp) {
		this.highTemp = highTemp;
	}

	public String getTempUnit() {
		return tempUnit;
	}

	public void setTempUnit(String tempUnit) {
		this.tempUnit = tempUnit;
	}

	public String getDisplayTemp() {
		return displayTemp;
	}

	public void setDisplayTemp(String displayTemp) {
		this.displayTemp = displayTemp;
	}
}