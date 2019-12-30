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
 * CLASSNAME: ReAppObjBean <br>
 * @version: 1.0, Apr 11, 2011 <br>
 *****************************************************************************/


public class ReAppObjBean extends BaseDataBean implements SQLData {

	private String reportingEntityId;
	private String reportingEntityDescription;
	private Collection<ApplicationObjBean> applicationList;
	private Array applicationListArray;
	private String sqlType = "RE_APP_OBJ";


	//constructor
	public ReAppObjBean() {
	}

	//setters
	public void setReportingEntityId(String reportingEntityId) {
		this.reportingEntityId = reportingEntityId;
	}
	public void setReportingEntityDescription(String reportingEntityDescription) {
		this.reportingEntityDescription = reportingEntityDescription;
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


	//getters
	public String getReportingEntityId() {
		return reportingEntityId;
	}
	public String getReportingEntityDescription() {
		return reportingEntityDescription;
	}
	public Collection getApplicationList() {
		return this.applicationList;
	}
	public Array getApplicationListArray() {
		return applicationListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setReportingEntityId(stream.readString());
			this.setReportingEntityDescription(stream.readString());
			this.setApplicationListArray(stream.readArray());
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
			stream.writeString(this.getReportingEntityId());
			stream.writeString(this.getReportingEntityDescription());
			stream.writeArray(this.getApplicationListArray());
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