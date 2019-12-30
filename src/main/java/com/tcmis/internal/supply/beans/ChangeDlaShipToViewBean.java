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
 * CLASSNAME: ChangeDlaShipToViewBean <br>
 * @version: 1.0, Feb 26, 2009 <br>
 *****************************************************************************/


public class ChangeDlaShipToViewBean extends BaseDataBean implements SQLData {

	private BigDecimal prNumber;
	private String lineItem;
	private String shipToDodaac;
	private String shipToLocationId;
	private String shipViaDodaac;
	private String shipViaLocationId;
	private String notes;
	private String milstripCode;
	private String portOfEmbarkation;
	private String portOfDebarkation;
	private String stCountryAbbrev;
	private String stStateAbbrev;
	private String stCity;
	private String stZip;
	private String stAddressLine1Display;
	private String stAddressLine2Display;
	private String stAddressLine3Display;
	private String stAddressLine4Display;
	private String mfCountryAbbrev;
	private String mfStateAbbrev;
	private String mfCity;
	private String mfZip;
	private String mfAddressLine1Display;
	private String mfAddressLine2Display;
	private String mfAddressLine3Display;
	private String mfAddressLine4Display;
	private String facPartNo;
	private String sqlType = "null";
    private Date desiredShipDate;

    //constructor
	public ChangeDlaShipToViewBean() {
	}

    public Date getDesiredShipDate() {
        return desiredShipDate;
    }

    public void setDesiredShipDate(Date desiredShipDate) {
        this.desiredShipDate = desiredShipDate;
    }

