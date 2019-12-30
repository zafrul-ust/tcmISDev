package com.tcmis.internal.report.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: ViewBean <br>
 * @version: 1.0, Mar 17, 2004 <br>
 *****************************************************************************/

public class ViewBean
    extends BaseDataBean {

  private int columnCount;
  private String name;
  private Collection columnBeanCollection;

  //constructor
  public ViewBean() {
  }

  //setters
  public void setColumnCount(int columnCount) {
    this.columnCount = columnCount;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setColumnBeanCollection(Collection columnBeanCollection) {
    this.columnBeanCollection = columnBeanCollection;
  }

  //getters
  public int getColumnCount() {
    return columnCount;
  }

  public String getName() {
    return name;
  }

  public Collection getColumnBeanCollection() {
    return columnBeanCollection;
  }

}
