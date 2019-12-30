package com.tcmis.client.report.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseInputBean;

/******************************************************************************
 * CLASSNAME: ReceiptDocumentInputBean <br>
 * @version: 1.0, Aug 5, 2005 <br>
 *****************************************************************************/


public class ReceiptDocumentViewerInputBean extends BaseInputBean {

	private String applicationId;
	private Date deliveryFromDate;
	private Date deliveryToDate;
	private String facilityId;
	private String facilityName;
	private String groupBy;
	private Date expireFromDate;
	private Date expireToDate;

	private String searchBy;
	private String searchText;
	private String searchType;
	private String submitSearch;
	private String facilityGroupIdSel;
	private String facilityGroupId;
	private String facilityIdSel;
	private String poRequired;
	private String companyId;
	private BigDecimal maxData;
	private String deliveriesCostData;

	//constructor
	public ReceiptDocumentViewerInputBean() {
	}

	public String getApplicationId() {
		return applicationId;
	}
	public Date getDeliveryFromDate() {
		return deliveryFromDate;
	}
	public Date getDeliveryToDate() {
		return deliveryToDate;
	}
	//getters
	public String getFacilityId() {
		return facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public String getGroupBy() {
		return groupBy;
	}

	public String getSearchBy() {
		return searchBy;
	}
	public String getSearchText() {
		return searchText;
	}
	public String getSearchType() {
		return searchType;
	}

	public String getSubmitSearch() {
		return submitSearch;
	}

	public boolean isGroupByMR() {
		return "mr".equalsIgnoreCase(groupBy);
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public void setDeliveryFromDate(Date deliveryFromDate) {
		this.deliveryFromDate = deliveryFromDate;
	}

	public void setDeliveryToDate(Date deliveryToDate) {
		this.deliveryToDate = deliveryToDate;
	}

	//setters
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	@Override
	public void setHiddenFormFields() {
        hiddenFormFieldList.add("companyId");
        hiddenFormFieldList.add("facilityGroupIdSel");
        hiddenFormFieldList.add("facilityGroupId");
        hiddenFormFieldList.add("facilityIdSel");
        hiddenFormFieldList.add("facilityId");
        hiddenFormFieldList.add("applicationId");
        hiddenFormFieldList.add("deliveryFromDate");
        hiddenFormFieldList.add("deliveryToDate");
        hiddenFormFieldList.add("expireFromDate");
        hiddenFormFieldList.add("expireToDate");
        hiddenFormFieldList.add("searchBy");
        hiddenFormFieldList.add("searchText");
        hiddenFormFieldList.add("searchType");
        hiddenFormFieldList.add("maxData");
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void setSubmitSearch(String submitSearch) {
		this.submitSearch = submitSearch;
	}

	public Date getExpireFromDate() {
		return expireFromDate;
	}

	public void setExpireFromDate(Date expireFromDate) {
		this.expireFromDate = expireFromDate;
	}

	public Date getExpireToDate() {
		return expireToDate;
	}

	public void setExpireToDate(Date expireToDate) {
		this.expireToDate = expireToDate;
	}

	public String getFacilityGroupIdSel() {
		return facilityGroupIdSel;
	}

	public void setFacilityGroupIdSel(String facilityGroupIdSel) {
		this.facilityGroupIdSel = facilityGroupIdSel;
	}

	public String getFacilityGroupId() {
		return facilityGroupId;
	}

	public void setFacilityGroupId(String facilityGroupId) {
		this.facilityGroupId = facilityGroupId;
	}

	public String getFacilityIdSel() {
		return facilityIdSel;
	}

	public void setFacilityIdSel(String facilityIdSel) {
		this.facilityIdSel = facilityIdSel;
	}

	public String getPoRequired() {
		return poRequired;
	}

	public void setPoRequired(String poRequired) {
		this.poRequired = poRequired;
	}

	public boolean isPoRequiredFacility() {
		return "Y".equals(poRequired);
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

    public BigDecimal getMaxData() {
        return maxData;
    }

    public void setMaxData(BigDecimal maxData) {
        this.maxData = maxData;
    }

    public String getDeliveriesCostData() {
        return deliveriesCostData;
    }

    public void setDeliveriesCostData(String deliveriesCostData) {
        this.deliveriesCostData = deliveriesCostData;
    }

    public boolean showDeliveriesCostData(){
	    return "Y".equals(deliveriesCostData);
    }
}