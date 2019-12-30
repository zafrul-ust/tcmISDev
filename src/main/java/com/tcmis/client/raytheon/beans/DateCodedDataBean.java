package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: DateCodedDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class DateCodedDataBean
    extends BaseDataBean {

  private java.lang.String date;
  private java.lang.String dateQualifierCoded;

  //constructor
  public DateCodedDataBean() {
  }

  //setters
  public void setDate(java.lang.String date) {
    this.date = date;
  }

  public void setDateQualifierCoded(java.lang.String dateQualifierCoded) {
    this.dateQualifierCoded = dateQualifierCoded;
  }

  //getters
  public java.lang.String getDate() {
    return date;
  }

  public java.lang.String getDateQualifierCoded() {
    return dateQualifierCoded;
  }
}