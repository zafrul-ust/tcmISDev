package com.tcmis.client.catalog.beans;

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

public class CatalogObjBean
    extends BaseDataBean
    implements SQLData {

  private String catalogId;
  private String catalogDesc;
  private String sqlType = "CATALOG_OBJ";

  //constructor
  public CatalogObjBean() {
  }

  //setters
  public void setCatalogId(String catalogId) {
    this.catalogId = catalogId;
  }

  public void setCatalogDesc(String catalogDesc) {
    this.catalogDesc = catalogDesc;
  }

  //getters
  public String getCatalogId() {
    return catalogId;
  }

  public String getCatalogDesc() {
    return catalogDesc;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setCatalogId(stream.readString());
      this.setCatalogDesc(stream.readString());
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
      stream.writeString(this.getCatalogId());
      stream.writeString(this.getCatalogDesc());
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