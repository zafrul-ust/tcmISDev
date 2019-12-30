package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ContactNumberDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class ContactNumberDataBean
    extends BaseDataBean {

  private java.lang.String contactNumberValue;
  private java.lang.String contactNumberTypeCoded;

  //constructor
  public ContactNumberDataBean() {
  }

  //setters
  public void setContactNumberValue(java.lang.String contactNumberValue) {
    this.contactNumberValue = contactNumberValue;
  }

  public void setContactNumberTypeCoded(java.lang.String contactNumberTypeCoded) {
    this.contactNumberTypeCoded = contactNumberTypeCoded;
  }

  //getters
  public java.lang.String getContactNumberValue() {
    return contactNumberValue;
  }

  public java.lang.String getContactNumberTypeCoded() {
    return contactNumberTypeCoded;
  }
}