package com.tcmis.client.catalog.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.tcmis.common.framework.BaseDataBean;

public class UserFacCatAppOvBean extends BaseDataBean
implements SQLData {

	 private String facilityId;
	 private String facilityName;
	 private String companyId;
	 private BigDecimal personnelId;
	 private String userName;
	 private String adminRole;
	 private Collection catalogList;
	 private Array catalogListArray;
	 private Collection applicationList;
	 private Array applicationListArray;
	 private String sqlType = "USER_FAC_CAT_APP_OBJ";

	 public UserFacCatAppOvBean() {
	 }

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAdminRole() {
		return adminRole;
	}

	public void setAdminRole(String adminRole) {
		this.adminRole = adminRole;
	}

	public Collection getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(Collection catalogList) {
		this.catalogList = catalogList;
	}

	public Array getCatalogListArray() {
		return catalogListArray;
	}

	public void setCatalogListArray(Array catalogListArray) {
		List list = null;
		try {
		 list = Arrays.asList( (Object[]) catalogListArray.getArray());
		}
		catch (SQLException sqle) {
		 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCatalogList(list);
	 }

	public Collection getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(Collection applicationList) {
		this.applicationList = applicationList;
	}

	public Array getApplicationListArray() {
		return applicationListArray;
	}

	public void setApplicationListArray(Array applicationListArray) {
		List list = null;
		try {
		 list = Arrays.asList( (Object[]) applicationListArray.getArray());
		}
		catch (SQLException sqle) {
		 System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setApplicationList(list);
	 }
	
	public String getSQLTypeName() {
		return this.sqlType;
	 }

	 public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
		 this.setCompanyId(stream.readString());
		 this.setPersonnelId(stream.readBigDecimal());
		 this.setUserName(stream.readString());
		 this.setFacilityId(stream.readString());
		 this.setAdminRole(stream.readString());
		 this.setFacilityName(stream.readString());
		 this.setCatalogListArray(stream.readArray());
		 this.setApplicationListArray(stream.readArray());
		 
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
		stream.writeString(this.getCompanyId());
	    stream.writeBigDecimal(this.getPersonnelId());
	    stream.writeString(this.getCompanyId());
		 stream.writeString(this.getFacilityId());
		 stream.writeString(this.getAdminRole());
		 stream.writeString(this.getFacilityName());
		 stream.writeArray(this.getCatalogListArray());
		 stream.writeArray(this.getApplicationListArray());
		 
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
