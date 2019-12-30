package com.tcmis.internal.catalog.beans;

import com.tcmis.common.framework.BaseDataBean;
import radian.tcmis.common.util.StringHandler;

import java.math.BigDecimal;

/******************************************************************************
 * CLASSNAME: ItemStorageBean <br>
 * @version: 1.0, Oct 9, 2019 <br>
 *****************************************************************************/


public class ItemStorageBean extends BaseDataBean {
	private String userAction;
	private String opsEntityId;
	private String selectedOpsEntityId;
	private String sourceHub;
	private String selectedHub;
	private String inventoryGroup;
	private String selectedInventoryGroup;
	private String searchWhat;
	private String searchType;
	private String searchText;
	private String inventoryGroupName;
	private String itemDesc;
	private String packaging;
	private BigDecimal itemId;
	private BigDecimal materialId;
	private BigDecimal partId;
	private String materialDesc;

	//constructor
	public ItemStorageBean() {
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getOpsEntityId() {
		return opsEntityId;
	}

	public void setOpsEntityId(String opsEntityId) {
		this.opsEntityId = opsEntityId;
	}

	public String getSelectedOpsEntityId() {
		return selectedOpsEntityId;
	}

	public void setSelectedOpsEntityId(String selectedOpsEntityId) {
		this.selectedOpsEntityId = selectedOpsEntityId;
	}

	public String getSourceHub() {
		return sourceHub;
	}

	public void setSourceHub(String sourceHub) {
		this.sourceHub = sourceHub;
	}

	public String getSelectedHub() {
		return selectedHub;
	}

	public void setSelectedHub(String selectedHub) {
		this.selectedHub = selectedHub;
	}

	public String getInventoryGroup() {
		return inventoryGroup;
	}

	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}

	public String getSelectedInventoryGroup() {
		return selectedInventoryGroup;
	}

	public void setSelectedInventoryGroup(String selectedInventoryGroup) {
		this.selectedInventoryGroup = selectedInventoryGroup;
	}

	public String getSearchWhat() {
		return searchWhat;
	}

	public void setSearchWhat(String searchWhat) {
		this.searchWhat = searchWhat;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public boolean isSearch() {
		return "search".equals(userAction);
	}

	public boolean isCreateExcel() {
		return "createExcel".equals(userAction);
	}

    public String getInventoryGroupName() {
        return inventoryGroupName;
    }

    public void setInventoryGroupName(String inventoryGroupName) {
        this.inventoryGroupName = inventoryGroupName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getMaterialId() {
		return materialId;
	}

	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}

	public BigDecimal getPartId() {
		return partId;
	}

	public void setPartId(BigDecimal partId) {
		this.partId = partId;
	}

	public boolean hasSearchText() {
		return !StringHandler.isBlankString(searchText);
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
}