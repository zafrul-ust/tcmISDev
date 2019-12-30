package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ReferenceCodedDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class ReferenceCodedDataBean
    extends BaseDataBean {

  private java.lang.String refNum;
  private java.lang.String referenceType;
  private java.lang.String referenceTypeOther;

  //constructor
  public ReferenceCodedDataBean() {
  }

  //setters
  public void setRefNum(java.lang.String refNum) {
    this.refNum = refNum;
  }

  public void setReferenceType(java.lang.String referenceType) {
    this.referenceType = referenceType;
  }

  public void setReferenceTypeOther(java.lang.String referenceTypeOther) {
    this.referenceTypeOther = referenceTypeOther;
  }
  //getters
  public java.lang.String getRefNum() {
    return refNum;
  }

  public java.lang.String getReferenceType() {
    return referenceType;
  }

  public java.lang.String getReferenceTypeOther() {
    return referenceTypeOther;
  }
}