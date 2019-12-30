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

public class CompanyCatalogObjBean
    extends BaseDataBean
    implements SQLData {

  private String companyId;
  private String companyName;
  private Collection catalogs;
  private Array catalogsArray;
  private String sqlType = "COMPANY_CATALOG_OBJ";

  //constructor
  public CompanyCatalogObjBean() {
  }

  //setters
  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setCatalogs(Collection coll) {
    this.catalogs = coll;
  }

  public void setCatalogsArray(Array catalogsArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) catalogsArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setCatalogs(list);
  }

  //getters
  public String getCompanyId() {
    return companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public Collection getCatalogs() {
    return this.catalogs;
  }

  public Array getCatalogsArray() {
    return catalogsArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setCompanyId(stream.readString());
      this.setCompanyName(stream.readString());
      this.setCatalogsArray(stream.readArray());
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
      stream.writeString(this.getCompanyId());
      stream.writeString(this.getCompanyName());
      stream.writeArray(this.getCatalogsArray());
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