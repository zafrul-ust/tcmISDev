package com.tcmis.internal.creditcard.beans;

import java.math.BigDecimal;

/**
 * Interface for credit card line beans
 */
public interface CreditCardLineBean {
  //abstract public BigDecimal getInvoice();
  abstract public BigDecimal getIssueId();
  abstract public BigDecimal getQuantity();
  abstract public BigDecimal getInvoiceUnitPrice();
  abstract public String getPoNumber();
}