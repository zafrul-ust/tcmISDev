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
 * CLASSNAME: BldgFloorEditOvBean <br>
 * @version: 1.0, May 23, 2005 <br>
 *****************************************************************************/

public class BldgFloorEditOvBean
    extends BaseDataBean
    implements SQLData {

  private String building;
  private String buildingVariable;
  private Collection floor;
  private Array floorArray;
  private String sqlType = "BLDG_FLOOR_EDIT_OBJ";

  //constructor
  public BldgFloorEditOvBean() {
  }

  //setters
  public void setBuilding(String building) {
    this.building = building;
  }

  public void setBuildingVariable(String buildingVariable) {
    this.buildingVariable = buildingVariable;
  }

  public void setFloor(Collection coll) {
    this.floor = coll;
  }

  public void setFloorArray(Array floorArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[])floorArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setFloor(list);
  }

  //getters
  public String getBuilding() {
    return this.building;
  }

  public String getBuildingVariable() {
    return this.buildingVariable;
  }

  public Collection getFloor() {
    return this.floor;
  }

  public Array getFloorArray() {
    return this.floorArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setBuilding(stream.readString());
      this.setBuildingVariable(stream.readString());
      this.setFloorArray(stream.readArray());
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
      stream.writeString(this.getBuilding());
      stream.writeString(this.getBuildingVariable());
      stream.writeArray(this.getFloorArray());
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
