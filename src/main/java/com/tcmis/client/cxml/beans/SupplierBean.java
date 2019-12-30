package com.tcmis.client.cxml.beans;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CxmlOrderRequestBean <br>
 * @version: 1.0, Jun 9, 2006 <br>
 *****************************************************************************/

public class SupplierBean
    extends BaseDataBean {

  private String supplierName;
  private String supplierComments;
  private String supplierDomain;
  private Collection supplierIdCollection = new Vector();

  //constructor
  public SupplierBean() {
  }

  //setters
  public void setSupplierName(String s) {
    this.supplierName = s;
  }

  public void setSupplierDomain(String s) {
    this.supplierDomain = s;
  }

  public void setSupplierId(Collection c) {
    this.supplierIdCollection = c;
  }


  public void setSupplierComments(String s) {
    this.supplierComments = s;
  }

  public void addSupplierId(String s) {
    this.supplierIdCollection.add(s);
  }

  //getters
  public String getSupplierName() {
    return this.supplierName;
  }

  public String getSupplierComments() {
    return this.supplierComments;
  }

  public String getSupplierDomain() {
    return this.supplierDomain;
  }

  public Collection getSupplierId() {
    return this.supplierIdCollection;
  }








}