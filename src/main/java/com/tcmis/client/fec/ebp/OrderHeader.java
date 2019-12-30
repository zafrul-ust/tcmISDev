/*
 * OrderHeader.java
 *
 * Created on June 13, 2005, 3:32 PM
 */

package com.tcmis.client.fec.ebp;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author  mike.najera
 */
public class OrderHeader implements Serializable {
   
   private String orderNumber;
   private String issueDate;
   private String currency;
   
   private String buyerParty;
   private String buyerAddress;
   private String buyerZip;
   private String buyerCity;
   private String buyerState;
   private String buyerCountry;
   private String buyerContact;
   
   private String sellerId;
   private String sellerParty;
   private String sellerAddress;
   private String sellerZip;
   private String sellerCity;
   private String sellerState;
   private String sellerCountry;
   
   private String shiptoId;
   private String shiptoParty;
   private String shiptoParty2;
   private String shiptoParty3;
   private String shiptoAddress;
   private String shiptoZip;
   private String shiptoCity;
   private String shiptoState;
   private String shiptoCountry;
   private String shiptoContactName;
   
   private String paymentTerm;
   private String paymentMean;
   private String cardNumber;
   private String cardExpDate;
   private String cardType;
   private String cardHolder;
   
   private String billtoId;
   private String billtoName;

   private Vector contactList;
   
   /** Creates a new instance of OrderHeader */
   public OrderHeader() {
      contactList = new Vector();
   }
   
   /** Getter for property orderNumber.
    * @return Value of property orderNumber.
    *
    */
   public String getOrderNumber() {
      return orderNumber;
   }
   
