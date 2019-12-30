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

public class GenFacilityObjBean extends BaseDataBean implements SQLData {

  private String facilityId;
  private String facilityName;
  private Collection wasteLocationList;
  private Array wasteLocationListArray;
  private String sqlType = "GEN_FACILITY_OBJ";

  //constructor
  public GenFacilityObjBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setFacilityName(String facilityName) {
    this.facilityName = facilityName;
  }

  public void setWasteLocationList(Collection coll) {
    this.wasteLocationList = coll;
  }

  public void setWasteLocationListArray(Array wasteLocationListArray) {
    List list = null;
    try {
      list = Arrays.asList((Object[]) wasteLocationListArray.getArray());
    } catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setWasteLocationList(list);
  }

  //getters
  public String getFacilityId() {
    return facilityId;
  }

  public String getFacilityName() {
    return facilityName;
  }

  public Collection getWasteLocationList() {
    return this.wasteLocationList;
  }

  public Array getWasteLocationListArray() {
    return wasteLocationListArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setFacilityId(stream.readString());
      this.setFacilityName(stream.readString());
      this.setWasteLocationListArray(stream.readArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
    }
  }

  public void writeSQL(SQLOutput stream) throws SQLException {
    try {
      stream.writeString(this.getFacilityId());
      stream.writeString(this.getFacilityName());
      stream.writeArray(this.getWasteLocationListArray());
    } catch (SQLException e) {
      throw (SQLException) e;
    } catch (Exception e) {
      new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
    }
  }
}