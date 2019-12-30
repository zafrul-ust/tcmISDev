package com.tcmis.client.common.beans;

import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacAreaBldgRmOvBean <br>
 * @version: 1.0, Mar 10, 2011 <br>
 *****************************************************************************/


public class FacAreaBldgRmOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private String facilityId;
	private Collection<AreaBldgFloorRmObjBean> areaList;
	private Array areaListArray;
	private String sqlType = "FAC_AREA_BLDG_RM_OV";


	//constructor
	public FacAreaBldgRmOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setAreaList(Collection coll) {
		this.areaList = coll;
	}
	public void setAreaListArray(Array areaListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) areaListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setAreaList(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public Collection getAreaList() {
		return this.areaList;
	}
	public Array getAreaListArray() {
		return areaListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
		this.setCompanyId(stream.readString());
		this.setFacilityId(stream.readString());
		this.setAreaListArray(stream.readArray());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").
			initCause(e);
		}
	}
	public void writeSQL(SQLOutput stream) throws SQLException {
		try {
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getFacilityId());
			stream.writeArray(this.getAreaListArray());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
}