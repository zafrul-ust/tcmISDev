package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PersonnelFacAreaBldgRmOvBean <br>
 * @version: 1.0, Jan 13, 2012 <br>
 *****************************************************************************/


public class PersonnelFacAreaBldgRmOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private String companyName;
	private BigDecimal personnelId;
	private Collection<FacilityGroupBean> facilityGroupList;
	private Collection<FacNameAreaBldgRmObjBean> facilityList;
	private Array facilityListArray;
	private String sqlType = "PERSONNEL_FAC_AREA_BLDG_RM_OV";


	//constructor
	public PersonnelFacAreaBldgRmOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
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
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
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
			this.setCompanyId(stream.readString());
			this.setPersonnelId(stream.readBigDecimal());
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
			stream.writeString(this.getCompanyId());
			stream.writeBigDecimal(this.getPersonnelId());
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

	public Collection<FacilityGroupBean> getFacilityGroupList() {
		return facilityGroupList;
	}

	public void setFacilityGroupList(Collection<FacilityGroupBean> facilityGroupList) {
		this.facilityGroupList = facilityGroupList;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}