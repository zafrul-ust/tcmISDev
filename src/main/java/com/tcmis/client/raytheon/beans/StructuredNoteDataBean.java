package com.tcmis.client.raytheon.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: StructuredNoteDataBean <br>
 * @version: 1.0, Jun 28, 2005 <br>
 *****************************************************************************/

public class StructuredNoteDataBean
    extends BaseDataBean {

  private java.lang.String generalNote;

  //constructor
  public StructuredNoteDataBean() {
  }

  //setters
  public void setGeneralNote(java.lang.String generalNote) {
    this.generalNote = generalNote;
  }

  //getters
  public java.lang.String getGeneralNote() {
    return generalNote;
  }

}