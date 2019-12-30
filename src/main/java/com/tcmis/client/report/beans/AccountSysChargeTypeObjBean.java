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
 * CLASSNAME: AccountSysChargeTypeObjBean <br>
 * @version: 1.0, Feb 6, 2009 <br>
 *****************************************************************************/


public class AccountSysChargeTypeObjBean extends BaseDataBean implements SQLData {

	private String chargeType;
	private String poRequired;
	private String prAccountRequired;
	private String chargeLabel11;
	private String chargeLabel22;
 	private String chargeLabel3;
	private String chargeLabel4;
	private String sqlType = "ACCOUNT_SYS_CHARGE_TYPE_OBJ";


	//constructor
	public AccountSysChargeTypeObjBean() {
	}

	//setters
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}
	public void setPrAccountRequired(String prAccountRequired) {
		this.prAccountRequired = prAccountRequired;
	}
	public void setChargeLabel1(String chargeLabel11) {
		this.chargeLabel11 = chargeLabel11;
	}
	public void setChargeLabel2(String chargeLabel22) {
		this.chargeLabel22 = chargeLabel22;
	}

	public void setChargeLabel3(String chargeLabel3) {
		this.chargeLabel3 = chargeLabel3;
	}

	public void setChargeLabel4(String chargeLabel4) {
		this.chargeLabel4 = chargeLabel4;
	}

	//getters
	public String getChargeType() {
		return chargeType;
	}
	public String getPoRequired() {
		return poRequired;
	}
	public String getPrAccountRequired() {
		return prAccountRequired;
	}
	public String getChargeLabel1() {
		return chargeLabel11;
	}
	public String getChargeLabel2() {
		return chargeLabel22;
	}

	public String getChargeLabel3() {
		return chargeLabel3;
	}

	public String getChargeLabel4() {
		return chargeLabel4;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setChargeType(stream.readString());
			this.setPoRequired(stream.readString());
			this.setPrAccountRequired(stream.readString());
			this.setChargeLabel1(stream.readString());
			this.setChargeLabel2(stream.readString());
			this.setChargeLabel3(stream.readString());
			this.setChargeLabel4(stream.readString());
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
			stream.writeString(this.getChargeType());
			stream.writeString(this.getPoRequired());
			stream.writeString(this.getPrAccountRequired());
			stream.writeString(this.getChargeLabel1());
			stream.writeString(this.getChargeLabel2());
			stream.writeString(this.getChargeLabel3());
			stream.writeString(this.getChargeLabel4());
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