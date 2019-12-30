package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InvDetailOnHandMaterialBean <br>
 * @version: 1.0, May 13, 2005 <br>
 *****************************************************************************/

public class InventoryDetailOnHandMaterialBean
 extends BaseDataBean {

 private BigDecimal receiptId;
 private BigDecimal itemId;
 private String lotStatus;
 private String inventoryGroup;
 private BigDecimal quantity;
 private String mfgLot;
 private Date expireDate;
 private Date readyToShipDate;
 private String reference;
 private String notes;
 private Date dateOfReceipt;
 private String ownerSegmentId;
 private String programId;
 private String traceId;
 private String qualityTrackingNumber;
 private Collection receiptDocumentColl;

 //constructor
 public InventoryDetailOnHandMaterialBean() {
 }

 //setters
 public void setReceiptId(BigDecimal receiptId) {
	this.receiptId = receiptId;
 }

 public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
 }

 public void setLotStatus(String lotStatus) {
	this.lotStatus = lotStatus;
 }

 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setQuantity(BigDecimal quantity) {
	this.quantity = quantity;
 }

 public void setMfgLot(String mfgLot) {
	this.mfgLot = mfgLot;
 }

 public void setExpireDate(Date expireDate) {
	this.expireDate = expireDate;
 }

 public void setReadyToShipDate(Date readyToShipDate) {
	this.readyToShipDate = readyToShipDate;
 }

 public void setReference(String reference) {
	this.reference = reference;
 }

 public void setNotes(String notes) {
	this.notes = notes;
 }

 public void setDateOfReceipt(Date dateOfReceipt) {
	this.dateOfReceipt = dateOfReceipt;
 }

 public void setOwnerSegmentId(String ownerSegmentId) {
	this.ownerSegmentId = ownerSegmentId;
}

public void setProgramId(String programId) {
	this.programId = programId;
}

public void setQualityTrackingNumber(String qualityTrackingNumber) {
	this.qualityTrackingNumber = qualityTrackingNumber;
}

public void setReceiptDocumentColl(Collection receiptDocumentColl) {
	this.receiptDocumentColl = receiptDocumentColl;
}

//getters
 public BigDecimal getReceiptId() {
	return receiptId;
 }

 public BigDecimal getItemId() {
	return itemId;
 }

 public String getLotStatus() {
	return lotStatus;
 }

 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public BigDecimal getQuantity() {
	return quantity;
 }

 public String getMfgLot() {
	return mfgLot;
 }

 public Date getExpireDate() {
	return expireDate;
 }

 public Date getReadyToShipDate() {
	return readyToShipDate;
 }

 public String getReference() {
	return reference;
 }

 public String getNotes() {
	return notes;
 }

 public Date getDateOfReceipt() {
	return dateOfReceipt;
 }

public String getOwnerSegmentId() {
	return ownerSegmentId;
}

public String getProgramId() {
	return programId;
}

public String getTraceId() {
    return traceId;
}

public void setTraceId(String traceId) {
    this.traceId = traceId;
}

public String getQualityTrackingNumber() {
	return qualityTrackingNumber;
}

public Collection getReceiptDocumentColl() {
	return receiptDocumentColl;
}

}