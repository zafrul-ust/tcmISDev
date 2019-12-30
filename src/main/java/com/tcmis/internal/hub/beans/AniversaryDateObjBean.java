package com.tcmis.internal.hub.beans;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FacilityInvoicePeriodViewBean <br>
 * @version: 1.0, Feb 7, 2006 <br>
 *****************************************************************************/

public class AniversaryDateObjBean extends BaseDataBean implements SQLData {

	private Date aniversaryDate;
	private String sqlType = "ANIVERSARY_DATE_OBJ";

	//constructor
	public AniversaryDateObjBean() {
	}

	//setters
	public void setAniversaryDate(Date aniversaryDate) {
		this.aniversaryDate = aniversaryDate;
	}

	//getters
	public Date getAniversaryDate() {
		return aniversaryDate;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
		  this.setAniversaryDate(new Date(stream.readDate().getTime()));
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
		  stream.writeDate(new java.sql.Date(this.getAniversaryDate().getTime()));
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