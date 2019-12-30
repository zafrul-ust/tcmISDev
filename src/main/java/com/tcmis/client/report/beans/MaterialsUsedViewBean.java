package com.tcmis.client.report.beans;

import java.util.Date;
import java.util.Collection;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialsUsedViewBean <br>
 * @version: 1.0, Feb 21, 2006 <br>
 *****************************************************************************/


public class MaterialsUsedViewBean extends BaseDataBean {

	private String facilityId;
	private String application;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private BigDecimal itemId;
	private BigDecimal materialId;
	private String packaging;
	private String materialDesc;
	private String mfgDesc;
	private Date dateDelivered;
	private BigDecimal itemQuantityDelivered;
  private Collection kitCollection;
  private BigDecimal rowSpan;

	//constructor
	public MaterialsUsedViewBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setDateDelivered(Date dateDelivered) {
		this.dateDelivered = dateDelivered;
	}
	public void setItemQuantityDelivered(BigDecimal itemQuantityDelivered) {
		this.itemQuantityDelivered = itemQuantityDelivered;
	}
	public void setKitCollection(Collection kitCollection) {
	 this.kitCollection =
		kitCollection;
	}
	public void setRowSpan(BigDecimal rowSpan) {
	this.rowSpan = rowSpan;
	}

	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public Date getDateDelivered() {
		return dateDelivered;
	}
	public BigDecimal getItemQuantityDelivered() {
		return itemQuantityDelivered;
	}
  public Collection getKitCollection() {
	 return kitCollection;
	}
	public BigDecimal getRowSpan() {
	 return rowSpan;
	}

}