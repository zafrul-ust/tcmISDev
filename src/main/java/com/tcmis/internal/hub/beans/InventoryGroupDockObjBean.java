package com.tcmis.internal.hub.beans;

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
 * CLASSNAME: HubIgDockOvBean <br>
 * @version: 1.0, Apr 19, 2005 <br>
 *****************************************************************************/

public class InventoryGroupDockObjBean
    extends BaseDataBean
    implements SQLData {

  private String inventoryGroup;
  private String hub;
  private String inventoryGroupName;
  private Collection docks;
  private Array docksArray;
  private String sqlType = "INVENTORY_GROUP_DOCK_OBJ";

  //constructor
  public InventoryGroupDockObjBean() {
  }

  //setters
  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setInventoryGroupName(String inventoryGroupName) {
    this.inventoryGroupName = inventoryGroupName;
  }


  public void setDocks(Collection coll) {
    this.docks = coll;
  }

  public void setDocksArray(Array docksArray) {
    //System.out.println("setting docks with:" + docksArray);
    List list = null;
    try {
      list = Arrays.asList( (Object[]) docksArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setDocks(list);
  }

  //getters
  public String getInventoryGroup() {
    return this.inventoryGroup;
  }

  public String getHub() {
    return this.hub;
  }

  public String getInventoryGroupName() {
    return this.inventoryGroupName;
  }

  public Collection getDocks() {
    return this.docks;
  }

  public Array getDocksArray() {
    return this.docksArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setInventoryGroup(stream.readString());
      this.setHub(stream.readString());
      this.setInventoryGroupName(stream.readString());
      this.setDocksArray(stream.readArray());
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
      stream.writeString(this.getHub());
      stream.writeString(this.getInventoryGroupName());
      stream.writeArray(this.getDocksArray());
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


