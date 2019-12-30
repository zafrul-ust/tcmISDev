package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.internal.catalog.beans.ReportingEntityBean;


/******************************************************************************
 * CLASSNAME: FacNameAreaBldgRmObjBean <br>
 * @version: 1.0, Jan 13, 2012 <br>
 *****************************************************************************/


public class FacNameAreaBldgRmObjBean extends BaseDataBean implements SQLData {

	private String facilityGroupId;
	private String facilityId;
	private String facilityName;
	private Collection<AccountSysBean> accountSysList;
	private Collection<AreaBldgFloorRmObjBean> areaList;
	private Collection<ReportingEntityBean> reportingEntityList;
	private Collection<DeptBean> deptList;
	private Collection<FlammabilityControlZoneBean> flammabilityControlZoneList;
	private Collection<VocZoneBean> vocZoneList;
	private Array areaListArray;
	private Array reportingEntityListArray;
	private Array deptListArray;
	private Array flammabilityControlZoneListArray;
	private Array accountSysListArray;
	private Array vocZoneListArray;
	private boolean putAwayRequired;
	private String sqlType = "FAC_NAME_AREA_BLDG_RM_OBJ";


	//constructor
	public FacNameAreaBldgRmObjBean() {
	}

	//setters
	
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public void setAreaList(Collection coll) {
		this.areaList = coll;
	}
	public void setReportingEntityList(Collection coll) {
		this.reportingEntityList = coll;
	}
	public void setDeptList(Collection coll) {
		this.deptList = coll;
	}
	public void setAccountSysList(Collection coll) {
		this.accountSysList = coll;
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
	
	public void setDeptListArray(Array deptListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) deptListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setDeptList(list);
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
	public void setVocZoneListArray(Array vocZoneListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) vocZoneListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setVocZoneList(list);
	}
	public void setFlammabilityControlZoneListArray(Array flammabilityControlZoneListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) flammabilityControlZoneListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setFlammabilityControlZoneList(list);
	}

	//getters
	
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public Collection getAreaList() {
		return this.areaList;
	}
	public Collection getReportingEntityList() {
		return this.reportingEntityList;
	}
	public Collection getDeptList() {
		return this.deptList;
	}
	public Array getAreaListArray() {
		return areaListArray;
	}
	public Array getReportingEntityListArray() {
		return reportingEntityListArray;
	}
	public Array getDeptListArray() {
		return deptListArray;
	}
	public Array getAccountSysListArray() {
		return accountSysListArray;
	}
	public Collection getAccountSysList() {
		return accountSysList;
	}
	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
			this.setAreaListArray(stream.readArray());
			this.setReportingEntityListArray(stream.readArray());
			this.setDeptListArray(stream.readArray());
			this.setVocZoneListArray (this.getVocZoneListArray());
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
			stream.writeArray(this.getAreaListArray());
			stream.writeArray(this.getReportingEntityListArray());
			stream.writeArray(this.getDeptListArray());
			stream.writeArray(this.getVocZoneListArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getFacilityGroupId() {
		return facilityGroupId;
	}

	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}

	public boolean isPutAwayRequired() {
		return putAwayRequired;
	}

	public void setPutAwayRequired(boolean putAwayRequired) {
		this.putAwayRequired = putAwayRequired;
	}

	public Collection<VocZoneBean> getVocZoneList() {
		return vocZoneList;
	}

	public void setVocZoneList(Collection vocZoneList) {
		this.vocZoneList = vocZoneList;
	}

	public Array getVocZoneListArray() {
		return vocZoneListArray;
	}

	public Collection<FlammabilityControlZoneBean> getFlammabilityControlZoneList() {
		return flammabilityControlZoneList;
	}

	public void setFlammabilityControlZoneList(Collection flammabilityControlZoneList) {
		this.flammabilityControlZoneList = flammabilityControlZoneList;
	}

	public Array getFlammabilityControlZoneListArray() {
		return flammabilityControlZoneListArray;
	}
}