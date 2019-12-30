package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: PersonnelCatalogViewBean <br>
 * @version: 1.0, Dec 5, 2006 <br>
 *****************************************************************************/

public class PersonnelCatalogViewBean
    extends BaseDataBean {

  private BigDecimal personnelId;
  private String catalogId;

  //constructor
  public PersonnelCatalogViewBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getCatalogId() {
    return catalogId;
  }
}