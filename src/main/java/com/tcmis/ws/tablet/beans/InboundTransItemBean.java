package com.tcmis.ws.tablet.beans;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.StringHandler;

public class InboundTransItemBean extends BaseDataBean {

	private BigDecimal						componentId;
	private BigDecimal						customerRmaId;
	private String							displayPkgStyle;
	private BigDecimal						docNum;
	private String							imageFileName;
	private String							imageUrl;
	private String							itemDescription;
	private BigDecimal						itemId;
	private String							itemStatus;
	private BigDecimal						lineItem;
	private String							manageKitsAsSingleUnit;
	private String							materialDesc;
	private Collection<MsdsRevisionBean>	msdsVersions	= Collections.emptyList();
	private String							mvItem;
	private String							mvType;
	private String							nonIntegerReceiving;
	private String							notes;
	private BigDecimal						numberOfKits;
	private String							packaging;
	private String							polchemIg;
	private String							purchasingUnitOfMeasure;
	private BigDecimal						purchasingUnitsPerItem;
	private boolean							qualityControlItem;
	private BigDecimal						radianPo;
	private String							storageTemp;
	private BigDecimal						transferReceiptId;
	private BigDecimal						transferRequestId;
	private String							transType;

	public BigDecimal getComponentId() {
		return componentId;
	}

	public BigDecimal getCustomerRmaId() {
		return customerRmaId;
	}

	public String getDisplayPkgStyle() {
		return displayPkgStyle;
	}

	public BigDecimal getDocNum() {
		return docNum;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public BigDecimal getLineItem() {
		return lineItem;
	}

	public String getManageKitsAsSingleUnit() {
		return manageKitsAsSingleUnit;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public Collection<MsdsRevisionBean> getMsdsVersions() {
		return msdsVersions;
	}

	public String getMvItem() {
		return mvItem;
	}

	public String getMvType() {
		return mvType;
	}

	public String getNonIntegerReceiving() {
		return nonIntegerReceiving;
	}

	public String getNotes() {
		return notes;
	}

	public BigDecimal getNumberOfKits() {
		return numberOfKits;
	}

	public String getPackaging() {
		return packaging;
	}

	public String getPolchemIg() {
		return polchemIg;
	}

	public String getPurchasingUnitOfMeasure() {
		return purchasingUnitOfMeasure;
	}

	public BigDecimal getPurchasingUnitsPerItem() {
		return purchasingUnitsPerItem;
	}

	public BigDecimal getRadianPo() {
		return radianPo;
	}

	public String getStorageTemp() {
		return storageTemp;
	}

	public BigDecimal getTransferReceiptId() {
		return transferReceiptId;
	}

	public BigDecimal getTransferRequestId() {
		return transferRequestId;
	}

	public String getTransType() {
		return transType;
	}

	public boolean isQualityControlItem() {
		return qualityControlItem;
	}

	public void setComponentId(BigDecimal componentId) {
		this.componentId = componentId;
	}

	public void setCustomerRmaId(BigDecimal customerRmaId) {
		this.customerRmaId = customerRmaId;
	}

	public void setDisplayPkgStyle(String displayPkgStyle) {
		this.displayPkgStyle = displayPkgStyle;
	}

	public void setDocNum(BigDecimal docNum) {
		this.docNum = docNum;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setImageUrl(String imageUrl) {
		if (StringHandler.isBlankString(imageUrl) && !imageUrl.contains("/")) {
			this.imageUrl = "/item_images/" + imageUrl;
		}
		this.imageUrl = imageUrl;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public void setLineItem(BigDecimal lineItem) {
		this.lineItem = lineItem;
	}

	public void setManageKitsAsSingleUnit(String manageKitsAsSingleUnit) {
		this.manageKitsAsSingleUnit = manageKitsAsSingleUnit;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public void setMsdsVersions(Collection<MsdsRevisionBean> msdsVersions) {
		this.msdsVersions = msdsVersions;
	}

	public void setMvItem(String mvItem) {
		this.mvItem = mvItem;
	}

	public void setMvType(String mvType) {
		this.mvType = mvType;
	}

	public void setNonIntegerReceiving(String nonIntegerReceiving) {
		this.nonIntegerReceiving = nonIntegerReceiving;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setNumberOfKits(BigDecimal numberOfKits) {
		this.numberOfKits = numberOfKits;
	}

	public void setPackaging(String packaging) {
		this.packaging = packaging;
	}

	public void setPolchemIg(String polchemIg) {
		this.polchemIg = polchemIg;
	}

	public void setPurchasingUnitOfMeasure(String purchasingUnitOfMeasure) {
		this.purchasingUnitOfMeasure = purchasingUnitOfMeasure;
	}

	public void setPurchasingUnitsPerItem(BigDecimal purchasingUnitsPerItem) {
		this.purchasingUnitsPerItem = purchasingUnitsPerItem;
	}

	public void setQualityControlItem(boolean qualityControlItem) {
		this.qualityControlItem = qualityControlItem;
	}

	public void setRadianPo(BigDecimal radianPo) {
		this.radianPo = radianPo;
	}

	public void setStorageTemp(String storageTemp) {
		this.storageTemp = storageTemp;
	}

	public void setTransferReceiptId(BigDecimal transferReceiptId) {
		this.transferReceiptId = transferReceiptId;
	}

	public void setTransferRequestId(BigDecimal transferRequestId) {
		this.transferRequestId = transferRequestId;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

}
