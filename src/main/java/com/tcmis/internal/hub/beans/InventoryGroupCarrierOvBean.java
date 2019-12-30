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
 * CLASSNAME: InventoryGroupCarrierOvBean <br>
 * @version: 1.0, Jan 25, 2008 <br>
 *****************************************************************************/


public class InventoryGroupCarrierOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private BigDecimal personnelId;
	private String inventoryGroup;
	private Collection modeCarrier;
	private Array modeCarrierArray;
	private String sqlType = "INVENTORY_GROUP_CARRIER_OV";


	//constructor
	public InventoryGroupCarrierOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setModeCarrier(Collection coll) {
		this.modeCarrier = coll;
	}
	public void setModeCarrierArray(Array modeCarrierArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) modeCarrierArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setModeCarrier(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public BigDecimal getPersonnelId() {
		return personnelId;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public Collection getModeCarrier() {
		return this.modeCarrier;
	}
	public Array getModeCarrierArray() {
		return modeCarrierArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setCompanyId(stream.readString());
			this.setPersonnelId(stream.readBigDecimal());
			this.setInventoryGroup(stream.readString());
			this.setModeCarrierArray(stream.readArray());
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
			stream.writeString(this.getInventoryGroup());
			stream.writeArray(this.getModeCarrierArray());
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