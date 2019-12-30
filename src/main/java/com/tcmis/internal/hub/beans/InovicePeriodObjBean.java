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
 * CLASSNAME: FacilityInvoicePeriodViewBean <br>
 * @version: 1.0, Feb 7, 2006 <br>
 *****************************************************************************/


public class InovicePeriodObjBean extends BaseDataBean implements SQLData {

	private Date endDate;
	private String invoiceGroup;
	private BigDecimal invoicePeriod;
	private String sqlType = "INVOICE_PERIOD_OBJ";

	//constructor
	public InovicePeriodObjBean() {
	}

	//setters
	public void setInvoiceGroup(String invoiceGroup) {
		this.invoiceGroup = invoiceGroup;
	}
	public void setInvoicePeriod(BigDecimal invoicePeriod) {
		this.invoicePeriod = invoicePeriod;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	//getters
	public String getInvoiceGroup() {
		return invoiceGroup;
	}
	public BigDecimal getInvoicePeriod() {
		return invoicePeriod;
	}
	public Date getEndDate() {
		return endDate;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
		  this.setEndDate(new Date(stream.readDate().getTime()));
			this.setInvoiceGroup(stream.readString());
			this.setInvoicePeriod(stream.readBigDecimal());
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
		  stream.writeDate(new java.sql.Date(this.getEndDate().getTime()));
			stream.writeString(this.getInvoiceGroup());
			stream.writeBigDecimal(this.getInvoicePeriod());
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