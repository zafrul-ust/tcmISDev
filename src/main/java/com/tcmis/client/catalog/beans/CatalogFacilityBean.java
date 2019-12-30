package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogFacilityBean <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class CatalogFacilityBean extends BaseDataBean {

	//catalog data
	private String catalogCompanyId;
	private String catalogId;
	private String catalogDesc;
	//catalog company data
	private String companyId;
	private String displayFlowDown;
	private String directedChargeByPart;
	private String consumableOption;
	private String labelSpecRequired;
	//catalog facility data
	private String facilityId;
	private String display;
	private String mrCreateFromCatalog;
	private String catalogAddAllowed;
	private String catAddAllowAllForApps;
	private String catPartAddAutoGenNullPn;
	private String customerCatAddProcess;
	private String qualityIdLabel;
	private String catPartAttributeHeader;
	private String catPartAttributeLabel1;
	private String catPartAttributeLabel2;
	private String label1SetFiChargeTypeVal;
	private String label2SetFiChargeTypeVal;
	private String label1SetCarnProdMatlVal;
	private String label2SetCarnProdMatlVal;

	//constructor
	public CatalogFacilityBean() {
	}

  	//setters
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public void setCatalogDesc(String catalogDesc) {
		this.catalogDesc = catalogDesc;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setDisplayFlowDown(String displayFlowDown) {
		this.displayFlowDown = displayFlowDown;
	}

	public void setDirectedChargeByPart(String directedChargeByPart) {
		this.directedChargeByPart = directedChargeByPart;
	}

	public void setConsumableOption(String consumableOption) {
		this.consumableOption = consumableOption;
	}

	public void setLabelSpecRequired(String labelSpecRequired) {
		this.labelSpecRequired = labelSpecRequired;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public void setMrCreateFromCatalog(String mrCreateFromCatalog) {
		this.mrCreateFromCatalog = mrCreateFromCatalog;
	}

	public void setCatalogAddAllowed(String catalogAddAllowed) {
		this.catalogAddAllowed = catalogAddAllowed;
	}

	public void setCatAddAllowAllForApps(String catAddAllowAllForApps) {
		this.catAddAllowAllForApps = catAddAllowAllForApps;
	}

	public void setCatPartAddAutoGenNullPn(String catPartAddAutoGenNullPn) {
		this.catPartAddAutoGenNullPn = catPartAddAutoGenNullPn;
	}

	public void setCustomerCatAddProcess(String customerCatAddProcess) {
		this.customerCatAddProcess = customerCatAddProcess;
	}

	//getters
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCompanyId() {
		return companyId;
	}

	public String getDisplayFlowDown() {
		return displayFlowDown;
	}

	public String getDirectedChargeByPart() {
		return directedChargeByPart;
	}

	public String getConsumableOption() {
		return consumableOption;
	}

	public String getLabelSpecRequired() {
		return labelSpecRequired;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getDisplay() {
		return display;
	}

	public String getMrCreateFromCatalog() {
		return mrCreateFromCatalog;
	}

	public String getCatalogAddAllowed() {
		return catalogAddAllowed;
	}

	public String getCatAddAllowAllForApps() {
		return catAddAllowAllForApps;
	}

	public String getCatPartAddAutoGenNullPn() {
		return catPartAddAutoGenNullPn;
	}

	public String getCustomerCatAddProcess() {
		return customerCatAddProcess;
	}

	public String getQualityIdLabel() {
		return qualityIdLabel;
	}

	public void setQualityIdLabel(String qualityIdLabel) {
		this.qualityIdLabel = qualityIdLabel;
	}

	public String getCatPartAttributeHeader() {
		return catPartAttributeHeader;
	}

	public void setCatPartAttributeHeader(String catPartAttributeHeader) {
		this.catPartAttributeHeader = catPartAttributeHeader;
	}

	public String getCatPartAttributeLabel1() {
		return catPartAttributeLabel1;
	}

	public void setCatPartAttributeLabel1(String catPartAttributeLabel1) {
		this.catPartAttributeLabel1 = catPartAttributeLabel1;
	}

	public String getCatPartAttributeLabel2() {
		return catPartAttributeLabel2;
	}

	public void setCatPartAttributeLabel2(String catPartAttributeLabel2) {
		this.catPartAttributeLabel2 = catPartAttributeLabel2;
	}

	public String getLabel1SetFiChargeTypeVal() {
		return label1SetFiChargeTypeVal;
	}

	public void setLabel1SetFiChargeTypeVal(String label1SetFiChargeTypeVal) {
		this.label1SetFiChargeTypeVal = label1SetFiChargeTypeVal;
	}

	public String getLabel2SetFiChargeTypeVal() {
		return label2SetFiChargeTypeVal;
	}

	public void setLabel2SetFiChargeTypeVal(String label2SetFiChargeTypeVal) {
		this.label2SetFiChargeTypeVal = label2SetFiChargeTypeVal;
	}

	public String getLabel1SetCarnProdMatlVal() {
		return label1SetCarnProdMatlVal;
	}

	public void setLabel1SetCarnProdMatlVal(String label1SetCarnProdMatlVal) {
		this.label1SetCarnProdMatlVal = label1SetCarnProdMatlVal;
	}

	public String getLabel2SetCarnProdMatlVal() {
		return label2SetCarnProdMatlVal;
	}

	public void setLabel2SetCarnProdMatlVal(String label2SetCarnProdMatlVal) {
		this.label2SetCarnProdMatlVal = label2SetCarnProdMatlVal;
	}
}