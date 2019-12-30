package com.tcmis.client.lmco.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.tcmis.common.framework.BaseDataBean;

/******************************************************************************
 * CLASSNAME: VocetMsdsSearchViewBean <br>
 * @version: 1.0, July 9, 2012 <br>
 *****************************************************************************/

public class VocetMsdsSearchViewBean extends BaseDataBean {

	private String companyId;
	private String facilityId;
	private BigDecimal materialId;
	private String customerMixtureNumber;
	private String customerMsdsNumber;
	private String vocetStatus;
	private String vocetCategoryId;
	private String customerMsdsDb;
	private String customerMsdsOrMixtureNo;
	private String changed;
	private String materialIdString;
    private String searchField;
    private String searchText;
    private String matchType;
    private String updatedByName;
    private Date updatedOn;
    private BigDecimal updatedBy;
    private String mixtureDesc;
    private String tradeName;


    //constructor
	public VocetMsdsSearchViewBean() {
	}

	public String getCompanyId() {
		return companyId;
	}


	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getCustomerMsdsDb() {
		return customerMsdsDb;
	}


	public void setCustomerMsdsDb(String customerMsdsDb) {
		this.customerMsdsDb = customerMsdsDb;
	}


	public String getCustomerMsdsNumber() {
		return customerMsdsNumber;
	}


	public void setCustomerMsdsNumber(String customerMsdsNumber) {
		this.customerMsdsNumber = customerMsdsNumber;
	}


	public String getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}


	public BigDecimal getMaterialId() {
		return materialId;
	}


	public void setMaterialId(BigDecimal materialId) {
		this.materialId = materialId;
	}


	public String getCustomerMixtureNumber() {
		return customerMixtureNumber;
	}

	public void setCustomerMixtureNumber(String customerMixtureNumber) {
		this.customerMixtureNumber = customerMixtureNumber;
	}

	public String getVocetCategoryId() {
		return vocetCategoryId;
	}


	public void setVocetCategoryId(String vocetCategoryId) {
		this.vocetCategoryId = vocetCategoryId;
	}


	public String getVocetStatus() {
		return vocetStatus;
	}


	public void setVocetStatus(String vocetStatus) {
		this.vocetStatus = vocetStatus;
	}

	public String getCustomerMsdsOrMixtureNo() {
		return customerMsdsOrMixtureNo;
	}

	public void setCustomerMsdsOrMixtureNo(String customerMsdsOrMixtureNo) {
		this.customerMsdsOrMixtureNo = customerMsdsOrMixtureNo;
	}

	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public String getMaterialIdString() {
		return materialIdString;
	}

	public void setMaterialIdString(String materialIdString) {
		this.materialIdString = materialIdString;
	}

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

	public String getUpdatedByName() {
		return updatedByName;
	}

	public void setUpdatedByName(String updatedByName) {
		this.updatedByName = updatedByName;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

    public BigDecimal getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(BigDecimal updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMixtureDesc() {
        return mixtureDesc;
    }

    public void setMixtureDesc(String mixtureDesc) {
        this.mixtureDesc = mixtureDesc;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }
}