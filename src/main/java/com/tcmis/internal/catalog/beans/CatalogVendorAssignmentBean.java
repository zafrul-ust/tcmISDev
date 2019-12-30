package com.tcmis.internal.catalog.beans;

import static com.tcmis.common.web.IHaasConstants.CST_OPTION_CREATE_EXCEL;
import static com.tcmis.common.web.IHaasConstants.CST_OPTION_CREATE_XSL;
import static com.tcmis.common.web.IHaasConstants.CST_OPTION_SEARCH;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class CatalogVendorAssignmentBean
    extends BaseDataBean {

  private BigDecimal catalogVendorAssignmentId;
  private String	supplier;
  private String	supplierName;
  private BigDecimal	itemId;
  private String	itemDesc;
  private BigDecimal	addlIngredItemId;
  private String localeCode;
  private BigDecimal	sourcingItemId;
  private BigDecimal	percentage;
  private String status;// A or I??
  private String status1;// to resolve input  bug
  private Date	startDate;
  private Date	stopDate;
  private BigDecimal	addlIngredBaseQty;
  private BigDecimal	addlIngredBundleQty;
  private BigDecimal	unitPrice;
  private BigDecimal	addlIngredUnitPrice;
  private String	currencyId;
  private String	currencyDescription;
  private BigDecimal catalogQueueItemId;
  private BigDecimal sourcingUnitPrice;
  private String	opsEntityId;
  private String	operatingEntityName;
  private String	task;
  private String	taskDescription;
  private String billedByComponent;
  private String showInactive;

  //constructor
  public CatalogVendorAssignmentBean() {
  }

public BigDecimal getCatalogVendorAssignmentId() {
	return catalogVendorAssignmentId;
}

public void setCatalogVendorAssignmentId(BigDecimal catalogVendorAssignmentId) {
	this.catalogVendorAssignmentId = catalogVendorAssignmentId;
}

public String getSupplier() {
	return supplier;
}

public void setSupplier(String supplier) {
	this.supplier = supplier;
}

public String getSupplierName() {
	return supplierName;
}

public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}

public BigDecimal getItemId() {
	return itemId;
}

public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
}

public String getItemDesc() {
	return itemDesc;
}

public void setItemDesc(String itemDesc) {
	this.itemDesc = itemDesc;
}

public BigDecimal getAddlIngredItemId() {
	return addlIngredItemId;
}

public void setAddlIngredItemId(BigDecimal addlIngredItemId) {
	this.addlIngredItemId = addlIngredItemId;
}

public String getLocaleCode() {
	return localeCode;
}

public void setLocaleCode(String localeCode) {
	this.localeCode = localeCode;
}

public BigDecimal getSourcingItemId() {
	return sourcingItemId;
}

public void setSourcingItemId(BigDecimal sourcingItemId) {
	this.sourcingItemId = sourcingItemId;
}

public BigDecimal getPercentage() {
	return percentage;
}

public void setPercentage(BigDecimal percentage) {
	this.percentage = percentage;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public Date getStartDate() {
	return startDate;
}

public void setStartDate(Date startDate) {
	this.startDate = startDate;
}

public Date getStopDate() {
	return stopDate;
}

public void setStopDate(Date stopDate) {
	this.stopDate = stopDate;
}

public BigDecimal getAddlIngredBaseQty() {
	return addlIngredBaseQty;
}

public void setAddlIngredBaseQty(BigDecimal addlIngredBaseQty) {
	this.addlIngredBaseQty = addlIngredBaseQty;
}

public BigDecimal getAddlIngredBundleQty() {
	return addlIngredBundleQty;
}

public void setAddlIngredBundleQty(BigDecimal addlIngredBundleQty) {
	this.addlIngredBundleQty = addlIngredBundleQty;
}

public BigDecimal getUnitPrice() {
	return unitPrice;
}

public void setUnitPrice(BigDecimal unitPrice) {
	this.unitPrice = unitPrice;
}

public BigDecimal getAddlIngredUnitPrice() {
	return addlIngredUnitPrice;
}

public void setAddlIngredUnitPrice(BigDecimal addlIngredUnitPrice) {
	this.addlIngredUnitPrice = addlIngredUnitPrice;
}

public String getCurrencyId() {
	return currencyId;
}

public void setCurrencyId(String currencyId) {
	this.currencyId = currencyId;
}

public String getCurrencyDescription() {
	return currencyDescription;
}

public void setCurrencyDescription(String currencyDescription) {
	this.currencyDescription = currencyDescription;
}

public BigDecimal getCatalogQueueItemId() {
	return catalogQueueItemId;
}

public void setCatalogQueueItemId(BigDecimal catalogQueueItemId) {
	this.catalogQueueItemId = catalogQueueItemId;
}

public BigDecimal getSourcingUnitPrice() {
	return sourcingUnitPrice;
}

public void setSourcingUnitPrice(BigDecimal sourcingUnitPrice) {
	this.sourcingUnitPrice = sourcingUnitPrice;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}

public String getOperatingEntityName() {
	return operatingEntityName;
}

public void setOperatingEntityName(String operatingEntityName) {
	this.operatingEntityName = operatingEntityName;
}

public String getTask() {
	return task;
}

public void setTask(String task) {
	this.task = task;
}

public String getTaskDescription() {
	return taskDescription;
}

public void setTaskDescription(String taskDescription) {
	this.taskDescription = taskDescription;
}

public String getStatus1() {
	return status1;
}

public void setStatus1(String status1) {
	this.status1 = status1;
}

public String getBilledByComponent() {
	return billedByComponent;
}

public void setBilledByComponent(String billedByComponent) {
	this.billedByComponent = billedByComponent;
}

	public String getShowInactive() {
		return showInactive;
	}

	public void setShowInactive(String showInactive) {
		this.showInactive = showInactive;
	}

	public boolean showInactiveChecked() {
		return "Y".equals(showInactive);

	}

}