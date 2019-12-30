package com.tcmis.client.openCustomer.beans;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OpenUserCustomerOvBean <br>
 * @version: 1.0, Jul 27, 2010 <br>
 *****************************************************************************/

public class OpenUserFacilityOvBean extends BaseDataBean implements SQLData {

	private BigDecimal customerId;
	private String facilityId;
	private String facilityName;
	private String sqlType = "OPEN_USER_FACILITY_OBJ";

	//constructor
	public OpenUserFacilityOvBean() {
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public String getSqlType() {
		return sqlType;
	}

	public String getSQLTypeName() throws SQLException {
		return this.sqlType;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		sqlType = typeName;
		try {
			setCustomerId(stream.readBigDecimal());
			setFacilityId(stream.readString());
			setFacilityName(stream.readString());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".readSQL method failed").initCause(e);
		}
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		// Meh, one way only.
	}
}