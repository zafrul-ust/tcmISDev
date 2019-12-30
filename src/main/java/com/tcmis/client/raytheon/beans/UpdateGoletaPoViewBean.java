package com.tcmis.client.raytheon.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: UpdateGoletaPoViewBean <br>
 * @version: 1.0, Apr 21, 2009 <br>
 *****************************************************************************/


public class UpdateGoletaPoViewBean extends BaseDataBean implements SQLData {

	private String application;
	private String facPartNo;
	private String inventoryGroup;
	private BigDecimal itemId;
	private String lineItem;
	private String notes;
	private BigDecimal prNumber;
	private BigDecimal quantity;
	private String requestLineStatus;
	private Date requiredDatetime;
	private String shipToLocationId;
	private String companyId;
	private String endUser;
	private String facilityId;
	private BigDecimal requestor;
	private String itemDesc;
	private String packaging;
	private String poNumber;
	private String releaseNumber;
	private String ok;
	private String sqlType = "null";


	//constructor
	public UpdateGoletaPoViewBean() {
	}

	//setters
	public void setApplication(String application) {
		this.application = application;
	}
	public void setFacPartNo(String facPartNo) {
		this.facPartNo = facPartNo;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setRequestLineStatus(String requestLineStatus) {
		this.requestLineStatus = requestLineStatus;
	}
	public void setRequiredDatetime(Date requiredDatetime) {
		this.requiredDatetime = requiredDatetime;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setRequestor(BigDecimal requestor) {
		this.requestor = requestor;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}


	//getters
	public String getApplication() {
		return application;
	}
	public String getFacPartNo() {
		return facPartNo;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getNotes() {
		return notes;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public String getRequestLineStatus() {
		return requestLineStatus;
	}
	public Date getRequiredDatetime() {
		return requiredDatetime;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getEndUser() {
		return endUser;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getRequestor() {
		return requestor;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setApplication(stream.readString());
			this.setFacPartNo(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setItemId(stream.readBigDecimal());
			this.setLineItem(stream.readString());
			this.setNotes(stream.readString());
			this.setPrNumber(stream.readBigDecimal());
			this.setQuantity(stream.readBigDecimal());
			this.setRequestLineStatus(stream.readString());
			this.setRequiredDatetime(new Date(stream.readDate().getTime()));
			this.setShipToLocationId(stream.readString());
			this.setCompanyId(stream.readString());
			this.setEndUser(stream.readString());
			this.setFacilityId(stream.readString());
			this.setRequestor(stream.readBigDecimal());
			this.setItemDesc(stream.readString());
			this.setPackaging(stream.readString());
			this.setPoNumber(stream.readString());
			this.setReleaseNumber(stream.readString());
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
			stream.writeString(this.getApplication());
			stream.writeString(this.getFacPartNo());
			stream.writeString(this.getInventoryGroup());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getLineItem());
			stream.writeString(this.getNotes());
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeBigDecimal(this.getQuantity());
			stream.writeString(this.getRequestLineStatus());
			stream.writeDate(new java.sql.Date(this.getRequiredDatetime().getTime()));
			stream.writeString(this.getShipToLocationId());
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getEndUser());
			stream.writeString(this.getFacilityId());
			stream.writeBigDecimal(this.getRequestor());
			stream.writeString(this.getItemDesc());
			stream.writeString(this.getPackaging());
			stream.writeString(this.getPoNumber());
			stream.writeString(this.getReleaseNumber());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getOk() {
		return ok;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}
}