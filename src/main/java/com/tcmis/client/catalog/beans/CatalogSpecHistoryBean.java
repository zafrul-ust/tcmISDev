package com.tcmis.client.catalog.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: CatalogSpecViewBean <br>
 * @version: 1.0, Dec 1, 2006 <br>
 *****************************************************************************/

public class CatalogSpecHistoryBean
    extends BaseDataBean {

  private String status;
  private String facPartNo;
  private String detail;
  private String dateSet;
  private String setBy;
  private String coc;
  private String coa;
  private String specId;

  //constructor
  public CatalogSpecHistoryBean() {
  }

  //setters
  public void setStatus(String status) {
    this.status = status;
  }

  public void setFacPartNo(String facPartNo) {
    this.facPartNo = facPartNo;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public void setDateSet(String dateSet) {
    this.dateSet = dateSet;
  }

  public void setSetBy(String setBy) {
    this.setBy = setBy;
  }

  public void setCoc(String coc) {
    this.coc = coc;
  }

  public void setCoa(String coa) {
    this.coa = coa;
  }

  public void setSpecId(String s) {
    this.specId = s;
  }

  //getters
  public String getStatus() {
    return status;
  }

  public String getFacPartNo() {
    return facPartNo;
  }

  public String getDetail() {
    return detail;
  }

  public String getDateSet() {
    return dateSet;
  }

  public String getSetBy() {
    return setBy;
  }

  public String getCoc() {
    return coc;
  }

  public String getCoa() {
    return coa;
  }

  public String getSpecId() {
    return specId;
  }
}