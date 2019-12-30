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

public class CatalogQueueItemBean
    extends BaseDataBean { 
//TABLE TCM_OPS.CATALOG_QUEUE_ITEM
//	 ITEM_ID                NUMBER                 NOT NULL,
//	  ADDL_INGRED_ITEM_ID    NUMBER,
//	  SOURCING_ITEM_ID       NUMBER,
//	  LOCALE_CODE            VARCHAR2(12 BYTE)      NOT NULL,
//	  CATALOG_QUEUE_ITEM_ID  INTEGER                NOT NULL,
//	  TASK                   VARCHAR2(30 BYTE)

	
  private BigDecimal	itemId;
  private BigDecimal	addlIngredItemId;
  private BigDecimal	sourcingItemId;
  private String localeCode;
  private String localeDisplay;
  private BigDecimal catalogQueueItemId;
  private String task;
  private String assignedToSupplier;

//constructor
  public CatalogQueueItemBean() {
  }

public BigDecimal getItemId() {
	return itemId;
}

public void setItemId(BigDecimal itemId) {
	this.itemId = itemId;
}

public BigDecimal getAddlIngredItemId() {
	return addlIngredItemId;
}

public void setAddlIngredItemId(BigDecimal addlIngredItemId) {
	this.addlIngredItemId = addlIngredItemId;
}

public BigDecimal getSourcingItemId() {
	return sourcingItemId;
}

public void setSourcingItemId(BigDecimal sourcingItemId) {
	this.sourcingItemId = sourcingItemId;
}

public String getLocaleDisplay() {
	return localeDisplay;
}

public void setLocaleDisplay(String localeDisplay) {
	this.localeDisplay = localeDisplay;
}

public String getLocaleCode() {
	return localeCode;
}

public void setLocaleCode(String localeCode) {
	this.localeCode = localeCode;
}

public BigDecimal getCatalogQueueItemId() {
	return catalogQueueItemId;
}

public void setCatalogQueueItemId(BigDecimal catalogQueueItemId) {
	this.catalogQueueItemId = catalogQueueItemId;
}

public String getTask() {
	return task;
}

public void setTask(String task) {
	this.task = task;
}

public String getAssignedToSupplier() {
	return assignedToSupplier;
}

public void setAssignedToSupplier(String assignedToSupplier) {
	this.assignedToSupplier = assignedToSupplier;
}
  
  

}