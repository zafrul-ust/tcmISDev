package com.tcmis.internal.catalog.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: HtcLocalOvBean <br>
 * @version: 1.0, Jun 1, 2010 <br>
 *****************************************************************************/


public class HtcLocalOvBean extends BaseDataBean implements SQLData {

	private BigDecimal itemId;
	private String itemDesc;
	private String harmonizedTradeCode;
	private Collection<HtcLocalObjBean> htcLocalCollection;
	private Array htcLocalCollectionArray;
	private String sqlType = "HTC_LOCAL_OV";

// from page input.
	private String ok;
	private String customsRegion;
	private String localExtension;
	private String isParent;
	private String changed;

	//constructor
	public HtcLocalOvBean() {
	}

	//setters
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setHarmonizedTradeCode(String harmonizedTradeCode) {
		this.harmonizedTradeCode = harmonizedTradeCode;
	}
	public void setHtcLocalCollection(Collection coll) {
		this.htcLocalCollection = coll;
	}
	public void setHtcLocalCollectionArray(Array htcLocalCollectionArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) htcLocalCollectionArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setHtcLocalCollection(list);
	}


	//getters
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getHarmonizedTradeCode() {
		return harmonizedTradeCode;
	}
	public Collection getHtcLocalCollection() {
		return this.htcLocalCollection;
	}
	public Array getHtcLocalCollectionArray() {
		return htcLocalCollectionArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setItemId(stream.readBigDecimal());
			this.setItemDesc(stream.readString());
			this.setHarmonizedTradeCode(stream.readString());
			this.setHtcLocalCollectionArray(stream.readArray());
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
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getItemDesc());
			stream.writeString(this.getHarmonizedTradeCode());
			stream.writeArray(this.getHtcLocalCollectionArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}
// getters and setters for page.	
	public String getOk() {
		return ok;
	}
	public void setOk(String ok) {
		this.ok = ok;
	}
	public String getCustomsRegion() {
		return customsRegion;
	}
	public void setCustomsRegion(String customsRegion) {
		this.customsRegion = customsRegion;
	}
	public String getLocalExtension() {
		return localExtension;
	}
	public void setLocalExtension(String localExtension) {
		this.localExtension = localExtension;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getChanged() {
		return changed;
	}
	public void setChanged(String changed) {
		this.changed = changed;
	}
	
}