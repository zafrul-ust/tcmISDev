package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: LocationPicklistObjBean <br>
 * @version: 1.0, Oct 25, 2007 <br>
 *****************************************************************************/


public class LocationPicklistObjBean extends BaseDataBean implements SQLData {

	private String locationId;
	private String locationShortName;
	private Collection picklists;
	private Array picklistsArray;
	private String sqlType = "CUSTOMER.LOCATION_PICKLIST_OBJ";


	//constructor
	public LocationPicklistObjBean() {
	}

	//setters
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setLocationShortName(String locationShortName) {
		this.locationShortName = locationShortName;
	}
	public void setPicklists(Collection coll) {
		this.picklists = coll;
	}
	public void setPicklistsArray(Array picklistsArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) picklistsArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setPicklists(list);
	}


	//getters
	public String getLocationId() {
		return locationId;
	}
	public String getLocationShortName() {
		return locationShortName;
	}
	public Collection getPicklists() {
		return this.picklists;
	}
	public Array getPicklistsArray() {
		return picklistsArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setLocationId(stream.readString());
			this.setLocationShortName(stream.readString());
			this.setPicklistsArray(stream.readArray());
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
			stream.writeArray(this.getPicklistsArray());
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