package com.tcmis.client.waste.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TsdfReportDropdownOvBean <br>
 * @version: 1.0, Mar 1, 2007 <br>
 *****************************************************************************/

public class ToVendorObjBean extends BaseDataBean implements SQLData {

  private String vendorId;
  private String vendorName;
  private String sqlType = "TO_VENDOR_OBJ";

  //constructor
  public ToVendorObjBean() {
  }

  //setters
  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  //getters
  public String getVendorId() {
    return vendorId;
  }

  public String getVendorName() {
    return vendorName;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setVendorId(stream.readString());
      this.setVendorName(stream.readString());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeString(this.getVendorId());
      stream.writeString(this.getVendorName());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}