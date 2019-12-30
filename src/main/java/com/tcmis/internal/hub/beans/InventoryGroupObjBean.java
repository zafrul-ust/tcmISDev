package com.tcmis.internal.hub.beans;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubIgDockOvBean <br>
 * @version: 1.0, Apr 19, 2005 <br>
 *****************************************************************************/

public class InventoryGroupObjBean
    extends BaseDataBean
    implements SQLData {

  private String inventoryGroup;
  private String inventoryGroupName;
  private String sqlType = "INVENTORY_GROUP_OBJ";

  //constructor
  public InventoryGroupObjBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setInventoryGroupName(String inventoryGroupName) {
    this.inventoryGroupName = inventoryGroupName;
  }

  //getters
  public String getInventoryGroup() {
    return this.inventoryGroup;
  }

  public String getInventoryGroupName() {
    return this.inventoryGroupName;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInventoryGroup(stream.readString());
      this.setInventoryGroupName(stream.readString());
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
      stream.writeString(this.getInventoryGroup());
      stream.writeString(this.getInventoryGroupName());
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













/////////////////////////////////////////

