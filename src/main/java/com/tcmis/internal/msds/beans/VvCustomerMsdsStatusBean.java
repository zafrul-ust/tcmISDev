package com.tcmis.internal.msds.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VvCustomerMsdsStatusBean <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class VvCustomerMsdsStatusBean
    extends BaseDataBean {

  private String status;
  private String description;
  private String display;

  //constructor
  public VvCustomerMsdsStatusBean() {
  }

  //setters
  public void setStatus(String status) {
    this.status = status;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  //getters
  public String getStatus() {
    return status;
  }

  public String getDescription() {
    return description;
  }

  public String getDisplay() {
    return display;
  }
}