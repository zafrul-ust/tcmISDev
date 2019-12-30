package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import com.tcmis.common.admin.beans.CreditCardInvoiceInterface;

public class PayflowInvoiceBean
    implements CreditCardInvoiceInterface {
  public PayflowInvoiceBean() {
  }

  private String transactionId;
  private String originalTransactionId;
  private String transactionStatus;
  private String originalTransactionStatus;
  private String transactionAuthorization;
  private String transactionDetail;
  private BigDecimal invoiceNumber;
  private BigDecimal invoiceQualifier;
  private BigDecimal cardAccountNumber;
  private Date cardExpirationDate;
  private String customerPoNumber;
  private BigDecimal invoiceAmount;
  private BigDecimal taxAmount;
  private String zipCode;
  private int numberOfLines;
  private Collection payflowInvoiceLineBeanColl;

  //setters
  public void setTransactionId(String s) {
    this.transactionId = s;
  }

  public void setOriginalTransactionId(String s) {
    this.originalTransactionId = s;
  }

  public void setTransactionStatus(String s) {
    this.transactionStatus = s;
  }

  public void setOriginalTransactionStatus(String s) {
    this.originalTransactionStatus = s;
  }

  public void setTransactionAuthorization(String s) {
    this.transactionAuthorization = s;
  }

  public void setTransactionDetail(String s) {
    this.transactionDetail = s;
  }

  public void setInvoiceNumber(BigDecimal b) throws Exception {
    this.invoiceNumber = b;
  }

  public void setInvoiceQualifier(BigDecimal b) throws Exception {
    this.invoiceQualifier = b;
  }

  public void setCardAccountNumber(BigDecimal b) throws Exception {
    this.cardAccountNumber = b;
  }

  public void setCardExpirationDate(Date date) throws Exception {
    this.cardExpirationDate = date;
  }

  public void setCustomerPoNumber(String s) throws Exception {
    this.customerPoNumber = s;
  }

  public void setInvoiceAmount(BigDecimal b) throws Exception {
    this.invoiceAmount = b;
  }

  public void setTaxAmount(BigDecimal b) throws Exception {
    this.taxAmount = b;
  }

  public void setZipCode(String s) throws Exception {
    this.zipCode = s;
  }

  public void setPayflowInvoiceLineBeanColl(Collection c) throws Exception {
    this.payflowInvoiceLineBeanColl = c;
  }

  public void setNumberOfLines(int i) {
    this.numberOfLines = i;
  }

  //getters
  public String getTransactionId() {
    return this.transactionId;
  }

  public String getOriginalTransactionId() {
    return this.originalTransactionId;
  }

  public String getTransactionStatus() {
    return this.transactionStatus;
  }

  public String getOriginalTransactionStatus() {
    return this.originalTransactionStatus;
  }

  public String getTransactionAuthorization() {
    return this.transactionAuthorization;
  }

  public String getTransactionDetail() {
    return this.transactionDetail;
  }

  public BigDecimal getInvoiceNumber() {
    return this.invoiceNumber;
  }

  public BigDecimal getInvoiceQualifier() {
    return this.invoiceQualifier;
  }

  public BigDecimal getCardAccountNumber() {
    return this.cardAccountNumber;
  }

  public Date getCardExpirationDate() {
    return this.cardExpirationDate;
  }

  public String getCustomerPoNumber() {
    return this.customerPoNumber;
  }

  public BigDecimal getInvoiceAmount() {
    return this.invoiceAmount;
  }

  public BigDecimal getTaxAmount() {
    return this.taxAmount;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public Collection getCreditCardInvoiceLineColl() {
    return this.payflowInvoiceLineBeanColl;
  }

  public int getNumberOfLines() {
    return this.numberOfLines;
  }
}