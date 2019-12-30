package com.tcmis.internal.distribution.beans;

import java.util.*;
import java.math.*;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: AllocationDetailBean <br>
 * @version: 1.0, Sep 7, 2009 <br>
 *****************************************************************************/


public class AllocationDetailBean extends BaseDataBean {

	private String specList;
	private String shelfLife;
	private BigDecimal regionAvailable;
	private BigDecimal globalAvailable;
	private BigDecimal regionOnHand;
	private BigDecimal globalOnHand;
	private BigDecimal unitPrice;
	private String stocked;
	private String source;
	private String reference;
	private BigDecimal unitCost;
	private String mfgLot;
	private BigDecimal quantity;
	private Date expireDate;
	private BigDecimal remainingShelfLife;
	private String inventoryGroup;
	private Date promisedDate;
	private BigDecimal prNumber;
	private String lineItem;
	private String docType;
	private BigDecimal docNum;
	private BigDecimal docLine;
	private BigDecimal docQuantityPerItem;

	private BigDecimal allocation;	
	private BigDecimal priceoverride;
	private BigDecimal shelfLifeRequired;
	
	private String opsRegion;
	private String opsRegionName;
	private String inventoryGroupName;
	private String hasPriceBreak;
	
	private String specFullList;
	private String specDetailList;
	private String specMatch;
	private String needType;
	
	private Date dateOfReceipt;
// new column
	private String itemType;
	private BigDecimal unitsPerItem;
	private BigDecimal sourceAmount;
	private String lotStatus;
	
	private Date sellByDate;
	private String definedShelfLifeItem;
	private String poIsDropship;
	private String meetSpec;
	private String meetShelfLife;
	private String qualityHold;
	
	
	public String getQualityHold() {
		return qualityHold;
	}

	public void setQualityHold(String qualityHold) {
		this.qualityHold = qualityHold;
	}

	public String getMeetSpec() {
		return meetSpec;
	}

	public void setMeetSpec(String meetSpec) {
		this.meetSpec = meetSpec;
	}

	public String getMeetShelfLife() {
		return meetShelfLife;
	}

	public void setMeetShelfLife(String meetShelfLife) {
		this.meetShelfLife = meetShelfLife;
	}
	
	public String getNeedType() {
		return needType;
	}

	public void setNeedType(String needType) {
		this.needType = needType;
	}

	public String getHasPriceBreak() {
		return hasPriceBreak;
	}

	public void setHasPriceBreak(String hasPriceBreak) {
		this.hasPriceBreak = hasPriceBreak;
	}

	public void setOpsRegionName(String opsRegionName) {
		this.opsRegionName = opsRegionName;
	}

	//constructor
	public AllocationDetailBean() {
	}

	//setters
	public void setSpecList(String specList) {
		this.specList = specList;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public void setRegionAvailable(BigDecimal regionAvailable) {
		this.regionAvailable = regionAvailable;
	}
	public void setGlobalAvailable(BigDecimal globalAvailable) {
		this.globalAvailable = globalAvailable;
	}
	public void setRegionOnHand(BigDecimal regionOnHand) {
		this.regionOnHand = regionOnHand;
	}
	public void setGlobalOnHand(BigDecimal globalOnHand) {
		this.globalOnHand = globalOnHand;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public void setStocked(String stocked) {
		this.stocked = stocked;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}
	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public void setRemainingShelfLife(BigDecimal remainingShelfLife) {
		this.remainingShelfLife = remainingShelfLife;
	}
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}
	public void setPrNumber(BigDecimal prNumber) {
		this.prNumber = prNumber;
	}
	public void setLineItem(String lineItem) {
		this.lineItem = lineItem;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}
	public void setDocLine(BigDecimal docLine) {
		this.docLine = docLine;
	}
	public void setDocQuantityPerItem(BigDecimal docQuantityPerItem) {
		this.docQuantityPerItem = docQuantityPerItem;
	}
	public void setPoIsDropship(String poIsDropship) {
		this.poIsDropship = poIsDropship;
	}
	//getters
	public String getPoIsDropship() {
		return poIsDropship;
	}
	public String getSpecList() {
		return specList;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public BigDecimal getRegionAvailable() {
		return regionAvailable;
	}
	public BigDecimal getGlobalAvailable() {
		return globalAvailable;
	}
	public BigDecimal getRegionOnHand() {
		return regionOnHand;
	}
	public BigDecimal getGlobalOnHand() {
		return globalOnHand;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public String getStocked() {
		return stocked;
	}
	public String getSource() {
		return source;
	}
	public String getReference() {
		return reference;
	}
	public BigDecimal getUnitCost() {
		return unitCost;
	}
	public String getMfgLot() {
		return mfgLot;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public BigDecimal getRemainingShelfLife() {
		return remainingShelfLife;
	}
	public String getInventoryGroup() {
		return inventoryGroup;
	}
	public Date getPromisedDate() {
		return promisedDate;
	}
	public BigDecimal getPrNumber() {
		return prNumber;
	}
	public String getLineItem() {
		return lineItem;
	}
	public String getDocType() {
		return docType;
	}
	public BigDecimal getDocNum() {
		return docNum;
	}
	public BigDecimal getDocLine() {
		return docLine;
	}
	public BigDecimal getDocQuantityPerItem() {
		return docQuantityPerItem;
	}
	public BigDecimal getAllocation() {
		return allocation;
	}
	public void setAllocation(BigDecimal allocation) {
		this.allocation = allocation;
	}
	public BigDecimal getPriceoverride() {
		return priceoverride;
	}
	public void setPriceoverride(BigDecimal priceoverride) {
		this.priceoverride = priceoverride;
	}
	public BigDecimal getShelfLifeRequired() {
		return shelfLifeRequired;
	}
	public void setShelfLifeRequired(BigDecimal shelfLifeRequired) {
		this.shelfLifeRequired = shelfLifeRequired;
	}
	public String getOpsRegion() {
		return opsRegion;
	}
	public void setOpsRegion(String opsRegion) {
		this.opsRegion = opsRegion;
	}
	public String getOpsRegionName() {
		return opsRegionName;
	}
	public String getInventoryGroupName() {
		return inventoryGroupName;
	}
	public void setInventoryGroupName(String inventoryGroupName) {
		this.inventoryGroupName = inventoryGroupName;
	}

	public String getSpecDetailList() {
		return specDetailList;
	}

	public void setSpecDetailList(String specDetailList) {
		this.specDetailList = specDetailList;
	}

	public String getSpecFullList() {
		return specFullList;
	}

	public void setSpecFullList(String specFullList) {
		this.specFullList = specFullList;
	}

	public String getSpecMatch() {
		return specMatch;
	}

	public void setSpecMatch(String specMatch) {
		this.specMatch = specMatch;
	}
	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public BigDecimal getUnitsPerItem() {
		return unitsPerItem;
	}

	public void setUnitsPerItem(BigDecimal unitsPerItem) {
		this.unitsPerItem = unitsPerItem;
	}

	public BigDecimal getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(BigDecimal sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getDefinedShelfLifeItem() {
		return definedShelfLifeItem;
	}

	public void setDefinedShelfLifeItem(String definedShelfLifeItem) {
		this.definedShelfLifeItem = definedShelfLifeItem;
	}

	public Date getSellByDate() {
		return sellByDate;
	}

	public void setSellByDate(Date sellByDate) {
		this.sellByDate = sellByDate;
	}

}