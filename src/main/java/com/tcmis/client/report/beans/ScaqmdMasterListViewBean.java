package com.tcmis.client.report.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ScaqmdMasterListViewBean <br>
 * @version: 1.0, Dec 16, 2005 <br>
 *****************************************************************************/


public class ScaqmdMasterListViewBean extends BaseDataBean {

	private Date dateAddedToInventory;
	private String manufacturer;
	private String partDescription;
	private BigDecimal component;
	private String materialCategory;
	private String productIdMmAndEeNo;
	private String vocCoatingLbPerGal;
	private String vocMatlLbPerGal;
	private String mixingRatio;
	private String vocCompVaporPressureMmhg;
	private String forUseBy;
	private String activityOrSubstrateDesc;
	private String applicableDistrictRule;
	private String categoryComment;
	private String ruleSubcategory;


	//constructor
	public ScaqmdMasterListViewBean() {
	}

	//setters
	public void setDateAddedToInventory(Date dateAddedToInventory) {
		this.dateAddedToInventory = dateAddedToInventory;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setComponent(BigDecimal component) {
		this.component = component;
	}
	public void setMaterialCategory(String materialCategory) {
		this.materialCategory = materialCategory;
	}
	public void setProductIdMmAndEeNo(String productIdMmAndEeNo) {
		this.productIdMmAndEeNo = productIdMmAndEeNo;
	}
	public void setVocCoatingLbPerGal(String vocCoatingLbPerGal) {
		this.vocCoatingLbPerGal = vocCoatingLbPerGal;
	}
	public void setVocMatlLbPerGal(String vocMatlLbPerGal) {
		this.vocMatlLbPerGal = vocMatlLbPerGal;
	}
	public void setMixingRatio(String mixingRatio) {
		this.mixingRatio = mixingRatio;
	}
	public void setVocCompVaporPressureMmhg(String vocCompVaporPressureMmhg) {
		this.vocCompVaporPressureMmhg = vocCompVaporPressureMmhg;
	}
	public void setForUseBy(String forUseBy) {
		this.forUseBy = forUseBy;
	}
	public void setActivityOrSubstrateDesc(String activityOrSubstrateDesc) {
		this.activityOrSubstrateDesc = activityOrSubstrateDesc;
	}
	public void setApplicableDistrictRule(String applicableDistrictRule) {
		this.applicableDistrictRule = applicableDistrictRule;
	}
	public void setCategoryComment(String categoryComment) {
		this.categoryComment = categoryComment;
	}
	public void setRuleSubcategory(String ruleSubcategory) {
		this.ruleSubcategory = ruleSubcategory;
	}


	//getters
	public Date getDateAddedToInventory() {
		return dateAddedToInventory;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getComponent() {
		return component;
	}
	public String getMaterialCategory() {
		return materialCategory;
	}
	public String getProductIdMmAndEeNo() {
		return productIdMmAndEeNo;
	}
	public String getVocCoatingLbPerGal() {
		return vocCoatingLbPerGal;
	}
	public String getVocMatlLbPerGal() {
		return vocMatlLbPerGal;
	}
	public String getMixingRatio() {
		return mixingRatio;
	}
	public String getVocCompVaporPressureMmhg() {
		return vocCompVaporPressureMmhg;
	}
	public String getForUseBy() {
		return forUseBy;
	}
	public String getActivityOrSubstrateDesc() {
		return activityOrSubstrateDesc;
	}
	public String getApplicableDistrictRule() {
		return applicableDistrictRule;
	}
	public String getCategoryComment() {
		return categoryComment;
	}
	public String getRuleSubcategory() {
		return ruleSubcategory;
	}
}