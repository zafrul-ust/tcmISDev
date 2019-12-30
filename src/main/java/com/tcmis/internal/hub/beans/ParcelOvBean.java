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
 * CLASSNAME: ParcelOvBean <br>
 * @version: 1.0, Feb 12, 2008 <br>
 *****************************************************************************/


public class ParcelOvBean extends BaseDataBean implements SQLData {

	private String sourceHub;
	private String transportationMode;
	private String carrierCode;
	private String carrierName;
	private String trackingNumber;
	private BigDecimal prNumber;
	private String lineItem;
	private BigDecimal packingGroupId;
	private String facilityId;
	private String originInspectionRequired;
	private String dot;
	private String hazardous;
	private BigDecimal fedexParcelHazardousDocId;
	private Collection<CarrierObjBean> carrier;
	private Array carrierArray;
	private Collection<BoxObjBean> packingGroupBox;
	private Array packingGroupBoxArray;
	private String sqlType = "parcel_ov";

	private String trackSerialNumber;
	private String missingSerialNumber;


	//constructor
	public ParcelOvBean() {
	}

	//setters
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setTransportationMode(String transportationMode) {
		this.transportationMode = transportationMode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setPackingGroupId(BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
	}
	public void setCarrier(Collection coll) {
		this.carrier = coll;
	}
	public void setCarrierArray(Array carrierArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) carrierArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setCarrier(list);
	}
	public void setPackingGroupBox(Collection coll) {
		this.packingGroupBox = coll;
	}
	public void setPackingGroupBoxArray(Array packingGroupBoxArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) packingGroupBoxArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setPackingGroupBox(list);
	}

	public void setTrackSerialNumber(String trackSerialNumber) {
		this.trackSerialNumber = trackSerialNumber;
	}

	public void setMissingSerialNumber(String missingSerialNumber) {
		this.missingSerialNumber = missingSerialNumber;
	}

	//getters
	public String getSourceHub() {
		return sourceHub;
	}
	public String getTransportationMode() {
		return transportationMode;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getOriginInspectionRequired() {
		return originInspectionRequired;
	}
	public Collection getCarrier() {
		return this.carrier;
	}
	public Array getCarrierArray() {
		return carrierArray;
	}
	public Collection getPackingGroupBox() {
		return this.packingGroupBox;
	}
	public Array getPackingGroupBoxArray() {
		return packingGroupBoxArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setSourceHub(stream.readString());
			this.setTransportationMode(stream.readString());
			this.setCarrierCode(stream.readString());
			this.setCarrierName(stream.readString());
			this.setTrackingNumber(stream.readString());
			this.setPrNumber(stream.readBigDecimal());
			this.setLineItem(stream.readString());
			this.setPackingGroupId(stream.readBigDecimal());
			this.setFacilityId(stream.readString());
			this.setOriginInspectionRequired(stream.readString());
			this.setDot(stream.readString());
			this.setHazardous(stream.readString());
			this.setFedexParcelHazardousDocId(stream.readBigDecimal());
			this.setTrackSerialNumber(stream.readString());
			this.setMissingSerialNumber(stream.readString());
			this.setCarrierArray(stream.readArray());
			this.setPackingGroupBoxArray(stream.readArray());
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
			stream.writeString(this.getSourceHub());
			stream.writeString(this.getTransportationMode());
			stream.writeString(this.getCarrierCode());
			stream.writeString(this.getCarrierName());
			stream.writeString(this.getTrackingNumber());
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeString(this.getLineItem());
			stream.writeBigDecimal(this.getPackingGroupId());
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getOriginInspectionRequired());
			stream.writeString(this.getDot());
			stream.writeString(this.getHazardous());
			stream.writeBigDecimal(this.getFedexParcelHazardousDocId());
			stream.writeString(this.getTrackSerialNumber());
			stream.writeString(this.getMissingSerialNumber());
			stream.writeArray(this.getCarrierArray());
			stream.writeArray(this.getPackingGroupBoxArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getDot() {
		return dot;
	}

	public void setDot(String dot) {
		this.dot = dot;
	}

	public String getHazardous() {
		return hazardous;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public BigDecimal getFedexParcelHazardousDocId() {
		return fedexParcelHazardousDocId;
	}

	public void setFedexParcelHazardousDocId(BigDecimal fedexParcelHazardousDocId) {
		this.fedexParcelHazardousDocId = fedexParcelHazardousDocId;
	}

	public String getTrackSerialNumber() {
		return trackSerialNumber;
	}

	public String getMissingSerialNumber() {
		return missingSerialNumber;
	}
}