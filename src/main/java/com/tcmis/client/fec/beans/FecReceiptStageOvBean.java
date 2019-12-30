package com.tcmis.client.fec.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: FecReceiptStageOvBean <br>
 * @version: 1.0, Mar 24, 2005 <br>
 *****************************************************************************/


public class FecReceiptStageOvBean extends BaseDataBean implements SQLData {

	private String poNumber;
	private String poLine;
	private BigDecimal receivedQuantity;
	private String partNumber;
	private Date receiptDate;
	private Date loadDate;
	private String filename;
	private BigDecimal line;
	private String sqlType = "FEC_RECEIPT_STAGE_OBJ";


	//constructor
	public FecReceiptStageOvBean() {
	}

	//setters
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setPoLine(String poLine) {
		this.poLine = poLine;
	}
	public void setReceivedQuantity(BigDecimal receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public void setLoadDate(Date loadDate) {
		this.loadDate = loadDate;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public void setLine(BigDecimal line) {
		this.line = line;
	}


	//getters
	public String getPoNumber() {
		return poNumber;
	}
	public String getPoLine() {
		return poLine;
	}
	public BigDecimal getReceivedQuantity() {
		return receivedQuantity;
	}
	public String getPartNumber() {
		return partNumber;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public Date getLoadDate() {
		return loadDate;
	}
	public String getFilename() {
		return filename;
	}
	public BigDecimal getLine() {
		return line;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPoNumber(stream.readString());
			this.setPoLine(stream.readString());
			this.setReceivedQuantity(stream.readBigDecimal());
			this.setPartNumber(stream.readString());
			this.setReceiptDate(new Date(stream.readDate().getTime()));
			this.setLoadDate(new Date(stream.readDate().getTime()));
			this.setFilename(stream.readString());
			this.setLine(stream.readBigDecimal());
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
			stream.writeString(this.getPoNumber());
			stream.writeString(this.getPoLine());
			stream.writeBigDecimal(this.getReceivedQuantity());
			stream.writeString(this.getPartNumber());
			stream.writeDate(new java.sql.Date(this.getReceiptDate().getTime()));
			stream.writeDate(new java.sql.Date(this.getLoadDate().getTime()));
			stream.writeString(this.getFilename());
			stream.writeBigDecimal(this.getLine());
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