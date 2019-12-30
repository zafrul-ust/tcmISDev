package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.framework.BaseDataBean;

@SuppressWarnings("serial")
public class TestResultBean extends BaseDataBean implements SQLData {
	public static Log log = LogFactory.getLog(TestResultBean.class);
	public final static String SQLTypeName = "CUSTOMER.LAB_TEST_RESULTS_OBJ";

	// Read-only fields from VV_TEST
	private String companyId;
	private String facilityId;	
	private String testDesc;
	private String criteria;	
	
	// Read-only fields from LAB_TEST_RESULTS
	private BigDecimal testRequestId;
	private BigDecimal testId;
	private String itemCode;
	
	// Fillable fields from LAB_TEST_RESULTS
	private String note;
	private String passFail;
	private String labLogNumber;
	
	public String getTestDesc()	{
		return testDesc;
	}
	
	public String getCriteria() {
		return criteria;		
	}
	
	public String getNote()	{
		return note;
	}
	
	public String getPassFail(){
		return passFail;
	}
	
	public String getCompanyId(){
		return companyId;
	}
	
	public String getFacilityId(){
		return facilityId;	
	}
	
	public BigDecimal getTestRequestId(){
		return testRequestId;		
	}
	
	public BigDecimal getTestId(){
		return testId;		
	}
	
	public void setCriteria(String criteria){
		this.criteria = criteria;
	}
	
	public void setTestDesc(String testDesc){
		this.testDesc = testDesc;		
	}
	
	public void setNote(String note){
		this.note  = note;
	}
	
	public void setPassFail(String passFail){
		this.passFail = passFail;
	}
	
	public void setCompanyId(String companyId){
		this.companyId = companyId;
	}
	
	public void setFacilityId(String facilityId){
		this.facilityId = facilityId;
	}
	
	public void setTestRequestId(BigDecimal testRequestId){
		this.testRequestId = testRequestId; 
	}
	
	public void setTestId(BigDecimal testId){
		this.testId = testId;
	}
	
	
	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	
	public String getLabLogNumber() {
		return labLogNumber;
	}

	public void setLabLogNumber(String labLogNumber) {
		this.labLogNumber = labLogNumber;
	}

	// Constructor
	public TestResultBean(){
	}

	public String getSQLTypeName() throws SQLException {
		return SQLTypeName;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try{
			setTestRequestId(stream.readBigDecimal());
			setTestId(stream.readBigDecimal());
			setNote(stream.readString());
			setTestDesc(stream.readString());
			setCriteria(stream.readString());
			setPassFail(stream.readString());
			setItemCode(stream.readString());
			setLabLogNumber(stream.readString());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error(getClass().getName() + ".readSQL method failed", e);
		}
		
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		try{
			stream.writeBigDecimal(getTestRequestId());
			stream.writeBigDecimal(getTestId());
			stream.writeString(getNote());
			stream.writeString(getTestDesc());
			stream.writeString(getCriteria());
			stream.writeString(getPassFail());
			stream.writeString(getItemCode());
			stream.writeString(getLabLogNumber());
		}
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").initCause(e);
		}
	}
}
