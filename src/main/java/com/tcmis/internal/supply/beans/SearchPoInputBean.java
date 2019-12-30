/*
 * SearchPoInputBean.java
 *
 * Created on September 22, 2006, 11:26 AM
 */

package com.tcmis.internal.supply.beans;

import java.util.Date;
import java.math.BigDecimal;

import com.tcmis.common.framework.BaseDataBean;


/******************************************************************************
 * CLASSNAME: SearchPoInputBean <br>
 * @version: 1.0, Sep 22, 2006 <br>
 *****************************************************************************/


public class SearchPoInputBean extends BaseDataBean {

        private String[] branchPlant;
	    private String inventoryGroup;
	    private BigDecimal buyer;
        private String searchField;
        private String searchMode;
        private String searchString;
        private String searchField2;
        private String searchMode2;
        private String searchString2;
	    private String supplierId;
	    private String supplierName;
        private String openOrders;
        private String unconfirmedOrders;
        private String brokenPrLink;
        private String submitSearch;
        private Date promisedFromDate;
        private Date promisedToDate;
        private Date confirmedFromDate;
        private Date confirmedToDate; 
        private String supplyPath;
        
        
		public String[] getBranchPlant() {
			return branchPlant;
		}
		public void setBranchPlant(String[] branchPlant) {
			this.branchPlant = branchPlant;
		}
		public String getBrokenPrLink() {
			return brokenPrLink;
		}
		public void setBrokenPrLink(String brokenPrLink) {
			this.brokenPrLink = brokenPrLink;
		}
		public BigDecimal getBuyer() {
			return buyer;
		}
		public void setBuyer(BigDecimal buyer) {
			this.buyer = buyer;
		}
		public Date getConfirmedFromDate() {
			return confirmedFromDate;
		}
		public void setConfirmedFromDate(Date confirmedFromDate) {
			this.confirmedFromDate = confirmedFromDate;
		}
		public Date getConfirmedToDate() {
			return confirmedToDate;
		}
		public void setConfirmedToDate(Date confirmedToDate) {
			this.confirmedToDate = confirmedToDate;
		}
		public String getInventoryGroup() {
			return inventoryGroup;
		}
		public void setInventoryGroup(String inventoryGroup) {
			this.inventoryGroup = inventoryGroup;
		}
		public String getOpenOrders() {
			return openOrders;
		}
		public void setOpenOrders(String openOrders) {
			this.openOrders = openOrders;
		}
		public Date getPromisedFromDate() {
			return promisedFromDate;
		}
		public void setPromisedFromDate(Date promisedFromDate) {
			this.promisedFromDate = promisedFromDate;
		}
		public Date getPromisedToDate() {
			return promisedToDate;
		}
		public void setPromisedToDate(Date promisedToDate) {
			this.promisedToDate = promisedToDate;
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
		public String getSearchString() {
			return searchString;
		}
		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}
		public String getSubmitSearch() {
			return submitSearch;
		}
		public void setSubmitSearch(String submitSearch) {
			this.submitSearch = submitSearch;
		}
		public String getSupplierId() {
			return supplierId;
		}
		public void setSupplierId(String supplierId) {
			this.supplierId = supplierId;
		}
		public String getSupplierName() {
			return supplierName;
		}
		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}
		public String getUnconfirmedOrders() {
			return unconfirmedOrders;
		}
		public void setUnconfirmedOrders(String unconfirmedOrders) {
			this.unconfirmedOrders = unconfirmedOrders;
		}
		public String getSearchField2() {
			return searchField2;
		}
		public void setSearchField2(String searchField2) {
			this.searchField2 = searchField2;
		}
		public String getSearchMode2() {
			return searchMode2;
		}
		public void setSearchMode2(String searchMode2) {
			this.searchMode2 = searchMode2;
		}
		public String getSearchString2() {
			return searchString2;
		}
		public void setSearchString2(String searchString2) {
			this.searchString2 = searchString2;
		}
		public String getSupplyPath() {
			return supplyPath;
		}
		public void setSupplyPath(String supplyPath) {
			this.supplyPath = supplyPath;
		}
              
}