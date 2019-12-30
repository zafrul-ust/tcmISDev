package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PriceGroupCatalogOvBean <br>
 * @version: 1.0, Oct 30, 2009 <br>
 *****************************************************************************/


public class PriceGroupCatalogOvBean extends BaseDataBean implements SQLData {

	private String priceGroupId;
	private String opsEntityId;
	private String opsCompanyId;
	private String catalogCompanyId;
	private String catalogCompanyName;
	private Collection<CatalogObjBean> catalogCollection;
	private Array catalogCollectionArray;
	private String sqlType = "PRICE_GROUP_CATALOG_OV";


	//constructor
	public PriceGroupCatalogOvBean() {
	}

	//setters
	public void setPriceGroupId(String priceGroupId) {
		this.priceGroupId = priceGroupId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setCatalogCompanyId(String catalogCompanyId) {
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogCompanyName(String catalogCompanyName) {
		this.catalogCompanyName = catalogCompanyName;
	}
	public void setCatalogCollection(Collection coll) {
		this.catalogCollection = coll;
	}
	public void setCatalogCollectionArray(Array catalogCollectionArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) catalogCollectionArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCatalogCollection(list);
	}


	//getters
	public String getPriceGroupId() {
		return priceGroupId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getCatalogCompanyId() {
		return catalogCompanyId;
	}
	public String getCatalogCompanyName() {
		return catalogCompanyName;
	}
	public Collection getCatalogCollection() {
		return this.catalogCollection;
	}
	public Array getCatalogCollectionArray() {
		return catalogCollectionArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setPriceGroupId(stream.readString());
			this.setOpsEntityId(stream.readString());
			this.setOpsCompanyId(stream.readString());
			this.setCatalogCompanyId(stream.readString());
			this.setCatalogCompanyName(stream.readString());
			this.setCatalogCollectionArray(stream.readArray());
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
			stream.writeString(this.getPriceGroupId());
			stream.writeString(this.getOpsEntityId());
			stream.writeString(this.getOpsCompanyId());
			stream.writeString(this.getCatalogCompanyId());
			stream.writeString(this.getCatalogCompanyName());
			stream.writeArray(this.getCatalogCollectionArray());
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