package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CabinetInventoryCountBean <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class CabinetInventoryCountBean
    extends BaseDataBean {

  private BigDecimal binId;
  private Date countDatetime;
  private BigDecimal receiptId;
  private String companyId;
  private BigDecimal personnelId;
  private BigDecimal countQuantity;
  private Date dateProcessed;

  //constructor
  public CabinetInventoryCountBean() {
  }

  //setters
  public void setBinId(BigDecimal binId) {
    this.binId = binId;
  }

  public void setCountDatetime(Date countDatetime) {
    this.countDatetime = countDatetime;
  }

  public void setReceiptId(BigDecimal receiptId) {
    this.receiptId = receiptId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCountQuantity(BigDecimal countQuantity) {
    this.countQuantity = countQuantity;
  }

  public void setDateProcessed(Date dateProcessed) {
    this.dateProcessed = dateProcessed;
  }

  //getters
  public BigDecimal getBinId() {
    return binId;
  }

  public Date getCountDatetime() {
    return countDatetime;
  }

  public BigDecimal getReceiptId() {
    return receiptId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public BigDecimal getCountQuantity() {
    return countQuantity;
  }

  public Date getDateProcessed() {
    return dateProcessed;
  }
}