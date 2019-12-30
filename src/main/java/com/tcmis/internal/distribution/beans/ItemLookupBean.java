package com.tcmis.internal.distribution.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemLookupBean <br>
 * 
 * @version: 1.0, Apr 27, 2010 <br>
 *****************************************************************************/

public class ItemLookupBean extends BaseDataBean {

	private BigDecimal itemId;
	private String grade;
	private String shelfLife;
	private String materialDesc;
	private String mfgDesc;
	private BigDecimal materialId;
	private String packaging;
	private String mfgPartNo;
	private String sampleOnly;

	// constructor
	public ItemLookupBean() {
	}

	// setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setMfgPartNo(String mfgPartNo) {
		this.mfgPartNo = mfgPartNo;
	}

	public void setSampleOnly(String sampleOnly) {
		this.sampleOnly = sampleOnly;
	}

	// getters
	public BigDecimal getItemId() {
		return itemId;
	}

	public String getGrade() {
		return grade;
	}

	public String getShelfLife() {
		return shelfLife;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public String getMfgDesc() {
		return mfgDesc;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getMfgPartNo() {
		return mfgPartNo;
	}

	public String getSampleOnly() {
		return sampleOnly;
	}
}