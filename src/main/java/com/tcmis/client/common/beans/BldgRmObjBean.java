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
 * CLASSNAME: BldgRmObjBean <br>
 * @version: 1.0, Mar 10, 2011 <br>
 *****************************************************************************/


public class BldgRmObjBean extends BaseDataBean implements SQLData {

	private BigDecimal buildingId;
	private String buildingName;
	private String buildingDescription;
	private Collection<RoomObjBean> roomList;
	private Array roomListArray;
	private String sqlType = "BLDG_RM_OBJ";


	//constructor
	public BldgRmObjBean() {
	}

	//setters
	public void setBuildingId(BigDecimal buildingId) {
		this.buildingId = buildingId;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public void setBuildingDescription(String buildingDescription) {
		this.buildingDescription = buildingDescription;
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
	public BigDecimal getBuildingId() {
		return buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public String getBuildingDescription() {
		return buildingDescription;
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
			this.setBuildingId(stream.readBigDecimal());
			this.setBuildingName(stream.readString());
			this.setBuildingDescription(stream.readString());
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
			stream.writeBigDecimal(this.getBuildingId());
			stream.writeString(this.getBuildingName());
			stream.writeString(this.getBuildingDescription());
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