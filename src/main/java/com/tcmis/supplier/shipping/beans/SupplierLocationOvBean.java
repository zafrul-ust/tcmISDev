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
 * CLASSNAME: SupplierLocationOvBean <br>
 * @version: 1.0, Oct 24, 2007 <br>
 *****************************************************************************/


public class SupplierLocationOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private BigDecimal personnelId;
	private String supplier;
	private String supplierName;
	private String containerLabel;
	private Collection supplierLocations;
	private Array supplierLocationsArray;
	private String sqlType = "CUSTOMER.SUPPLIER_LOCATION_OBJ";


	//constructor
	public SupplierLocationOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public void setContainerLabel(String containerLabel) {
		this.containerLabel = containerLabel;
	}
	public void setSupplierLocations(Collection coll) {
		this.supplierLocations = coll;
	}
	public void setSupplierLocationsArray(Array supplierLocationsArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) supplierLocationsArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setSupplierLocations(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getSupplier() {
		return supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public String getContainerLabel() {
		return containerLabel;
	}
	public Collection getSupplierLocations() {
		return this.supplierLocations;
	}
	public Array getSupplierLocationsArray() {
		return supplierLocationsArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCompanyId(stream.readString());
			this.setPersonnelId(stream.readBigDecimal());
			this.setSupplier(stream.readString());
			this.setSupplierName(stream.readString());
			this.setContainerLabel(stream.readString());
			this.setSupplierLocationsArray(stream.readArray());
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
			stream.writeString(this.getSupplier());
			stream.writeString(this.getSupplierName());
			stream.writeString(this.getContainerLabel());
			stream.writeArray(this.getSupplierLocationsArray());
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