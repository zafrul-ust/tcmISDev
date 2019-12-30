package com.tcmis.client.openCustomer.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpenUserFacilityObjBean <br>
 * @version: 1.0, Feb 7, 2011 <br>
 *****************************************************************************/


public class OpenUserFacilityObjBean extends BaseDataBean implements SQLData {
	
	private BigDecimal customerId;
	private String facilityId;
	private String facilityName;
	private String sqlType = "OPEN_USER_FACILITY_OBJ";

	//constructor
	public OpenUserFacilityObjBean() {
	}

	//setters
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}


	//getters
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCustomerId(stream.readBigDecimal());
			this.setFacilityId(stream.readString());
			this.setFacilityName(stream.readString());
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
			stream.writeBigDecimal(this.getCustomerId());
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getFacilityName());
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