   /** Setter for property orderNumber.
    * @param orderNumber New value of property orderNumber.
    *
    */
   public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
   }
   
   /** Getter for property issueDate.
    * @return Value of property issueDate.
    *
    */
   public String getIssueDate() {
      return issueDate;
   }
   
   /** Setter for property issueDate.
    * @param issueDate New value of property issueDate.
    *
    */
   public void setIssueDate(String issueDate) {
      this.issueDate = issueDate;
   }
   
   /** Getter for property currency.
    * @return Value of property currency.
    *
    */
   public String getCurrency() {
      return currency;
   }
   
   /** Setter for property currency.
    * @param currency New value of property currency.
    *
    */
   public void setCurrency(String currency) {
      this.currency = currency;
   }
   
   /** Getter for property buyerParty.
    * @return Value of property buyerParty.
    *
    */
   public String getBuyerParty() {
      return buyerParty;
   }
   
   /** Setter for property buyerParty.
    * @param buyerParty New value of property buyerParty.
    *
    */
   public void setBuyerParty(String buyerParty) {
      this.buyerParty = buyerParty;
   }
   
   /** Getter for property buyerAddress.
    * @return Value of property buyerAddress.
    *
    */
   public String getBuyerAddress() {
      return buyerAddress;
   }
   
   /** Setter for property buyerAddress.
    * @param buyerAddress New value of property buyerAddress.
    *
    */
   public void setBuyerAddress(String buyerAddress) {
      this.buyerAddress = buyerAddress;
   }
   
   /** Getter for property buyerZip.
    * @return Value of property buyerZip.
    *
    */
   public String getBuyerZip() {
      return buyerZip;
   }
   
   /** Setter for property buyerZip.
    * @param buyerZip New value of property buyerZip.
    *
    */
   public void setBuyerZip(String buyerZip) {
      this.buyerZip = buyerZip;
   }
   
   /** Getter for property buyerCity.
    * @return Value of property buyerCity.
    *
    */
   public String getBuyerCity() {
      return buyerCity;
   }
   
   /** Setter for property buyerCity.
    * @param buyerCity New value of property buyerCity.
    *
    */
   public void setBuyerCity(String buyerCity) {
      this.buyerCity = buyerCity;
   }
   
   /** Getter for property buyerState.
    * @return Value of property buyerState.
    *
    */
   public String getBuyerState() {
      return buyerState;
   }
   
   /** Setter for property buyerState.
    * @param buyerState New value of property buyerState.
    *
    */
   public void setBuyerState(String buyerState) {
      this.buyerState = buyerState;
   }
   
   /** Getter for property buyerCountry.
    * @return Value of property buyerCountry.
    *
    */
   public String getBuyerCountry() {
      return buyerCountry;
   }
   
   /** Setter for property buyerCountry.
    * @param buyerCountry New value of property buyerCountry.
    *
    */
   public void setBuyerCountry(String buyerCountry) {
      this.buyerCountry = buyerCountry;
   }
   
   /** Getter for property buyerContact.
    * @return Value of property buyerContact.
    *
    */
   public String getBuyerContact() {
      return buyerContact;
   }
   
   /** Setter for property buyerContact.
    * @param buyerContact New value of property buyerContact.
    *
    */
   public void setBuyerContact(String buyerContact) {
      this.buyerContact = buyerContact;
   }
   
   /** Getter for property sellerId.
    * @return Value of property sellerId.
    *
    */
   public String getSellerId() {
      return sellerId;
   }
   
   /** Setter for property sellerId.
    * @param sellerId New value of property sellerId.
    *
    */
   public void setSellerId(String sellerId) {
      this.sellerId = sellerId;
   }
   
   /** Getter for property sellerParty.
    * @return Value of property sellerParty.
    *
    */
   public String getSellerParty() {
      return sellerParty;
   }
   
   /** Setter for property sellerParty.
    * @param sellerParty New value of property sellerParty.
    *
    */
   public void setSellerParty(String sellerParty) {
      this.sellerParty = sellerParty;
   }
   
   /** Getter for property sellerAddress.
    * @return Value of property sellerAddress.
    *
    */
   public String getSellerAddress() {
      return sellerAddress;
   }
   
   /** Setter for property sellerAddress.
    * @param sellerAddress New value of property sellerAddress.
    *
    */
   public void setSellerAddress(String sellerAddress) {
      this.sellerAddress = sellerAddress;
   }
   
   /** Getter for property sellerZip.
    * @return Value of property sellerZip.
    *
    */
   public String getSellerZip() {
      return sellerZip;
   }
   
   /** Setter for property sellerZip.
    * @param sellerZip New value of property sellerZip.
    *
    */
   public void setSellerZip(String sellerZip) {
      this.sellerZip = sellerZip;
   }
   
   /** Getter for property sellerCity.
    * @return Value of property sellerCity.
    *
    */
   public String getSellerCity() {
      return sellerCity;
   }
   
   /** Setter for property sellerCity.
    * @param sellerCity New value of property sellerCity.
    *
    */
   public void setSellerCity(String sellerCity) {
      this.sellerCity = sellerCity;
   }
   
   /** Getter for property sellerState.
    * @return Value of property sellerState.
    *
    */
   public String getSellerState() {
      return sellerState;
   }
   
   /** Setter for property sellerState.
    * @param sellerState New value of property sellerState.
    *
    */
   public void setSellerState(String sellerState) {
      this.sellerState = sellerState;
   }
   
   /** Getter for property sellerCountry.
    * @return Value of property sellerCountry.
    *
    */
   public String getSellerCountry() {
      return sellerCountry;
   }
   
   /** Setter for property sellerCountry.
    * @param sellerCountry New value of property sellerCountry.
    *
    */
   public void setSellerCountry(String sellerCountry) {
      this.sellerCountry = sellerCountry;
   }
   
   /** Getter for property shiptoId.
    * @return Value of property shiptoId.
    *
    */
   public String getShiptoId() {
      return shiptoId;
   }
   
   /** Setter for property shiptoId.
    * @param shiptoId New value of property shiptoId.
    *
    */
   public void setShiptoId(String shiptoId) {
      this.shiptoId = shiptoId;
   }
   
   /** Getter for property shiptoParty.
    * @return Value of property shiptoParty.
    *
    */
   public String getShiptoParty() {
      return shiptoParty;
   }
   
   /** Setter for property shiptoParty.
    * @param shiptoParty New value of property shiptoParty.
    *
    */
   public void setShiptoParty(String shiptoParty) {
      this.shiptoParty = shiptoParty;
   }
   
   /** Getter for property shiptoParty2.
    * @return Value of property shiptoParty2.
    *
    */
   public String getShiptoParty2() {
      return shiptoParty2;
   }
   
   /** Setter for property shiptoParty2.
    * @param shiptoParty2 New value of property shiptoParty2.
    *
    */
   public void setShiptoParty2(String shiptoParty2) {
      this.shiptoParty2 = shiptoParty2;
   }
   
   /** Getter for property shiptoParty3.
    * @return Value of property shiptoParty3.
    *
    */
   public String getShiptoParty3() {
      return shiptoParty3;
   }
   
   /** Setter for property shiptoParty3.
    * @param shiptoParty3 New value of property shiptoParty3.
    *
    */
   public void setShiptoParty3(String shiptoParty3) {
      this.shiptoParty3 = shiptoParty3;
   }
   
   /** Getter for property shiptoAddress.
    * @return Value of property shiptoAddress.
    *
    */
   public String getShiptoAddress() {
      return shiptoAddress;
   }
   
   /** Setter for property shiptoAddress.
    * @param shiptoAddress New value of property shiptoAddress.
    *
    */
   public void setShiptoAddress(String shiptoAddress) {
      this.shiptoAddress = shiptoAddress;
   }
   
   /** Getter for property shiptoZip.
    * @return Value of property shiptoZip.
    *
    */
   public String getShiptoZip() {
      return shiptoZip;
   }
   
   /** Setter for property shiptoZip.
    * @param shiptoZip New value of property shiptoZip.
    *
    */
   public void setShiptoZip(String shiptoZip) {
      this.shiptoZip = shiptoZip;
   }
   
   /** Getter for property shiptoCity.
    * @return Value of property shiptoCity.
    *
    */
   public String getShiptoCity() {
      return shiptoCity;
   }
   
   /** Setter for property shiptoCity.
    * @param shiptoCity New value of property shiptoCity.
    *
    */
   public void setShiptoCity(String shiptoCity) {
      this.shiptoCity = shiptoCity;
   }
   
   /** Getter for property shiptoState.
    * @return Value of property shiptoState.
    *
    */
   public String getShiptoState() {
      return shiptoState;
   }
   
   /** Setter for property shiptoState.
    * @param shiptoState New value of property shiptoState.
    *
    */
   public void setShiptoState(String shiptoState) {
      this.shiptoState = shiptoState;
   }
   
   /** Getter for property shiptoCountry.
    * @return Value of property shiptoCountry.
    *
    */
   public String getShiptoCountry() {
      return shiptoCountry;
   }
   
   /** Setter for property shiptoCountry.
    * @param shiptoCountry New value of property shiptoCountry.
    *
    */
   public void setShiptoCountry(String shiptoCountry) {
      this.shiptoCountry = shiptoCountry;
   }
   
   /** Getter for property shiptoContactName.
    * @return Value of property shiptoContactName.
    *
    */
   public String getShiptoContactName() {
      return shiptoContactName;
   }
   
   /** Setter for property shiptoContactName.
    * @param shiptoContactName New value of property shiptoContactName.
    *
    */
   public void setShiptoContactName(String shiptoContactName) {
      this.shiptoContactName = shiptoContactName;
   }
   
   /** Getter for property paymentTerm.
    * @return Value of property paymentTerm.
    *
    */
   public String getPaymentTerm() {
      return paymentTerm;
   }
   
   /** Setter for property paymentTerm.
    * @param paymentTerm New value of property paymentTerm.
    *
    */
   public void setPaymentTerm(String paymentTerm) {
      this.paymentTerm = paymentTerm;
   }
   
   /** Getter for property paymentMean.
    * @return Value of property paymentMean.
    *
    */
   public String getPaymentMean() {
      return paymentMean;
   }
   
   /** Setter for property paymentMean.
    * @param paymentMean New value of property paymentMean.
    *
    */
   public void setPaymentMean(String paymentMean) {
      this.paymentMean = paymentMean;
   }
   
   /** Getter for property cardNumber.
    * @return Value of property cardNumber.
    *
    */
   public String getCardNumber() {
      return cardNumber;
   }
   
   /** Setter for property cardNumber.
    * @param cardNumber New value of property cardNumber.
    *
    */
   public void setCardNumber(String cardNumber) {
      this.cardNumber = cardNumber;
   }
   
   /** Getter for property cardExpDate.
    * @return Value of property cardExpDate.
    *
    */
   public String getCardExpDate() {
      return cardExpDate;
   }
   
   /** Setter for property cardExpDate.
    * @param cardExpDate New value of property cardExpDate.
    *
    */
   public void setCardExpDate(String cardExpDate) {
      this.cardExpDate = cardExpDate;
   }
   
   /** Getter for property cardType.
    * @return Value of property cardType.
    *
    */
   public String getCardType() {
      return cardType;
   }
   
   /** Setter for property cardType.
    * @param cardType New value of property cardType.
    *
    */
   public void setCardType(String cardType) {
      this.cardType = cardType;
   }
   
   /** Getter for property cardHolder.
    * @return Value of property cardHolder.
    *
    */
   public String getCardHolder() {
      return cardHolder;
   }
   
   /** Setter for property cardHolder.
    * @param cardHolder New value of property cardHolder.
    *
    */
   public void setCardHolder(String cardHolder) {
      this.cardHolder = cardHolder;
   }
   
   /** Getter for property billtoId.
    * @return Value of property billtoId.
    *
    */
   public String getBilltoId() {
      return billtoId;
   }
   
   /** Setter for property billtoId.
    * @param billtoId New value of property billtoId.
    *
    */
   public void setBilltoId(String billtoId) {
      this.billtoId = billtoId;
   }
   
   /** Getter for property billtoName.
    * @return Value of property billtoName.
    *
    */
   public String getBilltoName() {
      return billtoName;
   }
   
   /** Setter for property billtoName.
    * @param billtoName New value of property billtoName.
    *
    */
   public void setBilltoName(String billtoName) {
      this.billtoName = billtoName;
   }
   
   /* Adds a new contact number to the lsit
    *
    */
   public void addContactNumber(ContactNumber contactNumber) {
      contactList.add(contactNumber);
   }
   
   /* return the list of contact info */
   public Vector getContactList() {
      return contactList;
   }   
}
