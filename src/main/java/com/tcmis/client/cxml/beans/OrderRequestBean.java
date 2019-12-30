package com.tcmis.client.cxml.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PunchoutSetupRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class OrderRequestBean
    extends BaseDataBean {
//header
  private String payloadId;
  private String timestamp;
  private String fromDomain;
  private String fromIdentity;
  private String toDomain;
  private String toIdentity;
  private String senderDomain;
  private String senderIdentity;
  private String sharedSecret;
  private String userAgent;
//request
  private String deploymentMode;
  private String orderId;
  private String orderDate;
  private String orderType;
  private String type;
  private String currency;
  private String totalAmount;
  private String billToCountryCode;
  private String billToAddressId;
  private String billToName;
  private String billToPostalDeliverTo;
  private String billToPostalStreet;
  private String billToPostalCity;
  private String billToPostalState;
  private String billToPostalZip;
  private String billToPostalCountry;
  private String billToEmail;
  private String billToPhoneCountryNumber;
  private String billToPhoneAreaCode;
  private String billToPhoneNumber;
  private String billToPhoneExtension;
  private String billToUrl;
  private String paymentCardName;
  private String paymentCardNumber;
  private String paymentCardExpiration;
  private String comments;

  private HashMap additionalInfo = new HashMap();
  private ShipToBean shipToBean;
  private TaxBean taxBean;
  private Collection contactBeanCollection = new Vector();

  private Collection itemBeanCollection = new Vector();

  //constructor
  public OrderRequestBean() {
  }

  //setters
  public void setPayloadId(String payloadId) {
    this.payloadId = payloadId;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public void setFromDomain(String fromDomain) {
    this.fromDomain = fromDomain;
  }

  public void setFromIdentity(String fromIdentity) {
    this.fromIdentity = fromIdentity;
  }

  public void setToDomain(String toDomain) {
    this.toDomain = toDomain;
  }

  public void setToIdentity(String toIdentity) {
    this.toIdentity = toIdentity;
  }

  public void setSenderDomain(String senderDomain) {
    this.senderDomain = senderDomain;
  }

  public void setSenderIdentity(String senderIdentity) {
    this.senderIdentity = senderIdentity;
  }

  public void setSharedSecret(String s) {
    this.sharedSecret = s;
  }

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public void setDeploymentMode(String s) {
    this.deploymentMode = s;
  }

  public void setOrderId(String s) {
    this.orderId = s;
  }

  public void setOrderDate(String s) {
    this.orderDate = s;
  }

  public void setOrderType(String s) {
    this.orderType = s;
  }

  public void setType(String s) {
    this.type = s;
  }

  public void setCurrency(String s) {
    this.currency = s;
  }

  public void setTotalAmount(String s) {
    this.totalAmount = s;
  }

  public void setBillToCountryCode(String s) {
    this.billToCountryCode = s;
  }

  public void setBillToAddressId(String s) {
    this.billToAddressId = s;
  }

  public void setBillToName(String s) {
    this.billToName = s;
  }

  public void setBillToPostalDeliverTo(String s) {
    this.billToPostalDeliverTo = s;
  }

  public void setBillToPostalStreet(String s) {
    this.billToPostalStreet = s;
  }

  public void setBillToPostalCity(String s) {
    this.billToPostalCity = s;
  }

  public void setBillToPostalState(String s) {
    this.billToPostalState = s;
  }

  public void setBillToPostalZip(String s) {
    this.billToPostalZip = s;
  }

  public void setBillToPostalCountry(String s) {
    this.billToPostalCountry = s;
  }

  public void setBillToEmail(String s) {
    this.billToEmail = s;
  }

  public void setBillToPhoneCountryNumber(String s) {
    this.billToPhoneCountryNumber = s;
  }

  public void setBillToPhoneAreaCode(String s) {
    this.billToPhoneAreaCode = s;
  }

  public void setBillToPhoneNumber(String s) {
    this.billToPhoneNumber = s;
  }

  public void setBillToPhoneExtension(String s) {
    this.billToPhoneExtension = s;
  }

  public void setBillToUrl(String s) {
    this.billToUrl = s;
  }

  public void setPaymentCardName(String s) {
    this.paymentCardName = s;
  }

  public void setPaymentCardNumber(String s) {
    this.paymentCardNumber = s;
  }

  public void setPaymentCardExpiration(String s) {
    this.paymentCardExpiration = s;
  }

  public void setComments(String s) {
    this.comments = s;
  }

  public void setAdditionalInfo(HashMap h) {
    this.additionalInfo = h;
  }

  public void addAdditionalInfo(String key, String value) {
    this.additionalInfo.put(key, value);
  }

  public void setItemBeanCollection(Collection c) {
    this.itemBeanCollection = c;
  }

  public void addItemBean(ItemBean bean) {
    this.itemBeanCollection.add(bean);
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
  public String getPayloadId() {
    return payloadId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getFromDomain() {
    return fromDomain;
  }

  public String getFromIdentity() {
    return fromIdentity;
  }

  public String getToDomain() {
    return toDomain;
  }

  public String getToIdentity() {
    return toIdentity;
  }

  public String getSenderDomain() {
    return senderDomain;
  }

  public String getSenderIdentity() {
    return senderIdentity;
  }

  public String getSharedSecret() {
    return this.sharedSecret;
  }

  public String getUserAgent() {
    return userAgent;
  }

  public String getDeploymentMode() {
    return this.deploymentMode;
  }

  public String getOrderId() {
    return this.orderId;
  }

  public String getOrderDate() {
    return this.orderDate;
  }

  public String getOrderType() {
    return this.orderType;
  }

  public String getType() {
    return this.type;
  }

  public String getCurrency() {
    return this.currency;
  }

  public String getTotalAmount() {
    return this.totalAmount;
  }

  public String getBillToCountryCode() {
    return this.billToCountryCode;
  }

  public String getBillAddressId() {
    return this.billToAddressId;
  }

  public String getBillToName() {
    return this.billToName;
  }

  public String getBillToPostalDeliverTo() {
    return this.billToPostalDeliverTo;
  }

  public String getBillToPostalStreet() {
    return this.billToPostalStreet;
  }

  public String getBillToPostalCity() {
    return this.billToPostalCity;
  }

  public String getBillToPostalState() {
    return this.billToPostalState;
  }

  public String getBillToPostalZip() {
    return this.billToPostalZip;
  }

  public String getBillToPostalCountry() {
    return this.billToPostalCountry;
  }

  public String getBillToEmail() {
    return this.billToEmail;
  }

  public String getBillToPhoneCountryNumber() {
    return this.billToPhoneCountryNumber;
  }

  public String getBillToPhoneAreaCode() {
    return this.billToPhoneAreaCode;
  }

  public String getBillToPhoneNumber() {
    return this.billToPhoneNumber;
  }

  public String getBillToPhoneExtension() {
    return this.billToPhoneExtension;
  }

  public String getBillToUrl() {
    return this.billToUrl;
  }

  public String getPaymentCardName() {
    return this.paymentCardName;
  }

  public String getPaymentCardNumber() {
    return this.paymentCardNumber;
  }

  public String getPaymentCardExpiration() {
    return this.paymentCardExpiration;
  }

  public String getComments() {
    return this.comments;
  }

  public HashMap getAdditionalInfo() {
    return this.additionalInfo;
  }

  public Collection getItemBeanCollection() {
    return this.itemBeanCollection;
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