package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RecipeUserGroupOvBean <br>
 * @version: 1.0, Oct 2, 2007 <br>
 *****************************************************************************/

public class RecipeUserGroupOvBean
    extends BaseDataBean
    implements SQLData {

  private BigDecimal personnelId;
  private String companyId;
  private String userGroupId;
  private Collection companies;
  private Array companiesArray;
  private String sqlType = "RECIPE_USER_GROUP_OBJ";

  //constructor
  public RecipeUserGroupOvBean() {
  }

  //setters
  public void setPersonnelId(BigDecimal personnelId) {
    this.personnelId = personnelId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setUserGroupId(String userGroupId) {
    this.userGroupId = userGroupId;
  }

  public void setCompanies(Collection coll) {
    this.companies = coll;
  }

  public void setCompaniesArray(Array companiesArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) companiesArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setCompanies(list);
  }

  //getters
  public BigDecimal getPersonnelId() {
    return personnelId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public String getUserGroupId() {
    return userGroupId;
  }

  public Collection getCompanies() {
    return this.companies;
  }

  public Array getCompaniesArray() {
    return companiesArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setPersonnelId(stream.readBigDecimal());
      this.setCompanyId(stream.readString());
      this.setUserGroupId(stream.readString());
      this.setCompaniesArray(stream.readArray());
    }
    catch (SQLException e) {
      throw (SQLException) e;
    }
    catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").
          initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeBigDecimal(this.getPersonnelId());
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getUserGroupId());
      stream.writeArray(this.getCompaniesArray());
    }
    catch (SQLException e) {
      throw (SQLException) e;
    }
    catch (Exception e) {
      new IllegalStateException(getClass().getName() +
                                ".writeSQL method failed").
          initCause(e);
    }
  }
}