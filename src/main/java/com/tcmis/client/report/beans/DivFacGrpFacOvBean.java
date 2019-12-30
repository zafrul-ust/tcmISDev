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
 * CLASSNAME: DivFacGrpFacOvBean <br>
 * @version: 1.0, Mar 15, 2011 <br>
 *****************************************************************************/


public class DivFacGrpFacOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private String divisionId;
	private String divisionDescription;
	private Collection<FacilityGroupFacilityObjBean> facilityGroupList;
	private Array facilityGroupListArray;
	private String sqlType = "DIV_FAC_GRP_FAC_OV";


	//constructor
	public DivFacGrpFacOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public void setDivisionDescription(String divisionDescription) {
		this.divisionDescription = divisionDescription;
	}
	public void setFacilityGroupList(Collection coll) {
		this.facilityGroupList = coll;
	}
	public void setFacilityGroupListArray(Array facilityGroupListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) facilityGroupListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setFacilityGroupList(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public String getDivisionDescription() {
		return divisionDescription;
	}
	public Collection getFacilityGroupList() {
		return this.facilityGroupList;
	}
	public Array getFacilityGroupListArray() {
		return facilityGroupListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setCompanyId(stream.readString());
			this.setDivisionId(stream.readString());
			this.setDivisionDescription(stream.readString());
			this.setFacilityGroupListArray(stream.readArray());
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
			stream.writeString(this.getDivisionId());
			stream.writeString(this.getDivisionDescription());
			stream.writeArray(this.getFacilityGroupListArray());
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