package com.tcmis.client.catalog.beans;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClientLabelBean {

 private String customerPo;
 private String expirationDate;
 private String recertExpDate;
 private String employeeNum;
 private String catPartNo;
 private String partDescription;
 private String shelfLife;
 private String labelQty;
 private String inventoryGroup;

 //constructor
 public ClientLabelBean() {
 }

 //setters
 public void setExpirationDate(String expirationDate) {
	this.expirationDate = expirationDate;
 }

 public void setRecertExpDate(String recertExpDate) {
	this.recertExpDate = recertExpDate;
 }

 public void setCustomerPo(String customerPo) {
	this.customerPo = customerPo;
 }

 public void setEmployeeNum(String employeeNum) {
	this.employeeNum = employeeNum;
 }

 public void setCatPartNo(String catPartNo) {
	this.catPartNo = catPartNo;
 }

 public void setPartDescription(String partDescription) {
	this.partDescription = partDescription;
 }

 public void setShelfLife(String shelfLife) {
	this.shelfLife = shelfLife;
 }

 public void setLabelQty(String labelQty) {
	this.labelQty = labelQty;
 }
 public void setInventoryGroup(String inventoryGroup) {
  this.inventoryGroup = inventoryGroup;
 }
  
 //getters
 public String getExpirationDate() {
	return this.expirationDate;
 }

 public String getRecertExpDate() {
	return this.recertExpDate;
 }

 public String getCustomerPo() {
	return this.customerPo;
 }

 public String getEmployeeNum() {
	return this.employeeNum;
 }

 public String getCatPartNo() {
	return catPartNo;
 }

 public String getPartDescription() {
	return partDescription;
 }

 public String getShelfLife() {
	return shelfLife;
 }

 public String getLabelQty() {
	return labelQty;
 }
 public String getInventoryGroup() {
  return inventoryGroup;
 }
}