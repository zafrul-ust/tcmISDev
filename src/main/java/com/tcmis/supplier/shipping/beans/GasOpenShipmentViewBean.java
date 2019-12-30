package com.tcmis.supplier.shipping.beans;

import java.util.*;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.sql.SQLException;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: GasOpenShipmentViewBean <br>
 * @version: 1.0, May 28, 2008 <br>
 *****************************************************************************/


public class GasOpenShipmentViewBean extends BaseDataBean implements SQLData {

	private BigDecimal shipmentId;
	private String carrierName;
	private String trackingNumber;
	private String shipToCompanyId;
	private String shipToLocationId;
	private String sqlType = "null";
	private String customerPoNo;


	//constructor
	public GasOpenShipmentViewBean() {
	}

	//setters
	public void setShipmentId(BigDecimal shipmentId) {
		this.shipmentId = shipmentId;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public void setShipToCompanyId(String shipToCompanyId) {
		this.shipToCompanyId = shipToCompanyId;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}


	//getters
	public BigDecimal getShipmentId() {
		return shipmentId;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public String getShipToCompanyId() {
		return shipToCompanyId;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setShipmentId(stream.readBigDecimal());
			this.setCarrierName(stream.readString());
			this.setTrackingNumber(stream.readString());
			this.setShipToCompanyId(stream.readString());
			this.setShipToLocationId(stream.readString());
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
			stream.writeBigDecimal(this.getShipmentId());
			stream.writeString(this.getCarrierName());
			stream.writeString(this.getTrackingNumber());
			stream.writeString(this.getShipToCompanyId());
			stream.writeString(this.getShipToLocationId());
		}
		catch (SQLException e) {
			throw (SQLException) e;
		}
		catch (Exception e) {
			new IllegalStateException(getClass().getName() + ".writeSQL method failed").
			initCause(e);
		}
	}

	public String getCustomerPoNo() {
		return customerPoNo;
	}

	public void setCustomerPoNo(String customerPoNo) {
		this.customerPoNo = customerPoNo;
	}
}