    //setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setShipToDodaac(String shipToDodaac) {
		this.shipToDodaac = shipToDodaac;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setShipViaDodaac(String shipViaDodaac) {
		this.shipViaDodaac = shipViaDodaac;
	}
	public void setShipViaLocationId(String shipViaLocationId) {
		this.shipViaLocationId = shipViaLocationId;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setMilstripCode(String milstripCode) {
		this.milstripCode = milstripCode;
	}
	public void setPortOfEmbarkation(String portOfEmbarkation) {
		this.portOfEmbarkation = portOfEmbarkation;
	}
	public void setPortOfDebarkation(String portOfDebarkation) {
		this.portOfDebarkation = portOfDebarkation;
	}
	public void setStCountryAbbrev(String stCountryAbbrev) {
		this.stCountryAbbrev = stCountryAbbrev;
	}
	public void setStStateAbbrev(String stStateAbbrev) {
		this.stStateAbbrev = stStateAbbrev;
	}
	public void setStCity(String stCity) {
		this.stCity = stCity;
	}
	public void setStZip(String stZip) {
		this.stZip = stZip;
	}
	public void setStAddressLine1Display(String stAddressLine1Display) {
		this.stAddressLine1Display = stAddressLine1Display;
	}
	public void setStAddressLine2Display(String stAddressLine2Display) {
		this.stAddressLine2Display = stAddressLine2Display;
	}
	public void setStAddressLine3Display(String stAddressLine3Display) {
		this.stAddressLine3Display = stAddressLine3Display;
	}
	public void setStAddressLine4Display(String stAddressLine4Display) {
		this.stAddressLine4Display = stAddressLine4Display;
	}
	public void setMfCountryAbbrev(String mfCountryAbbrev) {
		this.mfCountryAbbrev = mfCountryAbbrev;
	}
	public void setMfStateAbbrev(String mfStateAbbrev) {
		this.mfStateAbbrev = mfStateAbbrev;
	}
	public void setMfCity(String mfCity) {
		this.mfCity = mfCity;
	}
	public void setMfZip(String mfZip) {
		this.mfZip = mfZip;
	}
	public void setMfAddressLine1Display(String mfAddressLine1Display) {
		this.mfAddressLine1Display = mfAddressLine1Display;
	}
	public void setMfAddressLine2Display(String mfAddressLine2Display) {
		this.mfAddressLine2Display = mfAddressLine2Display;
	}
	public void setMfAddressLine3Display(String mfAddressLine3Display) {
		this.mfAddressLine3Display = mfAddressLine3Display;
	}
	public void setMfAddressLine4Display(String mfAddressLine4Display) {
		this.mfAddressLine4Display = mfAddressLine4Display;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getShipToDodaac() {
		return shipToDodaac;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getShipViaDodaac() {
		return shipViaDodaac;
	}
	public String getShipViaLocationId() {
		return shipViaLocationId;
	}
	public String getNotes() {
		return notes;
	}
	public String getMilstripCode() {
		return milstripCode;
	}
	public String getPortOfEmbarkation() {
		return portOfEmbarkation;
	}
	public String getPortOfDebarkation() {
		return portOfDebarkation;
	}
	public String getStCountryAbbrev() {
		return stCountryAbbrev;
	}
	public String getStStateAbbrev() {
		return stStateAbbrev;
	}
	public String getStCity() {
		return stCity;
	}
	public String getStZip() {
		return stZip;
	}
	public String getStAddressLine1Display() {
		return stAddressLine1Display;
	}
	public String getStAddressLine2Display() {
		return stAddressLine2Display;
	}
	public String getStAddressLine3Display() {
		return stAddressLine3Display;
	}
	public String getStAddressLine4Display() {
		return stAddressLine4Display;
	}
	public String getMfCountryAbbrev() {
		return mfCountryAbbrev;
	}
	public String getMfStateAbbrev() {
		return mfStateAbbrev;
	}
	public String getMfCity() {
		return mfCity;
	}
	public String getMfZip() {
		return mfZip;
	}
	public String getMfAddressLine1Display() {
		return mfAddressLine1Display;
	}
	public String getMfAddressLine2Display() {
		return mfAddressLine2Display;
	}
	public String getMfAddressLine3Display() {
		return mfAddressLine3Display;
	}
	public String getMfAddressLine4Display() {
		return mfAddressLine4Display;
	}
	public String getFacPartNo() {
		return facPartNo;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setPrNumber(stream.readBigDecimal());
			this.setLineItem(stream.readString());
			this.setShipToDodaac(stream.readString());
			this.setShipToLocationId(stream.readString());
			this.setShipViaDodaac(stream.readString());
			this.setShipViaLocationId(stream.readString());
			this.setNotes(stream.readString());
			this.setMilstripCode(stream.readString());
			this.setPortOfEmbarkation(stream.readString());
			this.setPortOfDebarkation(stream.readString());
			this.setStCountryAbbrev(stream.readString());
			this.setStStateAbbrev(stream.readString());
			this.setStCity(stream.readString());
			this.setStZip(stream.readString());
			this.setStAddressLine1Display(stream.readString());
			this.setStAddressLine2Display(stream.readString());
			this.setStAddressLine3Display(stream.readString());
			this.setStAddressLine4Display(stream.readString());
			this.setMfCountryAbbrev(stream.readString());
			this.setMfStateAbbrev(stream.readString());
			this.setMfCity(stream.readString());
			this.setMfZip(stream.readString());
			this.setMfAddressLine1Display(stream.readString());
			this.setMfAddressLine2Display(stream.readString());
			this.setMfAddressLine3Display(stream.readString());
			this.setMfAddressLine4Display(stream.readString());
			this.setFacPartNo(stream.readString());
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
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeString(this.getLineItem());
			stream.writeString(this.getShipToDodaac());
			stream.writeString(this.getShipToLocationId());
			stream.writeString(this.getShipViaDodaac());
			stream.writeString(this.getShipViaLocationId());
			stream.writeString(this.getNotes());
			stream.writeString(this.getMilstripCode());
			stream.writeString(this.getPortOfEmbarkation());
			stream.writeString(this.getPortOfDebarkation());
			stream.writeString(this.getStCountryAbbrev());
			stream.writeString(this.getStStateAbbrev());
			stream.writeString(this.getStCity());
			stream.writeString(this.getStZip());
			stream.writeString(this.getStAddressLine1Display());
			stream.writeString(this.getStAddressLine2Display());
			stream.writeString(this.getStAddressLine3Display());
			stream.writeString(this.getStAddressLine4Display());
			stream.writeString(this.getMfCountryAbbrev());
			stream.writeString(this.getMfStateAbbrev());
			stream.writeString(this.getMfCity());
			stream.writeString(this.getMfZip());
			stream.writeString(this.getMfAddressLine1Display());
			stream.writeString(this.getMfAddressLine2Display());
			stream.writeString(this.getMfAddressLine3Display());
			stream.writeString(this.getMfAddressLine4Display());
			stream.writeString(this.getFacPartNo());
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