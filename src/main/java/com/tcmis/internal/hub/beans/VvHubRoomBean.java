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
 * CLASSNAME: VvHubRoomBean <br>
 * @version: 1.0, Sep 13, 2006 <br>
 *****************************************************************************/

public class VvHubRoomBean
    extends BaseDataBean
    implements SQLData {

  private String hub;
  private String room;
  private String roomDescription;
  private BigDecimal routeOrder;

  private String sqlType = "ROOM_OBJ";

  //constructor
  public VvHubRoomBean() {
  }

  //setters
  public void setHub(String hub) {
    this.hub = hub;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public void setRoomDescription(String roomDescription) {
    this.roomDescription = roomDescription;
  }

  //getters
  public String getHub() {
    return hub;
  }

  public String getRoom() {
    return room;
  }

  public String getRoomDescription() {
    return roomDescription;
  }

  public String getSQLTypeName() {
    return this.sqlType;
  }

  public BigDecimal getRouteOrder() {
	return routeOrder;
  }

  public void setRouteOrder(BigDecimal routeOrder) {
	this.routeOrder = routeOrder;
  }

  public void readSQL(SQLInput stream, String type) throws SQLException {
    sqlType = type;
    try {
      //this.setHub(stream.readString());
      this.setRoom(stream.readString());
      this.setRoomDescription(stream.readString());
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
      //stream.writeString(this.getHub());
      stream.writeString(this.getRoom());
      stream.writeString(this.getRoomDescription());
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