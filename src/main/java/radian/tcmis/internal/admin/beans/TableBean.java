package radian.tcmis.internal.admin.beans;

import java.util.Collection;

import radian.tcmis.common.framework.BaseDataBean;

/******************************************************************************
  * A data holder for table meta data.
 * @version: 1.0, Mar 17, 2004
 *****************************************************************************/

public class TableBean extends BaseDataBean {

  private int columnCount;
  private String name;
  private Collection columnBeanCollection;

  //constructor
  public TableBean() {
    super();
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
