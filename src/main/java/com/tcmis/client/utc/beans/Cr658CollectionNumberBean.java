package com.tcmis.client.utc.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: Cr658CollectionNumberBean <br>
 * @version: 1.0, Aug 29, 2006 <br>
 *****************************************************************************/

public class Cr658CollectionNumberBean
    extends BaseDataBean {

  private String division;
  private BigDecimal collno;

  //constructor
  public Cr658CollectionNumberBean() {
  }

  //setters
  public void setDivision(String division) {
    this.division = division;
  }

  public void setCollno(BigDecimal collno) {
    this.collno = collno;
  }

  //getters
  public String getDivision() {
    return division;
  }

  public BigDecimal getCollno() {
    return collno;
  }
}