package com.tcmis.client.raytheon.beans;

import java.util.Collection;
import java.util.Date;
import java.util.Vector;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.client.raytheon.util.XcblHandler;

/******************************************************************************
 * CLASSNAME: XcblOrderBean <br>
 * @version: 1.0, Jul 13, 2005 <br>
 *****************************************************************************/

public class XcblOrderBean
    extends BaseDataBean {

  private String buyerOrderNumber;
  private Date orderIssueDate;
  private String orderIssueDateString;
  private String accountCodeRefNum;
  private String purpose;
  private String requestResponse;
  private String orderType;
  private String orderCurrency;
  private String orderLanguage;
  private String orderPartyAgency;
  private String orderPartyIdent;
  private String orderPartyName1;
  private String orderPartyName2;
  private String orderPartyName3;
  private String orderPartyStreet;
  private String orderPartyPostalCode;
  private String orderPartyCity;
  private String orderPartyRegion;
  private String orderPartyCountry;
  private String orderPartyContactName;
  private String orderPartyContactFunction;
  private String sellerAgency;
  private String sellerIdent;
  private String sellerName1;
  private String sellerName2;
  private String sellerStreet;
  private String sellerPostalCode;
  private String sellerCity;
  private String sellerRegion;
  private String sellerCountry;
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
  private String billToAgency;
  private String billToIdent;
  private String billToName1;
  private String billToName2;
  private String billToStreet;
  private String billToPostalCode;
  private String billToCity;
  private String billToRegion;
  private String billToCountry;
  private String termsOfDeliveryFunction;
  private String transportTerms;
  private String shipmentMethodOfPayment;
  private String locationQualifier;
  private String locationAgency;
  private String paymentTerm;
  private String paymentTermsNote;
  private String paymentMean;
  private String notes = "";;
  private BigDecimal monetaryAmount;
  private BigDecimal id;
  private BigDecimal totalQuantity;
  private Collection xcblOrderDetailBeanColl = new Vector();
  private Collection xcblOrderBuyerContactBeanColl = new Vector();
  private Collection xcblOrderShipToContactBeanColl = new Vector();

  //these fields are used to call the proc to update status
  private String transport;
  private String transporterAccount;
  private String tradingPartner;
  private String tradingPartnerId;
  private String transactionType;

  //these fields are used for the response
  private String responseOrderStatus;
  private String responseOrderStatusNotes;
  private String responseType;
  private String statusEvent;
  private String statusEventOther;

  //change order fields
  private BigDecimal changeOrderSequence;
  private Date changeOrderDate;
  private String changeOrderDateString;
  private String changeType;
  private BigDecimal changeOrderTotalQuantity;


  //constructor
  public XcblOrderBean() {
    super();
  }

  //setters
  public void setBuyerOrderNumber(String buyerOrderNumber) {
    this.buyerOrderNumber = buyerOrderNumber;
  }

  public void setOrderIssueDate(Date orderIssueDate) {
    this.orderIssueDate = orderIssueDate;
  }

  public void setOrderIssueDateString(String s) {
    this.orderIssueDateString = s;
    this.setOrderIssueDate(XcblHandler.getXcblDate(s));
  }

  public void setAccountCodeRefNum(String accountCodeRefNum) {
    this.accountCodeRefNum = accountCodeRefNum;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }

  public void setRequestResponse(String requestResponse) {
    this.requestResponse = requestResponse;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public void setOrderCurrency(String orderCurrency) {
    this.orderCurrency = orderCurrency;
  }

  public void setOrderLanguage(String orderLanguage) {
    this.orderLanguage = orderLanguage;
  }

  public void setOrderPartyAgency(String orderPartyAgency) {
    this.orderPartyAgency = orderPartyAgency;
  }

  public void setOrderPartyIdent(String orderPartyIdent) {
    this.orderPartyIdent = orderPartyIdent;
  }

  public void setOrderPartyName1(String orderPartyName1) {
    this.orderPartyName1 = orderPartyName1;
  }

  public void setOrderPartyName2(String orderPartyName2) {
    this.orderPartyName2 = orderPartyName2;
  }

  public void setOrderPartyName3(String orderPartyName3) {
    this.orderPartyName3 = orderPartyName3;
  }

  public void setOrderPartyStreet(String orderPartyStreet) {
    this.orderPartyStreet = orderPartyStreet;
  }

  public void setOrderPartyPostalCode(String orderPartyPostalCode) {
    this.orderPartyPostalCode = orderPartyPostalCode;
  }

  public void setOrderPartyCity(String orderPartyCity) {
    this.orderPartyCity = orderPartyCity;
  }

  public void setOrderPartyRegion(String orderPartyRegion) {
    this.orderPartyRegion = orderPartyRegion;
  }

  public void setOrderPartyCountry(String orderPartyCountry) {
    this.orderPartyCountry = orderPartyCountry;
  }

  public void setOrderPartyContactName(String orderPartyContactName) {
    this.orderPartyContactName = orderPartyContactName;
  }

  public void setOrderPartyContactFunction(String orderPartyContactFunction) {
    this.orderPartyContactFunction = orderPartyContactFunction;
  }

  public void setSellerAgency(String sellerAgency) {
    this.sellerAgency = sellerAgency;
  }

  public void setSellerIdent(String sellerIdent) {
    this.sellerIdent = sellerIdent;
  }

  public void setSellerName1(String sellerName1) {
    this.sellerName1 = sellerName1;
  }

  public void setSellerName2(String sellerName2) {
    this.sellerName2 = sellerName2;
  }

  public void setSellerStreet(String sellerStreet) {
    this.sellerStreet = sellerStreet;
  }

  public void setSellerPostalCode(String sellerPostalCode) {
    this.sellerPostalCode = sellerPostalCode;
  }

  public void setSellerCity(String sellerCity) {
    this.sellerCity = sellerCity;
  }

  public void setSellerRegion(String sellerRegion) {
    this.sellerRegion = sellerRegion;
  }

  public void setSellerCountry(String sellerCountry) {
    this.sellerCountry = sellerCountry;
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

  public void setBillToAgency(String billToAgency) {
    this.billToAgency = billToAgency;
  }

  public void setBillToIdent(String billToIdent) {
    this.billToIdent = billToIdent;
  }

  public void setBillToName1(String billToName1) {
    this.billToName1 = billToName1;
  }

  public void setBillToName2(String billToName2) {
    this.billToName2 = billToName2;
  }

  public void setBillToStreet(String billToStreet) {
    this.billToStreet = billToStreet;
  }

  public void setBillToPostalCode(String billToPostalCode) {
    this.billToPostalCode = billToPostalCode;
  }

  public void setBillToCity(String billToCity) {
    this.billToCity = billToCity;
  }

  public void setBillToRegion(String billToRegion) {
    this.billToRegion = billToRegion;
  }

  public void setBillToCountry(String billToCountry) {
    this.billToCountry = billToCountry;
  }

  public void setTermsOfDeliveryFunction(String termsOfDeliveryFunction) {
    this.termsOfDeliveryFunction = termsOfDeliveryFunction;
  }

  public void setTransportTerms(String transportTerms) {
    this.transportTerms = transportTerms;
  }

  public void setShipmentMethodOfPayment(String shipmentMethodOfPayment) {
    this.shipmentMethodOfPayment = shipmentMethodOfPayment;
  }

  public void setLocationQualifier(String locationQualifier) {
    this.locationQualifier = locationQualifier;
  }

  public void setLocationAgency(String locationAgency) {
    this.locationAgency = locationAgency;
  }

  public void setPaymentTerm(String paymentTerm) {
    this.paymentTerm = paymentTerm;
  }

  public void setPaymentTermsNote(String paymentTermsNote) {
    this.paymentTermsNote = paymentTermsNote;
  }

  public void setPaymentMean(String paymentMean) {
    this.paymentMean = paymentMean;
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

  public void setMonetaryAmount(BigDecimal monetaryAmount) {
    this.monetaryAmount = monetaryAmount;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public void addXcblOrderDetailBean(XcblOrderDetailBean bean) {
    this.xcblOrderDetailBeanColl.add(bean);
  }

  public void setXcblOrderDetailBeanColl(Collection xcblOrderDetailBeanColl) {
    this.xcblOrderDetailBeanColl = xcblOrderDetailBeanColl;
  }

  public void addXcblOrderBuyerContactBean(XcblOrderBuyerContactBean bean) {
    this.xcblOrderBuyerContactBeanColl.add(bean);
  }

  public void setXcblOrderBuyerContactBeanColl(Collection xcblOrderBuyerContactBeanColl) {
    this.xcblOrderBuyerContactBeanColl = xcblOrderBuyerContactBeanColl;
  }

  public void addXcblOrderShipToContactBean(XcblOrderShipToContactBean bean) {
    this.xcblOrderShipToContactBeanColl.add(bean);
  }

  public void setXcblOrderShipToContactBeanColl(Collection xcblOrderShipToContactBeanColl) {
    this.xcblOrderShipToContactBeanColl = xcblOrderShipToContactBeanColl;
  }

  public void setResponseOrderStatus(String orderStatus) {
    this.responseOrderStatus = orderStatus;
  }

  public void setResponseOrderStatusNotes(String orderStatusNotes) {
    this.responseOrderStatusNotes = orderStatusNotes;
  }

  public void setTransport(String transport) {
    this.transport = transport;
  }

  public void setTransporterAccount(String transporterAccount) {
    this.transporterAccount = transporterAccount;
  }

  public void setTradingPartner(String tradingPartner) {
    this.tradingPartner = tradingPartner;
  }

  public void setTradingPartnerId(String tradingPartnerId) {
    this.tradingPartnerId = tradingPartnerId;
  }

  public void setTransactionType(String s) {
    this.transactionType = s;
  }

  public void setStatusEvent(String s) {
    this.statusEvent = s;
  }

  public void setStatusEventOther(String s) {
    this.statusEventOther = s;
  }

  public void setResponseType(String s) {
    this.responseType = s;
  }

  public void setTotalQuantity(BigDecimal b) {
    this.totalQuantity = b;
  }

  public void setChangeOrderSequence(BigDecimal changeOrderSequence) {
    this.changeOrderSequence = changeOrderSequence;
  }

  public void setChangeOrderDate(Date changeOrderDate) {
    this.changeOrderDate = changeOrderDate;
  }

  public void setChangeOrderDateString(String changeOrderDateString) {
    this.changeOrderDateString = changeOrderDateString;
    this.setChangeOrderDate(XcblHandler.getXcblDate(changeOrderDateString));
  }

  public void setChangeType(String changeType) {
    this.changeType = changeType;
  }

  public void setChangeOrderTotalQuantity(BigDecimal b) {
    this.changeOrderTotalQuantity = b;
  }

  //getters
  public String getBuyerOrderNumber() {
    return buyerOrderNumber;
  }

  public Date getOrderIssueDate() {
    return orderIssueDate;
  }

  public String getOrderIssueDateString() {
    return orderIssueDateString;
  }

  public String getAccountCodeRefNum() {
    return accountCodeRefNum;
  }

  public String getPurpose() {
    return purpose;
  }

  public String getRequestResponse() {
    return requestResponse;
  }

  public String getOrderType() {
    return orderType;
  }

  public String getOrderCurrency() {
    return orderCurrency;
  }

  public String getOrderLanguage() {
    return orderLanguage;
  }

  public String getOrderPartyAgency() {
    return orderPartyAgency;
  }

  public String getOrderPartyIdent() {
    return orderPartyIdent;
  }

  public String getOrderPartyName1() {
    return orderPartyName1;
  }

  public String getOrderPartyName2() {
    return orderPartyName2;
  }

  public String getOrderPartyName3() {
    return orderPartyName3;
  }

  public String getOrderPartyStreet() {
    return orderPartyStreet;
  }

  public String getOrderPartyPostalCode() {
    return orderPartyPostalCode;
  }

  public String getOrderPartyCity() {
    return orderPartyCity;
  }

  public String getOrderPartyRegion() {
    return orderPartyRegion;
  }

  public String getOrderPartyCountry() {
    return orderPartyCountry;
  }

  public String getOrderPartyContactName() {
    return orderPartyContactName;
  }

  public String getOrderPartyContactFunction() {
    return orderPartyContactFunction;
  }

  public String getSellerAgency() {
    return sellerAgency;
  }

  public String getSellerIdent() {
    return sellerIdent;
  }

  public String getSellerName1() {
    return sellerName1;
  }

  public String getSellerName2() {
    return sellerName2;
  }

  public String getSellerStreet() {
    return sellerStreet;
  }

  public String getSellerPostalCode() {
    return sellerPostalCode;
  }

  public String getSellerCity() {
    return sellerCity;
  }

  public String getSellerRegion() {
    return sellerRegion;
  }

  public String getSellerCountry() {
    return sellerCountry;
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

  public String getBillToAgency() {
    return billToAgency;
  }

  public String getBillToIdent() {
    return billToIdent;
  }

  public String getBillToName1() {
    return billToName1;
  }

  public String getBillToName2() {
    return billToName2;
  }

  public String getBillToStreet() {
    return billToStreet;
  }

  public String getBillToPostalCode() {
    return billToPostalCode;
  }

  public String getBillToCity() {
    return billToCity;
  }

  public String getBillToRegion() {
    return billToRegion;
  }

  public String getBillToCountry() {
    return billToCountry;
  }

  public String getTermsOfDeliveryFunction() {
    return termsOfDeliveryFunction;
  }

  public String getTransportTerms() {
    return transportTerms;
  }

  public String getShipmentMethodOfPayment() {
    return shipmentMethodOfPayment;
  }

  public String getLocationQualifier() {
    return locationQualifier;
  }

  public String getLocationAgency() {
    return locationAgency;
  }

  public String getPaymentTerm() {
    return paymentTerm;
  }

  public String getPaymentTermsNote() {
    return paymentTermsNote;
  }

  public String getPaymentMean() {
    return paymentMean;
  }

  public String getNotes() {
    return notes;
  }

  public BigDecimal getMonetaryAmount() {
    return monetaryAmount;
  }

  public BigDecimal getId() {
    return id;
  }

  public Collection getXcblOrderDetailBeanColl() {
    return this.xcblOrderDetailBeanColl;
  }

  public Collection getXcblOrderBuyerContactBeanColl() {
    return this.xcblOrderBuyerContactBeanColl;
  }

  public Collection getXcblOrderShipToContactBeanColl() {
    return this.xcblOrderShipToContactBeanColl;
  }

  public String getResponseOrderStatus() {
    return responseOrderStatus;
  }

  public String getResponseOrderStatusNotes() {
    return responseOrderStatusNotes;
  }

  public String getTransport() {
    return transport;
  }

  public String getTransporterAccount() {
    return transporterAccount;
  }

  public String getTradingPartner() {
    return tradingPartner;
  }

  public String getTradingPartnerId() {
    return tradingPartnerId;
  }

  public String getTransactionType() {
    return this.transactionType;
  }

  public String getResponseType() {
    return this.responseType;
  }

  public String getStatusEvent() {
    return this.statusEvent;
  }

  public String getStatusEventOther() {
    return this.statusEventOther;
  }

  public BigDecimal getTotalQuantity() {
    return this.totalQuantity;
  }

  public BigDecimal getChangeOrderSequence() {
    return this.changeOrderSequence;
  }

  public Date getChangeOrderDate() {
    return this.changeOrderDate;
  }

  public String getChangeOrderDateString() {
    return this.changeOrderDateString;
  }

  public String getChangeType() {
    return this.changeType;
  }

  public BigDecimal getChangeOrderTotalQuantity() {
    return this.changeOrderTotalQuantity;
  }
}