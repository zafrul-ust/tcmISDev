package com.tcmis.client.seagate.beans;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The common methods and values for American Express reconciliation records.
 */
public interface AmexRecord {


  /** Get the Record type */
  public int getRecordType();

  /** The SE identification number Amex assigns a payee (the SE receiving payment) */
  public BigDecimal getPayeeId();

  /** The ID number of the SE being reconciled (may be different than payee) */
  public BigDecimal getSubmitterId();

  /** The unit/store ID assigned by an SE (may be blank) */
  public String getUnitId();

  /** The Calendar date when payment was made */
  // public Timestamp getPaymentDate();
  public Date getPaymentDate();

  /**
   * The payment ID assigned by Amex. Format is: JJJTNNNN, where;
   * JJJ is the 3 digit julian day
   * T is an alphanumeric character
   * NNNN is a 4 digit number
   */
  public String getPaymentId();

  /** Writes the record to the appropriate database table */
  //public void close() throws Exception;

  /** Reconcile the payment data with invoice data */
  /*
    public void reconcile(Date date, String payid,
                          BigDecimal paymentAmount) throws Exception;
   */

  /** Reconcile the payment data with invoice data */
  /*
    public void update(Date date, String payid,
                       BigDecimal paymentAmount) throws Exception;
   */
  /** Delete this record from the database */
  /*
    public void delete() throws Exception;
   */
  /** Log an error for this record */
  //public void logError(String message, Exception e);
}
