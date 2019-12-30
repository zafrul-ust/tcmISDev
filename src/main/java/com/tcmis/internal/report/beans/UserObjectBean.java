package com.tcmis.internal.report.beans;

import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: UserObjectBean <br>
 * @version: 1.0, Apr 21, 2004 <br>
 *****************************************************************************/

public class UserObjectBean
    extends BaseDataBean {

  private int personnelId;
  private String tableName;
  private String comments;
  private Collection userColCommentsBeanCollection;

  //constructor
  public UserObjectBean() {
  }

  //setters
  public void setPersonnelId(int personnelId) {
    this.personnelId = personnelId;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void setUserColCommentsBeanCollection(Collection
                                               userColCommentsBeanCollection) {
    this.userColCommentsBeanCollection = userColCommentsBeanCollection;
  }

  //getters
  public int getPersonnelId() {
    return personnelId;
  }

  public String getTableName() {
    return tableName;
  }

  public String getComments() {
    return comments;
  }

  public Collection getUserColCommentsBeanCollection() {
    return userColCommentsBeanCollection;
  }
}