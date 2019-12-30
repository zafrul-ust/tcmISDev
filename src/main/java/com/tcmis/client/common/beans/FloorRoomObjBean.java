package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FloorRoomObjBean <br>
 * @version: 1.0, Jan 13, 2012 <br>
 *****************************************************************************/


public class FloorRoomObjBean extends BaseDataBean implements SQLData {

	private BigDecimal floorId;
	private String description;
	private Collection<RoomObjBean> roomList;
	private Array roomListArray;
	private String sqlType = "FLOOR_ROOM_OBJ";


	//constructor
	public FloorRoomObjBean() {
	}

	//setters
	public void setFloorId(BigDecimal floorId) {
		this.floorId = floorId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setRoomList(Collection coll) {
		this.roomList = coll;
	}
	public void setRoomListArray(Array roomListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) roomListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setRoomList(list);
	}


	//getters
	public BigDecimal getFloorId() {
		return floorId;
	}
	public String getDescription() {
		return description;
	}
	public Collection getRoomList() {
		return this.roomList;
	}
	public Array getRoomListArray() {
		return roomListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setFloorId(stream.readBigDecimal());
			this.setDescription(stream.readString());
			this.setRoomListArray(stream.readArray());
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
			stream.writeBigDecimal(this.getFloorId());
			stream.writeString(this.getDescription());
			stream.writeArray(this.getRoomListArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
}