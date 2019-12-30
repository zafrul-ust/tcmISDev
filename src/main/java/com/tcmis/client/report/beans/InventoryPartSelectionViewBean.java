package com.tcmis.client.report.beans;

import java.util.Collection;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: InventoryPartSelectionViewBean <br>
 * @version: 1.0, Nov 1, 2005 <br>
 *****************************************************************************/

public class InventoryPartSelectionViewBean
    extends BaseDataBean {

  private String companyId;
  private String inventoryGroup;
  private String catPartNo;
  private Collection detailCollection = new Vector();

  //constructor
  public InventoryPartSelectionViewBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setCatPartNo(String catPartNo) {
    this.catPartNo = catPartNo;
  }

  public void setDetailCollection(Collection c) {
    this.detailCollection = c;
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  public String getCatPartNo() {
    return catPartNo;
  }

  public Collection getDetailCollection() {
    return this.detailCollection;
  }
}