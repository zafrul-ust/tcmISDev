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

public class FloorEditOvBean
    extends BaseDataBean
    implements SQLData {

  private String floor;
  private String floorVariable;
  private String sqlType = "FLOOR_EDIT_OBJ";

  //constructor
  public FloorEditOvBean() {
  }

  //setters
  public void setFloor(String floor) {
    this.floor = floor;
  }

  public void setFloorVariable(String floorVariable) {
    this.floorVariable = floorVariable;
  }

  //getters
  public String getFloor() {
    return this.floor;
  }

  public String getFloorVariable() {
    return this.floorVariable;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setFloor(stream.readString());
      this.setFloorVariable(stream.readString());
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
      stream.writeString(this.getFloor());
      stream.writeString(this.getFloorVariable());
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
