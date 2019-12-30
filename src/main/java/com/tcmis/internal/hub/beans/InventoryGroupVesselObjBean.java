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
 * CLASSNAME: InventoryGroupVesselObjBean <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class InventoryGroupVesselObjBean extends BaseDataBean implements SQLData {

	private String vesselId;
	private String vesselName;
	private String sqlType = "INVENTORY_GROUP_VESSEL_OBJ";


	//constructor
	public InventoryGroupVesselObjBean() {
	}

	//setters
	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}


	//getters
	public String getVesselId() {
		return vesselId;
	}
	public String getVesselName() {
		return vesselName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setVesselId(stream.readString());
			this.setVesselName(stream.readString());
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
			stream.writeString(this.getVesselId());
			stream.writeString(this.getVesselName());
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