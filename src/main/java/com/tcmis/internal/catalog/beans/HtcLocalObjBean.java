package com.tcmis.internal.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HtcLocalObjBean <br>
 * @version: 1.0, Jun 1, 2010 <br>
 *****************************************************************************/


public class HtcLocalObjBean extends BaseDataBean implements SQLData {

	private String customsRegion;
	private String localExtension;
	private String sqlType = "HTC_LOCAL_OBJ";


	//constructor
	public HtcLocalObjBean() {
	}

	//setters
	public void setCustomsRegion(String customsRegion) {
		this.customsRegion = customsRegion;
	}
	public void setLocalExtension(String localExtension) {
		this.localExtension = localExtension;
	}


	//getters
	public String getCustomsRegion() {
		return customsRegion;
	}
	public String getLocalExtension() {
		return localExtension;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setCustomsRegion(stream.readString());
			this.setLocalExtension(stream.readString());
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
			stream.writeString(this.getCustomsRegion());
			stream.writeString(this.getLocalExtension());
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