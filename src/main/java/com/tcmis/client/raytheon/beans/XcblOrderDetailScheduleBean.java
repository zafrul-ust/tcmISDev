package com.tcmis.client.raytheon.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.client.raytheon.util.XcblHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderDetailScheduleBean <br>
 * @version: 1.0, Jul 13, 2005 <br>
 *****************************************************************************/

public class XcblOrderDetailScheduleBean
    extends BaseDataBean {

  private BigDecimal xcblOrderDetailId;
  private BigDecimal id;
  private BigDecimal scheduleLineId;
  private BigDecimal quantity;
  private String uom;
  private String requestedDeliveryDateString;
  private Date requestedDeliveryDate;

  private String shipToAgency;
  private String shipToIdent;
  private String shipToName1;
  private String shipToName2;
  private String shipToStreet;
  private String shipToPostalCode;
  private String shipToCity;
  private String shipToRegion;
  private String shipToCountry;
  private String shipToContactName;
  private String shipToContactFunction;

//these fields are for the response
  private Date responseDeliveryDate;
  private BigDecimal responseQuantity;
  private String responseUom;
  private String responseNotes;

  //constructor
  public XcblOrderDetailScheduleBean() {
  }

  //setters
  public void setXcblOrderDetailId(BigDecimal xcblOrderDetailId) {
    this.xcblOrderDetailId = xcblOrderDetailId;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public void setScheduleLineId(BigDecimal scheduleLineId) {
    this.scheduleLineId = scheduleLineId;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  public void setUom(String uom) {
    this.uom = uom;
  }

  public void setRequestedDeliveryDateString(String requestedDeliveryDateString) {
    this.requestedDeliveryDateString = requestedDeliveryDateString;
    this.setRequestedDeliveryDate(XcblHandler.getXcblDate(requestedDeliveryDateString)); 
  }

  public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
    this.requestedDeliveryDate = requestedDeliveryDate;
  }

  public void setResponseDeliveryDate(Date responseDeliveryDate) {
    this.responseDeliveryDate = responseDeliveryDate;
  }

  public void setResponseQuantity(BigDecimal responseQuantity) {
    this.responseQuantity = responseQuantity;
  }

  public void setResponseNotes(String s) {
    this.responseNotes = s;
  }

  public void setResponseUom(String s) {
    this.responseUom = s;
  }


  public void setShipToAgency(String shipToAgency) {
    this.shipToAgency = shipToAgency;
  }

  public void setShipToIdent(String shipToIdent) {
    this.shipToIdent = shipToIdent;
  }

  public void setShipToName1(String shipToName1) {
    this.shipToName1 = shipToName1;
  }

  public void setShipToName2(String shipToName2) {
    this.shipToName2 = shipToName2;
  }

  public void setShipToStreet(String shipToStreet) {
    this.shipToStreet = shipToStreet;
  }

  public void setShipToPostalCode(String shipToPostalCode) {
    this.shipToPostalCode = shipToPostalCode;
  }

  public void setShipToCity(String shipToCity) {
    this.shipToCity = shipToCity;
  }

  public void setShipToRegion(String shipToRegion) {
    this.shipToRegion = shipToRegion;
  }

  public void setShipToCountry(String shipToCountry) {
    this.shipToCountry = shipToCountry;
  }

  public void setShipToContactName(String shipToContactName) {
    this.shipToContactName = shipToContactName;
  }

  public void setShipToContactFunction(String shipToContactFunction) {
    this.shipToContactFunction = shipToContactFunction;
  }


  //getters
  public BigDecimal getXcblOrderDetailId() {
    return xcblOrderDetailId;
  }

  public BigDecimal getId() {
    return id;
  }

  public BigDecimal getScheduleLineId() {
    return scheduleLineId;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public String getUom() {
    return uom;
  }

  public String getRequestedDeliveryDateString() {
    return requestedDeliveryDateString;
  }

  public Date getRequestedDeliveryDate() {
    return requestedDeliveryDate;
  }

  public Date getResponseDeliveryDate() {
    return responseDeliveryDate;
  }

  public BigDecimal getResponseQuantity() {
    return responseQuantity;
  }

  public String getResponseNotes() {
    return responseNotes;
  }

  public String getResponseUom() {
    return responseUom;
  }


  public String getShipToAgency() {
    return shipToAgency;
  }

  public String getShipToIdent() {
    return shipToIdent;
  }

  public String getShipToName1() {
    return shipToName1;
  }

  public String getShipToName2() {
    return shipToName2;
  }

  public String getShipToStreet() {
    return shipToStreet;
  }

  public String getShipToPostalCode() {
    return shipToPostalCode;
  }

  public String getShipToCity() {
    return shipToCity;
  }

  public String getShipToRegion() {
    return shipToRegion;
  }

  public String getShipToCountry() {
    return shipToCountry;
  }

  public String getShipToContactName() {
    return shipToContactName;
  }

  public String getShipToContactFunction() {
    return shipToContactFunction;
  }
}