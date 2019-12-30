package com.tcmis.client.raytheon.beans;
import java.util.Vector;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OrderReferenceDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class OrderReferenceDataBean
    extends BaseDataBean {

  private java.lang.String accountCodeRefNum;
  private String dateQualifier;
  private java.util.Collection referenceCodedColl = new Vector();

  //constructor
  public OrderReferenceDataBean() {
  }

  //setters
  public void setAccountCodeRefNum(java.lang.String refNum) {
    this.accountCodeRefNum = refNum;
  }

  public void setDateQualifier(java.lang.String dateQualifier) {
    this.dateQualifier = dateQualifier;
  }

  public void addReferenceCodedDataBean(ReferenceCodedDataBean bean) {
    this.referenceCodedColl.add(bean);
  }

  public void setReferenceCodedColl(java.util.Collection referenceCodedColl) {
    this.referenceCodedColl = referenceCodedColl;
  }

  //getters
  public java.lang.String getAccountCodeRefNum() {
    return accountCodeRefNum;
  }

  public java.lang.String getDateQualifier() {
    return dateQualifier;
  }

  public java.util.Collection getReferenceCodedColl() {
    return referenceCodedColl;
  }
}