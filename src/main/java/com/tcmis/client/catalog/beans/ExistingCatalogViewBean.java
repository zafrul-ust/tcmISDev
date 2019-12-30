package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ExistingCatalogView Bean <br>
 * @version: 1.0, Dec 13, 2005 <br>
 *****************************************************************************/

public class ExistingCatalogViewBean
 extends BaseDataBean {

 private String companyId;
 private String catalogId;
 private String catPartNo;
 private BigDecimal partGroupNo;
 private BigDecimal itemId;
 private String status;
 private BigDecimal priority;
 private String kitPackaging;
 private String itemDesc;

 //constructor
 public ExistingCatalogViewBean() {
 }

 //setters
 public void setCompanyId(String companyId) {
	this.companyId = companyId;
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

 public void setStatus(String status) {
	this.status = status;
 }

 public void setPriority(BigDecimal priority) {
	this.priority = priority;
 }

 public void setKitPackaging(String kitPackaging) {
	this.kitPackaging = kitPackaging;
 }

 public void setItemDesc(String itemDesc) {
	this.itemDesc = itemDesc;
 }

 //getters
 public String getCompanyId() {
	return companyId;
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

 public String getStatus() {
	return status;
 }

 public BigDecimal getPriority() {
	return priority;
 }

 public String getKitPackaging() {
	return kitPackaging;
 }

 public String getItemDesc() {
	return itemDesc;
 }
}