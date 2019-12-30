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
 * CLASSNAME: HubInventoryGroupVesselOvBean <br>
 * @version: 1.0, Sep 18, 2007 <br>
 *****************************************************************************/


public class HubInventoryGroupVesselOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private BigDecimal personnelId;
	private String facilityId;
	private BigDecimal priority;
	private String homeCompanyId;
	private String homeCurrencyId;
	private String branchPlant;
	private String hubName;
	private Collection inventoryGroupCollection;
	private Array inventoryGroupCollectionArray;
	private String sqlType = "HUB_INVENTORY_GROUP_VESSEL_OBJ";


	//constructor
	public HubInventoryGroupVesselOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setPriority(BigDecimal priority) {
		this.priority = priority;
	}
	public void setHomeCompanyId(String homeCompanyId) {
		this.homeCompanyId = homeCompanyId;
	}
	public void setHomeCurrencyId(String homeCurrencyId) {
		this.homeCurrencyId = homeCurrencyId;
	}
	public void setBranchPlant(String branchPlant) {
		this.branchPlant = branchPlant;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroupCollection(Collection coll) {
		this.inventoryGroupCollection = coll;
	}
	public void setInventoryGroupCollectionArray(Array inventoryGroupCollectionArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) inventoryGroupCollectionArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setInventoryGroupCollection(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getPriority() {
		return priority;
	}
	public String getHomeCompanyId() {
		return homeCompanyId;
	}
	public String getHomeCurrencyId() {
		return homeCurrencyId;
	}
	public String getBranchPlant() {
		return branchPlant;
	}
	public String getHubName() {
		return hubName;
	}
	public Collection getInventoryGroupCollection() {
		return this.inventoryGroupCollection;
	}
	public Array getInventoryGroupCollectionArray() {
		return inventoryGroupCollectionArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCompanyId(stream.readString());
			this.setPersonnelId(stream.readBigDecimal());
			this.setFacilityId(stream.readString());
			this.setPriority(stream.readBigDecimal());
			this.setHomeCompanyId(stream.readString());
			this.setHomeCurrencyId(stream.readString());
			this.setBranchPlant(stream.readString());
			this.setHubName(stream.readString());
			this.setInventoryGroupCollectionArray(stream.readArray());
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
			stream.writeString(this.getCompanyId());
			stream.writeBigDecimal(this.getPersonnelId());
			stream.writeString(this.getFacilityId());
			stream.writeBigDecimal(this.getPriority());
			stream.writeString(this.getHomeCompanyId());
			stream.writeString(this.getHomeCurrencyId());
			stream.writeString(this.getBranchPlant());
			stream.writeString(this.getHubName());
			stream.writeArray(this.getInventoryGroupCollectionArray());
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