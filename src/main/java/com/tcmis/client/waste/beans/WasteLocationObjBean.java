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

public class WasteLocationObjBean extends BaseDataBean implements SQLData {

  private String refColumn;
  private String locationGroup;
  private String wasteLocationId;
  private String locationGroupWithLocation;
  private String sqlType = "WASTE_LOCATION_OBJ";

  //constructor
  public WasteLocationObjBean() {
  }

  //setters
  public void setRefColumn(String refColumn) {
    this.refColumn = refColumn;
  }
  public void setLocationGroup(String locationGroup) {
    this.locationGroup = locationGroup;
  }
  public void setWasteLocationId(String wasteLocationId) {
    this.wasteLocationId = wasteLocationId;
  }

  public void setLocationGroupWithLocation(String locationGroupWithLocation) {
    this.locationGroupWithLocation = locationGroupWithLocation;
  }

  //getters
  public String getRefColumn() {
    return refColumn;
  }
  public String getLocationGroup() {
    return locationGroup;
  }
  public String getWasteLocationId() {
    return wasteLocationId;
  }

  public String getLocationGroupWithLocation() {
    return locationGroupWithLocation;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setRefColumn(stream.readString());
      this.setLocationGroup(stream.readString());
      this.setWasteLocationId(stream.readString());
      this.setLocationGroupWithLocation(stream.readString());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeString(this.getRefColumn());
      stream.writeString(this.getLocationGroup());
      stream.writeString(this.getWasteLocationId());
      stream.writeString(this.getLocationGroupWithLocation());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}