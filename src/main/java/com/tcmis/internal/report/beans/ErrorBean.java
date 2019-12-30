package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ErrorBean <br>
 * @version: 1.0, Mar 17, 2004 <br>
 *****************************************************************************/

public class ErrorBean
    extends BaseDataBean {
  private String cause;

  //constructor
  public ErrorBean() {
  }

  //setters
  public void setCause(String cause) {
    this.cause = cause;
  }

  //getters
  public String getCause() {
    return cause;
  }
}
