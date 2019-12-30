package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.util.*;

import com.tcmis.common.framework.*;

/******************************************************************************
 * CLASSNAME: HubCabinetViewBean <br>
 * @version: 1.0, Jul 22, 2004 <br>
 *****************************************************************************/

public class TransferRequestInputBean
    extends BaseDataBean {
  
  private String opsEntityId;;	
  private String hub;	
  private String inventoryGroup;
  private String searchField;
  private String searchMode;
  private String searchArgument;
  private String destInventoryGroup;
  private String includeNoInventory;
 

//constructor
  public TransferRequestInputBean() {
  }

  public void setInventoryGroup(String inventoryGroup) {
    this.inventoryGroup = inventoryGroup;
  }

  public String getInventoryGroup() {
    return inventoryGroup;
  }

  
  public String getHub() {
	return hub;
}

public void setHub(String hub) {
	this.hub = hub;
}

public String getOpsEntityId() {
	return opsEntityId;
}

public void setOpsEntityId(String opsEntityId) {
	this.opsEntityId = opsEntityId;
}


public String getDestInventoryGroup() {
	return destInventoryGroup;
}

public void setDestInventoryGroup(String destInventoryGroup) {
	this.destInventoryGroup = destInventoryGroup;
}


public String getIncludeNoInventory() {
	return includeNoInventory;
}


public void setIncludeNoInventory(String includeNoInventory) {
	this.includeNoInventory = includeNoInventory;
}


public String getSearchField() {
	return searchField;
}

public void setSearchField(String searchField) {
	this.searchField = searchField;
}

public String getSearchMode() {
	return searchMode;
}

public void setSearchMode(String searchMode) {
	this.searchMode = searchMode;
}

public String getSearchArgument() {
	return searchArgument;
}

public void setSearchArgument(String searchArgument) {
	this.searchArgument = searchArgument;
}







}