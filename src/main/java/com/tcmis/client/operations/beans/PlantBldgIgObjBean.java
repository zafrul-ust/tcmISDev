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
 * CLASSNAME: PlantBldgIgObjBean <br>
 * @version: 1.0, Nov 19, 2007 <br>
 *****************************************************************************/


public class PlantBldgIgObjBean extends BaseDataBean implements SQLData {

	private String plantId;
	private Collection bldgIg;
	private Array bldgIgArray;
	private String sqlType = "PLANT_BLDG_IG_OBJ";


	//constructor
	public PlantBldgIgObjBean() {
	}

	//setters
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public void setBldgIg(Collection coll) {
		this.bldgIg = coll;
	}
	public void setBldgIgArray(Array bldgIgArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) bldgIgArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setBldgIg(list);
	}


	//getters
	public String getPlantId() {
		return plantId;
	}
	public Collection getBldgIg() {
		return this.bldgIg;
	}
	public Array getBldgIgArray() {
		return bldgIgArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPlantId(stream.readString());
			this.setBldgIgArray(stream.readArray());
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
			stream.writeString(this.getPlantId());
			stream.writeArray(this.getBldgIgArray());
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