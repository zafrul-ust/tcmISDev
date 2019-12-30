package com.tcmis.internal.hub.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PalletObjBean <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class PalletObjBean extends BaseDataBean implements SQLData {

	private String palletId;
	private String sqlType = "PALLET_OBJ";


	//constructor
	public PalletObjBean() {
	}

	//setters
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}

	//getters
	public String getPalletId() {
		return palletId;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPalletId(stream.readString());
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
			stream.writeString(this.getPalletId());
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