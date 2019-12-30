package com.tcmis.client.catalog.beans;

import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityIgViewOvBean <br>
 * @version: 1.0, May 12, 2005 <br>
 *****************************************************************************/

public class InventoryGroupNameOvBean
 extends BaseDataBean
 implements SQLData {

 private String inventoryGroup;
 private String inventoryGroupName;
 private String collection;

 private String sqlType = "CUSTOMER.INVENTORY_GROUP_NAME";

 //constructor
 public InventoryGroupNameOvBean() {
 }

 //setters
 public void setInventoryGroup(String inventoryGroup) {
	this.inventoryGroup = inventoryGroup;
 }

 public void setInventoryGroupName(String inventoryGroupName) {
	this.inventoryGroupName = inventoryGroupName;
 }

 public void setCollection(String collection) {
	this.collection = collection;
 }

 //getters
 public String getInventoryGroup() {
	return inventoryGroup;
 }

 public String getInventoryGroupName() {
	return inventoryGroupName;
 }

 public String getCollection() {
	return collection;
 }

 public String getSQLTypeName() {
	return this.sqlType;
 }

 public void readSQL(SQLInput stream, String type) throws SQLException {
	sqlType = type;
	try {
	 this.setInventoryGroup(stream.readString());
	 this.setInventoryGroupName(stream.readString());
	 this.setCollection(stream.readString());
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
	 stream.writeString(this.getInventoryGroup());
	 stream.writeString(this.getInventoryGroupName());
	 stream.writeString(this.getCollection());
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
