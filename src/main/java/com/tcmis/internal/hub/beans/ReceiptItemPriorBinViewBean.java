package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ReceiptItemPriorBinViewBean <br>
 * @version: 1.0, Jun 16, 2004 <br>
 *****************************************************************************/

public class ReceiptItemPriorBinViewBean
    extends BaseDataBean {

  private String hub;
  private String bin;
  private BigDecimal itemId;
  private Date transactionDate;
  private String onSite;
  private String status;

  //constructor
  public ReceiptItemPriorBinViewBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setBin(String bin) {
    this.bin = bin;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  public void setOnSite(String onSite) {
    this.onSite = onSite;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  //getters
  public String getHub() {
    return hub;
  }

  public String getBin() {
    return bin;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public String getOnSite() {
    return onSite;
  }

  public String getStatus() {
    return status;
  }
}