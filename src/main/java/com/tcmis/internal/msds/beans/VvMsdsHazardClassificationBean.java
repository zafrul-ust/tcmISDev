package com.tcmis.internal.msds.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvMsdsHazardClassificationBean <br>
 * @version: 1.0, Oct 4, 2006 <br>
 *****************************************************************************/

public class VvMsdsHazardClassificationBean
    extends BaseDataBean {

  private String hazardClassification;
  private String hazardClassificationDesc;

  //constructor
  public VvMsdsHazardClassificationBean() {
  }

  //setters
  public void setHazardClassification(String hazardClassification) {
    this.hazardClassification = hazardClassification;
  }

  public void setHazardClassificationDesc(String hazardClassificationDesc) {
    this.hazardClassificationDesc = hazardClassificationDesc;
  }

  //getters
  public String getHazardClassification() {
    return hazardClassification;
  }

  public String getHazardClassificationDesc() {
    return hazardClassificationDesc;
  }
}