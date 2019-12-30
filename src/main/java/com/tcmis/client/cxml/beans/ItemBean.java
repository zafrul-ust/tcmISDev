package com.tcmis.client.cxml.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class ItemBean
    extends BaseDataBean {

  private String quantity;
  private String agreementItemNumber;
  private String requestedDeliveryDate;
  private String supplierPartId;
  private String supplierPartAuxiliaryId;
  private String currency;
  private String unitPrice;
  private String description;
  private String unitOfMeasure;
  private String classificationDomain;
  private String classification;
  private String manufacturerPartId;
  private String manufacturerName;
  private String url;
/*
  private String shipToName;
  private String shipToPostalDeliverTo;
  private String shipToPostalStreet;
  private String shipToPostalCity;
  private String shipToPostalState;
  private String shipToPostalZip;
  private String shipToPostalCountry;
  private String shipToEmail;
  private String shipToPhoneCountryNumber;
  private String shipToPhoneAreaCode;
  private String shipToPhoneNumber;
  private String shipToPhoneExtension;
  private String taxCurrency;
  private String taxDescription;
  private String taxCategory;
  private String taxPercent;
  private String taxAmount;
  private String taxLocation;
*/
  private String comments;
/*
  private String supplierName;
  private String supplierComments;
  private String supplierDomain;
  private String supplierId;
  private String supplierPostalDeliverTo;
  private String supplierPostalStreet;
  private String supplierPostalCity;
  private String supplierPostalState;
  private String supplierPostalZip;
  private String supplierPostalCountry;
  private String supplierEmail;
  private String supplierPhoneCountryNumber;
  private String supplierPhoneAreaCode;
  private String supplierPhoneNumber;
  private String supplierPhoneExtension;
*/
  private HashMap additionalInfo = new HashMap();
  private ShipToBean shipToBean;
  private TaxBean taxBean;
  private Collection contactBeanCollection = new Vector();

  //constructor
  public ItemBean() {
  }

  //setters
  public void setQuantity(String s) {
    this.quantity = s;
  }

  public void setAgreementItemNumber(String s) {
    this.agreementItemNumber = s;
  }

  public void setRequestedDeliveryDate(String s) {
    this.requestedDeliveryDate = s;
  }

  public void setSupplierPartId(String s) {
    this.supplierPartId = s;
  }

  public void setSupplierPartAuxiliaryId(String s) {
    this.supplierPartAuxiliaryId = s;
  }

  public void setCurrency(String s) {
    this.currency = s;
  }

  public void setUnitPrice(String s) {
    this.unitPrice = s;
  }

  public void setDescription(String s) {
    this.description = s;
  }

  public void setUnitOfMeasure(String s) {
    this.unitOfMeasure = s;
  }

  public void setClassificationDomain(String s) {
    this.classificationDomain = s;
  }

  public void setClassification(String s) {
    this.classification = s;
  }

  public void setManufacturerPartId(String s) {
    this.manufacturerPartId = s;
  }

  public void setManufacturerName(String s) {
    this.manufacturerName = s;
  }

  public void setUrl(String s) {
    this.url = s;
  }
/*
  public void setShipToName(String s) {
    this.shipToName = s;
  }

  public void setShipToPostalDeliverTo(String s) {
    this.shipToPostalDeliverTo = s;
  }

  public void setShipToPostalStreet(String s) {
    this.shipToPostalStreet = s;
  }

  public void setShipToPostalCity(String s) {
    this.shipToPostalCity = s;
  }

  public void setShipToPostalState(String s) {
    this.shipToPostalState = s;
  }

  public void setShipToPostalZip(String s) {
    this.shipToPostalZip = s;
  }

  public void setShipToPostalCountry(String s) {
    this.shipToPostalCountry = s;
  }

  public void setShipToEmail(String s) {
    this.shipToEmail = s;
  }

  public void setShipToPhoneCountryNumber(String s) {
    this.shipToPhoneCountryNumber = s;
  }

  public void setShipToPhoneAreaCode(String s) {
    this.shipToPhoneAreaCode = s;
  }

  public void setShipToPhoneNumber(String s) {
    this.shipToPhoneNumber = s;
  }

  public void setShipToPhoneExtension(String s) {
    this.shipToPhoneExtension = s;
  }

  public void setTaxCurrency(String s) {
    this.taxCurrency = s;
  }

  public void setTaxDescription(String s) {
    this.taxDescription = s;
  }

  public void setTaxCategory(String s) {
    this.taxCategory = s;
  }

  public void setTaxPercent(String s) {
    this.taxPercent = s;
  }

  public void setTaxAmount(String s) {
    this.taxAmount = s;
  }

  public void setTaxLocation(String s) {
    this.taxLocation = s;
  }
*/
  public void setComments(String s) {
    this.comments = s;
  }

  public void setAdditionalInfo(HashMap h) {
    this.additionalInfo = h;
  }

  public void addAdditionalInfo(String key, String value) {
    this.additionalInfo.put(key, value);
  }

  public void setShipToBean(ShipToBean b) {
    this.shipToBean = b;
  }

  public void setTaxBean(TaxBean b) {
    this.taxBean = b;
  }

  public void setContactBeanCollection(Collection c) {
    this.contactBeanCollection = c;
  }

  public void addContactBean(ContactBean b) {
    this.contactBeanCollection.add(b);
  }
  //getters
  public String getQuantity() {
    return this.quantity;
  }

  public String getAgreementItemNumber() {
    return this.agreementItemNumber;
  }

  public String getRequestedDeliveryDate() {
    return this.requestedDeliveryDate;
  }

  public String getSupplierPartId() {
    return this.supplierPartId;
  }

  public String getSupplierPartAuxiliaryId() {
    return this.supplierPartAuxiliaryId;
  }

  public String getCurrency() {
    return this.currency;
  }

  public String getUnitPrice() {
    return this.unitPrice;
  }

  public String getDescription() {
    return this.description;
  }

  public String getUnitOfMeasure() {
    return this.unitOfMeasure;
  }

  public String getClassificationDomain() {
    return this.classificationDomain;
  }

  public String getClassification() {
    return this.classification;
  }

  public String getManufacturerPartId() {
    return this.manufacturerPartId;
  }

  public String getManufacturerName() {
    return this.manufacturerName;
  }

  public String getUrl() {
    return this.url;
  }
/*
  public String getShipToName() {
    return this.shipToName;
  }

  public String getShipToPostalDeliverTo() {
    return this.shipToPostalDeliverTo;
  }

  public String getShipToPostalStreet() {
    return this.shipToPostalStreet;
  }

  public String getShipToPostalCity() {
    return this.shipToPostalCity;
  }

  public String getShipToPostalState() {
    return this.shipToPostalState;
  }

  public String getShipToPostalZip() {
    return this.shipToPostalZip;
  }

  public String getShipToPostalCountry() {
    return this.shipToPostalCountry;
  }

  public String getShipToEmail() {
    return this.shipToEmail;
  }

  public String getShipToPhoneCountryNumber() {
    return this.shipToPhoneCountryNumber;
  }

  public String getShipToPhoneAreaCode() {
    return this.shipToPhoneAreaCode;
  }

  public String getShipToPhoneNumber() {
    return this.shipToPhoneNumber;
  }

  public String getShipToPhoneExtension() {
    return this.shipToPhoneExtension;
  }

  public String getTaxCurrency() {
    return this.taxCurrency;
  }

  public String getTaxDescription() {
    return this.taxDescription;
  }

  public String getTaxCategory() {
    return this.taxCategory;
  }

  public String getTaxPercent() {
    return this.taxPercent;
  }

  public String getTaxAmount() {
    return this.taxAmount;
  }

  public String getTaxLocation() {
    return this.taxLocation;
  }
*/
  public String getComments() {
    return this.comments;
  }

  public HashMap getAdditionalInfo() {
    return this.additionalInfo;
  }

  public ShipToBean getShipToBean() {
    return this.shipToBean;
  }

  public TaxBean getTaxBean() {
    return this.taxBean;
  }

  public Collection getContactBeanCollection() {
    return this.contactBeanCollection;
  }
}