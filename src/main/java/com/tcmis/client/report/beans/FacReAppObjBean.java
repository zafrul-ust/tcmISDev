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
 * CLASSNAME: FacReAppObjBean <br>
 * @version: 1.0, Apr 11, 2011 <br>
 *****************************************************************************/


public class FacReAppObjBean extends BaseDataBean implements SQLData {

	private String facilityId;
	private String facilityName;
	private Collection<ReAppObjBean> reportingEntityList;
	private Array reportingEntityListArray;
	private String sqlType = "FAC_RE_APP_OBJ";


	//constructor
	public FacReAppObjBean() {
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setReportingEntityList(Collection coll) {
		this.reportingEntityList = coll;
	}
	public void setReportingEntityListArray(Array reportingEntityListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) reportingEntityListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setReportingEntityList(list);
	}


	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public Collection getReportingEntityList() {
		return this.reportingEntityList;
	}
	public Array getReportingEntityListArray() {
		return reportingEntityListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
			this.setReportingEntityListArray(stream.readArray());
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
			stream.writeArray(this.getReportingEntityListArray());
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