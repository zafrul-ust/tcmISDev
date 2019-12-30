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
 * CLASSNAME: BatchTestResultViewBean <br>
 * @version: 1.0, Oct 1, 2007 <br>
 *****************************************************************************/


public class BatchTestResultViewBean extends BaseDataBean implements SQLData {

	private BigDecimal batchId;
	private BigDecimal receiptId;
	private BigDecimal testId;
	private Date testDate;
	private BigDecimal testUserId;
	private String testUsername;
	private String testParameter;
	private String testResult;
	private String testResultUnit;
	private String modified = null;
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getModified() {
		return modified;
	}
	private String sqlType = "null";


	//constructor
	public BatchTestResultViewBean() {
	}

	//setters
	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setTestId(BigDecimal testId) {
		this.testId = testId;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	public void setTestUserId(BigDecimal testUserId) {
		this.testUserId = testUserId;
	}
	public void setTestUsername(String testUsername) {
		this.testUsername = testUsername;
	}
	public void setTestParameter(String testParameter) {
		this.testParameter = testParameter;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public void setTestResultUnit(String testResultUnit) {
		this.testResultUnit = testResultUnit;
	}


	//getters
	public BigDecimal getBatchId() {
		return batchId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getTestId() {
		return testId;
	}
	public Date getTestDate() {
		return testDate;
	}
	public BigDecimal getTestUserId() {
		return testUserId;
	}
	public String getTestUsername() {
		return testUsername;
	}
	public String getTestParameter() {
		return testParameter;
	}
	public String getTestResult() {
		return testResult;
	}
	public String getTestResultUnit() {
		return testResultUnit;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBatchId(stream.readBigDecimal());
			this.setReceiptId(stream.readBigDecimal());
			this.setTestId(stream.readBigDecimal());
			this.setTestDate(new Date(stream.readDate().getTime()));
			this.setTestUserId(stream.readBigDecimal());
			this.setTestUsername(stream.readString());
			this.setTestParameter(stream.readString());
			this.setTestResult(stream.readString());
			this.setTestResultUnit(stream.readString());
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
			stream.writeBigDecimal(this.getBatchId());
			stream.writeBigDecimal(this.getReceiptId());
			stream.writeBigDecimal(this.getTestId());
			stream.writeDate(new java.sql.Date(this.getTestDate().getTime()));
			stream.writeBigDecimal(this.getTestUserId());
			stream.writeString(this.getTestUsername());
			stream.writeString(this.getTestParameter());
			stream.writeString(this.getTestResult());
			stream.writeString(this.getTestResultUnit());
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