package com.tcmis.supplier.shipping.beans;

import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LocationObjBean <br>
 * @version: 1.0, Oct 24, 2007 <br>
 *****************************************************************************/


public class LocationObjBean extends BaseDataBean implements SQLData {

	private String locationId;
	private String locationShortName;
	private String sqlType = "CUSTOMER.LOCATION_OBJ";


	//constructor
	public LocationObjBean() {
	}

	//setters
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}


	//getters
	public String getLocationId() {
		return locationId;
	}
	public String getLocationShortName() {
		return locationShortName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setLocationId(stream.readString());
			this.setLocationShortName(stream.readString());
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
			stream.writeString(this.getLocationId());
			stream.writeString(this.getLocationShortName());
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