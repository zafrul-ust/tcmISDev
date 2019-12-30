package com.tcmis.client.catalog.beans;

import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacAppUserGrpOvBean <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class FacAppUserGrpOvBean
 extends BaseDataBean
 implements SQLData {

 private String facilityId;
 private String facilityName;
 private Collection applicationVar;
 private Array applicationVarArray;
 private Collection userGroupVar;
 private Array userGroupVarArray;
 private Collection facDockVar;
 private Array facDockVarArray;
 private String sqlType = "FAC_APP_USER_GRP_OBJ";

 //constructor
 public FacAppUserGrpOvBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
 }

 public void setApplicationVar(Collection coll) {
	this.applicationVar = coll;
 }

 public void setApplicationVarArray(Array applicationVarArray) {
	List list = null;
	try {
	 list = Arrays.asList( (Object[]) applicationVarArray.getArray());
	}
	catch (SQLException sqle) {
	 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
	}
	this.setApplicationVar(list);
 }

 public void setUserGroupVar(Collection coll) {
	this.userGroupVar = coll;
 }

 public void setUserGroupVarArray(Array userGroupVarArray) {
	List list = null;
	try {
	 list = Arrays.asList( (Object[]) userGroupVarArray.getArray());
	}
	catch (SQLException sqle) {
	 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
	}
	this.setUserGroupVar(list);
 }

 public void setFacDockVar(Collection coll) {
	this.facDockVar = coll;
 }

 public void setFacDockVarArray(Array facDockVarArray) {
	List list = null;
	try {
	 list = Arrays.asList( (Object[]) facDockVarArray.getArray());
	}
	catch (SQLException sqle) {
	 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
	}
	this.setFacDockVar(list);
 }

 //getters
 public String getFacilityId() {
	return facilityId;
 }

 public String getFacilityName() {
	return facilityName;
 }

 public Collection getApplicationVar() {
	return this.applicationVar;
 }

 public Array getApplicationVarArray() {
	return applicationVarArray;
 }

 public Collection getUserGroupVar() {
	return this.userGroupVar;
 }

 public Array getUserGroupVarArray() {
	return userGroupVarArray;
 }

 public Collection getFacDockVar() {
	return this.facDockVar;
 }

 public Array getFacDockVarArray() {
	return facDockVarArray;
 }

 public String getSQLTypeName() {
	return this.sqlType;
 }

 public void readSQL(SQLInput stream, String type) throws SQLException {
	sqlType = type;
	try {
	 this.setFacilityId(stream.readString());
	 this.setFacilityName(stream.readString());
	 this.setApplicationVarArray(stream.readArray());
	 this.setUserGroupVarArray(stream.readArray());
	 this.setFacDockVarArray(stream.readArray());
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
	 stream.writeString(this.getFacilityId());
	 stream.writeString(this.getFacilityName());
	 stream.writeArray(this.getApplicationVarArray());
	 stream.writeArray(this.getUserGroupVarArray());
	 stream.writeArray(this.getFacDockVarArray());
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