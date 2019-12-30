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
 * CLASSNAME: LtlTlPalletOvBean <br>
 * @version: 1.0, Feb 12, 2008 <br>
 *****************************************************************************/


public class LtlTlPalletOvBean extends BaseDataBean implements SQLData {

	private String sourceHub;
	private String transportationMode;
	private String carrierCode;
	private String carrierName;
	private String trackingNumber;
	private String trailerNumber;
	private BigDecimal stopNumber;
	private String palletId;
	private String facilityId;
	private String originInspectionRequired;	
	private Collection carrier;
	private Array carrierArray;
	private String consolidationNumber;
	private String sqlType = "ltl_tl_pallet_ov";

	private String trackSerialNumber;
	private String missingSerialNumber;

	//constructor
	public LtlTlPalletOvBean() {
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
	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}
	public void setStopNumber(BigDecimal stopNumber) {
		this.stopNumber = stopNumber;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setCarrier(Collection coll) {
		this.carrier = coll;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setOriginInspectionRequired(String originInspectionRequired) {
		this.originInspectionRequired = originInspectionRequired;
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
	public String getTrailerNumber() {
		return trailerNumber;
	}
	public BigDecimal getStopNumber() {
		return stopNumber;
	}
	public String getPalletId() {
		return palletId;
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
			this.setTrailerNumber(stream.readString());
			this.setStopNumber(stream.readBigDecimal());
			this.setPalletId(stream.readString());
			this.setConsolidationNumber(stream.readString());
			this.setFacilityId(stream.readString());
			this.setOriginInspectionRequired(stream.readString());
			this.setTrackSerialNumber(stream.readString());
			this.setMissingSerialNumber(stream.readString());
			this.setCarrierArray(stream.readArray());
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
			stream.writeString(this.getTrailerNumber());
			stream.writeBigDecimal(this.getStopNumber());
			stream.writeString(this.getPalletId());
			stream.writeString(this.getConsolidationNumber());
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getOriginInspectionRequired());
			stream.writeString(this.getTrackSerialNumber());
			stream.writeString(this.getMissingSerialNumber());
			stream.writeArray(this.getCarrierArray());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getConsolidationNumber() {
		return consolidationNumber;
	}

	public void setConsolidationNumber(String consolidationNumber) {
		this.consolidationNumber = consolidationNumber;
	}

	public String getTrackSerialNumber() {
		return trackSerialNumber;
	}

	public String getMissingSerialNumber() {
		return missingSerialNumber;
	}
}