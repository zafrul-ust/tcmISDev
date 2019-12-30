package com.tcmis.client.common.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: PointOfSaleInventoryViewBean <br>
 * @version: 1.0, Jun 5, 2009 <br>
 *****************************************************************************/


public class PointOfSaleInventoryViewBean extends BaseDataBean  {

	private BigDecimal prNumber;
	private String lineItem;
	private String catalogId;
	private String catPartNo;
	private BigDecimal partGroupNo;
	private String inventoryGroup;
	private String facilityId;
	private String application;
	private BigDecimal openMrQty;
	private BigDecimal mrQuantity;
	private Date releaseDate;
	private BigDecimal catalogPrice;
	private String requestedDispensedSizeUnit;
	private String partDescription;
	private String packaging;
	private String itemType;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private BigDecimal quantityOnHand;
	private BigDecimal quantityAllocated;
	private String hub;
	private String mfgLot;
	private String bin;
	private Date expireDate;
	private String lotStatus;
	
	private String item;
	private BigDecimal sumOfQuantityPicked;  
	private String clickBtn;


	//constructor
	public PointOfSaleInventoryViewBean() {
	}

	//setters
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(BigDecimal partGroupNo) {
		this.partGroupNo = partGroupNo;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setOpenMrQty(BigDecimal openMrQty) {
		this.openMrQty = openMrQty;
	}
	public void setMrQuantity(BigDecimal mrQuantity) {
		this.mrQuantity = mrQuantity;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public void setCatalogPrice(BigDecimal catalogPrice) {
		this.catalogPrice = catalogPrice;
	}
	public void setRequestedDispensedSizeUnit(String requestedDispensedSizeUnit) {
		this.requestedDispensedSizeUnit = requestedDispensedSizeUnit;
	}
	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}
	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}
	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}
	public void setQuantityOnHand(BigDecimal quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}
	public void setQuantityAllocated(BigDecimal quantityAllocated) {
		this.quantityAllocated = quantityAllocated;
	}
	public void setHub(String hub) {
		this.hub = hub;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}


	//getters
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public String getCatPartNo() {
		return catPartNo;
	}
	public BigDecimal getPartGroupNo() {
		return partGroupNo;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public String getApplication() {
		return application;
	}
	public BigDecimal getOpenMrQty() {
		return openMrQty;
	}
	public BigDecimal getMrQuantity() {
		return mrQuantity;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public BigDecimal getCatalogPrice() {
		return catalogPrice;
	}
	public String getRequestedDispensedSizeUnit() {
		return requestedDispensedSizeUnit;
	}
	public String getPartDescription() {
		return partDescription;
	}
	public String getPackaging() {
		return packaging;
	}
	public String getItemType() {
		return itemType;
	}
	public BigDecimal getItemId() {
		return itemId;
	}
	public BigDecimal getReceiptId() {
		return receiptId;
	}
	public BigDecimal getQuantityOnHand() {
		return quantityOnHand;
	}
	public BigDecimal getQuantityAllocated() {
		return quantityAllocated;
	}
	public String getHub() {
		return hub;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public String getBin() {
		return bin;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public String getLotStatus() {
		return lotStatus;
	}

	public String getClickBtn() {
		return clickBtn;
	}

	public void setClickBtn(String clickBtn) {
		this.clickBtn = clickBtn;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public BigDecimal getSumOfQuantityPicked() {
		return sumOfQuantityPicked;
	}

	public void setSumOfQuantityPicked(BigDecimal sumOfQuantityPicked) {
		this.sumOfQuantityPicked = sumOfQuantityPicked;
	}

	
}