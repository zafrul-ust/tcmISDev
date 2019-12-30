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
 * CLASSNAME: PolchemNsnVerificationBean <br>
 * @version: 1.0, Jul 1, 2008 <br>
 *****************************************************************************/


public class PolchemNsnVerificationBean extends BaseDataBean implements SQLData {

	private String nsn;
	private String partShortName;
	private String vmiGrouping;
	private BigDecimal grossWeightLbs;
	private BigDecimal cubicFeet;
	private String ui;
	private BigDecimal containersPerUi;
	private BigDecimal maxNsnPerBox;
	private BigDecimal maxNsnPerPallet;
	private String externalContainer;
	private String verifiedBy;
	private Date verifiedDate;
	private BigDecimal oconusQtyPerPallet;
	private String status;
	private String quantityUnitPack;
	private String quantityWithinUnitPack;
	private String quantityInUnitPack;
	private String unitPackUnit;
	private String unitPackageType;
	private String ok;
	private String sqlType = "null";


	//constructor
	public PolchemNsnVerificationBean() {
	}

	//setters
	public void setNsn(String nsn) {
		this.nsn = nsn;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setVmiGrouping(String vmiGrouping) {
		this.vmiGrouping = vmiGrouping;
	}
	public void setGrossWeightLbs(BigDecimal grossWeightLbs) {
		this.grossWeightLbs = grossWeightLbs;
	}
	public void setCubicFeet(BigDecimal cubicFeet) {
		this.cubicFeet = cubicFeet;
	}
	public void setUi(String ui) {
		this.ui = ui;
	}
	public void setContainersPerUi(BigDecimal containersPerUi) {
		this.containersPerUi = containersPerUi;
	}
	public void setMaxNsnPerBox(BigDecimal maxNsnPerBox) {
		this.maxNsnPerBox = maxNsnPerBox;
	}
	public void setMaxNsnPerPallet(BigDecimal maxNsnPerPallet) {
		this.maxNsnPerPallet = maxNsnPerPallet;
	}
	public void setExternalContainer(String externalContainer) {
		this.externalContainer = externalContainer;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public void setVerifiedDate(Date verifiedDate) {
		this.verifiedDate = verifiedDate;
	}
	public void setOconusQtyPerPallet(BigDecimal oconusQtyPerPallet) {
		this.oconusQtyPerPallet = oconusQtyPerPallet;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setQuantityUnitPack(String quantityUnitPack) {
		this.quantityUnitPack = quantityUnitPack;
	}
	public void setQuantityWithinUnitPack(String quantityWithinUnitPack) {
		this.quantityWithinUnitPack = quantityWithinUnitPack;
	}
	public void setQuantityInUnitPack(String quantityInUnitPack) {
		this.quantityInUnitPack = quantityInUnitPack;
	}
	public void setUnitPackUnit(String unitPackUnit) {
		this.unitPackUnit = unitPackUnit;
	}
	public void setUnitPackageType(String unitPackageType) {
		this.unitPackageType = unitPackageType;
	}


	//getters
	public String getNsn() {
		return nsn;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public String getVmiGrouping() {
		return vmiGrouping;
	}
	public BigDecimal getGrossWeightLbs() {
		return grossWeightLbs;
	}
	public BigDecimal getCubicFeet() {
		return cubicFeet;
	}
	public String getUi() {
		return ui;
	}
	public BigDecimal getContainersPerUi() {
		return containersPerUi;
	}
	public BigDecimal getMaxNsnPerBox() {
		return maxNsnPerBox;
	}
	public BigDecimal getMaxNsnPerPallet() {
		return maxNsnPerPallet;
	}
	public String getExternalContainer() {
		return externalContainer;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public Date getVerifiedDate() {
		return verifiedDate;
	}
	public BigDecimal getOconusQtyPerPallet() {
		return oconusQtyPerPallet;
	}
	public String getStatus() {
		return status;
	}
	public String getQuantityUnitPack() {
		return quantityUnitPack;
	}
	public String getQuantityWithinUnitPack() {
		return quantityWithinUnitPack;
	}
	public String getQuantityInUnitPack() {
		return quantityInUnitPack;
	}
	public String getUnitPackUnit() {
		return unitPackUnit;
	}
	public String getUnitPackageType() {
		return unitPackageType;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setNsn(stream.readString());
			this.setPartShortName(stream.readString());
			this.setVmiGrouping(stream.readString());
			this.setGrossWeightLbs(stream.readBigDecimal());
			this.setCubicFeet(stream.readBigDecimal());
			this.setUi(stream.readString());
			this.setContainersPerUi(stream.readBigDecimal());
			this.setMaxNsnPerBox(stream.readBigDecimal());
			this.setMaxNsnPerPallet(stream.readBigDecimal());
			this.setExternalContainer(stream.readString());
			this.setVerifiedBy(stream.readString());
			this.setVerifiedDate(new Date(stream.readDate().getTime()));
			this.setOconusQtyPerPallet(stream.readBigDecimal());
			this.setStatus(stream.readString());
			this.setQuantityUnitPack(stream.readString());
			this.setQuantityWithinUnitPack(stream.readString());
			this.setQuantityInUnitPack(stream.readString());
			this.setUnitPackUnit(stream.readString());
			this.setUnitPackageType(stream.readString());
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
			stream.writeString(this.getNsn());
			stream.writeString(this.getPartShortName());
			stream.writeString(this.getVmiGrouping());
			stream.writeBigDecimal(this.getGrossWeightLbs());
			stream.writeBigDecimal(this.getCubicFeet());
			stream.writeString(this.getUi());
			stream.writeBigDecimal(this.getContainersPerUi());
			stream.writeBigDecimal(this.getMaxNsnPerBox());
			stream.writeBigDecimal(this.getMaxNsnPerPallet());
			stream.writeString(this.getExternalContainer());
			stream.writeString(this.getVerifiedBy());
			stream.writeDate(new java.sql.Date(this.getVerifiedDate().getTime()));
			stream.writeBigDecimal(this.getOconusQtyPerPallet());
			stream.writeString(this.getStatus());
			stream.writeString(this.getQuantityUnitPack());
			stream.writeString(this.getQuantityWithinUnitPack());
			stream.writeString(this.getQuantityInUnitPack());
			stream.writeString(this.getUnitPackUnit());
			stream.writeString(this.getUnitPackageType());
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