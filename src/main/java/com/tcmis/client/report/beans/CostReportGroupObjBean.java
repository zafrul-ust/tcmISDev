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
 * CLASSNAME: CostReportGroupObjBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class CostReportGroupObjBean extends BaseDataBean implements SQLData {

	private String costReportGroup;
	private Collection facilityList;
	private Array facilityListArray;
	private Collection accountSysList;
	private Array accountSysListArray;
	private String sqlType = "COST_REPORT_GROUP_OBJ";


	//constructor
	public CostReportGroupObjBean() {
	}

	//setters
	public void setCostReportGroup(String costReportGroup) {
		this.costReportGroup = costReportGroup;
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
	public String getCostReportGroup() {
		return costReportGroup;
	}
	public Collection getFacilityList() {
		return this.facilityList;
	}
	public Array getFacilityListArray() {
		return facilityListArray;
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
			this.setCostReportGroup(stream.readString());
			this.setFacilityListArray(stream.readArray());
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
			stream.writeString(this.getCostReportGroup());
			stream.writeArray(this.getFacilityListArray());
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