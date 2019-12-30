package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LocationObjBean <br>
 * @version: 1.0, Oct 24, 2007 <br>
 *****************************************************************************/


public class AreaObjBean extends BaseDataBean implements SQLData {

	private int areaId;
	private String areaName;
	private String areaDescription;
	private String facilityId;
	private String sqlType = "CUSTOMER.AREA_OBJ";


	//constructor
	public AreaObjBean() {
	}
	
	

	public int getAreaId() {
		return areaId;
	}



	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}



	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaDescription() {
		return areaDescription;
	}

	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setAreaId(stream.readInt());
			this.setAreaName(stream.readString());
			this.setAreaDescription(stream.readString());
			this.setFacilityId(stream.readString());
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
			stream.writeInt(this.getAreaId());
			stream.writeString(this.getAreaName());
			stream.writeString(this.getAreaDescription());
			stream.writeString(this.getFacilityId());
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

