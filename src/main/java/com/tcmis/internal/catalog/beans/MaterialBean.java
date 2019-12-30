
package com.tcmis.internal.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: MaterialBean <br>
 * @version: 1.0, Jan 10, 2008 <br>
 *****************************************************************************/


public class MaterialBean extends BaseDataBean {

	private String companyId;
	private BigDecimal materialId;
	private String materialDesc;
	private String materialCategory;
	private BigDecimal mfgId;
	private String hazardClass;
	private String tradeName;
	private String packingGroup;
	private String unNumber;
	private String naNumber;
	private String subsidiaryHazardClass;
	private String groundShippingName;
	private String airShippingName;
	private String emergencyResponseGuideNo;
	private String review;
	private String dryIce;
	private String erg;
	private String msdsOnLine;
	private String productCode;
	private String customerOnlyMsds;

	// manufacturer data
	private String mfgDesc;
	private String countryAbbrev;


	//constructor
	public MaterialBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public void setMaterialCategory(String materialCategory) {
		this.materialCategory = materialCategory;
	}
	public void setMfgId(BigDecimal mfgId) {
		this.mfgId = mfgId;
	}
	public void setHazardClass(String hazardClass) {
		this.hazardClass = hazardClass;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}
	public void setUnNumber(String unNumber) {
		this.unNumber = unNumber;
	}
	public void setNaNumber(String naNumber) {
		this.naNumber = naNumber;
	}
	public void setSubsidiaryHazardClass(String subsidiaryHazardClass) {
		this.subsidiaryHazardClass = subsidiaryHazardClass;
	}
	public void setGroundShippingName(String groundShippingName) {
		this.groundShippingName = groundShippingName;
	}
	public void setAirShippingName(String airShippingName) {
		this.airShippingName = airShippingName;
	}
	public void setEmergencyResponseGuideNo(String emergencyResponseGuideNo) {
		this.emergencyResponseGuideNo = emergencyResponseGuideNo;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public void setDryIce(String dryIce) {
		this.dryIce = dryIce;
	}

	public void setMsdsOnLine(String msdsOnLine) {
		this.msdsOnLine = msdsOnLine;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public void setCustomerOnlyMsds(String customerOnlyMsds) {
		this.customerOnlyMsds = customerOnlyMsds;
	}
	//getters
	public String getCustomerOnlyMsds() {
		return customerOnlyMsds;
	}
	public String getProductCode() {
		return productCode;
	}
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getMaterialId() {
		return materialId;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public String getMaterialCategory() {
		return materialCategory;
	}
	public BigDecimal getMfgId() {
		return mfgId;
	}
	public String getHazardClass() {
		return hazardClass;
	}
	public String getTradeName() {
		return tradeName;
	}
	public String getPackingGroup() {
		return packingGroup;
	}
	public String getUnNumber() {
		return unNumber;
	}
	public String getNaNumber() {
		return naNumber;
	}
	public String getSubsidiaryHazardClass() {
		return subsidiaryHazardClass;
	}
	public String getGroundShippingName() {
		return groundShippingName;
	}
	public String getAirShippingName() {
		return airShippingName;
	}
	public String getEmergencyResponseGuideNo() {
		return emergencyResponseGuideNo;
	}
	public String getReview() {
		return review;
	}
	public String getDryIce() {
		return dryIce;
	}
	public String getMsdsOnLine() {
		return msdsOnLine;
	}
	public String getMfgDesc() {
		return mfgDesc;
	}
	public void setMfgDesc(String mfgDesc) {
		this.mfgDesc = mfgDesc;
	}
	public String getCountryAbbrev() {
		return countryAbbrev;
	}
	public void setCountryAbbrev(String countryAbbrev) {
		this.countryAbbrev = countryAbbrev;
	}

	public String getErg() {
		return erg;
	}

	public void setErg(String erg) {
		this.erg = erg;
	}
}
