package com.tcmis.internal.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: MaterialBean <br>
 * 
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/

public class ItemSearchBean extends BaseDataBean {
	private String grade;
	private String itemDesc;
	private BigDecimal itemId;
	private String materialDesc;
	private BigDecimal materialId;
	private String mfgDesc;
	private BigDecimal mfgId;
	private String mfgPartNo;
	private String pkgStyle;
	private String shelfLifeBasis;
	private BigDecimal shelfLifeDays;
	private String formattedStorageTemp;
	private String mfgPartNoExtension;


	// constructor
	public ItemSearchBean() {}
	
	public String getGrade() {
		return grade;
	}	
	public String getItemDesc() {
		return itemDesc;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getMfgPartNo() {
		return mfgPartNo;
	}
	public String getPkgStyle() {
		return pkgStyle;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getFormattedStorageTemp() {
		return formattedStorageTemp;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}
	public void setPkgStyle(String pkgStyle) {
		this.pkgStyle = pkgStyle;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setFormattedStorageTemp(String formattedStorageTemp) {
		this.formattedStorageTemp = formattedStorageTemp;
	}

	public String getMfgPartNoExtension() {
		return mfgPartNoExtension;
	}

	public void setMfgPartNoExtension(String mfgPartNoExtension) {
		this.mfgPartNoExtension = mfgPartNoExtension;
	}
}
