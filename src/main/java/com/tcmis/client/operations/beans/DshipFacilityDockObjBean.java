package com.tcmis.client.operations.beans;

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
 * CLASSNAME: HubIgDockOvBean <br>
 * @version: 1.0, Apr 19, 2005 <br>
 *****************************************************************************/

public class DshipFacilityDockObjBean
 extends BaseDataBean
 implements SQLData {

 private String dockLocationId;
 private String dockDesc;
 private String sqlType = "FAC_DOCK_OBJ";

 //constructor
 public DshipFacilityDockObjBean() {
 }

 //setters
 public void setDockLocationId(String dockLocationId) {
	this.dockLocationId = dockLocationId;
 }

 public void setDockDesc(String dockDesc) {
	this.dockDesc = dockDesc;
 }

 //getters
 public String getDockLocationId() {
	return this.dockLocationId;
 }

 public String getDockDesc() {
	return this.dockDesc;
 }

 public String getSQLTypeName() {
	return this.sqlType;
 }

 public void readSQL(SQLInput stream, String type) throws SQLException {
	sqlType = type;
	try {
	 this.setDockLocationId(stream.readString());
	 this.setDockDesc(stream.readString());
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
	 stream.writeString(this.getDockLocationId());
	 stream.writeString(this.getDockDesc());
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
/////////////////////////////////////////
