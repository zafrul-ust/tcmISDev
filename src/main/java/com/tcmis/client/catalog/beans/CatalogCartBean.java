package com.tcmis.client.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;
import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: CatalogBean <br>
 * @version: 1.0, Jul 11, 2005 <br>
 *****************************************************************************/

public class CatalogCartBean
 extends BaseDataBean {

 private String catalogId;
 private String catalogDesc;
 private String branchPlant;
 private String companyId;
 private String specLibrary;
 private String displayFlowDown;
 private String directedChargeByPart;
 private String consumableOption;

 private BigDecimal NEW_ITEM_QUANTITY;
 private String NEW_ITEM_MATNR;
 private String NEW_ITEM_DESCRIPTION;
 //constructor
 public CatalogCartBean() {
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

public String getNEW_ITEM_MATNR() {
	return NEW_ITEM_MATNR;
}

public void setNEW_ITEM_MATNR(String new_item_matnr) {
	NEW_ITEM_MATNR = new_item_matnr;
}

public BigDecimal getNEW_ITEM_QUANTITY() {
	return NEW_ITEM_QUANTITY;
}

public void setNEW_ITEM_QUANTITY(BigDecimal new_item_quantity) {
	NEW_ITEM_QUANTITY = new_item_quantity;
}

public String getNEW_ITEM_DESCRIPTION() {
	return NEW_ITEM_DESCRIPTION;
}

public void setNEW_ITEM_DESCRIPTION(String new_item_description) {
	NEW_ITEM_DESCRIPTION = new_item_description;
}
}