package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: XbuyViewBean <br>
 * @version: 1.0, Oct 8, 2008 <br>
 *****************************************************************************/


public class XbuyViewBean extends BaseDataBean {
   
   private BigDecimal radianPo;
   private BigDecimal poLine;
   private BigDecimal quantity;
   private BigDecimal unitPrice;
   private String uom;
   private String supplierPartNo;
   private Date vendorShipDate;
   private Date promisedDate;
   private String supplierAccountNumber;
   private String vmiLocationCode;
   private String supplierLocationCode;
   private String supplierRegionCode;
   private String originInspectionRequired;
   private BigDecimal priorityRating;
   private String deliveryComments;
   private String notes;
   private String originalTransactionType;
   private String shipToLocationId;
   
   //constructor
   public XbuyViewBean() {
   }
   
   //setters
   public void setRadianPo(BigDecimal radianPo) {
      this.radianPo = radianPo;
   }
   public void setPoLine(BigDecimal poLine) {
      this.poLine = poLine;
   }
   public void setQuantity(BigDecimal quantity) {
      this.quantity = quantity;
   }
   public void setUnitPrice(BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
   }
   public void setUom(String uom) {
      this.uom = uom;
   }
   public void setSupplierPartNo(String supplierPartNo) {
      this.supplierPartNo = supplierPartNo;
   }
   public void setVendorShipDate(Date vendorShipDate) {
      this.vendorShipDate = vendorShipDate;
   }
   public void setPromisedDate(Date promisedDate) {
      this.promisedDate = promisedDate;
   }
   public void setSupplierAccountNumber(String supplierAccountNumber) {
      this.supplierAccountNumber = supplierAccountNumber;
   }
   public void setVmiLocationCode(String vmiLocationCode) {
      this.vmiLocationCode = vmiLocationCode;
   }
   public void setSupplierLocationCode(String supplierLocationCode) {
      this.supplierLocationCode = supplierLocationCode;
   }
   public void setSupplierRegionCode(String supplierRegionCode) {
      this.supplierRegionCode = supplierRegionCode;
   }
   public void setOriginInspectionRequired(String originInspectionRequired) {
      this.originInspectionRequired = originInspectionRequired;
   }
   public void setPriorityRating(BigDecimal priorityRating) {
      this.priorityRating = priorityRating;
   }
   public void setDeliveryComments(String deliveryComments) {
      this.deliveryComments = deliveryComments;
   }
   public void setNotes(String notes) {
      this.notes = notes;
   }
   public void setOriginalTransactionType(String originalTransactionType) {
      if (originalTransactionType == null) {
         originalTransactionType = "";
      }
      this.originalTransactionType = originalTransactionType;
   }
   public void setShipToLocationId(String shipToLocationId) {
      this.shipToLocationId = shipToLocationId;
   }
   
   //getters
   public BigDecimal getRadianPo() {
      return radianPo;
   }
   public BigDecimal getPoLine() {
      return poLine;
   }
   public BigDecimal getQuantity() {
      return quantity;
   }
   public BigDecimal getUnitPrice() {
      return unitPrice;
   }
   public String getUom() {
      return uom;
   }
   public String getSupplierPartNo() {
      return supplierPartNo;
   }
   public Date getVendorShipDate() {
      return vendorShipDate;
   }
   public Date getPromisedDate() {
      return promisedDate;
   }
   public String getSupplierAccountNumber() {
      return supplierAccountNumber;
   }
   public String getVmiLocationCode() {
      return vmiLocationCode;
   }
   public String getSupplierLocationCode() {
      return supplierLocationCode;
   }
   public String getSupplierRegionCode() {
      return supplierRegionCode;
   }
   public String getOriginInspectionRequired() {
      return originInspectionRequired;
   }
   public BigDecimal getPriorityRating() {
      return priorityRating;
   }
   public String getDeliveryComments() {
      return deliveryComments;
   }
   public String getNotes() {
      return notes;
   }
   public String getOriginalTransactionType() {
      return originalTransactionType;
   }
   public String getShipToLocationId() {
      return shipToLocationId;
   }
}