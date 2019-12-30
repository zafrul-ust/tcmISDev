package com.tcmis.client.common.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: VvSecLblDataTypCmmntOvBean <br>
 * @version: 1.0, Feb 16, 2011 <br>
 *****************************************************************************/


public class VvSecLblDataTypCmmntOvBean extends BaseDataBean implements SQLData {
	Log log = LogFactory.getLog(this.getClass());
	private String companyId;
	private String facilityId;
	private BigDecimal typeId;
	private String typeName;
	private Collection<VvSecLblDatTypCommentObjBean> commentList;
	private Array commentListArray;
	private String sqlType = "VV_SEC_LBL_DATA_TYP_CMMNT_OV";


	//constructor
	public VvSecLblDataTypCmmntOvBean() {
	}

	//setters
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setTypeId(BigDecimal typeId) {
		this.typeId = typeId;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setCommentList(Collection coll) {
		this.commentList = coll;
	}
	public void setCommentListArray(Array commentListArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) commentListArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCommentList(list);
	}


	//getters
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getTypeId() {
		return typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public Collection getCommentList() {
		return this.commentList;
	}
	public Array getCommentListArray() {
		return commentListArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { 
			this.setCompanyId(stream.readString());
			this.setFacilityId(stream.readString());
			this.setTypeId(stream.readBigDecimal());
			this.setTypeName(stream.readString());
			this.setCommentListArray(stream.readArray());
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
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getTypeName());
			stream.writeBigDecimal(this.getTypeId());
			stream.writeArray(this.getCommentListArray());
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