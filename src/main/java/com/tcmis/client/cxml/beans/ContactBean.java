package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class ContactBean
    extends BaseDataBean {

  private String contactName;
  private String contactPostalDeliverTo;
  private String contactPostalStreet;
  private String contactPostalCity;
  private String contactPostalState;
  private String contactPostalZip;
  private String contactPostalCountry;
  private String contactEmail;
  private String contactPhoneCountryNumber;
  private String contactPhoneAreaCode;
  private String contactPhoneNumber;
  private String contactPhoneExtension;


  //constructor
  public ContactBean() {
  }

  //setters
  public void setContactName(String s) {
    this.contactName = s;
  }

  public void setContactPostalDeliverTo(String s) {
    this.contactPostalDeliverTo = s;
  }

  public void setContactPostalStreet(String s) {
    this.contactPostalStreet = s;
  }

  public void setContactPostalCity(String s) {
    this.contactPostalCity = s;
  }

  public void setContactPostalState(String s) {
    this.contactPostalState = s;
  }

  public void setContactPostalZip(String s) {
    this.contactPostalZip = s;
  }

  public void setContactPostalCountry(String s) {
    this.contactPostalCountry = s;
  }

  public void setContactEmail(String s) {
    this.contactEmail = s;
  }

  public void setContactPhoneCountryNumber(String s) {
    this.contactPhoneCountryNumber = s;
  }

  public void setContactPhoneAreaCode(String s) {
    this.contactPhoneAreaCode = s;
  }

  public void setContactPhoneNumber(String s) {
    this.contactPhoneNumber = s;
  }

  public void setContactPhoneExtension(String s) {
    this.contactPhoneExtension = s;
  }

  //getters
  public String getContactName() {
    return this.contactName;
  }

  public String getContactPostalDeliverTo() {
    return this.contactPostalDeliverTo;
  }

  public String getContactPostalStreet() {
    return this.contactPostalStreet;
  }

  public String getContactPostalCity() {
    return this.contactPostalCity;
  }

  public String getContactPostalState() {
    return this.contactPostalState;
  }

  public String getContactPostalZip() {
    return this.contactPostalZip;
  }

  public String getContactPostalCountry() {
    return this.contactPostalCountry;
  }

  public String getContactEmail() {
    return this.contactEmail;
  }

  public String getContactPhoneCountryNumber() {
    return this.contactPhoneCountryNumber;
  }

  public String getContactPhoneAreaCode() {
    return this.contactPhoneAreaCode;
  }

  public String getContactPhoneNumber() {
    return this.contactPhoneNumber;
  }

  public String getContactPhoneExtension() {
    return this.contactPhoneExtension;
  }
}