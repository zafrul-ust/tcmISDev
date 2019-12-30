package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogBean <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class CatalogBean
 extends BaseDataBean {

 private String catalogId;
 private String catalogDesc;
 private String branchPlant;
 private String companyId;
 private String specLibrary;
 private String displayFlowDown;
 private String directedChargeByPart;
 private String consumableOption;

 //constructor
 public CatalogBean() {
 }

 //setters
 public void setCatalogId(String catalogId) {
	this.catalogId = catalogId;
 }

 public void setCatalogDesc(String catalogDesc) {
	this.catalogDesc = catalogDesc;
 }

 public void setBranchPlant(String branchPlant) {
	this.branchPlant = branchPlant;
 }

 public void setCompanyId(String companyId) {
	this.companyId = companyId;
 }

 public void setSpecLibrary(String specLibrary) {
	this.specLibrary = specLibrary;
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

 //getters
 public String getCatalogId() {
	return catalogId;
 }

 public String getCatalogDesc() {
	return catalogDesc;
 }

 public String getBranchPlant() {
	return branchPlant;
 }

 public String getCompanyId() {
	return companyId;
 }

 public String getSpecLibrary() {
	return specLibrary;
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
}