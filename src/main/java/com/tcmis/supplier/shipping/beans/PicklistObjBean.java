package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;
import java.text.SimpleDateFormat;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PicklistObjBean <br>
 * @version: 1.0, Oct 25, 2007 <br>
 *****************************************************************************/


public class PicklistObjBean extends BaseDataBean implements SQLData {

	private BigDecimal picklistId;
	private Date picklistPrintDate;
	private String sqlType = "CUSTOMER.PICKLIST_OBJ";

	//constructor
	public PicklistObjBean() {
	}

	//setters
	public void setPicklistId(BigDecimal picklistId) {
		this.picklistId = picklistId;
	}
	public void setPicklistPrintDate(Date picklistPrintDate) {		
		this.picklistPrintDate = picklistPrintDate;
	}

	//getters
	public BigDecimal getPicklistId() {
		return picklistId;
	}
	public Date getPicklistPrintDate() {
		return picklistPrintDate;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPicklistId(stream.readBigDecimal());
			this.setPicklistPrintDate(stream.readTimestamp());
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
			stream.writeBigDecimal(this.getPicklistId());
			stream.writeDate(new java.sql.Date(this.getPicklistPrintDate().getTime()));
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