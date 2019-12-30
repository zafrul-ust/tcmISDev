package com.tcmis.client.raytheon.beans;
import java.util.Vector;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemDetailDataBean <br>
 * @version: 1.0, Jun 29, 2005 <br>
 *****************************************************************************/

public class ItemDetailDataBean
    extends BaseDataBean {

  private java.lang.String buyerLineItemNum;
  private java.lang.String sellerPartId;
  private java.lang.String buyerPartId;
  private java.lang.String manufacturerPartId;
  private java.lang.String manufacturerAgencyCoded;
  private java.lang.String manufacturerIdent;
  private java.lang.String itemDescription;
  private java.lang.String quantityValue;
  private java.lang.String uomCoded;
  private java.lang.String offCatalogFlag;
  private java.lang.String monetaryAmount;
  private java.util.Collection productIdentifierCodedColl = new Vector();
  private java.util.Collection itemReferenceColl = new Vector();
  private java.util.Collection priceColl = new Vector();
  private java.util.Collection scheduleLineColl = new Vector();

  //constructor
  public ItemDetailDataBean() {
  }

  //setters
  public void setBuyerLineItemNum(java.lang.String buyerLineItemNum) {
    this.buyerLineItemNum = buyerLineItemNum;
  }

  public void setSellerPartId(java.lang.String sellerPartId) {
    this.sellerPartId = sellerPartId;
  }

  public void setBuyerPartId(java.lang.String buyerPartId) {
    this.buyerPartId = buyerPartId;
  }

  public void setManufacturerPartId(java.lang.String manufacturerPartId) {
    this.manufacturerPartId = manufacturerPartId;
  }

  public void setManufacturerAgencyCoded(java.lang.String manufacturerAgencyCoded) {
    this.manufacturerAgencyCoded = manufacturerAgencyCoded;
  }

  public void setManufacturerIdent(java.lang.String manufacturerIdent) {
    this.manufacturerIdent = manufacturerIdent;
  }

  public void setItemDescription(java.lang.String itemDescription) {
    this.itemDescription = itemDescription;
  }

  public void setQuantityValue(java.lang.String quantityValue) {
    this.quantityValue = quantityValue;
  }

  public void setUomCoded(java.lang.String uomCoded) {
    this.uomCoded = uomCoded;
  }

  public void setOffCatalogFlag(java.lang.String offCatalogFlag) {
    this.offCatalogFlag = offCatalogFlag;
  }

  public void setMonetaryAmount(java.lang.String monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }

  public void addProductIdentifierCodedDataBean(ProductIdentifierCodedDataBean bean) {
    this.productIdentifierCodedColl.add(bean);
  }

  public void setProductIdentifierCodedColl(java.util.Collection
                                            productIdentifierCodedColl) {
    this.productIdentifierCodedColl = productIdentifierCodedColl;
  }

  public void addItemReferenceDataBean(ItemReferenceDataBean bean) {
    this.itemReferenceColl.add(bean);
  }

  public void setItemReferenceColl(java.util.Collection itemReferenceColl) {
    this.itemReferenceColl = itemReferenceColl;
  }

  public void addPriceDataBean(PriceDataBean bean) {
    this.priceColl.add(bean);
  }

  public void setPriceColl(java.util.Collection priceColl) {
    this.priceColl = priceColl;
  }

  public void addScheduleLineDataBean(ScheduleLineDataBean bean) {
    this.scheduleLineColl.add(bean);
  }

  public void setScheduleLineColl(java.util.Collection scheduleLineColl) {
    this.scheduleLineColl = scheduleLineColl;
  }

  //getters
  public java.lang.String getBuyerLineItemNum() {
    return buyerLineItemNum;
  }

  public java.lang.String getSellerPartId() {
    return sellerPartId;
  }

  public java.lang.String getBuyerPartId() {
    return buyerPartId;
  }

  public java.lang.String getManufacturerPartId() {
    return manufacturerPartId;
  }

  public java.lang.String getManufacturerAgencyCoded() {
    return manufacturerAgencyCoded;
  }

  public java.lang.String getManufacturerIdent() {
    return manufacturerIdent;
  }

  public java.lang.String getItemDescription() {
    return itemDescription;
  }

  public java.lang.String getQuantityValue() {
    return quantityValue;
  }

  public java.lang.String getUomCoded() {
    return uomCoded;
  }

  public java.lang.String getOffCatalogFlag() {
    return offCatalogFlag;
  }

  public java.lang.String getMonetaryAmount() {
    return monetaryAmount;
  }

  public java.util.Collection getProductIdentifierCodedColl() {
    return productIdentifierCodedColl;
  }

  public java.util.Collection getItemReferenceColl() {
    return itemReferenceColl;
  }

  public java.util.Collection getPriceColl() {
    return priceColl;
  }

  public java.util.Collection getScheduleLineColl() {
    return scheduleLineColl;
  }
}