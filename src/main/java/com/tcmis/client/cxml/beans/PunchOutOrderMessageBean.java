package com.tcmis.client.cxml.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PunchoutOrderMessageBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchOutOrderMessageBean
    extends BaseDataBean {

  private String payloadId;
  private String timestamp;
  private String fromDomain;
  private String fromIdentity;
  private String toDomain;
  private String toIdentity;
  private String senderDomain;
  private String senderIdentity;
  private String userAgent;
  private String deploymentMode;
  private String buyerCookie;
  private String quoteStatus;
  private String operationAllowed;
  private String currency;
  private String totalAmount;

  private Collection itemBeanCollection = new Vector();

  //constructor
  public PunchOutOrderMessageBean() {
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

  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }

  public void setDeploymentMode(String s) {
    this.deploymentMode = s;
  }

  public void setBuyerCookie(String buyerCookie) {
    this.buyerCookie = buyerCookie;
  }

  public void setQuoteStatus(String s) {
    this.quoteStatus = s;
  }

  public void setOperationAllowed(String s) {
    this.operationAllowed = s;
  }

  public void setTotalAmount(String s) {
    this.totalAmount = s;
  }

  public void setCurrency(String s) {
    this.currency = s;
  }

  public void setItemBeanCollection(Collection c) {
    this.itemBeanCollection = c;
  }

  public void addItemBean(ItemBean b) {
    this.itemBeanCollection.add(b);
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

  public String getUserAgent() {
    return userAgent;
  }

  public String getDeploymentMode() {
    return this.deploymentMode;
  }

  public String getBuyerCookie() {
    return buyerCookie;
  }

  public String getQuteStatus() {
    return this.quoteStatus;
  }

  public String getOperationAllowed() {
    return this.operationAllowed;
  }

  public String getTotalAmount() {
    return this.totalAmount;
  }

  public String getCurrency() {
    return this.currency;
  }

  public Collection getItemBeanCollection() {
    return this.itemBeanCollection;
  }
}