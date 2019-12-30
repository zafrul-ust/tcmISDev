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
 * CLASSNAME: ExpiredInventoryViewBean <br>
 * @version: 1.0, Jul 14, 2009 <br>
 *****************************************************************************/


public class ExpiredInventoryViewBean extends BaseDataBean implements SQLData {

	private String bin;
	private String clientPartNos;
	private String currencyId;
	private Date expireDate;
	private String hub;
	private String hubName;
	private String inventoryGroup;
	private String inventoryGroupName;
	private String partShortName;
	private BigDecimal itemId;
	private String lotStatus;
	private BigDecimal lotStatusAge;
	private String opsCompanyId;
	private String opsEntityId;
	private BigDecimal quantity;
	private BigDecimal receiptId;
	private String specs;
	private BigDecimal unitCost;
	private BigDecimal shelfLifeDays;
	private String shelfLifeBasis;
	private BigDecimal expired;
	private BigDecimal month1;
	private BigDecimal month2;
	private BigDecimal month3;
	private BigDecimal month4;
	private BigDecimal month5;
	private BigDecimal month6;
	private String operatingEntityName;
	private String sqlType = "null";


	//constructor
	public ExpiredInventoryViewBean() {
	}

	//setters
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setClientPartNos(String clientPartNos) {
		this.clientPartNos = clientPartNos;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setHubName(String hubName) {
		this.hubName = hubName;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setPartShortName(String partShortName) {
		this.partShortName = partShortName;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}
	public void setLotStatusAge(BigDecimal lotStatusAge) {
		this.lotStatusAge = lotStatusAge;
	}
	public void setOpsCompanyId(String opsCompanyId) {
		this.opsCompanyId = opsCompanyId;
	}
	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setSpecs(String specs) {
		this.specs = specs;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	public void setShelfLifeDays(BigDecimal shelfLifeDays) {
		this.shelfLifeDays = shelfLifeDays;
	}
	public void setShelfLifeBasis(String shelfLifeBasis) {
		this.shelfLifeBasis = shelfLifeBasis;
	}
	public void setExpired(BigDecimal expired) {
		this.expired = expired;
	}
	public void setMonth1(BigDecimal month1) {
		this.month1 = month1;
	}
	public void setMonth2(BigDecimal month2) {
		this.month2 = month2;
	}
	public void setMonth3(BigDecimal month3) {
		this.month3 = month3;
	}
	public void setMonth4(BigDecimal month4) {
		this.month4 = month4;
	}
	public void setMonth5(BigDecimal month5) {
		this.month5 = month5;
	}
	public void setMonth6(BigDecimal month6) {
		this.month6 = month6;
	}
	public void setOperatingEntityName(String operatingEntityName) {
		this.operatingEntityName = operatingEntityName;
	}


	//getters
	public String getBin() {
		return bin;
	}
	public String getClientPartNos() {
		return clientPartNos;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getHub() {
		return hub;
	}
	public String getHubName() {
		return hubName;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public String getPartShortName() {
		return partShortName;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public String getLotStatus() {
		return lotStatus;
	}
	public BigDecimal getLotStatusAge() {
		return lotStatusAge;
	}
	public String getOpsCompanyId() {
		return opsCompanyId;
	}
	public String getOpsEntityId() {
		return opsEntityId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public String getSpecs() {
		return specs;
	}
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public BigDecimal getShelfLifeDays() {
		return shelfLifeDays;
	}
	public String getShelfLifeBasis() {
		return shelfLifeBasis;
	}
	public BigDecimal getExpired() {
		return expired;
	}
	public BigDecimal getMonth1() {
		return month1;
	}
	public BigDecimal getMonth2() {
		return month2;
	}
	public BigDecimal getMonth3() {
		return month3;
	}
	public BigDecimal getMonth4() {
		return month4;
	}
	public BigDecimal getMonth5() {
		return month5;
	}
	public BigDecimal getMonth6() {
		return month6;
	}
	public String getOperatingEntityName() {
		return operatingEntityName;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setBin(stream.readString());
			this.setClientPartNos(stream.readString());
			this.setCurrencyId(stream.readString());
			this.setExpireDate(new Date(stream.readDate().getTime()));
			this.setHub(stream.readString());
			this.setHubName(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setInventoryGroupName(stream.readString());
			this.setPartShortName(stream.readString());
			this.setItemId(stream.readBigDecimal());
			this.setLotStatus(stream.readString());
			this.setLotStatusAge(stream.readBigDecimal());
			this.setOpsCompanyId(stream.readString());
			this.setOpsEntityId(stream.readString());
			this.setQuantity(stream.readBigDecimal());
			this.setReceiptId(stream.readBigDecimal());
			this.setSpecs(stream.readString());
			this.setUnitCost(stream.readBigDecimal());
			this.setShelfLifeDays(stream.readBigDecimal());
			this.setShelfLifeBasis(stream.readString());
			this.setExpired(stream.readBigDecimal());
			this.setMonth1(stream.readBigDecimal());
			this.setMonth2(stream.readBigDecimal());
			this.setMonth3(stream.readBigDecimal());
			this.setMonth4(stream.readBigDecimal());
			this.setMonth5(stream.readBigDecimal());
			this.setMonth6(stream.readBigDecimal());
			this.setOperatingEntityName(stream.readString());
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
			stream.writeString(this.getBin());
			stream.writeString(this.getClientPartNos());
			stream.writeString(this.getCurrencyId());
			stream.writeDate(new java.sql.Date(this.getExpireDate().getTime()));
			stream.writeString(this.getHub());
			stream.writeString(this.getHubName());
			stream.writeString(this.getInventoryGroup());
			stream.writeString(this.getInventoryGroupName());
			stream.writeString(this.getPartShortName());
			stream.writeBigDecimal(this.getItemId());
			stream.writeString(this.getLotStatus());
			stream.writeBigDecimal(this.getLotStatusAge());
			stream.writeString(this.getOpsCompanyId());
			stream.writeString(this.getOpsEntityId());
			stream.writeBigDecimal(this.getQuantity());
			stream.writeBigDecimal(this.getReceiptId());
			stream.writeString(this.getSpecs());
			stream.writeBigDecimal(this.getUnitCost());
			stream.writeBigDecimal(this.getShelfLifeDays());
			stream.writeString(this.getShelfLifeBasis());
			stream.writeBigDecimal(this.getExpired());
			stream.writeBigDecimal(this.getMonth1());
			stream.writeBigDecimal(this.getMonth2());
			stream.writeBigDecimal(this.getMonth3());
			stream.writeBigDecimal(this.getMonth4());
			stream.writeBigDecimal(this.getMonth5());
			stream.writeBigDecimal(this.getMonth6());
			stream.writeString(this.getOperatingEntityName());
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