package com.tcmis.client.openCustomer.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: OpenUserShiptoSelOvBean <br>
 * @version: 1.0, Feb 7, 2011 <br>
 *****************************************************************************/


public class OpenUserShiptoSelOvBean extends BaseDataBean implements SQLData {
	Log log = LogFactory.getLog(this.getClass());
	private BigDecimal userId;
	private BigDecimal personnelId;
	private BigDecimal customerId;
	private String customerName;
	private String userAccess;
	private Collection<OpenUserFacilityObjBean> facilityList;
	private Array facilityListArray;
	private String sqlType = "OPEN_USER_SHIPTO_SEL_OV";


	//constructor
	public OpenUserShiptoSelOvBean() {
	}

	//setters
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}
	public void setFacilityList(Collection coll) {
		this.facilityList = coll;
	}
	public void setFacilityListArray(Array facilityListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) facilityListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setFacilityList(list);
	}


	//getters
	public BigDecimal getUserId() {
		return userId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getUserAccess() {
		return userAccess;
	}
	public Collection getFacilityList() {
		return this.facilityList;
	}
	public Array getFacilityListArray() {
		return facilityListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { 
			this.setUserId(stream.readBigDecimal());
			this.setPersonnelId(stream.readBigDecimal());
			this.setCustomerId(stream.readBigDecimal());
			this.setCustomerName(stream.readString());
			this.setUserAccess(stream.readString());
			this.setFacilityListArray(stream.readArray());
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
			stream.writeBigDecimal(this.getUserId());
			stream.writeBigDecimal(this.getPersonnelId());
			stream.writeBigDecimal(this.getCustomerId());
			stream.writeString(this.getCustomerName());
			stream.writeString(this.getUserAccess());
			stream.writeArray(this.getFacilityListArray());
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