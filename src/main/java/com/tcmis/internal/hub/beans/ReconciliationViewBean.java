package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: TempShipmentHistoryViewBean <br>
 * @version: 1.0, Apr 13, 2005 <br>
 *****************************************************************************/

public class ReconciliationViewBean extends BaseDataBean {


	private static final long serialVersionUID = -9177064277117694135L;
	private String hub;
	private BigDecimal itemId;
	private BigDecimal receiptId;
	private BigDecimal radianPo;
	private Date transactionDate;
	private String mfgLot;
	private String bin;
	private String lotStatus;
	private String inventoryGroup;
	private String catPartNo;
	private Date ExpireDate;
	private BigDecimal quantityReceived;
	private BigDecimal quantityConfirmed;
	private BigDecimal quantityUnconfirmed;
	private BigDecimal quantityIssued;
	private BigDecimal onHand;
	private BigDecimal actualQuantity;
	private String packaging;
	private String manufacturer;
	private String itemDesc;
	private String catPartNos;
	private BigDecimal unitCost;
	private String OwnerCompanyId;	
	private BigDecimal oldActualQuantity;

	//constructor
	public ReconciliationViewBean() {
	}

	public BigDecimal getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(BigDecimal actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public String getCatPartNo() {
		return catPartNo;
	}

	public void setCatPartNo(String catPartNo) {
		this.catPartNo = catPartNo;
	}

	public String getCatPartNos() {
		return catPartNos;
	}

	public void setCatPartNos(String catPartNos) {
		this.catPartNos = catPartNos;
	}

	public Date getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(Date expireDate) {
		ExpireDate = expireDate;
	}

	public String getHub() {
		return hub;
	}

	public void setHub(String hub) {
		this.hub = hub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getLotStatus() {
		return lotStatus;
	}

	public void setLotStatus(String lotStatus) {
		this.lotStatus = lotStatus;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getMfgLot() {
		return mfgLot;
	}

	public void setMfgLot(String mfgLot) {
		this.mfgLot = mfgLot;
	}

	public BigDecimal getOnHand() {
		return onHand;
	}

	public void setOnHand(BigDecimal onHand) {
		this.onHand = onHand;
	}

	public String getOwnerCompanyId() {
		return OwnerCompanyId;
	}

	public void setOwnerCompanyId(String ownerCompanyId) {
		OwnerCompanyId = ownerCompanyId;
	}

	public String getPackaging() {
		return packaging;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public BigDecimal getQuantityConfirmed() {
		return quantityConfirmed;
	}

	public void setQuantityConfirmed(BigDecimal quantityConfirmed) {
		this.quantityConfirmed = quantityConfirmed;
	}

	public BigDecimal getQuantityIssued() {
		return quantityIssued;
	}

	public void setQuantityIssued(BigDecimal quantityIssued) {
		this.quantityIssued = quantityIssued;
	}

	public BigDecimal getQuantityReceived() {
		return quantityReceived;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public BigDecimal getQuantityUnconfirmed() {
		return quantityUnconfirmed;
	}

	public void setQuantityUnconfirmed(BigDecimal quantityUnconfirmed) {
		this.quantityUnconfirmed = quantityUnconfirmed;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public BigDecimal getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(BigDecimal receiptId) {
		this.receiptId = receiptId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public void setOldActualQuantity(BigDecimal oldActualQuantity) {
		this.oldActualQuantity = oldActualQuantity;
	}

	public BigDecimal getOldActualQuantity() {
		return oldActualQuantity;
	}

}
