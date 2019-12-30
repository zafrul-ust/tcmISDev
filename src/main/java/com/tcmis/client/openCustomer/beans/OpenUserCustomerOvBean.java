package com.tcmis.client.openCustomer.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: OpenUserCustomerOvBean <br>
 * @version: 1.0, Jul 27, 2010 <br>
 *****************************************************************************/

public class OpenUserCustomerOvBean extends BaseDataBean implements SQLData {

	private BigDecimal personnelId;
	private BigDecimal customerId;
	private String customerName;
	private String sqlType = "OPEN_USER_CUSTOMER_OBJ";
	@SuppressWarnings("unchecked")
	private Collection userFacilityCollection;

	//constructor
	public OpenUserCustomerOvBean() {
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	//getters
	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public String getSQLTypeName() throws SQLException {
		return this.sqlType;
	}

	public Collection getUserFacilityCollection() {
		return userFacilityCollection;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		sqlType = typeName;
		try {
			setPersonnelId(stream.readBigDecimal());
			setCustomerId(stream.readBigDecimal());
			setCustomerName(stream.readString());
			setUserFacilityArray(stream.readArray());
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

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	//setters
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setUserFacilityArray(Array userFacilityArray) {
		try {
			setUserFacilityCollection(Arrays.asList((Object[]) userFacilityArray.getArray()));
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING UserFacilityArray:" + sqle.getMessage());
		}
	}

	public void setUserFacilityCollection(Collection userFacilityCollection) {
		this.userFacilityCollection = userFacilityCollection;
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		// Meh, one way only.
	}
}