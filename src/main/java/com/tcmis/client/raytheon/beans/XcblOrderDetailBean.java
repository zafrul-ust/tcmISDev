package com.tcmis.client.raytheon.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailBean <br>
 * @version: 1.0, Jul 20, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailBean
    extends BaseDataBean {

  private BigDecimal xcblOrderId;
  private BigDecimal buyerLineItemNum;
  private String manufacturerPartId;
  private String sellerPartId;
  private String buyerPartId;
  private String itemDescription;
  private BigDecimal quantity;
  private String offCatalogFlag;
  private String uom;
  //private String partialShipmentFlag;
  private BigDecimal unitPrice;
  private String currency;
  private String notes = "";
  private BigDecimal id;
  private String priceBasisUom;
  private BigDecimal priceBasisQuantity;
  private Collection xcblOrderDetailReferenceBeanColl = new Vector();
  private Collection xcblOrderDetailScheduleBeanColl = new Vector();
  private Collection xcblOrderDetailIdentifierBeanColl = new Vector();

//these fields are for the response
  private String responseOrderStatus;
  private String responseOrderStatusNotes;
  private BigDecimal responseUnitPrice;
  private BigDecimal responseQuantity;
  private String responseUom;
  private String responseType;
  private String statusEvent;
  private Collection xcblOrderDetailOriginalScheduleBeanColl = new Vector();
  //constructor
  public XcblOrderDetailBean() {
  }

  //setters
  public void setXcblOrderId(BigDecimal xcblOrderId) {
    this.xcblOrderId = xcblOrderId;
  }

  public void setBuyerLineItemNum(BigDecimal buyerLineItemNum) {
    this.buyerLineItemNum = buyerLineItemNum;
  }

  public void setManufacturerPartId(String manufacturerPartId) {
    this.manufacturerPartId = manufacturerPartId;
  }

  public void setSellerPartId(String sellerPartId) {
    this.sellerPartId = sellerPartId;
  }

  public void setBuyerPartId(String buyerPartId) {
    this.buyerPartId = buyerPartId;
  }

  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setOffCatalogFlag(String offCatalogFlag) {
    this.offCatalogFlag = offCatalogFlag;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

//  public void setPartialShipmentFlag(String partialShipmentFlag) {
//    this.partialShipmentFlag = partialShipmentFlag;
//  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public void addNotes(String notes) {
    this.notes = this.notes.concat(notes);
    if(this.notes != null && this.notes.length() > 4000) {
      this.notes = this.notes.substring(0,3999);
    }
  }
  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public void setPriceBasisUom(String priceBasisUom) {
    this.priceBasisUom = priceBasisUom;
  }

  public void setPriceBasisQuantity(BigDecimal priceBasisQuantity) {
    this.priceBasisQuantity = priceBasisQuantity;
  }

  public void addXcblOrderDetailReferenceBean(XcblOrderDetailReferenceBean bean) {
    this.xcblOrderDetailReferenceBeanColl.add(bean);
  }

  public void setXcblOrderReferenceScheduleBeanColl(Collection
      xcblOrderDetailReferenceBeanColl) {
    this.xcblOrderDetailReferenceBeanColl = xcblOrderDetailReferenceBeanColl;
  }

  public void addXcblOrderDetailScheduleBean(XcblOrderDetailScheduleBean bean) {
    this.xcblOrderDetailScheduleBeanColl.add(bean);
  }

  public void setXcblOrderDetailScheduleBeanColl(Collection
                                                 xcblOrderDetailScheduleBeanColl) {
    this.xcblOrderDetailScheduleBeanColl = xcblOrderDetailScheduleBeanColl;
  }

  public void addXcblOrderDetailIdentifierBean(XcblOrderDetailIdentifierBean bean) {
    this.xcblOrderDetailIdentifierBeanColl.add(bean);
  }

  public void setXcblOrderDetailIdentifierBeanColl(Collection
                                                 xcblOrderDetailIdentifierBeanColl) {
    this.xcblOrderDetailIdentifierBeanColl = xcblOrderDetailIdentifierBeanColl;
  }

  public void setResponseOrderStatus(String s) {
    this.responseOrderStatus = s;
  }

  public void setResponseOrderStatusNotes(String s) {
    this.responseOrderStatusNotes = s;
  }

  public void setResponseUnitPrice(BigDecimal b) {
    this.responseUnitPrice = b;
  }

  public void setResponseQuantity(BigDecimal b) {
    this.responseQuantity = b;
  }

  public void setResponseUom(String s) {
    this.responseUom = s;
  }

  public void setStatusEvent(String s) {
    this.statusEvent = s;
  }

  public void setResponseType(String s) {
    this.responseType = s;
  }

  public void addXcblOrderDetailOriginalScheduleBean(XcblOrderDetailScheduleBean bean) {
    this.xcblOrderDetailOriginalScheduleBeanColl.add(bean);
  }

  public void setXcblOrderDetailOriginalScheduleBeanColl(Collection
                                                 xcblOrderDetailScheduleBeanColl) {
    this.xcblOrderDetailOriginalScheduleBeanColl = xcblOrderDetailScheduleBeanColl;
  }
  //getters
  public BigDecimal getXcblOrderId() {
    return xcblOrderId;
  }

  public BigDecimal getBuyerLineItemNum() {
    return buyerLineItemNum;
  }

  public String getManufacturerPartId() {
    return manufacturerPartId;
  }

  public String getSellerPartId() {
    return sellerPartId;
  }

  public String getBuyerPartId() {
    return buyerPartId;
  }

  public String getItemDescription() {
    return itemDescription;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getOffCatalogFlag() {
    return offCatalogFlag;
  }

  public String getUom() {
    return uom;
  }

//  public String getPartialShipmentFlag() {
//    return partialShipmentFlag;
//  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public String getCurrency() {
    return currency;
  }

  public String getNotes() {
    return notes;
  }

  public BigDecimal getId() {
    return id;
  }

  public String getPriceBasisUom() {
    return priceBasisUom;
  }

  public BigDecimal getPriceBasisQuantity() {
    return priceBasisQuantity;
  }

  public Collection getXcblOrderDetailReferenceBeanColl() {
    return this.xcblOrderDetailReferenceBeanColl;
  }

  public Collection getXcblOrderDetailScheduleBeanColl() {
    return this.xcblOrderDetailScheduleBeanColl;
  }

  public Collection getXcblOrderDetailIdentifierBeanColl() {
    return this.xcblOrderDetailIdentifierBeanColl;
  }

  public String getResponseOrderStatus() {
    return this.responseOrderStatus;
  }

  public String getResponseOrderStatusNotes() {
    return this.responseOrderStatusNotes;
  }

  public BigDecimal getResponseUnitPrice() {
    return this.responseUnitPrice;
  }

  public BigDecimal getResponseQuantity() {
    return this.responseQuantity;
  }

  public String getResponseUom() {
    return responseUom;
  }

  public String getResponseType() {
    return this.responseType;
  }

  public String getStatusEvent() {
    return this.statusEvent;
  }

  public Collection getXcblOrderDetailOriginalScheduleBeanColl() {
    return this.xcblOrderDetailOriginalScheduleBeanColl;
  }
}