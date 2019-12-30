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
 * CLASSNAME: FacilityAppObjBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class FacilityAppObjBean extends BaseDataBean implements SQLData {

	private String facilityId;
	private String facilityName;
	private Collection applicationList;
	private Array applicationListArray;
	private Collection accountSysList;
	private Array accountSysListArray;
	private String sqlType = "FACILITY_APP_OBJ";


	//constructor
	public FacilityAppObjBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setApplicationList(Collection coll) {
		this.applicationList = coll;
	}
	public void setApplicationListArray(Array applicationListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) applicationListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setApplicationList(list);
	}
	public void setAccountSysList(Collection coll) {
		this.accountSysList = coll;
	}
	public void setAccountSysListArray(Array accountSysListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) accountSysListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setAccountSysList(list);
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public Collection getApplicationList() {
		return this.applicationList;
	}
	public Array getApplicationListArray() {
		return applicationListArray;
	}
	public Collection getAccountSysList() {
		return this.accountSysList;
	}
	public Array getAccountSysListArray() {
		return accountSysListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
			this.setApplicationListArray(stream.readArray());
			this.setAccountSysListArray(stream.readArray());
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
			stream.writeArray(this.getApplicationListArray());
			stream.writeArray(this.getAccountSysListArray());
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