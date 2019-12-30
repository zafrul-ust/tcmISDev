package com.tcmis.ws.scanner.beans;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: HubBean <br>
 * 
 * @version: 1.0, Jun 1, 2004 <br>
 *****************************************************************************/

public class Cylinder extends BaseDataBean {

	private String		conversionGroup;
	private String		correspondingNsn;
	private String		cylinderConditionCode;
	private String		cylinderStatus;
	private BigDecimal	cylinderTrackingId;
	private Date		dateDisposed;
	private Date		dateReceived;
	private Date		dateSentOut;
	private String		documentNumber;
	private String		id;
	private String		lastShippedDodaac;
	private Date		lastUpdated;
	private Date		latestHydroTestDate;
	private String		locationId;
	private String		manufacturerIdNo;
	private String		manufacturerName;
	private String		notes;
	private boolean		onHand;
	private BigDecimal	personnelId;
	private String		refurbCategory;
	private String		returnFromLocation;
	private String		serialNumber;
	private String		status;
	private String		supplier;
	private String		vendorPartNo;

	// constructor
	public Cylinder() {
	}

	public String getConversionGroup() {
		return this.conversionGroup;
	}

	public String getCorrespondingNsn() {
		return this.correspondingNsn;
	}

	public String getCylinderConditionCode() {
		return this.cylinderConditionCode;
	}

	public String getCylinderStatus() {
		return this.cylinderStatus;
	}

	public BigDecimal getCylinderTrackingId() {
		return this.cylinderTrackingId;
	}

	public Date getDateDisposed() {
		return this.dateDisposed;
	}

	public Date getDateReceived() {
		return this.dateReceived;
	}

	public Date getDateSentOut() {
		return this.dateSentOut;
	}

	public String getDocumentNumber() {
		return this.documentNumber;
	}

	public String getId() {
		return this.id;
	}

	public String getInvalidMessage() {
		return "Cylinder requires: supplier, manufacturerIdNo, serialNumber,  cylinderConditionCode, locationId and personnelId";
	}

	public String getLastShippedDodaac() {
		return this.lastShippedDodaac;
	}

	public Date getLastUpdated() {
		if (lastUpdated == null) {
			return getDateReceived();
		}
		return this.lastUpdated;
	}

	public Date getLatestHydroTestDate() {
		return this.latestHydroTestDate;
	}

	public String getLocationId() {
		return this.locationId;
	}

	public String getManufacturerIdNo() {
		return this.manufacturerIdNo;
	}

	public String getManufacturerName() {
		return this.manufacturerName;
	}

	public String getNotes() {
		return this.notes;
	}

	public BigDecimal getPersonnelId() {
		return this.personnelId;
	}

	public String getRefurbCategory() {
		return this.refurbCategory;
	}

	public String getReturnFromLocation() {
		return this.returnFromLocation;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public String getStatus() {
		return this.status;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public String getVendorPartNo() {
		return this.vendorPartNo;
	}
	
	public boolean hasCorrespondingNsn() {
		return StringUtils.isNotBlank(correspondingNsn);
	}

	public boolean hasStatus() {
		return StringUtils.isNotBlank(status);
	}

	public boolean hasDocumentNumber() {
		return StringUtils.isNotBlank(documentNumber);
	}

	public boolean hasCylinderTrackingId() {
		return cylinderTrackingId != null;
	}
	
	public boolean isOnHand() {
		return this.onHand;
	}

	public boolean isOut() {
		return "Out".equals(cylinderStatus);
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(supplier) && StringUtils.isNotBlank(manufacturerIdNo) && StringUtils.isNotBlank(serialNumber) && StringUtils.isNotBlank(refurbCategory)
				 && StringUtils.isNotBlank(cylinderConditionCode) && StringUtils.isNotBlank(locationId) && personnelId != null;
	}

	public void setConversionGroup(String conversionGroup) {
		this.conversionGroup = conversionGroup;
	}

	public void setCorrespondingNsn(String correspondingNsn) {
		this.correspondingNsn = correspondingNsn;
	}

	public void setCylinderConditionCode(String cylinderConditionCode) {
		this.cylinderConditionCode = cylinderConditionCode;
	}

	public void setCylinderStatus(String cylinderStatus) {
		this.cylinderStatus = cylinderStatus;
	}

	public void setCylinderTrackingId(BigDecimal cylinderTrackingId) {
		this.cylinderTrackingId = cylinderTrackingId;
	}

	public void setDateDisposed(Date dateDisposed) {
		this.dateDisposed = dateDisposed;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public void setDateSentOut(Date dateSentOut) {
		this.dateSentOut = dateSentOut;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastShippedDodaac(String lastShippedDodaac) {
		this.lastShippedDodaac = lastShippedDodaac;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public void setLatestHydroTestDate(Date lastestHydroTestDate) {
		this.latestHydroTestDate = lastestHydroTestDate;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public void setManufacturerIdNo(String manufacturerIdNo) {
		this.manufacturerIdNo = manufacturerIdNo;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOnHand(boolean onHand) {
		this.onHand = onHand;
	}

	public void setPersonnelId(BigDecimal personnelId) {
		this.personnelId = personnelId;
	}

	public void setRefurbCategory(String refurbCategory) {
		this.refurbCategory = refurbCategory;
	}

	public void setReturnFromLocation(String returnFromLocation) {
		this.returnFromLocation = returnFromLocation;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public void setVendorPartNo(String vendorPartNo) {
		this.vendorPartNo = vendorPartNo;
	}
}