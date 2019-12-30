package com.tcmis.internal.supply.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: NewSupplierRequestInputBean <br>
 * @version: 1.0, Jan 18, 2007 <br>
 *****************************************************************************/

public class SearchSupplierRequestInputBean
    extends BaseDataBean {

  private BigDecimal supplierRequestId;
  private String status;
  private String searchText;

  //constructor
  public SearchSupplierRequestInputBean() {
  }

  //setters
  public void setSupplierRequestId(BigDecimal b) {
    this.supplierRequestId = b;
  }

  public void setStatus(String s) {
    this.status = s;
  }

  public void setSearchText(String s) {
    this.searchText = s;
  }

  //getters
  public BigDecimal getSupplierRequestId() {
    return this.supplierRequestId;
  }

  public String getStatus() {
    return this.status;
  }

  public String getSearchText() {
    return this.searchText;
  }

}