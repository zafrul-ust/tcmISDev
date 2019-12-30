package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ShoppingCartViewBean <br>
 * @version: 1.0, Jun 16, 2006 <br>
 *****************************************************************************/

public class ShoppingCartViewBean
    extends BaseDataBean {

  private BigDecimal prNumber;
  private String payloadId;
  private String lineItem;
  private BigDecimal itemId;
  private BigDecimal quantity;
  private BigDecimal quotedPrice;
  private BigDecimal extendedPrice;
  private BigDecimal prepaidAmount;
  private String accountSysId;
  private String accountSysDesc;
  private String application;
  private String shipToLocationId;
  private String addressLine11;
  private String addressLine22;
  private String addressLine33;
  private String city;
  private String stateAbbrev;
  private String zip;
  private String countryAbbrev;
  private String materialDesc;
  private String packaging;
  private String facPartNo;
  private String itemType;
  private BigDecimal entryId;
  private String useFacilityId;

  //constructor
  public ShoppingCartViewBean() {
  }

  //setters
  public void setPrNumber(BigDecimal prNumber) {
    this.prNumber = prNumber;
  }

  public void setPayloadId(String payloadId) {
    this.payloadId = payloadId;
  }

  public void setLineItem(String lineItem) {
    this.lineItem = lineItem;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setQuotedPrice(BigDecimal quotedPrice) {
    this.quotedPrice = quotedPrice;
  }

  public void setExtendedPrice(BigDecimal extendedPrice) {
    this.extendedPrice = extendedPrice;
  }

  public void setPrepaidAmount(BigDecimal prepaidAmount) {
    this.prepaidAmount = prepaidAmount;
  }

  public void setAccountSysId(String accountSysId) {
    this.accountSysId = accountSysId;
  }

  public void setAccountSysDesc(String accountSysDesc) {
    this.accountSysDesc = accountSysDesc;
  }

  public void setApplication(String application) {
    this.application = application;
  }

  public void setShipToLocationId(String shipToLocationId) {
    this.shipToLocationId = shipToLocationId;
  }

  public void setAddressLine1(String addressLine11) {
    this.addressLine11 = addressLine11;
  }

  public void setAddressLine2(String addressLine22) {
    this.addressLine22 = addressLine22;
  }

  public void setAddressLine3(String addressLine33) {
    this.addressLine33 = addressLine33;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setStateAbbrev(String stateAbbrev) {
    this.stateAbbrev = stateAbbrev;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public void setCountryAbbrev(String countryAbbrev) {
    this.countryAbbrev = countryAbbrev;
  }

  public void setMaterialDesc(String materialDesc) {
    this.materialDesc = materialDesc;
  }

  public void setPackaging(String packaging) {
    this.packaging = packaging;
  }

  public void setFacPartNo(String facPartNo) {
    this.facPartNo = facPartNo;
  }

  public void setItemType(String itemType) {
    this.itemType = itemType;
  }

  public void setEntryId(BigDecimal entryId) {
    this.entryId = entryId;
  }

  public void setUseFacilityId(String useFacilityId) {
    this.useFacilityId = useFacilityId;
  }

  //getters
  public BigDecimal getPrNumber() {
    return prNumber;
  }

  public String getPayloadId() {
    return payloadId;
  }

  public String getLineItem() {
    return lineItem;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getQuotedPrice() {
    return quotedPrice;
  }

  public BigDecimal getExtendedPrice() {
    return extendedPrice;
  }

  public BigDecimal getPrepaidAmount() {
    return prepaidAmount;
  }

  public String getAccountSysId() {
    return accountSysId;
  }

  public String getAccountSysDesc() {
    return accountSysDesc;
  }

  public String getApplication() {
    return application;
  }

  public String getShipToLocationId() {
    return shipToLocationId;
  }

  public String getAddressLine1() {
    return addressLine11;
  }

  public String getAddressLine2() {
    return addressLine22;
  }

  public String getAddressLine3() {
    return addressLine33;
  }

  public String getCity() {
    return city;
  }

  public String getStateAbbrev() {
    return stateAbbrev;
  }

  public String getZip() {
    return zip;
  }

  public String getCountryAbbrev() {
    return countryAbbrev;
  }

  public String getMaterialDesc() {
    return materialDesc;
  }

  public String getPackaging() {
    return packaging;
  }

  public String getFacPartNo() {
    return facPartNo;
  }

  public String getItemType() {
    return itemType;
  }

  public BigDecimal getEntryId() {
    return entryId;
  }

  public String getUseFacilityId() {
    return useFacilityId;
  }
}