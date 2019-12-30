package com.tcmis.client.common.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: RoomObjBean <br>
 * @version: 1.0, Mar 10, 2011 <br>
 *****************************************************************************/

public class RoomObjBean extends BaseDataBean implements SQLData {

	private boolean interior = true;
	private String roomDescription;
	private BigDecimal roomId;
	private String roomName;
	private String sqlType = "ROOM_OBJ";

	//constructor
	public RoomObjBean() {
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	//getters
	public BigDecimal getRoomId() {
		return roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}

	public boolean isInterior() {
		return interior;
	}

	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		setRoomId(stream.readBigDecimal());
		setRoomName(stream.readString());
		setRoomDescription(stream.readString());
		setInterior("Y".equals(stream.readString()));
	}

	public void setInterior(boolean interior) {
		this.interior = interior;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	//setters
	public void setRoomId(BigDecimal roomId) {
		this.roomId = roomId;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		stream.writeBigDecimal(this.getRoomId());
		stream.writeString(this.getRoomName());
		stream.writeString(this.getRoomDescription());
	}
}