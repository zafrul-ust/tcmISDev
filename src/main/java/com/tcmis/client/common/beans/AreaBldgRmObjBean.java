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
 * CLASSNAME: AreaBldgRmObjBean <br>
 * @version: 1.0, Mar 10, 2011 <br>
 *****************************************************************************/


public class AreaBldgRmObjBean extends BaseDataBean implements SQLData {

	private BigDecimal areaId;
	private String areaName;
	private String areaDescription;
	private Collection<BldgRmObjBean> buildingList;
	private Array buildingListArray;
	private String sqlType = "AREA_BLDG_RM_OBJ";


	//constructor
	public AreaBldgRmObjBean() {
	}

	//setters
	public void setAreaId(BigDecimal areaId) {
		this.areaId = areaId;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	public void setBuildingList(Collection coll) {
		this.buildingList = coll;
	}
	public void setBuildingListArray(Array buildingListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) buildingListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setBuildingList(list);
	}


	//getters
	public BigDecimal getAreaId() {
		return areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public Collection getBuildingList() {
		return this.buildingList;
	}
	public Array getBuildingListArray() {
		return buildingListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setAreaId(stream.readBigDecimal());
			this.setAreaName(stream.readString());
			this.setAreaDescription(stream.readString());
			this.setBuildingListArray(stream.readArray());
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
			stream.writeBigDecimal(this.getAreaId());
			stream.writeString(this.getAreaName());
			stream.writeString(this.getAreaDescription());
			stream.writeArray(this.getBuildingListArray());
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