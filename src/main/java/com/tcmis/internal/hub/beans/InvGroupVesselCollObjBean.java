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
 * CLASSNAME: InvGroupVesselCollObjBean <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class InvGroupVesselCollObjBean extends BaseDataBean implements SQLData {

	private String inventoryGroup;
	private String inventoryGroupName;
	private String collection;
	private String issueGeneration;
	private String skipReceivingQc;
	private String hazardLabelGroup;
	private String orderQtyUpdateOnReceipt;
	private Collection vessels;
	private Array vesselsArray;
	private String sqlType = "INV_GROUP_VESSEL_COLL_OBJ";


	//constructor
	public InvGroupVesselCollObjBean() {
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
	public void setIssueGeneration(String issueGeneration) {
		this.issueGeneration = issueGeneration;
	}
	public void setSkipReceivingQc(String skipReceivingQc) {
		this.skipReceivingQc = skipReceivingQc;
	}
	public void setHazardLabelGroup(String hazardLabelGroup) {
		this.hazardLabelGroup = hazardLabelGroup;
	}
	public void setOrderQtyUpdateOnReceipt(String orderQtyUpdateOnReceipt) {
		this.orderQtyUpdateOnReceipt = orderQtyUpdateOnReceipt;
	}
	public void setVessels(Collection coll) {
		this.vessels = coll;
	}
	public void setVesselsArray(Array vesselsArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) vesselsArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setVessels(list);
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
	public String getIssueGeneration() {
		return issueGeneration;
	}
	public String getSkipReceivingQc() {
		return skipReceivingQc;
	}
	public String getHazardLabelGroup() {
		return hazardLabelGroup;
	}
	public String getOrderQtyUpdateOnReceipt() {
		return orderQtyUpdateOnReceipt;
	}
	public Collection getVessels() {
		return this.vessels;
	}
	public Array getVesselsArray() {
		return vesselsArray;
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
			this.setIssueGeneration(stream.readString());
			this.setSkipReceivingQc(stream.readString());
			this.setHazardLabelGroup(stream.readString());
			this.setOrderQtyUpdateOnReceipt(stream.readString());
			this.setVesselsArray(stream.readArray());
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
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getInventoryGroupName());
			stream.writeString(this.getCollection());
			stream.writeString(this.getIssueGeneration());
			stream.writeString(this.getSkipReceivingQc());
			stream.writeString(this.getHazardLabelGroup());
			stream.writeString(this.getOrderQtyUpdateOnReceipt());
			stream.writeArray(this.getVesselsArray());
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