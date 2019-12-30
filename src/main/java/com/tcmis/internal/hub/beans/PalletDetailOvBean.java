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
 * CLASSNAME: PalletDetailOvBean <br>
 * @version: 1.0, Feb 13, 2008 <br>
 *****************************************************************************/


public class PalletDetailOvBean extends BaseDataBean implements SQLData {

	private String sourceHub;
	private String inventoryGroup;
	private BigDecimal packingGroupId;
	private BigDecimal prNumber;
	private String lineItem;
	private String catPartNo;
	private String partDescription;
	private BigDecimal boxLabelId;
	private BigDecimal quantity;
	private BigDecimal uosQuantity;
	private String unitOfSale;
	private String boxId;
	private String palletId;
	private String companyId;
	private String facilityId;
	private String application;
	private String shipToLocationId;
	private Collection boxAltPallet;
	private Array boxAltPalletArray;
	private String sqlType = "PALLET_DETAIL_OV";


	//constructor
	public PalletDetailOvBean() {
	}

	//setters
	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public void setPackingGroupId (BigDecimal packingGroupId) {
		this.packingGroupId = packingGroupId;
	}

	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setBoxLabelId(BigDecimal boxLabelId) {
		this.boxLabelId = boxLabelId;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setUosQuantity(BigDecimal uosQuantity) {
		this.uosQuantity = uosQuantity;
	}
	public void setUnitOfSale(String unitOfSale) {
		this.unitOfSale = unitOfSale;
	}
	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}
	public void setPalletId(String palletId) {
		this.palletId = palletId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setShipToLocationId(String shipToLocationId) {
		this.shipToLocationId = shipToLocationId;
	}
	public void setBoxAltPallet(Collection coll) {
		this.boxAltPallet = coll;
	}
	public void setBoxAltPalletArray(Array boxAltPalletArray) {
		List list = null;
		try {
			list = Arrays.asList( (Object[]) boxAltPalletArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setBoxAltPallet(list);
	}


	//getters
	public String getSourceHub() {
		return sourceHub;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public BigDecimal getPackingGroupId() {
		return packingGroupId;
	}

	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public BigDecimal getBoxLabelId() {
		return boxLabelId;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public BigDecimal getUosQuantity() {
		return uosQuantity;
	}
	public String getUnitOfSale() {
		return unitOfSale;
	}
	public String getBoxId() {
		return boxId;
	}
	public String getPalletId() {
		return palletId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public String getShipToLocationId() {
		return shipToLocationId;
	}
	public Collection getBoxAltPallet() {
		return this.boxAltPallet;
	}
	public Array getBoxAltPalletArray() {
		return boxAltPalletArray;
	}

	public String getSQLTypeName() {
		return this.sqlType;
	}
	public void readSQL(SQLInput stream, String type) throws SQLException {
		sqlType = type;
		try {
			this.setSourceHub(stream.readString());
			this.setInventoryGroup(stream.readString());
			this.setPackingGroupId(stream.readBigDecimal());
			this.setPrNumber(stream.readBigDecimal());
			this.setLineItem(stream.readString());
			this.setCatPartNo(stream.readString());
			this.setPartDescription(stream.readString());
			this.setBoxLabelId(stream.readBigDecimal());
			this.setQuantity(stream.readBigDecimal());
			this.setUosQuantity(stream.readBigDecimal());
			this.setUnitOfSale(stream.readString());
			this.setBoxId(stream.readString());
			this.setPalletId(stream.readString());
			this.setCompanyId(stream.readString());
			this.setFacilityId(stream.readString());
			this.setApplication(stream.readString());
			this.setShipToLocationId(stream.readString());
			this.setBoxAltPalletArray(stream.readArray());
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
			stream.writeString(this.getInventoryGroup());
			stream.writeBigDecimal(this.getPackingGroupId());
			stream.writeBigDecimal(this.getPrNumber());
			stream.writeString(this.getLineItem());
			stream.writeString(this.getCatPartNo());
			stream.writeString(this.getPartDescription());
			stream.writeBigDecimal(this.getBoxLabelId());
			stream.writeBigDecimal(this.getQuantity());
			stream.writeBigDecimal(this.getUosQuantity());
			stream.writeString(this.getUnitOfSale());
			stream.writeString(this.getBoxId());
			stream.writeString(this.getPalletId());
			stream.writeString(this.getCompanyId());
			stream.writeString(this.getFacilityId());
			stream.writeString(this.getApplication());
			stream.writeString(this.getShipToLocationId());
			stream.writeArray(this.getBoxAltPalletArray());
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