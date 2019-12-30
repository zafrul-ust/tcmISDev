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
 * CLASSNAME: CostReportInitialOvBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class CostReportInitialOvBean extends BaseDataBean implements SQLData {

	private BigDecimal personnelId;
	private String companyId;
	private String companyName;
	private Collection costReportGroupList;
	private Array costReportGroupListArray;
	private Collection companyAccountSysList;
	private Array companyAccountSysListArray;
	private String sqlType = "COST_REPORT_INITIAL_OV";


	//constructor
	public CostReportInitialOvBean() {
	}

	//setters
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setCostReportGroupList(Collection coll) {
		this.costReportGroupList = coll;
	}
	public void setCostReportGroupListArray(Array costReportGroupListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) costReportGroupListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCostReportGroupList(list);
	}
	public void setCompanyAccountSysList(Collection coll) {
		this.companyAccountSysList = coll;
	}
	public void setCompanyAccountSysListArray(Array companyAccountSysListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) companyAccountSysListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCompanyAccountSysList(list);
	}


	//getters
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Collection getCostReportGroupList() {
		return this.costReportGroupList;
	}
	public Array getCostReportGroupListArray() {
		return costReportGroupListArray;
	}
	public Collection getCompanyAccountSysList() {
		return this.companyAccountSysList;
	}
	public Array getCompanyAccountSysListArray() {
		return companyAccountSysListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPersonnelId(stream.readBigDecimal());
			this.setCompanyId(stream.readString());
			this.setCompanyName(stream.readString());
			this.setCostReportGroupListArray(stream.readArray());
			this.setCompanyAccountSysListArray(stream.readArray());
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
			stream.writeBigDecimal(this.getPersonnelId());
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getCompanyName());
			stream.writeArray(this.getCostReportGroupListArray());
			stream.writeArray(this.getCompanyAccountSysListArray());
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