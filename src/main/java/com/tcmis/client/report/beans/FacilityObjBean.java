package com.tcmis.client.report.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilityObjBean <br>
 * @version: 1.0, Mar 15, 2011 <br>
 *****************************************************************************/


public class FacilityObjBean extends BaseDataBean implements SQLData {

	private String facilityId;
	private String facilityName;
	private String sqlType = "FACILITY_OBJ";


	//constructor
	public FacilityObjBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
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
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getFacilityName());
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