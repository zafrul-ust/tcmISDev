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
 * CLASSNAME: HubIgBuyerOvBean <br>
 * @version: 1.0, May 7, 2008 <br>
 *****************************************************************************/


public class FacPlanningReportOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private String facilityId;
	private String facilityName;
	private Collection planningReportAreaList;
	private Array planningReportAreaArray;
	private String sqlType = "FACILITY_PLANNING_REPORT_OV";


	//constructor
	public FacPlanningReportOvBean() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
    }

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setPlanningReportAreaList(Collection coll) {
		this.planningReportAreaList = coll;
	}
	
	public void setPlanningReportAreaArray(Array planningReportAreaArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) planningReportAreaArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setPlanningReportAreaList(list);
	}

	
	public Collection getPlanningReportAreaList() {
		return this.planningReportAreaList;
	}
	public Array getPlanningReportAreaArray() {
		return planningReportAreaArray;
	}

	

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCompanyId(stream.readString());
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
			this.setPlanningReportAreaArray(stream.readArray());
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
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getFacilityName());
			stream.writeArray(this.getPlanningReportAreaArray());
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