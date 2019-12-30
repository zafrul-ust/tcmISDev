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
 * CLASSNAME: AccountSysObjBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class AccountSysObjBean extends BaseDataBean implements SQLData {

	private String accountSysId;
	private Collection chargeTypeList;
	private Array chargeTypeListArray;
	private String sqlType = "ACCOUNT_SYS_OBJ";


	//constructor
	public AccountSysObjBean() {
	}

	//setters
	public void setAccountSysId(String accountSysId) {
		this.accountSysId = accountSysId;
	}
	public void setChargeTypeList(Collection coll) {
		this.chargeTypeList = coll;
	}
	public void setChargeTypeListArray(Array chargeTypeListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) chargeTypeListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setChargeTypeList(list);
	}


	//getters
	public String getAccountSysId() {
		return accountSysId;
	}
	public Collection getChargeTypeList() {
		return this.chargeTypeList;
	}
	public Array getChargeTypeListArray() {
		return chargeTypeListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setAccountSysId(stream.readString());
			this.setChargeTypeListArray(stream.readArray());
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
			stream.writeString(this.getAccountSysId());
			stream.writeArray(this.getChargeTypeListArray());
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