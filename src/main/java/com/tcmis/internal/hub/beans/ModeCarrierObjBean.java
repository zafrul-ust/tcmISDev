package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ModeCarrierObjBean <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class ModeCarrierObjBean extends BaseDataBean implements SQLData {

	private String transportationMode;
	private Collection carrier;
	private Array carrierArray;
	private String sqlType = "MODE_CARRIER_OBJ";


	//constructor
	public ModeCarrierObjBean() {
	}

	//setters
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setCarrier(Collection coll) {
		this.carrier = coll;
	}
	public void setCarrierArray(Array carrierArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) carrierArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCarrier(list);
	}


	//getters
	public String getTransportationMode() {
		return transportationMode;
	}
	public Collection getCarrier() {
		return this.carrier;
	}
	public Array getCarrierArray() {
		return carrierArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setTransportationMode(stream.readString());
			this.setCarrierArray(stream.readArray());
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
			stream.writeString(this.getTransportationMode());
			stream.writeArray(this.getCarrierArray());
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