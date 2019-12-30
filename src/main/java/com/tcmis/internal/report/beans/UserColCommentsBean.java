package com.tcmis.internal.report.beans;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UserColCommentsBean <br>
 * @version: 1.0, Mar 30, 2004 <br>
 *****************************************************************************/

public class UserColCommentsBean
    extends BaseDataBean {

  private String tableName;
  private String columnName;
  private String comments;
  private String dataType;

  //constructor
  public UserColCommentsBean() {
  }

  //setters
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  //getters
  public String getTableName() {
    return tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getComments() {
    return comments;
  }

  public String getDataType() {
    return dataType;
  }

}