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

public class DeptBldgFloorEditOvBean
    extends BaseDataBean
    implements SQLData {

  private String department;
  private String departmentVariable;
  private Collection bldgFloor;
  private Array bldgFloorArray;
  private String sqlType = "DEPT_BLDG_FLOOR_EDIT_OBJ";

  //constructor
  public DeptBldgFloorEditOvBean() {
  }

  //setters
  public void setDepartment(String department) {
    this.department = department;
  }

  public void setDepartmentVariable(String departmentVariable) {
    this.departmentVariable = departmentVariable;
  }

  public void setBldgFloor(Collection coll) {
    this.bldgFloor = coll;
  }

  public void setBldgFloorArray(Array bldgFloorArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) bldgFloorArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setBldgFloor(list);
  }

  //getters
  public String getDepartment() {
    return this.department;
  }

  public String getDepartmentVariable() {
    return this.departmentVariable;
  }

  public Collection getBldgFloor() {
    return this.bldgFloor;
  }

  public Array getBldgFloorArray() {
    return this.bldgFloorArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setDepartment(stream.readString());
      this.setDepartmentVariable(stream.readString());
      this.setBldgFloorArray(stream.readArray());
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
      stream.writeString(this.getDepartment());
      stream.writeString(this.getDepartmentVariable());
      stream.writeArray(this.getBldgFloorArray());
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
