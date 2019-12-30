package com.tcmis.internal.hub.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogPartItemGroupBean <br>
 * @version: 1.0, Oct 23, 2006 <br>
 *****************************************************************************/

public class CatalogPartItemGroupBean
    extends BaseDataBean {

  private String companyId;
  private String catalogId;
  private String catPartNo;
  private BigDecimal partGroupNo;
  private BigDecimal priority;
  private String status;
  private BigDecimal itemId;
  private String bundle;
  private String catalogItemId;
  private Date dateInserted;
  private String comments;

  //constructor
  public CatalogPartItemGroupBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setPartGroupNo(BigDecimal partGroupNo) {
    this.partGroupNo = partGroupNo;
  }

  public void setPriority(BigDecimal priority) {
    this.priority = priority;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setItemId(BigDecimal itemId) {
    this.itemId = itemId;
  }

  public void setBundle(String bundle) {
    this.bundle = bundle;
  }

  public void setCatalogItemId(String catalogItemId) {
    this.catalogItemId = catalogItemId;
  }

  public void setDateInserted(Date dateInserted) {
    this.dateInserted = dateInserted;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getCatalogId() {
    return catalogId;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public BigDecimal getPartGroupNo() {
    return partGroupNo;
  }

  public BigDecimal getPriority() {
    return priority;
  }

  public String getStatus() {
    return status;
  }

  public BigDecimal getItemId() {
    return itemId;
  }

  public String getBundle() {
    return bundle;
  }

  public String getCatalogItemId() {
    return catalogItemId;
  }

  public Date getDateInserted() {
    return dateInserted;
  }

  public String getComments() {
    return comments;
  }
}