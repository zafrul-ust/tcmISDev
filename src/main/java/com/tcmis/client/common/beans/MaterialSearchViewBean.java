
package com.tcmis.client.common.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialSearchViewBean <br>
 * @version: 1.0, April 5, 2011 <br>
 *****************************************************************************/


public class MaterialSearchViewBean extends BaseDataBean {

	private BigDecimal mfgId;
	private String mfgDesc;
	private BigDecimal materialId;
	private String materialDesc;
	private String tradeName;
	private String msdsOnLine;
	private String msdsNumber;
	private String companyId;
	private String catalogCompanyId;
	private String catalogId;
	private String facilityId;


	//constructor
	public MaterialSearchViewBean() {
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


	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	public String getMaterialDesc() {
		return materialDesc;
	}


	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}


	public BigDecimal getMaterialId() {
		return materialId;
	}


	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}


	public String getMfgDesc() {
		return mfgDesc;
	}


	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}


	public BigDecimal getMfgId() {
		return mfgId;
	}


	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}


	public String getMsdsNumber() {
		return msdsNumber;
	}


	public void setMsdsNumber(String msdsNumber) {
		this.msdsNumber = msdsNumber;
	}


	public String getMsdsOnLine() {
		return msdsOnLine;
	}


	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}


	public String getTradeName() {
		return tradeName;
	}


	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

}
