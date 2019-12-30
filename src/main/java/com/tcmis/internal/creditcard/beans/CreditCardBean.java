package com.tcmis.internal.creditcard.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Interface for credit card beans
 */
public interface CreditCardBean {
  abstract public BigDecimal getInvoice();
  abstract public BigDecimal getInvoiceAmount();
  abstract public String getZip();
  abstract public BigDecimal getCreditCardNumber();
  abstract public Date getCreditCardExpirationDate();
  abstract public String getTxref();
  abstract public String getResultCode();
  abstract public BigDecimal getInvoicePeriod();
  abstract public String getCompanyId();
  abstract public String getInvoiceGroup();
  abstract public Collection getInvoiceLines();
}