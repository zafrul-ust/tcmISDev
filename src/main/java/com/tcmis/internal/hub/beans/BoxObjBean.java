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
 * CLASSNAME: CarrierObjBean <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class BoxObjBean extends BaseDataBean implements SQLData {

	private String boxId;
	private String trackingNumber;
	private String sqlType = "BOX_OBJ";


	//constructor
	public BoxObjBean() {
	}

	//setters
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	//getters
	public String getBoxId() {
		return boxId;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBoxId(stream.readString());
			this.setTrackingNumber(stream.readString());
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
			stream.writeString(this.getBoxId());
			stream.writeString(this.getTrackingNumber());
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