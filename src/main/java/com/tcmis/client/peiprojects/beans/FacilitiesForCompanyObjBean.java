package com.tcmis.client.peiprojects.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilitiesForCompanyObjBean <br>
 * @version: 1.0, Jan 16, 2006 <br>
 *****************************************************************************/


public class FacilitiesForCompanyObjBean extends BaseDataBean implements SQLData {

	private String companyId;
	private String companyName;
	private Collection facilities;
	private Array facilitiesArray;
	private String sqlType = "COMPANY_TYPE";

	//constructor
	public FacilitiesForCompanyObjBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public void setFacilities(Collection coll) {
		this.facilities = coll;
	}
	public void setFacilitiesArray(Array facilitiesArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) facilitiesArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setFacilities(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Collection getFacilities() {
		return this.facilities;
	}
	public Array getFacilitiesArray() {
		return facilitiesArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCompanyId(stream.readString());
			this.setCompanyName(stream.readString());
			this.setFacilitiesArray(stream.readArray());
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
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getCompanyName());
			stream.writeArray(this.getFacilitiesArray());
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