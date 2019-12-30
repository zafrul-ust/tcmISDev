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
 * CLASSNAME: SalesQuoteHoldOvBean <br>
 * @version: 1.0, Mar 3, 2010 <br>
 *****************************************************************************/


public class SalesQuoteHoldOvBean extends BaseDataBean implements SQLData {

	private String companyId;
	private BigDecimal prNumber;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String shipToAddressLine1;
	private String shipToAddressLine2;
	private String shipToAddressLine3;
	private String shipToAddressLine4;
	private String shipToAddressLine5;
	private String releaseStatus;
	private BigDecimal totalExtendedPrice;
	private BigDecimal customerId;
	private String customerName;
	private String poNumber;
	private String submittedByName;
	private String requestorName;
	private BigDecimal availableCredit;
	private String withinTerms;
	private String customerNote;
	private String internalNote;
	private String shiptoNote;
	private String specialInstructions;
	private String opsEntityId;
	private String hub;
	private String hubName;
	private String orderStatus;
	private Date submittedDate;
	private BigDecimal submittedBy;
	private String qualityHold;
	private String billToCompanyId;
	private String opsCompanyId;
	private String displayStatus;
	private String holdComments;
	private Date dateFirstConfirmed;
	private String materialRequestOrigin;
	private Collection<SalesQuoteLineObjBean> line;
	private Array lineArray;
	private String sqlType = "SALES_QUOTE_HOLD_OV";


	//constructor
	public SalesQuoteHoldOvBean() {
	}

