package com.tcmis.client.catalog.beans;

import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: FacilityIgViewOvBean <br>
 * @version: 1.0, May 12, 2005 <br>
 *****************************************************************************/

public class FacilityIgViewOvBean
 extends BaseDataBean
 implements SQLData {

 private String facilityId;
 private String facilityName;
 private String companyId;
 private BigDecimal personnelId;
 private Collection inventoryGroups;
 private Array inventoryGroupsArray;
 private String sqlType = "CUSTOMER.FACILITY_IG_VIEW_OBJ";

 //constructor
 public FacilityIgViewOvBean() {
 }

 //setters
 public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
 }

 public void setFacilityName(String facilityName) {
	this.facilityName = facilityName;
 }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setPersonnelId(BigDecimal personnelId) {
        this.personnelId = personnelId;
    }

 public void setInventoryGroups(Collection coll) {
	this.inventoryGroups = coll;
 }

 public void setInventoryGroupsArray(Array inventoryGroupsArray) {
	List list = null;
	try {
	 list = Arrays.asList( (Object[]) inventoryGroupsArray.getArray());
	}
	catch (SQLException sqle) {
	 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
	}
	this.setInventoryGroups(list);
 }

 //getters
 public String getFacilityId() {
	return facilityId;
 }

 public String getFacilityName() {
	return facilityName;
 }

    public String getCompanyId() {
        return companyId;
    }

    public BigDecimal getPersonnelId() {
        return personnelId;
    }

  public Collection getInventoryGroups() {
	return this.inventoryGroups;
 }

 public Array getInventoryGroupsArray() {
	return inventoryGroupsArray;
 }

 public String getSQLTypeName() {
	return this.sqlType;
 }

 public void readSQL(SQLInput stream, String type) throws SQLException {
	sqlType = type;
	try {
	 this.setFacilityId(stream.readString());
	 this.setFacilityName(stream.readString());
     this.setCompanyId(stream.readString());
     this.setPersonnelId(stream.readBigDecimal());
     this.setInventoryGroupsArray(stream.readArray());
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
     stream.writeString(this.getCompanyId());
     stream.writeBigDecimal(this.getPersonnelId());
     stream.writeArray(this.getInventoryGroupsArray());
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