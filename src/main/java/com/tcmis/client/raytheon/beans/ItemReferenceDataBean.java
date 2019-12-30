package com.tcmis.client.raytheon.beans;
import java.util.Vector;
import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ItemReferenceDataBean <br>
 * @version: 1.0, Jun 29, 2005 <br>
 *****************************************************************************/

public class ItemReferenceDataBean
    extends BaseDataBean {

  private java.util.Collection referenceCodedColl = new Vector();

  //constructor
  public ItemReferenceDataBean() {
  }

  //setters
  public void addReferenceCodedDataBean(ReferenceCodedDataBean bean) {
    this.referenceCodedColl.add(bean);
  }

  public void setReferenceCodedColl(java.util.Collection referenceCodedColl) {
    this.referenceCodedColl = referenceCodedColl;
  }

  //getters
  public java.util.Collection getReferenceCodedColl() {
    return referenceCodedColl;
  }
}