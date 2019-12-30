package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubRoomOvBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class HubRoomOvBean
    extends BaseDataBean
    implements SQLData {

  private String hub;
  private String hubName;
  private Collection roomVar;
  private Array roomVarArray;
  private String sqlType = "HUB_ROOM_OBJ";

  //constructor
  public HubRoomOvBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public void setRoomVar(Collection coll) {
    this.roomVar = coll;
  }

  public void setRoomVarArray(Array roomVarArray) {
    List list = null;
    try {
      list = Arrays.asList( (Object[]) roomVarArray.getArray());
    }
    catch (SQLException sqle) {
      System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
    }
    this.setRoomVar(list);
  }

  //getters
  public String getHub() {
    return hub;
  }

  public String getHubName() {
    return hubName;
  }

  public Collection getRoomVar() {
    return this.roomVar;
  }

  public Array getRoomVarArray() {
    return roomVarArray;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      this.setHub(stream.readString());
      this.setHubName(stream.readString());
      this.setRoomVarArray(stream.readArray());
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
      stream.writeString(this.getHub());
      stream.writeString(this.getHubName());
      stream.writeArray(this.getRoomVarArray());
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