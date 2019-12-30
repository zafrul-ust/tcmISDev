package com.tcmis.client.operations.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BldgIgObjBean <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class BldgIgObjBean extends BaseDataBean implements SQLData {

	private String bldgId;
	private String facilityId;
	private Collection inventoryGroupColl;
	private Array inventoryGroupCollArray;
	private String sqlType = "BLDG_IG_OBJ";


	//constructor
	public BldgIgObjBean() {
	}

	//setters
	public void setBldgId(String bldgId) {
		this.bldgId = bldgId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setInventoryGroupColl(Collection coll) {
		this.inventoryGroupColl = coll;
	}
	public void setInventoryGroupCollArray(Array inventoryGroupCollArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) inventoryGroupCollArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setInventoryGroupColl(list);
	}


	//getters
	public String getBldgId() {
		return bldgId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public Collection getInventoryGroupColl() {
		return this.inventoryGroupColl;
	}
	public Array getInventoryGroupCollArray() {
		return inventoryGroupCollArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBldgId(stream.readString());
			this.setFacilityId(stream.readString());
			this.setInventoryGroupCollArray(stream.readArray());
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
			stream.writeString(this.getBldgId());
			stream.writeString(this.getFacilityId());
			stream.writeArray(this.getInventoryGroupCollArray());
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