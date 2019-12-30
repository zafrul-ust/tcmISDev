package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: BinLabelsInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class InventoryLevelManagementInputBean extends BaseDataBean {

        private String branchPlant;
	private String inventoryGroup;
        private String searchBy;
        private String searchType;
        private String searchText;
        private String showItemIssued;
        private BigDecimal itemIssuedDay;
        private String submitSearch;
        private String createExcel;
        private String showOnHandItem;


	//constructor
	public InventoryLevelManagementInputBean() {
	}

	//setters
        public void setBranchPlant(String branchPlant) {
          this.branchPlant = branchPlant;
        }
	public void setInventoryGroup(String inventoryGroup) {
		this.inventoryGroup = inventoryGroup;
	}
        public void setSearchBy(String searchBy) {
                this.searchBy = searchBy;
        }
        public void setSearchType(String searchType) {
                this.searchType = searchType;
        }
        public void setSearchText(String searchText) {
                this.searchText = searchText;
        }
        public void setShowItemIssued(String showItemIssued) {
          this.showItemIssued = showItemIssued;
        }
        public void setItemIssuedDay(BigDecimal itemIssuedDay) {
          this.itemIssuedDay = itemIssuedDay;
        }
        public void setSubmitSearch(String submitSearch) {
                this.submitSearch = submitSearch;
        }
        public void setCreateExcel(String createExcel) {
          this.createExcel = createExcel;
        }
        public void setShowOnHandItem(String showOnHandItem) {
          this.showOnHandItem = showOnHandItem;
        }

	//getters
        public String getBranchPlant() {
          return branchPlant;
        }
	public String getInventoryGroup() {
		return inventoryGroup;
	}
        public String getSearchBy() {
                return searchBy;
        }
        public String getSearchType() {
                return searchType;
        }
        public String getSearchText() {
                return searchText;
        }
        public String getShowItemIssued() {
                return showItemIssued;
        }
        public BigDecimal getItemIssuedDay() {
                return itemIssuedDay;
        }
        public String getSubmitSearch() {
                return submitSearch;
        }
        public String getCreateExcel() {
                return createExcel;
        }
        public String getShowOnHandItem() {
                return showOnHandItem;
        }

}