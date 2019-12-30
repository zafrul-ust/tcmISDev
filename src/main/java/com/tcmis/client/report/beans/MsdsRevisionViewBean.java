package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MsdsRevisionViewBean <br>
 * @version: 1.0, Apr 8, 2008 <br>
 *****************************************************************************/


public class MsdsRevisionViewBean extends BaseDataBean {

	private String companyId;
	private String facility;
	private String partNo;
	private BigDecimal itemId;
	private BigDecimal partId;
	private String tradeName;
	private String manufacturer;
	private Date lastRevisionDate;
	private Date lastRequestDate;
	private String catalogCompanyId;


	//constructor
	public MsdsRevisionViewBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacility(String facility) {
		this.facility = facility;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setLastRevisionDate(Date lastRevisionDate) {
		this.lastRevisionDate = lastRevisionDate;
	}
	public void setLastRequestDate(Date lastRequestDate) {
		this.lastRequestDate = lastRequestDate;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacility() {
		return facility;
	}
	public String getPartNo() {
		return partNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getPartId() {
		return partId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public Date getLastRevisionDate() {
		return lastRevisionDate;
	}
	public Date getLastRequestDate() {
		return lastRequestDate;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
}