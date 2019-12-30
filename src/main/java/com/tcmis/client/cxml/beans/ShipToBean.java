package com.tcmis.client.cxml.beans;

import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class ShipToBean
    extends BaseDataBean {

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

  //constructor
  public ShipToBean() {
  }

  //setters
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

  //getters
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
}