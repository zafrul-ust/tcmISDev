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
 * CLASSNAME: FacilityGroupFacilityObjBean <br>
 * @version: 1.0, Mar 15, 2011 <br>
 *****************************************************************************/


public class FacilityGroupFacilityObjBean extends BaseDataBean implements SQLData {

	private String facilityGroupId;
	private String facilityGroupDescription;
	private Collection<FacilityObjBean> facilityList;
	private Array facilityListArray;
	private String sqlType = "FACILITY_GROUP_FACILITY_OBJ";


	//constructor
	public FacilityGroupFacilityObjBean() {
	}

	//setters
	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}
	public void setFacilityGroupDescription(String facilityGroupDescription) {
		this.facilityGroupDescription = facilityGroupDescription;
	}
	public void setFacilityList(Collection coll) {
		this.facilityList = coll;
	}
	public void setFacilityListArray(Array facilityListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) facilityListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setFacilityList(list);
	}


	//getters
	public String getFacilityGroupId() {
		return facilityGroupId;
	}
	public String getFacilityGroupDescription() {
		return facilityGroupDescription;
	}
	public Collection getFacilityList() {
		return this.facilityList;
	}
	public Array getFacilityListArray() {
		return facilityListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setFacilityGroupId(stream.readString());
			this.setFacilityGroupDescription(stream.readString());
			this.setFacilityListArray(stream.readArray());
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
			stream.writeString(this.getFacilityGroupId());
			stream.writeString(this.getFacilityGroupDescription());
			stream.writeArray(this.getFacilityListArray());
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