package com.tcmis.internal.msds.beans;

import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacBldgFloorDeptEditOvBean <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class FacBldgFloorDeptEditOvBean
    extends BaseDataBean
    implements SQLData {

  private String facilityId;
  private Collection deptBldgFloor;
  private Array deptBldgFloorArray;
  private String sqlType = "FAC_DEPT_BLDG_FLOOR_EDIT_OBJ";

  //constructor
  public FacBldgFloorDeptEditOvBean() {
  }

  //setters
  public void setFacilityId(String facilityId) {
    this.facilityId = facilityId;
  }

  public void setDeptBldgFloor(Collection coll) {
    this.deptBldgFloor = coll;
  }

  public void setDeptBldgFloorArray(Array deptBldgFloorArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) deptBldgFloorArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setDeptBldgFloor(list);
  }

  //getters
  public String getFacilityId() {
    return facilityId;
  }

  public Collection getDeptBldgFloor() {
    return this.deptBldgFloor;
  }

  public Array getDeptBldgFloorArray() {
    return deptBldgFloorArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setFacilityId(stream.readString());
      this.setDeptBldgFloorArray(stream.readArray());
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
      stream.writeString(this.getFacilityId());
      stream.writeArray(this.getDeptBldgFloorArray());
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