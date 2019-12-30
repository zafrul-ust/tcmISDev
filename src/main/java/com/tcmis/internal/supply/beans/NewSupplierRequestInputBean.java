package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: NewSupplierRequestInputBean <br>
 * @version: 1.0, Jan 18, 2007 <br>
 *****************************************************************************/

public class NewSupplierRequestInputBean
    extends BaseDataBean {

  private String hub;
  private String facilityId;
  private String inventoryGroup;
  private String lotStatus;
  private String sortBy;
  private String progressStatus;
  private String itemOrMrCriteria;
  private BigDecimal itemOrMr;
  private String daySpanCriteria;
  private BigDecimal daySpan;


  //constructor
  public NewSupplierRequestInputBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setLotStatus(String lotStatus) {
    this.lotStatus = lotStatus;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public void setProgressStatus(String s) {
    this.progressStatus = s;
  }

  public void setItemOrMrCriteria(String s) {
    this.itemOrMrCriteria = s;
  }

  public void setItemOrMr(BigDecimal b) {
    this.itemOrMr = b;
  }

  public void setDaySpanCriteria(String s) {
    this.daySpanCriteria = s;
  }

  public void setDaySpan(BigDecimal b) {
    this.daySpan = b;
  }
  //getters
  public String getHub() {
    return hub;
  }

  public String getFacilityId() {
    return facilityId;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getLotStatus() {
    return lotStatus;
  }

  public String getSortBy() {
    return sortBy;
  }

  public String getProgressStatus() {
    return progressStatus;
  }

  public String getItemOrMrCriteria() {
    return this.itemOrMrCriteria;
  }

  public BigDecimal getItemOrMr() {
    return this.itemOrMr;
  }

  public String getDaySpanCriteria() {
    return this.daySpanCriteria;
  }

  public BigDecimal getDaySpan() {
    return this.daySpan;
  }
}