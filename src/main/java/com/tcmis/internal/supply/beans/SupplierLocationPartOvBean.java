package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SupplierLocationPartOvBean <br>
 * @version: 1.0, Oct 7, 2008 <br>
 *****************************************************************************/


public class SupplierLocationPartOvBean extends BaseDataBean implements SQLData {

	private String supplier;
	private String shipFromLocationId;
	private String supplierRegionCode;
	private String supplierLocationType;
	private String supplierLocationCode;
	private Collection supplierPartList;
	private Array supplierPartListArray;
	private String sqlType = "SUPPLIER_LOCATION_PART_OV";


	//constructor
	public SupplierLocationPartOvBean() {
	}

	//setters
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setShipFromLocationId(String shipFromLocationId) {
		this.shipFromLocationId = shipFromLocationId;
	}
	public void setSupplierRegionCode(String supplierRegionCode) {
		this.supplierRegionCode = supplierRegionCode;
	}
	public void setSupplierLocationType(String supplierLocationType) {
		this.supplierLocationType = supplierLocationType;
	}
	public void setSupplierLocationCode(String supplierLocationCode) {
		this.supplierLocationCode = supplierLocationCode;
	}
	public void setSupplierPartList(Collection coll) {
		this.supplierPartList = coll;
	}
	public void setSupplierPartListArray(Array supplierPartListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) supplierPartListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setSupplierPartList(list);
	}


	//getters
	public String getSupplier() {
		return supplier;
	}
	public String getShipFromLocationId() {
		return shipFromLocationId;
	}
	public String getSupplierRegionCode() {
		return supplierRegionCode;
	}
	public String getSupplierLocationType() {
		return supplierLocationType;
	}
	public String getSupplierLocationCode() {
		return supplierLocationCode;
	}
	public Collection getSupplierPartList() {
		return this.supplierPartList;
	}
	public Array getSupplierPartListArray() {
		return supplierPartListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setSupplier(stream.readString());
			this.setShipFromLocationId(stream.readString());
			this.setSupplierRegionCode(stream.readString());
			this.setSupplierLocationType(stream.readString());
			this.setSupplierLocationCode(stream.readString());
			this.setSupplierPartListArray(stream.readArray());
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
			stream.writeString(this.getSupplier());
			stream.writeString(this.getShipFromLocationId());
			stream.writeString(this.getSupplierRegionCode());
			stream.writeString(this.getSupplierLocationType());
			stream.writeString(this.getSupplierLocationCode());
			stream.writeArray(this.getSupplierPartListArray());
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