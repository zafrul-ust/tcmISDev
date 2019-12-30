package com.tcmis.client.report.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: ApplicationObjBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class ApplicationObjBean extends BaseDataBean implements SQLData {

	private String application;
	private String applicationDesc;
	private String sqlType = "APPLICATION_OBJ";


	//constructor
	public ApplicationObjBean() {
	}

	//setters
	public void setApplication(String application) {
		this.application = application;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}


	//getters
	public String getApplication() {
		return application;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setApplication(stream.readString());
			this.setApplicationDesc(stream.readString());
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
			stream.writeString(this.getApplication());
			stream.writeString(this.getApplicationDesc());
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