package com.tcmis.client.report.beans;

import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: ReceiptDocumentInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class MaterialTransferHistoryInputBean extends BaseInputBean {

	private String fromApplicationId;
	private String toApplicationId;
	private Date transferredFromDate;
	private Date transferredToDate;
	private String facilityId;

	private String searchBy;
	private String searchType;
	private String searchText;
	private String submitSearch;

	//constructor
	public MaterialTransferHistoryInputBean() {
	}



	public String getFacilityId() {
		return facilityId;
	}



	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}



	public String getFromApplicationId() {
		return fromApplicationId;
	}



	public void setFromApplicationId(String fromApplicationId) {
		this.fromApplicationId = fromApplicationId;
	}



	public String getSearchBy() {
		return searchBy;
	}



	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}



	public String getSearchText() {
		return searchText;
	}



	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}



	public String getSubmitSearch() {
		return submitSearch;
	}



	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}



	public String getToApplicationId() {
		return toApplicationId;
	}



	public void setToApplicationId(String toApplicationId) {
		this.toApplicationId = toApplicationId;
	}



	public Date getTransferredFromDate() {
		return transferredFromDate;
	}



	public void setTransferredFromDate(Date transferredFromDate) {
		this.transferredFromDate = transferredFromDate;
	}



	public Date getTransferredToDate() {
		return transferredToDate;
	}



	public void setTransferredToDate(Date transferredToDate) {
		this.transferredToDate = transferredToDate;
	}



	@Override
	public void setHiddenFormFields() {
		// TODO Auto-generated method stub

	}



	public String getSearchType() {
		return searchType;
	}



	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	
}