	//setters

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setShipToAddressLine1(String shipToAddressLine1) {
		this.shipToAddressLine1 = shipToAddressLine1;
	}
	public void setShipToAddressLine2(String shipToAddressLine2) {
		this.shipToAddressLine2 = shipToAddressLine2;
	}
	public void setShipToAddressLine3(String shipToAddressLine3) {
		this.shipToAddressLine3 = shipToAddressLine3;
	}
	public void setShipToAddressLine4(String shipToAddressLine4) {
		this.shipToAddressLine4 = shipToAddressLine4;
	}
	public void setShipToAddressLine5(String shipToAddressLine5) {
		this.shipToAddressLine5 = shipToAddressLine5;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public void setTotalExtendedPrice(BigDecimal totalExtendedPrice) {
		this.totalExtendedPrice = totalExtendedPrice;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setSubmittedByName(String submittedByName) {
		this.submittedByName = submittedByName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}
	public void setWithinTerms(String withinTerms) {
		this.withinTerms = withinTerms;
	}
	public void setCustomerNote(String customerNote) {
		this.customerNote = customerNote;
	}
	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}
	public void setShiptoNote(String shiptoNote) {
		this.shiptoNote = shiptoNote;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setSubmittedBy(BigDecimal submittedBy) {
		this.submittedBy = submittedBy;
	}
	public void setQualityHold(String qualityHold) {
		this.qualityHold = qualityHold;
	}
	public void setDisplayStatus(String displayStatus) {
		this.displayStatus = displayStatus;
	}
	public void setLine(Collection coll) {
		this.line = coll;
	}
	public void setMaterialRequestOrigin(String materialRequestOrigin) {
		this.materialRequestOrigin = materialRequestOrigin;
	}
	
	public void setLineArray(Array lineArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) lineArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setLine(list);
	}


	//getters

	public String getCompanyId() {
		return companyId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getShipToAddressLine1() {
		return shipToAddressLine1;
	}
	public String getShipToAddressLine2() {
		return shipToAddressLine2;
	}
	public String getShipToAddressLine3() {
		return shipToAddressLine3;
	}
	public String getShipToAddressLine4() {
		return shipToAddressLine4;
	}
	public String getShipToAddressLine5() {
		return shipToAddressLine5;
	}
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public BigDecimal getTotalExtendedPrice() {
		return totalExtendedPrice;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public String getSubmittedByName() {
		return submittedByName;
	}
	public String getRequestorName() {
		return requestorName;
	}
	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}
	public String getWithinTerms() {
		return withinTerms;
	}
	public String getCustomerNote() {
		return customerNote;
	}
	public String getInternalNote() {
		return internalNote;
	}
	public String getShiptoNote() {
		return shiptoNote;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public Date getSubmittedDate() {
		return submittedDate;
	}
	public BigDecimal getSubmittedBy() {
		return submittedBy;
	}
	public String getQualityHold() {
		return qualityHold;
	}
	public String getDisplayStatus() {
		return displayStatus;
	}
	public Collection getLine() {
		return this.line;
	}
	public Array getLineArray() {
		return lineArray;
	}
	public String getMaterialRequestOrigin() {
		return materialRequestOrigin;
	}
	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try { Date dd = null;
			this.setCompanyId(stream.readString());
			this.setPrNumber(stream.readBigDecimal());
			this.setInventoryGroup(stream.readString());
			this.setInventoryGroupName(stream.readString());
			this.setShipToAddressLine1(stream.readString());
			this.setShipToAddressLine2(stream.readString());
			this.setShipToAddressLine3(stream.readString());
			this.setShipToAddressLine4(stream.readString());
			this.setShipToAddressLine5(stream.readString());
			this.setReleaseStatus(stream.readString());
			this.setTotalExtendedPrice(stream.readBigDecimal());
			this.setCustomerId(stream.readBigDecimal());
			this.setCustomerName(stream.readString());
			this.setPoNumber(stream.readString());
			this.setSubmittedByName(stream.readString());
			this.setRequestorName(stream.readString());
			this.setAvailableCredit(stream.readBigDecimal());
			this.setWithinTerms(stream.readString());
			this.setCustomerNote(stream.readString());
			this.setInternalNote(stream.readString());
			this.setShiptoNote(stream.readString());
			this.setSpecialInstructions(stream.readString());
			this.setOpsEntityId(stream.readString());
			this.setHub(stream.readString());
			this.setHubName(stream.readString());
			this.setOrderStatus(stream.readString());
			 dd = stream.readDate();			if( dd != null ) this.setSubmittedDate(new Date(dd.getTime()));
			this.setSubmittedBy(stream.readBigDecimal());
			this.setQualityHold(stream.readString());
			this.setDisplayStatus(stream.readString());
			this.setBillToCompanyId(stream.readString());
			this.setOpsCompanyId(stream.readString());
			this.setHoldComments(stream.readString());
			 dd = stream.readDate();    		if( dd != null ) this.setDateFirstConfirmed(new Date(dd.getTime()));
			this.setMaterialRequestOrigin(stream.readString());			 
			this.setLineArray(stream.readArray());
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
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getInventoryGroupName());
			stream.writeString(this.getShipToAddressLine1());
			stream.writeString(this.getShipToAddressLine2());
			stream.writeString(this.getShipToAddressLine3());
			stream.writeString(this.getShipToAddressLine4());
			stream.writeString(this.getShipToAddressLine5());
			stream.writeString(this.getReleaseStatus());
			stream.writeBigDecimal(this.getTotalExtendedPrice());
			stream.writeBigDecimal(this.getCustomerId());
			stream.writeString(this.getCustomerName());
			stream.writeString(this.getPoNumber());
			stream.writeString(this.getSubmittedByName());
			stream.writeString(this.getRequestorName());
			stream.writeBigDecimal(this.getAvailableCredit());
			stream.writeString(this.getWithinTerms());
			stream.writeString(this.getCustomerNote());
			stream.writeString(this.getInternalNote());
			stream.writeString(this.getShiptoNote());
			stream.writeString(this.getSpecialInstructions());
			stream.writeString(this.getOpsEntityId());
			stream.writeString(this.getHub());
			stream.writeString(this.getHubName());
			stream.writeString(this.getOrderStatus());
			stream.writeDate(new java.sql.Date(this.getSubmittedDate().getTime()));
			stream.writeBigDecimal(this.getSubmittedBy());
			stream.writeString(this.getQualityHold());
			stream.writeString(this.getDisplayStatus());
			stream.writeString(this.getBillToCompanyId());
			stream.writeString(this.getOpsCompanyId());
			stream.writeString(this.getHoldComments());
			stream.writeDate(new java.sql.Date(this.getDateFirstConfirmed().getTime()));
			stream.writeString(this.getMaterialRequestOrigin());
			stream.writeArray(this.getLineArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getBillToCompanyId() {
		return billToCompanyId;
	}

	public void setBillToCompanyId(String billToCompanyId) {
		this.billToCompanyId = billToCompanyId;
	}

	public String getOpsCompanyId() {
		return opsCompanyId;
	}

	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}

	public Date getDateFirstConfirmed() {
		return dateFirstConfirmed;
	}

	public void setDateFirstConfirmed(Date dateFirstConfirmed) {
		this.dateFirstConfirmed = dateFirstConfirmed;
	}

	public String getHoldComments() {
		return holdComments;
	}

	public void setHoldComments(String holdComments) {
		this.holdComments = holdComments;
	}
}