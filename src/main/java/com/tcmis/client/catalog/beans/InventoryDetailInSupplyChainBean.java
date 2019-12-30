package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvDetailInSupplyChainBean <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class InventoryDetailInSupplyChainBean
 extends BaseDataBean {

 private BigDecimal refNo;
 private String inventoryGroup;
 private BigDecimal itemId;
 private String status;
 private BigDecimal quantity;
 private String reference;
 private Date readyToShipDate;
 private String notes;

 //constructor
 public InventoryDetailInSupplyChainBean() {
 }

 //setters
 public void setRefNo(BigDecimal refNo) {
	this.refNo = refNo;
 }

 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setStatus(String status) {
	this.status = status;
 }

 public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
 }

 public void setReference(String reference) {
	this.reference = reference;
 }

 public void setReadyToShipDate(Date readyToShipDate) {
	this.readyToShipDate = readyToShipDate;
 }

 public void setNotes(String notes) {
	this.notes = notes;
 }

 //getters
 public BigDecimal getRefNo() {
	return refNo;
 }

 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public String getStatus() {
	return status;
 }

 public BigDecimal getQuantity() {
	return quantity;
 }

 public String getReference() {
	return reference;
 }

 public Date getReadyToShipDate() {
	return readyToShipDate;
 }

 public String getNotes() {
	return notes;
 }
}