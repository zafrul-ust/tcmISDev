package com.tcmis.internal.supply.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: DbuyContractPriceBreakObjBean <br>
 * @version: 1.0, Nov 16, 2009 <br>
 *****************************************************************************/


public class DbuyContractPriceBreakObjBean extends BaseDataBean implements SQLData {

	private BigDecimal breakQuantity;
	private BigDecimal unitPrice;
	private String comments;
	private String updatedBy;
	private Date updatedDate;

    private String sqlType = "DBUY_CONTRACT_PRICE_BREAK_OBJ";

	//constructor
	public DbuyContractPriceBreakObjBean() {
	}

	//setters
	public void setBreakQuantity(BigDecimal breakQuantity) {
		this.breakQuantity = breakQuantity;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

    //getters
	public BigDecimal getBreakQuantity() {
		return breakQuantity;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getComments() {
		return comments;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
    public Date getUpdatedDate() {
		return updatedDate;
	}
    
    public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setBreakQuantity(stream.readBigDecimal());
			this.setUnitPrice(stream.readBigDecimal());
			this.setComments(stream.readString());
			this.setUpdatedBy(stream.readString());
			dd = stream.readDate();
			if( dd != null ) this.setUpdatedDate(new Date(dd.getTime()));
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
			stream.writeBigDecimal(this.getBreakQuantity());
			stream.writeBigDecimal(this.getUnitPrice());
			stream.writeString(this.getComments());
			stream.writeString(this.getUpdatedBy());
			stream.writeDate(new java.sql.Date(this.getUpdatedDate().getTime()));
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