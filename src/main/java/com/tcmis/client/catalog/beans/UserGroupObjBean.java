package com.tcmis.client.catalog.beans;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityObjBean <br>
 * @version: 1.0, Feb 07, 2006 <br>
 * This is the facility collection for company_fac_invoice_date_ov
 *****************************************************************************/

public class UserGroupObjBean
 extends BaseDataBean
 implements SQLData {

 private String userGroupId;
 private String userGroupDesc;
 private String sqlType = "USER_GROUP_OBJ";

 //constructor
 public UserGroupObjBean() {
 }

 //setters
 public void setUserGroupId(String userGroupId) {
	this.userGroupId = userGroupId;
 }

 public void setUserGroupDesc(String userGroupDesc) {
	this.userGroupDesc = userGroupDesc;
 }

 //getters
 public String getUserGroupId() {
	return this.userGroupId;
 }

 public String getUserGroupDesc() {
	return this.userGroupDesc;
 }

 public String getSQLTypeName() {
	return this.sqlType;
 }

 public void readSQL(SQLInput stream, String type) throws SQLException {
	sqlType = type;
	try {
	 this.setUserGroupId(stream.readString());
	 this.setUserGroupDesc(stream.readString());
	}
	catch (SQLException e) {
	 throw (SQLException) e;
	}
	catch (Exception e) {
	 new IllegalStateException(getClass().getName() +
		".readSQL method failed").initCause(e);
	}
 }

 public void writeSQL(SQLOutput stream) throws SQLException {
	try {
	 stream.writeString(this.getUserGroupId());
	 stream.writeString(this.getUserGroupDesc());
	}
	catch (SQLException e) {
	 throw (SQLException) e;
	}
	catch (Exception e) {
	 new IllegalStateException(getClass().getName() +
		".writeSQL method failed").initCause(e);
	}
 }
}
