package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PunchoutSetupRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class PunchOutSetupRequestBean
    extends BaseDataBean {

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
  private String operation;
  private String buyerCookie;
  private String extrinsicName;
  private String browserFormPost;
  private String contactName;
  private String contactEmail;
  private String supplierSetupUrl;
  private HashMap additionalInfo = new HashMap();


  //constructor
  public PunchOutSetupRequestBean() {
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

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public void setBuyerCookie(String buyerCookie) {
    this.buyerCookie = buyerCookie;
  }

  public void setExtrinsicName(String s) {
    this.extrinsicName = s;
  }

  public void setBrowserFormPost(String browserFormPost) {
    this.browserFormPost = browserFormPost;
  }

  public void setContactName(String s) {
    this.contactName = s;
  }

  public void setContactEmail(String s) {
    this.contactEmail = s;
  }

  public void setSupplierSetupUrl(String s) {
    this.supplierSetupUrl = s;
  }

  public void setAdditionalInfo(HashMap h) {
    this.additionalInfo = h;
  }

  public void addAdditionalInfo(String key, String value) {
    this.additionalInfo.put(key, value);
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

  public String getOperation() {
    return operation;
  }

  public String getBuyerCookie() {
    return buyerCookie;
  }

  public String getBrowserFormPost() {
    return browserFormPost;
  }

  public String getExtrinsicName() {
    return this.extrinsicName;
  }

  public String getContactName() {
    return this.contactName;
  }

  public String getContactEmail() {
    return this.contactEmail;
  }

  public String getSupplierSetupUrl() {
    return this.supplierSetupUrl;
  }

  public HashMap getAdditionalInfo() {
    return this.additionalInfo;
  }
}