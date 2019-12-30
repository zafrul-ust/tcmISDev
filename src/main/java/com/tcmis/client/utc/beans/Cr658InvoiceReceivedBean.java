package com.tcmis.client.utc.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Cr658InvoiceReceivedBean <br>
 * @version: 1.0, May 3, 2005 <br>
 *****************************************************************************/

public class Cr658InvoiceReceivedBean
    extends BaseDataBean {

  private String xblnr;
  private Date dateReceived;
  private BigDecimal invoiceAmount;

  //constructor
  public Cr658InvoiceReceivedBean() {
  }

  //setters
  public void setXblnr(String xblnr) {
    this.xblnr = xblnr;
  }

  public void setDateReceived(Date dateReceived) {
    this.dateReceived = dateReceived;
  }

  public void setInvoiceAmount(BigDecimal invoiceAmount) {
    this.invoiceAmount = invoiceAmount;
  }

  //getters
  public String getXblnr() {
    return xblnr;
  }

  public Date getDateReceived() {
    return dateReceived;
  }

  public BigDecimal getInvoiceAmount() {
    return invoiceAmount;
  }
}
